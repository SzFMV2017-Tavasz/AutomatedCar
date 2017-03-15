package hu.oe.nik.szfmv17t.physics;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;


public class BrakeTest {

	@Test
	public void CalculateAccelerationReturns0(){
		Brake brakeTest = new Brake();
		double pedalPercentage = 0;
		
		double result = brakeTest.CalculateAcceleration(pedalPercentage);
		
		assertEquals(0, result, 0);
	}
	
	String mbd = "MAX_BRAKING_DECELERATION";
	
	@Test
	public void CalculateAccelerationReturnsMax(){
		Brake brakeTest = new Brake();
		double pedalPercentage = 1;
		Field maxDeceleration = null;
		try {
			maxDeceleration = Brake.class.getDeclaredField(mbd);
			maxDeceleration.setAccessible(true);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double expected = 0.0;
		try {
			expected = maxDeceleration.getDouble(brakeTest);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double result = brakeTest.CalculateAcceleration(pedalPercentage);
		
		assertEquals(expected, result, 0);
	}
	
	@Test
	public void CalculateAccelerationReturns50Percent(){
		Brake brakeTest = new Brake();
		double pedalPercentage = 0.5;
		Field maxDeceleration = null;
		try {
			maxDeceleration = Brake.class.getDeclaredField(mbd);
			maxDeceleration.setAccessible(true);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double expected = 0.0;
		try {
			expected = maxDeceleration.getDouble(brakeTest) * pedalPercentage;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double result = brakeTest.CalculateAcceleration(pedalPercentage);
		
		assertEquals(expected, result, 0);
	}
	
	@Test
	public void CalculateAccelerationBiggerReturnsBigger(){
		Brake brakeTest = new Brake();
		double pedalPercentageFirst = 0.8;
		double pedalPercentageSecond = 0.4;
		
		double resultFirst = brakeTest.CalculateAcceleration(pedalPercentageFirst);
		double resultSecond = brakeTest.CalculateAcceleration(pedalPercentageSecond);
		
		assertTrue(resultFirst < resultSecond); //negatív
	}
}
