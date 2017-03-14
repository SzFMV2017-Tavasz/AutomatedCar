package hu.oe.nik.szfmv17t.physics;

public class EngineBrake {

	float deceleration;
	
	private void setDecelerationByGear(int gear){
	deceleration=-10;	
	}

	public float calculateAcceleration(int gear, float gasPedalPercentage){
		double s = SpeedControl.GEAR_MAX_ACCELERATION[gear];
		double t = SpeedControl.GEAR_MAX_VELOCITY[gear];

		return 0;
	}
}

