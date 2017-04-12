package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.automatedcar.hmi.SteeringWheel;

//Right is positive, left is negative
public class SteeringControl {
	private final double MAX_STEERING_ANGLE = 45;
	private final int MILLISECONDSTOSECONDS = 1000;
	private final double WHEELBASE = 5.3;

	private int max;
	private long previousTime;
	private double steerAngle;

	public SteeringControl(){
		this.max = SteeringWheel.maxRight;
		this.previousTime = System.currentTimeMillis();
	}

	public double calculateAngle(double speed, int wheelState) {
		long deltaTime = System.currentTimeMillis() - this.previousTime;
		this.previousTime += deltaTime;

		double wheelAngle = calculateSteerAngle(wheelState);

		double turningCircleRadius = (WHEELBASE / Math.sin(wheelAngle));

		double angularSpeed = speed / turningCircleRadius;

		double timeInSec = deltaTime / (double) MILLISECONDSTOSECONDS;

		this.steerAngle += angularSpeed * timeInSec;

		return this.steerAngle;
	}

	private double calculateSteerAngle(int wheelState){
		if(wheelState != 0){
			return Math.toRadians(steer(wheelState));
		}
		return 0;
	}

	private double steer(int wheelState){
		return ((double)wheelState/max)*MAX_STEERING_ANGLE;
	}
}