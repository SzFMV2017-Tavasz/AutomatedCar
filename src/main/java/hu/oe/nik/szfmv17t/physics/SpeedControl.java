package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.Main;
import hu.oe.nik.szfmv17t.automatedcar.hmi.AutoGearStates;
import hu.oe.nik.szfmv17t.automatedcar.hmi.BrakePedal;
import hu.oe.nik.szfmv17t.automatedcar.hmi.GasPedal;

public class SpeedControl {
	/* m/s^2, R, P, 1, 2... */
	public static final double[] GEAR_MAX_ACCELERATION = new double[] { 6, 0, 10, 6, 4.5, 2.65, 1.6 };
	/* m/s, km/h: 0, 20, 45, 75, 110, 200 */
	public static final double[] GEAR_MAX_VELOCITY = new double[] { 4, 0, 5.5, 12.5, 20.8, 30.6, 55.5 };

	private final int SECOND_MULTIPLIER = 5;

	private GearControl gearControl;
	private Brake brake;
	private EngineBrake engineBrake;
	private ExternalForces externalForces;

	private double carWeight;

	private AutoGearStates autoGearState;
	private boolean autoGear;
	private int gearShift;
	private int gasPedal;
	private int brakePedal;
	private int maxGasPedal;
	private int maxBrakePedal;
	private double actualVelocity;

	public SpeedControl(double carWeight) {
		this.autoGear = false;
		this.gearControl = new GearControl(this.GEAR_MAX_VELOCITY);
		this.brake = new Brake();
		this.engineBrake = new EngineBrake();
		this.externalForces = new ExternalForces();

		this.carWeight = carWeight;
		this.maxGasPedal = GasPedal.MAX_STATE;
		this.maxBrakePedal = BrakePedal.MAX_STATE;
	}

	public double calculateVelocity() {
		if (this.autoGear) {
			this.gearShift = this.gearControl.actualGearState(this.autoGearState, this.gearShift, this.actualVelocity);
		}
		double sumAcceleration = sumAcceleration();
		actualVelocity += sumAcceleration * Main.CYCLE_PERIOD * SECOND_MULTIPLIER;
		return actualVelocity;
	}

	public void setCarWeight(double carWeight) {
		this.carWeight = carWeight;
	}

	public void setGearShift(int gearShift) {
		this.gearShift = gearShift;
	}

	public void setAutoGearState(AutoGearStates gearState) {
		this.autoGearState = gearState;
		this.autoGear = true;
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
		double gasPedalPercentage = calculatePedalPercentage(this.gasPedal, this.maxGasPedal);
		double brakePedalPercentage = calculatePedalPercentage(this.brakePedal, this.maxBrakePedal);

		double gasPedalAccelerationByGear = Acceleration.calculateAcceleration(this.gearShift, gasPedalPercentage);

		double brakeAcceleration = this.brake.calculateAcceleration(brakePedalPercentage);

		double engineBrakeAcceleration = this.engineBrake.calculateAcceleration(this.gearShift,
				(float) gasPedalPercentage,
				this.actualVelocity);

		double externalForcesAcceleration = this.externalForces.calculateAcceleration(this.carWeight,
				this.actualVelocity);

		double summedAcceleration = gasPedalAccelerationByGear + brakeAcceleration + engineBrakeAcceleration
				+ externalForcesAcceleration;

		return summedAcceleration;
	}
	
	private double calculatePedalPercentage(int actualValue, int maxValue) throws IllegalArgumentException {
		if (maxValue == 0) {
			throw new IllegalArgumentException();
		}

		return (double) actualValue / maxValue;
	}
}