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

	public KeyPanel() {
		this.setLayout(new GridLayout(6, 2));

		rightKeyLabel = new Label(HMI.STEER_RIGHT_KEY + " : Right");
		this.add(rightKeyLabel);

		leftKeyLabel = new Label(HMI.STEER_LEFT_KEY + " : Left");
		this.add(leftKeyLabel);

		increaseGasKeyLabel = new Label(HMI.INCRASE_GAS_KEY + " : Increase Gas");
		this.add(increaseGasKeyLabel);

		decreaseGasKeyLabel = new Label(HMI.DECRASE_GAS_KEY + " : Decrease Gas");
		this.add(decreaseGasKeyLabel);

		gearUpKeyLabel = new Label(HMI.GEAR_UP_KEY + " : Gear Up");
		this.add(gearUpKeyLabel);

		gearDownKeyLabel = new Label(HMI.GEAR_DOWN_KEY + " : Gear Down");
		this.add(gearDownKeyLabel);

		increaseBreakKeyLabel = new Label(HMI.INCRASE_BRAKE_KEY + " : Increase Break");
		this.add(increaseBreakKeyLabel);

		decreaseBreakKeyLabel = new Label(HMI.DECRASE_BRAKE_KEY + " : Decrease Break");
		this.add(decreaseBreakKeyLabel);

		indicateLeftLabel = new Label(HMI.INDICATE_LEFT + " : Indicate Left");
		this.add(indicateLeftLabel);

		indicateRightLabel = new Label(HMI.INDICATE_RIGHT + " : Indicate Right");
		this.add(indicateRightLabel);

		breakdownLabel = new Label(HMI.BREAKDOWN + " : Breakdown");
		this.add(breakdownLabel);
	}
}
