package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.automatedcar.hmi.SteeringWheel;

//Right is positive, left is negative
public class SteeringControl {
	
	private final double MAX_STEERING_ANGLE = 45;
	
	private int maxLeft;
	private int maxRight;
	
	public SteeringControl(){
		this.maxLeft = SteeringWheel.maxLeft;
		this.maxRight = SteeringWheel.maxRight;
	}
	
	public double calculateWheelAngle(int wheelState){
		if(wheelState > 0){
			return steerRight(wheelState);
		}
		else if (wheelState < 0){
			return steerLeft(wheelState);
		}
		else
			return 0;
	}
	
	private double steerRight(int wheelState){
		return ((double)wheelState/maxRight)*MAX_STEERING_ANGLE;
	}
	
	private double steerLeft(int wheelState){
		return ((double)wheelState/maxRight)*MAX_STEERING_ANGLE;
	}

}
