package hu.oe.nik.szfmv17t.physics;

public class ExternalForces {
	// http://www.engineeringtoolbox.com/rolling-friction-resistance-d_1303.html
	private static double constantDrag =  0.4257;
	private static double tirePressure = 2.3; //in bar
	private static double accelerationOfGravity = 9.81; //m/s2
	
	public double calculateAcceleration(double carWeight, double velocity) {		
		if (carWeight <= 0 || velocity <= 0)
			return 0;
		
		double forceDrag = calculateDragForce(velocity);
		
		double forceRollingResistance = calculateRollingResistance(carWeight, velocity);
				
		double finalExternalForce = -(forceDrag + forceRollingResistance);

		return finalExternalForce;
	}
	
	private double calculateDragForce(double velocity) {
		
		return constantDrag * Math.pow(velocity, 2);
		
	}
	
	private double calculateRollingResistance(double carWeight, double velocity) {
		double constantRollingCoefficient = 0;
		
		if (velocity != 0)
			constantRollingCoefficient = 0.005 + (1 / tirePressure) * (0.01 + 0.0095 * Math.pow(velocity / 100, 2));
		
		return constantRollingCoefficient * carWeight * accelerationOfGravity;
	}
}
