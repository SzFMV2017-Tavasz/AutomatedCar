package hu.oe.nik.szfmv17t.physics;


public final class Acceleration {

	private static double maxGearAcceleration;
	private Acceleration()
	{		
	}
	
	//linear proportion
	//gears: -1..5
	public static double calculateAcceleration(int gearShift,double gaspedalPercentage)
	{
		setMaxGearAccelerationByGear(gearShift);
		return gaspedalPercentage*maxGearAcceleration;
	}
	private static void setMaxGearAccelerationByGear(int gearShift)
	{
		maxGearAcceleration=SpeedControl.GEAR_MAX_ACCELERATION[gearShift+1];
	}	
}
