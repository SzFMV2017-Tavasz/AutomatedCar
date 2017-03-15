package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.Main;
import hu.oe.nik.szfmv17t.automatedcar.hmi.BrakePedal;
import hu.oe.nik.szfmv17t.automatedcar.hmi.GasPedal;

public class SpeedControl {
	/* m/s^2 */
	public static final double[] GEAR_MAX_ACCELERATION = new double[] { 6,0, 10, 6, 4.5, 2.65, 1.6 };
	/* m/s, km/h: 0, 20, 45, 75, 110, 200 */
	public static final double[] GEAR_MAX_VELOCITY = new double[] { 4,0, 5.5, 12.5, 20.8, 30.6, 55.5 };

	private final int SECOND_MULTIPLIER = 1000;

	private Brake brake;

	private double carWeight;
	private int gearShift;
	private int gasPedal;
	private int brakePedal;
	private int maxGasPedal;
	private int maxBrakePedal;
	private double actualVelocity;

	public SpeedControl(double carWeight) {
		brake = new Brake();
		this.carWeight = carWeight;
		this.maxGasPedal = GasPedal.MAX_STATE;
		this.maxBrakePedal = BrakePedal.MAX_STATE;
	}

	public double calculateVelocity() {
		double sumAcceleration = sumAcceleration();
		actualVelocity = sumAcceleration * Main.CYCLE_PERIOD * SECOND_MULTIPLIER;
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
		double gasPedalAccelerationByGear = Acceleration.CalculateAcceleration(this.gearShift,
				calculatePedalPercentage(this.gasPedal, this.maxGasPedal));

		double brakeDeceleration = brake
				.CalculateAcceleration(calculatePedalPercentage(this.brakePedal, this.maxBrakePedal));

		double sumAcceleration = gasPedalAccelerationByGear + brakeDeceleration;

		return sumAcceleration;
	}
	
	private double calculatePedalPercentage(int actualValue, int maxValue) throws IllegalArgumentException {
		if (maxValue == 0) {
			throw new IllegalArgumentException();
		}

		return (double) actualValue / maxValue;
	}
}