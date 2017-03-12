package hu.oe.nik.szfmv17t.physics;

public class SpeedControl {
	/* m/s^2 */
	public static final double[] GEAR_MAX_ACCELERATION = new double[] { 0, 10, 6, 4.5, 2.65, 1.6 };
	/* m/s, km/h: 0, 20, 45, 75, 110, 200 */
	public static final double[] GEAR_MAX_VELOCITY = new double[] { 0, 5.5, 12.5, 20.8, 30.6, 55.5 };

	private final int SECOND_MULTIPLIER = 1000;

	private double carWeight;
	private int gearShift;
	private int gasPedal;
	private int brakePedal;
	private int maxGasPedal;
	private int maxBrakePedal;
	private double actualVelocity;


	public SpeedControl(double carWeight) {
		this.carWeight = carWeight;
	}

	public double calculateVelocity() {
		return actualVelocity;
	}

	public void setCarWeight(double carWeight) {
		this.carWeight = carWeight;
	}

	public void setGearShift(int gearShift) {
		this.gearShift = gearShift;
	}

	public void setGasPedal(int gasPedal) {
		this.gasPedal = gasPedal;
	}

	public void setBrakePedal(int brakePedal) {
		this.brakePedal = brakePedal;
	}

	public void setMaxGasPedal(int maxGasPedal) {
		this.maxGasPedal = maxGasPedal;
	}

	public void setMaxBrakePedal(int maxBrakePedal) {
		this.maxBrakePedal = maxBrakePedal;
	}

	private double sumAcceleration() {
		return 0;
	}
	
	private double calculatePedalPercentage(int actualValue, int maxValue) throws IllegalArgumentException {
		if (maxValue == 0) {
			throw new IllegalArgumentException();
		}

		return (double) actualValue / maxValue;
	}
}