package hu.oe.nik.szfmv17t.physics;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExternalForcesTest {
	
	@Test
	public void carWeightNull() {
		ExternalForces newCar = new ExternalForces();
		assertEquals(0,newCar.calculateAcceleration(0,100),0);
	}
		
	@Test
	public void velocityIsNull() {
		ExternalForces newCar = new ExternalForces();
		assertEquals(0,newCar.calculateAcceleration(1500,0),0);
	}
	
	@Test
	public void carWeightIsNegative() {
		ExternalForces newCar = new ExternalForces();
		assertEquals(0,newCar.calculateAcceleration(-100, 100),0);
	}
	
	@Test
	public void velocityIsNegative() {
		ExternalForces newCar = new ExternalForces();
		assertEquals(0,newCar.calculateAcceleration(100, -100),0);
	}
	
	@Test
	public void lowWeightHighWeight() {
		ExternalForces lowWeightCar = new ExternalForces();
		ExternalForces highWeightCar = new ExternalForces();
		
		double lowerForce = Math.abs(lowWeightCar.calculateAcceleration(1000, 10));
		double higherForce = Math.abs(highWeightCar.calculateAcceleration(2000, 10));
		
		assertTrue(lowerForce < higherForce);
	}
	
	@Test
	public void lowerSpeedHigherSpeed() {
		ExternalForces lowerSpeed = new ExternalForces();
		ExternalForces higherSpeed = new ExternalForces();
		
		double lowerForce = Math.abs(lowerSpeed.calculateAcceleration(1000, 100));
		double higherForce = Math.abs(higherSpeed.calculateAcceleration(1000, 250));
		
		assertTrue(lowerForce < higherForce);
	}
	
}
