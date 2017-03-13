package hu.oe.nik.szfmv17t.physics;


public final class Acceleration {

	private Acceleration()
	{		
	}
	private static double maxGearAcceleration;
	private static double maxGearVelocity;
	
	private static double[] gear_max_acceleration={-10,0,20,18,15,10,5};
	private static double[] gear_max_velocity={-10,0,20,18,15,10,5};
	
	public static double CalculateAcceleration(int gearShift,double gaspedalPercentage)
	{
		setMaxGearAccelerationByGear(gearShift);
		return gaspedalPercentage/100*maxGearAcceleration;
	}
	private static void setMaxGearAccelerationByGear(int gearShift)
	{
		//maxGearAcceleration=SpeedControl.GEAR_MAX_ACCELERATION[GEARSHIFT+1];
		maxGearAcceleration=gear_max_acceleration[gearShift+1];
	}
	private static void setMaxGearVelocityByGear(int gearShift)
	{
		//maxGearVelocity=SpeedControl.GEAR_MAX_VELOCITY[GEARSHIFT+1];
		maxGearVelocity=gear_max_velocity[gearShift+1];
	}
}
