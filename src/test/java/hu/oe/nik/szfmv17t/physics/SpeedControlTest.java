package hu.oe.nik.szfmv17t.physics;

import org.junit.Before;

public class SpeedControlTest {
	private SpeedControl speedControl;
	private final double carWeight = 2000;

	@Before
	public void setUp() {
		speedControl = new SpeedControl(carWeight);
	}
}
