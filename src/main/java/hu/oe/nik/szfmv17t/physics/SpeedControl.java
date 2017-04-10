package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.Main;
import hu.oe.nik.szfmv17t.automatedcar.hmi.AutoGearStates;
import hu.oe.nik.szfmv17t.automatedcar.hmi.BrakePedal;
import hu.oe.nik.szfmv17t.automatedcar.hmi.GasPedal;

public class SpeedControl {
	/* m/s^2, R, P, 1, 2... */
	public static final double[] GEAR_MAX_ACCELERATION = new double[] { 3, 0, 8, 6, 4.55, 2.6, 1.6 };
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
		this.gearShift = 1;
	}

	public double calculateVelocity() {
		if (this.autoGear) {
			this.gearShift = this.gearControl.actualGearState(this.autoGearState, this.gearShift, this.actualVelocity);
		}
		double sumAcceleration = sumAcceleration();
		
		double calculatedVelocity = actualVelocity + (sumAcceleration * Main.CYCLE_PERIOD / 1000);

		calculatedVelocity = preventNegativeVelocity(calculatedVelocity);
		
		calculatedVelocity = preventPositiveVelocityInReverse(calculatedVelocity);
		
		actualVelocity = minOrMaxSpeed(calculatedVelocity);
		
		//System.out.println("Gear: " + (this.gearShift - 1));
		
		return actualVelocity;
	}

	private double minOrMaxSpeed(double velocity) {
		if(velocity > SpeedControl.GEAR_MAX_VELOCITY[SpeedControl.GEAR_MAX_VELOCITY.length-1]){
			return SpeedControl.GEAR_MAX_VELOCITY[SpeedControl.GEAR_MAX_VELOCITY.length-1];
		}
		else if(velocity < -SpeedControl.GEAR_MAX_VELOCITY[0]){
			return -SpeedControl.GEAR_MAX_VELOCITY[0];
		}
		return velocity;
	}

	private double preventPositiveVelocityInReverse(double velocity) {
		if(this.gearShift == 0 && this.brakePedal != 0 && velocity > 0){
			return 0;
		}
		else{
			return velocity;
		}
	}

	private double preventNegativeVelocity(double velocity) {
		if(this.gearShift != 0 && velocity < 0){
			return 0;
		}
		else{
			return velocity;
		}
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

		double externalForces = this.externalForces.calculateAcceleration(this.carWeight,this.actualVelocity);
		double externalAcceleration = (double)externalForces / this.carWeight; 
		
		//reverting accelerations while in reverse
		if(this.gearShift == 0){
			gasPedalAccelerationByGear *= -1;
			brakeAcceleration *= -1;
			engineBrakeAcceleration *= -1;
			externalAcceleration *= -1;
		}

		double summedAcceleration = gasPedalAccelerationByGear + brakeAcceleration + engineBrakeAcceleration + externalAcceleration;

		return summedAcceleration;
	}
	
	private double calculatePedalPercentage(int actualValue, int maxValue) throws IllegalArgumentException {
		if (maxValue == 0) {
			throw new IllegalArgumentException();
		}

		return (double) actualValue / maxValue;
	}
}