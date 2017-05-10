package hu.oe.nik.szfmv17t.physics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Test;

public class SteeringControlTest {
	
	/*@Test
	/*public void CalculateWheelAngleReturnsZero(){
		SteeringControl steeringControl = new SteeringControl();
		double expected = 0;
		double result;
		int wheelState = 0;
		
		result = steeringControl.calculateWheelAngle(wheelState);
	
		assertEquals(expected, result, 0);
	}
	
	@Test
	public void CalculateWheelAngleRightTurn(){
		SteeringControl steeringControl = new SteeringControl();
		double result;
		int wheelState = 50;
		
		result = steeringControl.calculateWheelAngle(wheelState);
	
		assertTrue(result > 0);
	}
	
	@Test
	public void CalculateWheelAngleLeftTurn(){
		SteeringControl steeringControl = new SteeringControl();
		double result;
		int wheelState = -50;
		
		result = steeringControl.calculateWheelAngle(wheelState);
	
		assertTrue(result < 0);
	}
	
	@Test
	public void CalculateWheelAngleReturnsCorrectValue(){
		SteeringControl steeringControl = new SteeringControl();
		double result;
		double expected = 0;
		int wheelState = 50;
		Field maxAngle = null;
		try {
			maxAngle = SteeringControl.class.getDeclaredField("MAX_STEERING_ANGLE");
			maxAngle.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		result = Math.toDegrees(steeringControl.calculateWheelAngle(wheelState));
		try {
			expected = maxAngle.getDouble(steeringControl) * 0.5;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	
		assertEquals(expected, result, 0);
	}*/
}
