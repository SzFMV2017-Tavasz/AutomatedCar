package hu.oe.nik.szfmv17t.physics;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SteeringControlTest {
	private double speed = 0;
	@Test
	public void CalculateWheelAngleReturnsZero(){
		SteeringControl steeringControl = new SteeringControl();
		double expected = 0;
		double result;
		int wheelState = 0;
		
		result = steeringControl.calculateSteeringAngle(wheelState, speed);
	
		assertEquals(expected, result, 0);
	}
	
	@Test
	public void CalculateWheelAngleRightTurn(){
		SteeringControl steeringControl = new SteeringControl();
		double result;
		int wheelState = 50;
		
		result = steeringControl.calculateSteeringAngle(wheelState, speed);
	
		assertTrue(result > 0);
	}
	
	@Test
	public void CalculateWheelAngleLeftTurn(){
		SteeringControl steeringControl = new SteeringControl();
		double result;
		int wheelState = -50;
		
		result = steeringControl.calculateSteeringAngle(wheelState, speed);
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = Math.toDegrees(steeringControl.calculateSteeringAngle(wheelState,speed));
		try {
			expected = maxAngle.getDouble(steeringControl) * 0.5;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		assertEquals(expected, result, 0);
	}
}
