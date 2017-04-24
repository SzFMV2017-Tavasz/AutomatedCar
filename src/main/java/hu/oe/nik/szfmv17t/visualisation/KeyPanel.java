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

	public KeyPanel() {
		this.setLayout(new GridLayout(8, 2));

		rightKeyLabel = new Label(HMI.STEER_RIGHT_KEY + " : Right");
		this.add(rightKeyLabel);

		leftKeyLabel = new Label(HMI.STEER_LEFT_KEY + " : Left");
		this.add(leftKeyLabel);

		increaseGasKeyLabel = new Label(HMI.INCRASE_GAS_KEY + " : Increase Gas");
		this.add(rightKeyLabel);

		decreaseGasKeyLabel = new Label(HMI.DECRASE_GAS_KEY + " : Decrease Gas");
		this.add(leftKeyLabel);

		gearUpKeyLabel = new Label(HMI.GEAR_UP_KEY + " : Gear Up");
		this.add(rightKeyLabel);

		gearDownKeyLabel = new Label(HMI.GEAR_DOWN_KEY + " : Gear Down");
		this.add(leftKeyLabel);

		increaseBreakKeyLabel = new Label(HMI.INCRASE_BRAKE_KEY + " : Increase Break");
		this.add(increaseBreakKeyLabel);

		decreaseBreakKeyLabel = new Label(HMI.DECRASE_BRAKE_KEY + " : Decrease Break");
		this.add(decreaseBreakKeyLabel);

	}
}
