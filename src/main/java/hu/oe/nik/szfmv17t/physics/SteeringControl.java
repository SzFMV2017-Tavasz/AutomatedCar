package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.automatedcar.hmi.SteeringWheel;
import hu.oe.nik.szfmv17t.environment.utils.Position;
import hu.oe.nik.szfmv17t.environment.utils.Vector2d;

//Right is positive, left is negative
public class SteeringControl {

	private final double MAX_STEERING_ANGLE = 45;
	private static final int MILLISECONDSTOSECONDS = 1000;

	private int max;
	private double steerAngle;
	private long previousTime;

	public SteeringControl(){
		this.max = SteeringWheel.maxRight;
		this.previousTime = System.currentTimeMillis();
	}

	public double calculateAngle(Position carPosition, double speed, int wheelState){
			double wheelAngle = calculateSteerAngle(wheelState);
			long deltaTime = System.currentTimeMillis() - this.previousTime;
			this.previousTime += deltaTime;
			double omega = speed / (carPosition.getHeight() / Math.sin(wheelAngle)) * (deltaTime/ MILLISECONDSTOSECONDS);

			this.steerAngle += omega;

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