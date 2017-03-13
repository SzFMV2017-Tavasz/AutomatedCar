package hu.oe.nik.szfmv17t.physics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AccelerationTest {

	
	@Test
	public void acceleration_0_0() {
		assertEquals(0,Acceleration.CalculateAcceleration(0, 0),0);
	}
	
	@Test
	public void acceleration_0_10() {
		assertEquals(0,Acceleration.CalculateAcceleration(0, 10),0);
	}
	
	@Test
	public void acceleration_0_N() {
		for (int i = 0; i < 100; i++) {
			assertEquals(0,Acceleration.CalculateAcceleration(0, i),0);
		}		
	}
	
	@Test
	public void acceleration_1_0() {
		assertEquals(0,Acceleration.CalculateAcceleration(1, 0),0);
	}
	
	@Test
	public void acceleration_1_100() {
		
		double expected=20;
		double actual=Acceleration.CalculateAcceleration(1, 100);		
		assertEquals(expected,actual,0);
			
	}
	
	@Test
	public void acceleration_1_N() {
		for (int i = 0; i < 100; i++) {
			double div= (double)i/100;
			double expected=20*div;
			double actual=Acceleration.CalculateAcceleration(1, i);			
			assertEquals(expected,actual,0);	
		}		
	}
	
	@Test
	public void acceleration_R_100() {		
		assertEquals(-10,Acceleration.CalculateAcceleration(-1, 100),0);		
	}
	
	@Test
	public void acceleration_all() {
		double[] gear_max_acceleration = { -10, 0, 20, 18, 15, 10, 5, 4 };
		for (int j = -1; j < 6; j++) {
			double actGearMaxAcc = gear_max_acceleration[j + 1];
			for (int i = 0; i < 100; i++) {
				double percentage = (double) i / 100;
				double expected = actGearMaxAcc * percentage;
				double actual = Acceleration.CalculateAcceleration(j, i);
				assertEquals(expected, actual, 0);
			}
		}
	}
}
