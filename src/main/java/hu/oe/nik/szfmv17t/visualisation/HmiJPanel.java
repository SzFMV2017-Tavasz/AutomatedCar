package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.automatedcar.hmi.HMI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by SebestyenMiklos on 2017. 03. 05..
 */
public class HmiJPanel extends JPanel {

    private static HMI hmi;

    private Label labelGasPedalValue;
    private Label gasPedalValue;

    private Label labelSpeed;
    private Label speed;

    private Label labelBrakePedalValue;
    private Label brakePedalValue;


    private Label labelGearState;
    private Label gearStateValue;

    private Label labelSteeringWheelValue;
    private Label steeringWheelValue;

    public static void setHmi(HMI hmi){
        HmiJPanel.hmi = hmi;
    }

    public HmiJPanel() {

        labelGasPedalValue = new Label("Gas(%):");
        this.add(labelGasPedalValue);
        gasPedalValue = new Label(String.valueOf(0));
        this.add(gasPedalValue);

        labelSpeed = new Label("Speed: ");
        this.add(labelSpeed);
        speed = new Label(String.valueOf(0));
        this.add(speed);

        labelBrakePedalValue = new Label("Break(%):");
        this.add(labelBrakePedalValue);
        brakePedalValue = new Label(String.valueOf(0));
        this.add(brakePedalValue);

        labelSteeringWheelValue = new Label("Wheel");
        this.add(labelSteeringWheelValue);
        steeringWheelValue = new Label(String.valueOf(0));
        this.add(steeringWheelValue);

        labelGearState = new Label("Gear: ");
        this.add(labelGearState);
        gearStateValue = new Label("P");
        this.add(gearStateValue);




    }

    public static HMI getHmi() {
        return hmi;
    }


}
