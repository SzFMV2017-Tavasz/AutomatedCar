package hu.oe.nik.szfmv17t.physics;


public final class Acceleration {

	private Acceleration()
	{		
	}
	private static double maxGearAcceleration;
	private static double maxGearVelocity;
	
	//linear proportion
	public static double CalculateAcceleration(int gearShift,double gaspedalPercentage)
	{
		setMaxGearAccelerationByGear(gearShift);
		return gaspedalPercentage*maxGearAcceleration;
	}
	private static void setMaxGearAccelerationByGear(int gearShift)
	{
		maxGearAcceleration=SpeedControl.GEAR_MAX_ACCELERATION[gearShift];
	}
	private static void setMaxGearVelocityByGear(int gearShift)
	{
		maxGearVelocity=SpeedControl.GEAR_MAX_VELOCITY[gearShift];		
	}
}
