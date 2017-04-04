package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.automatedcar.hmi.SteeringWheel;

public class SteeringControl {
	
	private final double MAX_STEERING_ANGLE = 45;
	
	private int max;
	private double steerAngle;
	
	public SteeringControl(){
		this.max = SteeringWheel.maxRight;
	}

	public double calculateSteeringAngle(int wheelState){
		if(wheelState != 0){
			this.steerAngle = Math.toRadians(steer(wheelState));
			return this.steerAngle;
		}
		return 0;
	}
	
	private double steer(int wheelState){
		return ((double)wheelState/max)*MAX_STEERING_ANGLE;
	}
}
