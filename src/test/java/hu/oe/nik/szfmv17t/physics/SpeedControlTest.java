package hu.oe.nik.szfmv17t.physics;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class SpeedControlTest {

	// TODO mock private method call (sumAcceleration) in calculateVelocity
	@Test
	public void CalculateVelocityTestReturn0() {
		// arrange
		double carWeight = 2000;

		SpeedControl speedControl = new SpeedControl(carWeight);

		// act
		double result = speedControl.calculateVelocity();
		
		// assert
		assertEquals(0, result, 0);
	}

	// TODO mock methods called in SumAcceleration
	@Test
	public void SumAccelerationReturn0() {
		// arrange
		double carWeight = 2000;
		SpeedControl speedControl = new SpeedControl(carWeight);

		Method method = null;
		try {
			method = SpeedControl.class.getDeclaredMethod("sumAcceleration");
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// act
		double result = -1;
		try {
			result = (double) method.invoke(speedControl);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// assert
		assertEquals(0, result, 0);
	}

	@Test
	public void CalculatePedalPercentageActual20Max100Return20percent() {
		// arrange
		double carWeight = 2000;
		SpeedControl speedControl = new SpeedControl(carWeight);

		Method method = null;
		try {
			method = SpeedControl.class.getDeclaredMethod("calculatePedalPercentage", int.class, int.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		// act
		double result = -1;
		try {
			result = (double) method.invoke(speedControl, 20, 100);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// assert
		assertEquals(0.2, result, 0);
	}

	@Test
	public void CalculatePedalPercentageMax0IllegalArgumentException() {
		// arrange
		double carWeight = 2000;
		SpeedControl speedControl = new SpeedControl(carWeight);

		Method method = null;
		try {
			method = SpeedControl.class.getDeclaredMethod("calculatePedalPercentage", int.class, int.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// act
		boolean illegelArgumentExceptionThrown = false;
		try {
			method.invoke(speedControl, 20, 0);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			if (IllegalArgumentException.class == e.getTargetException().getClass()) {
				illegelArgumentExceptionThrown = true;
			}
		}

		// assert
		assertEquals(true, illegelArgumentExceptionThrown);
	}
}
