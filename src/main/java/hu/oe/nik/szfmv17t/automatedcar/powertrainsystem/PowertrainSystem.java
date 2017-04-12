package hu.oe.nik.szfmv17t.automatedcar.powertrainsystem;

import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.automatedcar.hmi.AutoGearStates;
import hu.oe.nik.szfmv17t.physics.SpeedControl;
import hu.oe.nik.szfmv17t.physics.SteeringControl;

public class PowertrainSystem extends SystemComponent {

    // signal id table for PowertrainSystem
    public static final int DEMO = 0;
    public static final int SMI_BrakePedal = 10;
    public static final int SMI_Gaspedal = 11;
    public static final int SMI_Gear = 12;
    public static final int SMI_SteeringWheel = 13;
    public static final int ULTRASONIC_SENSOR_ID = 14;
    public static final int Modelling = 20;
    public static final int Physics = 30;
    public static final int Physics_Speed = 31;
    public static final int Physics_Gear = 32;
    public static final int Visualisation = 40;

    // physics
    private SpeedControl speedControl;
    private SteeringControl steeringControl;

	// Output signals
	// Only these are available trough getters
	private int wheelState = 0;

    // Output signals
    // Only these are available trough getters

    public PowertrainSystem(double height, double width, double carWeight) {
        super();

        this.speedControl = new SpeedControl(carWeight);
        this.steeringControl = new SteeringControl();
    }

	@Override
	public void loop() {
		// TODO write this
	}

	@Override
	public void receiveSignal(Signal s) {
		switch(s.getId()) {
		// Handle demo signal
		case SMI_BrakePedal:
			int brakePedal = (int) s.getData();
			this.speedControl.setBrakePedal(brakePedal);
			break;
		case SMI_Gaspedal:
			int gasPedal = (int) s.getData();
			this.speedControl.setGasPedal(gasPedal);
			break;
		case SMI_Gear:
			AutoGearStates gear = AutoGearStates.values()[(int) s.getData()];
			this.speedControl.setAutoGearState(gear);
			break;
		case SMI_SteeringWheel:
			this.wheelState = (int)s.getData();
			break;
		case ULTRASONIC_SENSOR_ID:
			// System.out.println("Ultrasonic sensor: " + s.getData());
			break;
		default:
			// ignore other signals
		}
	}

	public double getSteeringAngle(double carVelocity) {
		return steeringControl.calculateAngle(carVelocity, this.wheelState);
	}

    public double getVelocity() {
        return speedControl.calculateVelocity();
    }
}
