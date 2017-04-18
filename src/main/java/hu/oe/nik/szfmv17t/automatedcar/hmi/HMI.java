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
    public static final char GEAR_UP_KEY = 'l';
    public static final char GEAR_DOWN_KEY = 'k';
    public static final char INCRASE_BRAKE_KEY = 'h';
    public static final char DECRASE_BRAKE_KEY = 'j';
    public static final char INDICATE_LEFT = 'u';
    public static final char BREAKDOWN = 'i';
    public static final char INDICATE_RIGHT = 'o';

    public static final int BUTTON_PRESSING_LENGTH_FOR_PTTM = 5;
    public static final int DURATION_FOR_PTTM = 100;

    private int previousSteeringWheelState = 0;
    private int previousGasPedalState = 0;
    private int previousBrakePedalState = 0;
    private AutoGearStates previousGearStickState = AutoGearStates.P;
    private DirectionIndicatorStates previousDirection = DirectionIndicatorStates.Default;

    SteeringWheel steeringWheel;
    GasPedal gasPedal;
    BrakePedal brakePedal;
    GearStick gearStick;
    private boolean keyPressHandled;
    DirectionIndicator directionIndicator;
    double carspeed;

    public void setCarspeed(double carspeed) {
        this.carspeed = carspeed * 3.6;
    }

    public HMI() {
        super();
        keyPressHandled = false;
        gasPedal = new GasPedal();
        gearStick = new GearStick();
        directionIndicator = new DirectionIndicator();
        brakePedal = new BrakePedal(directionIndicator);
        steeringWheel = new SteeringWheel(directionIndicator);
    }

    @Override
    public void loop() {
        sendSteeringWheelSignal();
        sendGasPedalSignal();
        sendBrakePedalSignal();
        sendGearStickSignal();
        if(carspeed > 0 && steeringWheel.isSteerReleased()){
            steeringWheel.steerRelease();
        }
    }

    private void sendSteeringWheelSignal() {
        if (steeringWheel.getState() != previousSteeringWheelState) {
            VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.SMI_SteeringWheel, steeringWheel.getState()));
            previousSteeringWheelState = steeringWheel.getState();
        }
    }

    private void sendGasPedalSignal() {
        if (gasPedal.getState() != previousGasPedalState) {
            VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.SMI_Gaspedal, gasPedal.getState()));
            previousGasPedalState = gasPedal.getState();
        }
    }

    private void sendBrakePedalSignal() {
        if (brakePedal.getState() != previousBrakePedalState) {
            VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.SMI_BrakePedal, brakePedal.getState()));
            previousBrakePedalState = brakePedal.getState();
        }
    }

    private void sendGearStickSignal() {
        if (gearStick.getAutoGearState() != previousGearStickState) {
            VirtualFunctionBus
                    .sendSignal(new Signal(PowertrainSystem.SMI_Gear, gearStick.getAutoGearState().ordinal()));
            previousGearStickState = gearStick.getAutoGearState();
        }

    }

    @Override
    public void receiveSignal(Signal s) {
        // System.out.println("HMI received signal: " + s.getId() + " data: " +
        // s.getData());
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        // System.out.println("keyTyped:" + keyEvent.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {


        //System.out.println("keyPressed:" + keyEvent.getKeyChar());
        char key = keyEvent.getKeyChar();
        switch (key) {
            case STEER_LEFT_KEY:
                steeringWheel.steerLeft();
                break;
            case STEER_RIGHT_KEY:
                steeringWheel.steerRight();
                break;
        }
        if (keyPressHandled) {
            return;
        }
        switch (key) {
            case INCRASE_GAS_KEY:
                gasPedal.start();
                break;
            case DECRASE_GAS_KEY:
                gasPedal.start();
                break;
            case INCRASE_BRAKE_KEY:
                brakePedal.start();
                break;
            case DECRASE_BRAKE_KEY:
                brakePedal.start();
                break;
        }
        keyPressHandled = true;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        //System.out.println("keyReleased:" + keyEvent.getKeyChar());
        keyPressHandled = false;
        char key = keyEvent.getKeyChar();
        switch (key) {
            case STEER_LEFT_KEY:
                steeringWheel.setSteerReleased(true);
                break;
            case STEER_RIGHT_KEY:
                steeringWheel.setSteerReleased(true);
                break;
            case INCRASE_GAS_KEY:
                gasPedal.acceleration();
                break;
            case DECRASE_GAS_KEY:
                gasPedal.deceleration();
                break;
            case INCRASE_BRAKE_KEY:
                brakePedal.braking();
                break;
            case DECRASE_BRAKE_KEY:
                brakePedal.releasingBrake();
                break;
            case GEAR_UP_KEY:
                gearStick.gearUpAutomatic();
                break;
            case GEAR_DOWN_KEY:
                gearStick.gearDownAutomatic();
                break;
            case INDICATE_LEFT:
                directionIndicator.IndicatingLeft();
                break;
            case INDICATE_RIGHT:
                directionIndicator.IndicatingRight();
                break;
            case BREAKDOWN:
                directionIndicator.IndicatingBreakdown();
                break;
        }
    }

    public int getGaspedalValue() {
        return gasPedal.getState();
    }

    public int getBrakepedalValue() {
        return brakePedal.getState();
    }

    public int getSteeringWheelPosition() {
        return steeringWheel.getState();
    }

    public AutoGearStates getGearStickPosition() {
        return gearStick.getAutoGearState();
    }

    public DirectionIndicatorStates getDirectionIndicatorState() {
        return directionIndicator.GetDirectionIndicatorState();
    }

    public double getSpeed() {
        return carspeed;
    }
}
