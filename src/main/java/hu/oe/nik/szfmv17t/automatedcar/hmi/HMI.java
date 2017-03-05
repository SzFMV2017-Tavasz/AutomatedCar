package hu.oe.nik.szfmv17t.automatedcar.hmi;

import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by SebestyenMiklos on 2017. 02. 26..
 */
public class HMI extends SystemComponent implements KeyListener{

    public HMI() {
        super();

    }

    @Override
    public void loop() {
        //TODO:send break gas and gear signals
        System.out.println("HMI loop");


    }

    @Override
    public void receiveSignal(Signal s) {
        System.out.println("HMI received signal: " + s.getId() +" data: " + s.getData());
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        System.out.println("keyTyped:" + keyEvent.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        System.out.println("keyPressed:" + keyEvent.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        System.out.println("keyReleased:" + keyEvent.getKeyChar());
    }
}
