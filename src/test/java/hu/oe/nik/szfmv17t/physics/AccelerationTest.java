package hu.oe.nik.szfmv17t.physics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AccelerationTest {

	public static final double[] GEAR_MAX_ACCELERATION = new double[] { 3, 0, 8, 6, 4.55, 2.6, 1.6 };

	
	@Test
	public void acceleration0x0() {
		assertEquals(0,Acceleration.calculateAcceleration(0, 0),0);
	}
	
	@Test
	public void acceleration0x10() {
		assertEquals(3*0.1,Acceleration.calculateAcceleration(0, 0.1),0);
	}
	
	@Test
	public void acceleration0xN() {
		for (double percentage = 0; percentage < 1; percentage+=0.01) {
			assertEquals(3*percentage,Acceleration.calculateAcceleration(0, percentage),0);
		}		
	}
	
	@Test
	public void acceleration1x0() {
		assertEquals(0,Acceleration.calculateAcceleration(1, 0),0);
	}
	
	@Test
	public void acceleration1x100() {
		
		double expected=0;
		double actual=Acceleration.calculateAcceleration(1, 1);		
		assertEquals(expected,actual,0);
			
	}
	
	@Test
	public void acceleration1xN() {
		for (double percentage = 0; percentage < 1; percentage+=0.01) {			
			double expected=0*percentage;
			double actual=Acceleration.calculateAcceleration(1, percentage);			
			assertEquals(expected,actual,0);	
		}		
	}
	
	@Test
	public void accelerationRxN() {
		for (double percentage = 0; percentage < 1; percentage+=0.01) {			
			double expected=3*percentage;
			double actual=Acceleration.calculateAcceleration(0, percentage);			
			assertEquals(expected,actual,0);	
		}		
	}
	
	
	@Test
	public void accelerationAllHundred() {
		double[] gear_max_acceleration = SpeedControl.GEAR_MAX_ACCELERATION;
		for (int j = 0; j < gear_max_acceleration.length; j++) {
			double actGearMaxAcc = gear_max_acceleration[j];
			for (double percentage = 0; percentage < 1; percentage+=0.01) {				
				double expected = actGearMaxAcc * percentage;
				double actual = Acceleration.calculateAcceleration(j, percentage);
				assertEquals(expected, actual, 0);
			}
		}
	}
}
