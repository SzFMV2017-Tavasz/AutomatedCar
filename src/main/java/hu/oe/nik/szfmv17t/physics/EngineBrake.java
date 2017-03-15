package hu.oe.nik.szfmv17t.physics;

public class EngineBrake {
	
	public double calculateAcceleration(int gear, float gasPedalPercentage, double actualVelocity) {
		
		double deceleration = 0;

		if (gear >0 && gasPedalPercentage == 0 && actualVelocity>0) {

			double startInterval = SpeedControl.GEAR_MAX_VELOCITY[gear];
			double endInterval = SpeedControl.GEAR_MAX_VELOCITY[gear+1];
			double gearVelocityInterval = endInterval - startInterval;
			
			int ratio=(gear+1)*4;
			
			if (actualVelocity >= startInterval && actualVelocity <= startInterval + gearVelocityInterval * 0.3)
				deceleration = -gearVelocityInterval / ratio;
			else if (actualVelocity > startInterval + gearVelocityInterval * 0.3
					&& actualVelocity <= startInterval + gearVelocityInterval * 0.7)
				deceleration = -gearVelocityInterval / ratio;
			else
				deceleration = -gearVelocityInterval / ratio;
		}
		else if(gear==-1 && gasPedalPercentage == 0 && actualVelocity>0 ){
			double interval = SpeedControl.GEAR_MAX_VELOCITY[0];
			if(actualVelocity >0 && actualVelocity <= interval*0.3)
				deceleration= -interval/20;
			else if(actualVelocity >interval*0.3 && actualVelocity <= interval*0.7)
				deceleration = -interval/15;
			else
				deceleration=-interval/12;
		}
		return deceleration;
	}
}
