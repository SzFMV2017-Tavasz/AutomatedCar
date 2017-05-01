package hu.oe.nik.szfmv17t.visualisation;

import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JPanel;

import hu.oe.nik.szfmv17t.automatedcar.hmi.HMI;

public class KeyPanel extends JPanel {
	private Label rightKeyLabel;
	private Label leftKeyLabel;

	private Label gearUpKeyLabel;
	private Label gearDownKeyLabel;

	private Label increaseGasKeyLabel;
	private Label decreaseGasKeyLabel;

	private Label increaseBreakKeyLabel;
	private Label decreaseBreakKeyLabel;

	private Label indicateLeftLabel;
	private Label breakdownLabel;
	private Label indicateRightLabel;

	private Label searchingLabel;
	private Label parkingLabel;

	public KeyPanel() {
		// this.setLayout(new GridLayout(6, 2));

		rightKeyLabel = new Label(Character.toUpperCase(HMI.STEER_RIGHT_KEY) + " : Right");
		this.add(rightKeyLabel);

		leftKeyLabel = new Label(Character.toUpperCase(HMI.STEER_LEFT_KEY) + " : Left");
		this.add(leftKeyLabel);

		increaseGasKeyLabel = new Label(Character.toUpperCase(HMI.INCRASE_GAS_KEY) + " : Increase Gas");
		this.add(increaseGasKeyLabel);

		decreaseGasKeyLabel = new Label(Character.toUpperCase(HMI.DECRASE_GAS_KEY) + " : Decrease Gas");
		this.add(decreaseGasKeyLabel);

		gearUpKeyLabel = new Label(Character.toUpperCase(HMI.GEAR_UP_KEY) + " : Gear Up");
		this.add(gearUpKeyLabel);

		gearDownKeyLabel = new Label(Character.toUpperCase(HMI.GEAR_DOWN_KEY) + " : Gear Down");
		this.add(gearDownKeyLabel);

		increaseBreakKeyLabel = new Label(Character.toUpperCase(HMI.INCRASE_BRAKE_KEY) + " : Increase Break");
		this.add(increaseBreakKeyLabel);

		decreaseBreakKeyLabel = new Label(Character.toUpperCase(HMI.DECRASE_BRAKE_KEY) + " : Decrease Break");
		this.add(decreaseBreakKeyLabel);

		indicateLeftLabel = new Label(Character.toUpperCase(HMI.INDICATE_LEFT) + " : Indicate Left");
		this.add(indicateLeftLabel);

		indicateRightLabel = new Label(Character.toUpperCase(HMI.INDICATE_RIGHT) + " : Indicate Right");
		this.add(indicateRightLabel);

		breakdownLabel = new Label(Character.toUpperCase(HMI.BREAKDOWN) + " : Breakdown");
		this.add(breakdownLabel);

		searchingLabel = new Label(Character.toUpperCase(HMI.SEARCHING_TOGGLE) + " : Searching");
		this.add(searchingLabel);

		parkingLabel = new Label(Character.toUpperCase(HMI.PARKING_TOGGLE) + " : Parking");
		this.add(parkingLabel);
	}
}
