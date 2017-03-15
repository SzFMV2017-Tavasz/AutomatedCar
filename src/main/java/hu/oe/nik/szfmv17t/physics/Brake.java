package hu.oe.nik.szfmv17t.physics;

public class Brake {

	private final double MAX_BRAKING_DECELERATION = -9.5; // m/s^2
	
	public double calculateAcceleration(double brakePedalPercentage){
		double acceleration = 0;
		acceleration = MAX_BRAKING_DECELERATION * brakePedalPercentage;
		return acceleration;
	}

}
