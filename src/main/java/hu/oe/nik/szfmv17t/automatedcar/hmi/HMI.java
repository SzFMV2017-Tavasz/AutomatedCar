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
	public static final char GAS_UP_KEY = 'w';
	public static final char BREAK_DOWN_KEY = 's';

	public int previousSteeringWheelState = 0;
	public int previousGasPedalState = 0;

	HmiTimer hmiTimerForPedalToTheMetal;
	HmiTimer hmiTimerForSlowing;
	SteeringWheel steeringWheel;
	GasPedal gasPedal;

	public HMI() {
		super();
		steeringWheel = new SteeringWheel();
		hmiTimerForPedalToTheMetal = new HmiTimer();
		hmiTimerForSlowing = new HmiTimer();
		gasPedal = new GasPedal();
		hmiTimerForSlowing.Start();
	}

	@Override
	public void loop() {
		//TODO:send break gas and gear signals
		sendSteeringWheelSignal();
		sendGasPedalSignal();
		gasPedalRelease();

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

	//
	private void gasPedalRelease() {
		long duration = hmiTimerForSlowing.getDuration();
		int pedalState = gasPedal.getState();
		if(duration != 0 && duration % 100 == 0 && pedalState != 0) {
			gasPedal.GasPedalRelease();
			System.out.println("decrease gas");
		}
	}

	@Override
	public void receiveSignal(Signal s) {
		System.out.println("HMI received signal: " + s.getId() + " data: " + s.getData());
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		System.out.println("keyPressed:" + keyEvent.getKeyChar());
		char key = keyEvent.getKeyChar();
		switch(key) {
			case STEER_LEFT_KEY:
				steeringWheel.start();
			break;
			case STEER_RIGHT_KEY:
				steeringWheel.start();
			break;
			case GAS_UP_KEY:
				hmiTimerForPedalToTheMetal.Start();
			//hmiTimerForSlowing.Start();
			break;
			case BREAK_DOWN_KEY:
			//Break
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		System.out.println("keyReleased:" + keyEvent.getKeyChar());
		keyEvent.getSource();
		char key = keyEvent.getKeyChar();
		switch(key) {
			case STEER_LEFT_KEY:
				steeringWheel.steerLeft();
			break;
			case STEER_RIGHT_KEY:
				steeringWheel.steerRight();
			break;
			case GAS_UP_KEY:
				long duration = hmiTimerForPedalToTheMetal.getDuration();
				if(duration > 1000)
					gasPedal.PedalToTheMetal();
				else
					gasPedal.Accelerate();
			break;
			case BREAK_DOWN_KEY:
			//Break
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
