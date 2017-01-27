package hu.oe.nik.Visualisation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Example taken from ZetCode: http://zetcode.com/tutorials/javaswingtutorial/firstprograms/

public class GUI extends JFrame {

	public GUI() {
		initUI();
	}

	private void initUI() {

		JButton quitButton = new JButton("Quit");

		quitButton.addActionListener((ActionEvent event) -> {
			System.exit(0);
		});

		createLayout(quitButton);

		setTitle("AutomatedCar");
		setSize(1024, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void createLayout(JComponent... arg) {

		Container pane = getContentPane();
		GroupLayout gl = new GroupLayout(pane);
		pane.setLayout(gl);

		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addComponent(arg[0])
		);

		gl.setVerticalGroup(gl.createSequentialGroup()
				.addComponent(arg[0])
		);
	}

}