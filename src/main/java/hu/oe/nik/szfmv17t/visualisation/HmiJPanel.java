package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.automatedcar.hmi.HMI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by SebestyenMiklos on 2017. 03. 05..
 */
public class HmiJPanel extends JPanel {

    private static HMI hmi;

    Label labelGasPedalValue;
    Label gasPedalValue;

    Label labelSpeed;
    Label speed;

    Label labelBrakePedalValue;
    Label brakePedalValue;


    Label labelGearState;
    Label gearStateValue; //Auto or Manual

    Label labelGear;
    Label gear; // 1-5 or PD....

    public static void setHmi(HMI hmi){
        HmiJPanel.hmi = hmi;
    }

    public HmiJPanel() {

        labelGasPedalValue = new Label("Gas(%):");
        this.add(labelGasPedalValue);
        gasPedalValue = new Label(String.valueOf(0));
        this.add(gasPedalValue);

    }

    public static HMI getHmi() {
        return hmi;
    }


}
