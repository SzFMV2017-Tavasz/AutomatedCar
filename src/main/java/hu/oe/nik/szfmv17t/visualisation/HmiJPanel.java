package hu.oe.nik.szfmv17t.visualisation;

import java.awt.Label;

import javax.swing.JPanel;

import hu.oe.nik.szfmv17t.automatedcar.hmi.HMI;

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

	private Label labelIndicationValue;
	private Label indicationValue;

	public static void setHmi(HMI hmi) {
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
		steeringWheelValue = new Label(String.valueOf(hmi.getSteeringWheelPosition()));
		this.add(steeringWheelValue);

		labelGearState = new Label("Gear: ");
		this.add(labelGearState);
		gearStateValue = new Label(String.valueOf(hmi.getGearStickPosition()));
		this.add(gearStateValue);

		labelIndicationValue = new Label("Indicating: ");
		this.add(labelIndicationValue);
		indicationValue = new Label(String.valueOf(hmi.getDirectionIndicatorState()));
		this.add(indicationValue);

	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (steeringWheelValue != null) {
			steeringWheelValue.setText(String.valueOf(hmi.getSteeringWheelPosition()));
		}
		if (gasPedalValue != null) {
			gasPedalValue.setText(String.valueOf(hmi.getGaspedalValue()));
		}
        if (brakePedalValue != null) {
			brakePedalValue.setText(String.valueOf(hmi.getBrakepedalValue()));
		}
		if(gearStateValue != null){
			gearStateValue.setText(String.valueOf(hmi.getGearStickPosition()));
		}
		if(indicationValue != null){
			indicationValue.setText(String.valueOf(hmi.getDirectionIndicatorState()));
		}
	}

	public static HMI getHmi() {
		return hmi;
	}

}
