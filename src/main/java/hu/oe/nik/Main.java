package hu.oe.nik;

import hu.oe.nik.Visualisation.GUI;

import java.awt.*;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			GUI ex = new GUI();
			ex.setVisible(true);
		});
	}
}
