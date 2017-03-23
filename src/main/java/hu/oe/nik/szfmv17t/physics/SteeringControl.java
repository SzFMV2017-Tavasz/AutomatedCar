package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.automatedcar.hmi.SteeringWheel;

//Right is positive, left is negative
public class SteeringControl {
	
	private final double MAX_STEERING_ANGLE = 45;
	
	private int max;
	private double width;
	private double height;
	
	public SteeringControl(double width, double height){
		this.max = SteeringWheel.maxLeft;
		this.width = width;
		this.height = height;
	}
	
	public double calculateWheelAngle(int wheelState){
		if(wheelState != 0)
			return Math.toRadians(steer(wheelState));
		else
			return 0;
	}
	
	private double steer(int wheelState){
		return ((double)wheelState/max)*MAX_STEERING_ANGLE;
	}
}
