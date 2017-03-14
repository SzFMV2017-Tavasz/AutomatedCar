package hu.oe.nik.szfmv17t.physics;

public class EngineBrake {

	float deceleration;
	
	private void setDecelerationByGear(int gear){
	deceleration=-10;	
	}

	public float calculateAcceleration(int gear, float gasPedalPercentage){
		return 0;
	}
}

