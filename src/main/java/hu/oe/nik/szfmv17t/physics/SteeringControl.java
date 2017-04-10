package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.automatedcar.hmi.SteeringWheel;

public class SteeringControl {
	
	private final double MAX_STEERING_ANGLE = 45;
	
	private int max;
	private double steerAngle;
	private final double wheelBase = 5.3;
    private long previousTime;

	public SteeringControl(){
		this.max = SteeringWheel.maxRight;
		previousTime = System.currentTimeMillis();
	}

	public double calculateSteeringAngle(int wheelState, double velocity){
		this.steerAngle += steer(wheelState,velocity);
		return this.steerAngle;
	}

	private double steer(int wheelState, double speed){

        long deltaTime = System.currentTimeMillis() - this.previousTime;

		double wheelAngle = ((double)wheelState/max)*MAX_STEERING_ANGLE;

		double turningCircleRadius = wheelBase/Math.sin(Math.toRadians(wheelAngle));

		double angularSpeedinRadPerSec = speed/turningCircleRadius;

        double timeInSec = (deltaTime/1000.0);
        double turningAngleInCycle = (angularSpeedinRadPerSec * timeInSec);

        this.previousTime += deltaTime;
        return turningAngleInCycle;

	}
}
