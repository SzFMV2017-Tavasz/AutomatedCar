package hu.oe.nik.szfmv17t.visualisation;

import java.awt.*;

import javax.swing.JPanel;

import hu.oe.nik.szfmv17t.automatedcar.hmi.HMI;
import hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor.UltrasonicController;

/**
 * Created by SebestyenMiklos on 2017. 03. 05..
 */
public class HmiJPanel extends JPanel {

	private final Color AEB_ALERT_COLOR = Color.yellow;
	private final Color ACA_ALERT_COLOR = Color.red;

	private static HMI hmi;
	private static UltrasonicController usc;

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

	private Label labelAutomaticParkingValue;
	private Label automaticParkingValue;

	private Label labelSpaceFoundValue;
	private Label spaceFoundValue;

    private Label labelAvoidableCollisionAlert;

    public static void setHmi(HMI hmi, UltrasonicController usc) {
		HmiJPanel.hmi = hmi;
		HmiJPanel.usc = usc;
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

		labelAutomaticParkingValue = new Label("Automatic Parking: ");
		this.add(labelAutomaticParkingValue);
		automaticParkingValue = new Label(String.valueOf(hmi.getParkingState()));
		this.add(automaticParkingValue);

		labelSpaceFoundValue = new Label("Parking space found: ");
		this.add(labelSpaceFoundValue);
		spaceFoundValue = new Label(String.valueOf(usc.getSpaceFound()));
		this.add(spaceFoundValue);

		labelAvoidableCollisionAlert = new Label("POSSIBLE COLLISION!!!");
		labelAvoidableCollisionAlert.setVisible(false);
		labelAvoidableCollisionAlert.setForeground(ACA_ALERT_COLOR);
		this.add(labelAvoidableCollisionAlert);
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
		if(automaticParkingValue != null){
			automaticParkingValue.setText(String.valueOf(hmi.getParkingState()));
		}
		if(spaceFoundValue != null){
			spaceFoundValue.setText(String.valueOf(usc.getSpaceFound()));
		}
		if(speed != null){
			speed.setText(String.valueOf(Math.round(hmi.getSpeed())+" km/h"));
		}
		if(hmi.isAEBAlertIsOn()){
			this.setBackground(AEB_ALERT_COLOR);
			for (Component component : this.getComponents()) {
				component.setBackground(AEB_ALERT_COLOR);
			}
		}
		else
		{
			this.setBackground(null);
			for (Component component : this.getComponents()) {
				component.setBackground(null);
			}
		}
		if(hmi.isAvoidableCollisionAlert()){
			this.labelAvoidableCollisionAlert.setVisible(true);
		}
		else{
			this.labelAvoidableCollisionAlert.setVisible(false);
		}
	}

	public static HMI getHmi() {
		return hmi;
	}

}
