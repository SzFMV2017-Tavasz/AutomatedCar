package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.Main;
import hu.oe.nik.szfmv17t.automatedcar.hmi.AutoGearStates;
import hu.oe.nik.szfmv17t.automatedcar.hmi.BrakePedal;
import hu.oe.nik.szfmv17t.automatedcar.hmi.GasPedal;

public class SpeedControl {
	/* m/s^2, R, P, 1, 2... */
	public static final double[] GEAR_MAX_ACCELERATION = new double[] { 3, 0, 5, 3, 2.25, 1.3, 0.8 };
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
		actualVelocity += sumAcceleration * Main.CYCLE_PERIOD/1000 * SECOND_MULTIPLIER;

		//Preventing going backwards by braking
		if(this.autoGearState != AutoGearStates.R && actualVelocity < 0){
			actualVelocity = 0;
		}
		
		//Preventing going forwards while in reverse
		if(this.autoGearState == AutoGearStates.R && this.brakePedal != 0 && actualVelocity > 0){
			actualVelocity = 0;
		}
		
		//reaching max speed
		if(actualVelocity > this.GEAR_MAX_VELOCITY[this.GEAR_MAX_VELOCITY.length-1]){
			actualVelocity = this.GEAR_MAX_VELOCITY[this.GEAR_MAX_VELOCITY.length-1];
		}
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
		
		//either brake or gas
		if(brakeAcceleration < 0){
			gasPedalAccelerationByGear = 0;
		}

		double engineBrakeAcceleration = this.engineBrake.calculateAcceleration(this.gearShift,
				(float) gasPedalPercentage,
				this.actualVelocity);

		double externalForcesAcceleration = this.externalForces.calculateAcceleration(this.carWeight,
				this.actualVelocity);
		
		//reverting accelerations while in reverse
		if(this.autoGearState == AutoGearStates.R){
			gasPedalAccelerationByGear *= -1;
			brakeAcceleration *= -1;
			engineBrakeAcceleration *= -1;
		}

		double summedAcceleration = gasPedalAccelerationByGear + brakeAcceleration + engineBrakeAcceleration;
				//+ externalForcesAcceleration;

		return summedAcceleration;
	}
	
	private double calculatePedalPercentage(int actualValue, int maxValue) throws IllegalArgumentException {
		if (maxValue == 0) {
			throw new IllegalArgumentException();
		}

		return (double) actualValue / maxValue;
	}
}