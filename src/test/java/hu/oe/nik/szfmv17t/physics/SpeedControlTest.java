package hu.oe.nik.szfmv17t.physics;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import hu.oe.nik.szfmv17t.environment.domain.WorldObjectState;
import org.junit.Assert;
import org.junit.Test;

import hu.oe.nik.szfmv17t.automatedcar.hmi.AutoGearStates;

public class SpeedControlTest {
	private final String calculatePedalPercentageMethodName = "calculatePedalPercentage";
	private final String sumAccelerationMethodName = "sumAcceleration";
	private final String minOrMaxSpeedMetHodName = "minOrMaxSpeed";
	private final String gearShiftFieldName = "gearShift";

	// TODO mock private method call (sumAcceleration) in calculateVelocity
	@Test
	public void calculateVelocityTestReturn0() {
		// arrange
		double carWeight = 2000;

		SpeedControl speedControl = new SpeedControl(carWeight);

		// act
		double result = speedControl.calculateVelocity(WorldObjectState.Untouched);
		
		// assert
		assertEquals(0, result, 0);
	}

	// TODO mock private method call (sumAcceleration) in calculateVelocity
	@Test
	public void calculateVelocityTestAutoGear() {
		// arrange
		double carWeight = 2000;
		SpeedControl speedControl = new SpeedControl(carWeight);
		speedControl.setAutoGearState(AutoGearStates.D);

		// act
		speedControl.calculateVelocity(WorldObjectState.Untouched);
		Field gearField = null;
		try {
			gearField = speedControl.getClass().getDeclaredField(gearShiftFieldName);
			gearField.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int result = 0;
		if (gearField != null) {
			try {
				result = (int) gearField.get(speedControl);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// assert
		assertEquals(2, result);
	}

	// TODO mock methods called in SumAcceleration
	@Test
	public void sumAccelerationReturn0() {
		// arrange
		double carWeight = 2000;
		SpeedControl speedControl = new SpeedControl(carWeight);

		Method method = null;
		try {
			method = SpeedControl.class.getDeclaredMethod(sumAccelerationMethodName);
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
	public void sumAccelerationTestGear0NegativAcceleration() {
		// arrange
		double carWeight = 2000;
		int gasPedalValue = 20;
		SpeedControl speedControl = new SpeedControl(carWeight);
		speedControl.setGearShift(0);
		speedControl.setGasPedal(gasPedalValue);

		Method method = null;
		try {
			method = SpeedControl.class.getDeclaredMethod(sumAccelerationMethodName);
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
		Assert.assertTrue(result < 0);
	}

	@Test
	public void calculatePedalPercentageActual20Max100Return20percent() {
		// arrange
		double carWeight = 2000;
		SpeedControl speedControl = new SpeedControl(carWeight);

		Method method = null;
		try {
			method = SpeedControl.class.getDeclaredMethod(calculatePedalPercentageMethodName, int.class, int.class);
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
	public void calculatePedalPercentageMax0IllegalArgumentException() {
		// arrange
		double carWeight = 2000;
		SpeedControl speedControl = new SpeedControl(carWeight);

		Method method = null;
		try {
			method = SpeedControl.class.getDeclaredMethod(calculatePedalPercentageMethodName, int.class, int.class);
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

	@Test 
	public void minOrMaxSpeedTestVelocity250ReturnMaxVelocityDefinedInSpeedControl() {
		// arrange
		double velocityValue = 250;
		double carWeight = 2000;
		SpeedControl speedControl = new SpeedControl(carWeight);

		Method method = null;
		try {
			method = SpeedControl.class.getDeclaredMethod(minOrMaxSpeedMetHodName, double.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// act
		double result = 0;
		try {
			result = (double) method.invoke(speedControl, velocityValue);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// assert
		assertEquals(SpeedControl.GEAR_MAX_VELOCITY[SpeedControl.GEAR_MAX_VELOCITY.length - 1], result, 0);
	}

	@Test
	public void minOrMaxSpeedTestVelocityNegative250ReturnMinVelocityDefinedInSpeedControl() {
		// arrange
		double velocityValue = 250;
		double carWeight = 2000;
		SpeedControl speedControl = new SpeedControl(carWeight);

		Method method = null;
		try {
			method = SpeedControl.class.getDeclaredMethod(minOrMaxSpeedMetHodName, double.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// act
		double result = 0;
		try {
			result = (double) method.invoke(speedControl, -velocityValue);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// assert
		assertEquals(-SpeedControl.GEAR_MAX_VELOCITY[0], result, 0);
	}
}