package hu.oe.nik.szfmv17t.automatedcar.hmi;

import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.automatedcar.powertrainsystem.PowertrainSystem;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by SebestyenMiklos on 2017. 02. 26..
 */
public class HMI extends SystemComponent implements KeyListener, IHmiDisplay{

    public static final char STEER_LEFT_KEY = 'a';
    public static final char STEER_RIGHT_KEY = 'd';
    public int previousSteeringWheelState = 0;

    SteeringWheel steeringWheel;

    public HMI(){
        super();
        steeringWheel = new SteeringWheel();
    }

    @Override
    public void loop() {
        //TODO:send break gas and gear signals
        //System.out.println("HMI loop");
        sendSteeringWheelSignal();


    }

    private void sendSteeringWheelSignal(){
        if(steeringWheel.getState() != previousSteeringWheelState) {
            VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.SMI_SteeringWheel, steeringWheel.getState()));
            previousSteeringWheelState = steeringWheel.getState();
        }
    }

    @Override
    public void receiveSignal(Signal s) {
        System.out.println("HMI received signal: " + s.getId() +" data: " + s.getData());
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
                steeringWheel.steerLeft();
                break;
            case STEER_RIGHT_KEY:
                steeringWheel.steerRight();
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        System.out.println("keyReleased:" + keyEvent.getKeyChar());
    }

    public int getGaspedalValue() {
        return 10;
    }

    @Override
    public int getSteeringWheelPosition() {
        return steeringWheel.getState();
    }
}
