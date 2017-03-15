package hu.oe.nik.szfmv17t.physics;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EngineBrakeTest {

	@Test
	public void calculateAccelerationShouldReturnNegativeAccelerationWhenCalledWithOneToFiveGearsTest1(){
		EngineBrake eb = new EngineBrake();
		double dec = eb.calculateAcceleration(1, 0, 2);
		assertEquals(-0.5,dec,0);
	}
	
	@Test
	public void calculateAccelerationShouldReturnNehativeAccelerationWhenCalledWithOneToFiveGearsTest2(){
		EngineBrake eb = new EngineBrake();
		double dec = eb.calculateAcceleration(1, 0, 4);
		assertEquals(-0.6875,dec,0);
	}
	
	@Test
	public void calculateAccelerationShouldReturnNegativeAccelerationWhenCalledWithMinusOneGear(){
		EngineBrake eb = new EngineBrake();
		double acc = eb.calculateAcceleration(-1, 0, 4);
		double expected = -((double)4/12);
		assertEquals(expected,acc,0);
	}
	
}
