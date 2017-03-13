package hu.oe.nik.szfmv17t.physics;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class SpeedControlTest {

	// TODO mock private method call (sumAcceleration) in calculateVelocity
	@Test
	public void CalculateVelocityTest_Return0() {
		// arrange
		double carWeight = 2000;

		SpeedControl speedControl = new SpeedControl(carWeight);

		// act
		double result = speedControl.calculateVelocity();
		
		// assert
		assertEquals(0, result, 0);
	}

	@Test
	public void SumAcceleration_Return0() {
		// arrange
		double carWeight = 2000;
		SpeedControl speedControl = new SpeedControl(carWeight);

		Method method = null;
		try {
			method = SpeedControl.class.getDeclaredMethod("sumAcceleration");
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// act
		double result = -1;
		try {
			result = (double) method.invoke(speedControl);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// assert
		assertEquals(0, result, 0);
	}

	@Test
	public void CalculatePedalPercentage_Actual20Max100_Return20percent() {
		// arrange
		double carWeight = 2000;
		SpeedControl speedControl = new SpeedControl(carWeight);

		Method method = null;
		try {
			method = SpeedControl.class.getDeclaredMethod("calculatePedalPercentage", int.class, int.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// act
		double result = -1;
		try {
			result = (double) method.invoke(speedControl, 20, 100);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// assert
		assertEquals(0.2, result, 0);
	}

	@Test
	public void CalculatePedalPercentage_Max0_ThrowDivideByZeroException() {
		// arrange
		double carWeight = 2000;
		SpeedControl speedControl = new SpeedControl(carWeight);

		Method method = null;
		try {
			method = SpeedControl.class.getDeclaredMethod("calculatePedalPercentage", int.class, int.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// act
		boolean illegelArgumentExceptionThrown = false;
		try {
			method.invoke(speedControl, 20, 0);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			if (IllegalArgumentException.class == e.getTargetException().getClass()) {
				illegelArgumentExceptionThrown = true;
			}
		}

		// assert
		assertEquals(true, illegelArgumentExceptionThrown);
	}
}
