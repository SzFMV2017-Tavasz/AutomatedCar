package hu.oe.nik.szfmv17t.automatedcar.hmi;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.automatedcar.powertrainsystem.PowertrainSystem;

/**
 * Created by SebestyenMiklos on 2017. 02. 26..
 */
public class HMI extends SystemComponent implements KeyListener {

	public static final char STEER_LEFT_KEY = 'a';
	public static final char STEER_RIGHT_KEY = 'd';
	public static final char INCRASE_GAS_KEY = 'w';
	public static final char DECRASE_GAS_KEY = 's';
	public static final int BUTTON_PRESSING_LENGTH_FOR_PTTM = 5;
	public static final int DURATION_FOR_PTTM = 100;

	private int previousSteeringWheelState = 0;
	private int previousGasPedalState = 0;

	SteeringWheel steeringWheel;
	GasPedal gasPedal;
    private boolean keyPressHandled;

    public HMI() {
		super();
		keyPressHandled = false;
		steeringWheel = new SteeringWheel();
		gasPedal = new GasPedal();
	}

	@Override
	public void loop() {
		sendSteeringWheelSignal();
		sendGasPedalSignal();
	}

	private void sendSteeringWheelSignal() {
		if(steeringWheel.getState() != previousSteeringWheelState) {
			VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.SMI_SteeringWheel, steeringWheel.getState()));
			previousSteeringWheelState = steeringWheel.getState();
		}
	}

	private void sendGasPedalSignal() {
		if(gasPedal.getState() != previousGasPedalState) {
			VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.SMI_Gaspedal, gasPedal.getState()));
			previousGasPedalState = gasPedal.getState();
		}

	}

	@Override
	public void receiveSignal(Signal s) {
		//System.out.println("HMI received signal: " + s.getId() + " data: " + s.getData());
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
        //System.out.println("keyTyped:" + keyEvent.getKeyChar());
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {

        if(keyPressHandled){
	        return;
        }
		System.out.println("keyPressed:" + keyEvent.getKeyChar());
		char key = keyEvent.getKeyChar();
		switch(key) {
			case STEER_LEFT_KEY:
				steeringWheel.start();
			break;
			case STEER_RIGHT_KEY:
				steeringWheel.start();
			break;
			case INCRASE_GAS_KEY:
				gasPedal.start();
			break;
			case DECRASE_GAS_KEY:
			    gasPedal.start();
			break;
		}
		keyPressHandled = true;
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		System.out.println("keyReleased:" + keyEvent.getKeyChar());
		keyPressHandled = false;
		char key = keyEvent.getKeyChar();
		switch(key) {
			case STEER_LEFT_KEY:
				steeringWheel.steerLeft();
			break;
			case STEER_RIGHT_KEY:
				steeringWheel.steerRight();
			break;
			case INCRASE_GAS_KEY:
                gasPedal.acceleration();
			break;
			case DECRASE_GAS_KEY:
			    gasPedal.deceleration();
			break;
		}
	}

	public int getGaspedalValue() {
		return gasPedal.getState();
	}

	public int getSteeringWheelPosition() {
		return steeringWheel.getState();
	}
}
