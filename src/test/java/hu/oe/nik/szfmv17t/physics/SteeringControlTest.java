package hu.oe.nik.szfmv17t.physics;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.Test;

public class SteeringControlTest {

	@Test
	public void CalculateWheelAngleReturnsZero(){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = steeringControl.calculateWheelAngle(wheelState);
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
