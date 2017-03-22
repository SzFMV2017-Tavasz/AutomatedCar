package hu.oe.nik.szfmv17t.physics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import hu.oe.nik.szfmv17t.automatedcar.hmi.AutoGearStates;
import junit.framework.Assert;

public class GearControlTest {
	private final String switchGearDependOnSpeedMethodName = "switchGearDependOnSpeed";

	private double[] maxGearVelocity;
	private GearControl gearControl;


	@Before
	public void setUp() {
		this.maxGearVelocity = new double[] { 4, 0, 5.5, 12.5, 20.8 };
		this.gearControl = new GearControl(this.maxGearVelocity);
	}

	@Test
	public void actualGearStateTestRReturn0() {
		// arrange
		AutoGearStates autoGearState = AutoGearStates.R;
		int gearShift = 0;
		double actualVelocity = 0;

		// act
		int result = this.gearControl.actualGearState(autoGearState, gearShift, actualVelocity);

		// assert
		Assert.assertEquals(0, result);
	}

	@Test
	public void actualGearStateTestPReturn1() {
		// arrange
		AutoGearStates autoGearState = AutoGearStates.P;
		int gearShift = 0;
		double actualVelocity = 0;

		// act
		int result = this.gearControl.actualGearState(autoGearState, gearShift, actualVelocity);

		// assert
		Assert.assertEquals(1, result);
	}

	@Test
	public void actualGearStateTestNReturn1() {
		// arrange
		AutoGearStates autoGearState = AutoGearStates.N;
		int gearShift = 0;
		double actualVelocity = 0;

		// act
		int result = this.gearControl.actualGearState(autoGearState, gearShift, actualVelocity);

		// assert
		Assert.assertEquals(1, result);
	}

	@Test
	public void actualGearStateTestDReturn2() {
		// arrange
		AutoGearStates autoGearState = AutoGearStates.D;
		int gearShift = 0;
		double actualVelocity = 0;

		// act
		int result = this.gearControl.actualGearState(autoGearState, gearShift, actualVelocity);

		// assert
		Assert.assertEquals(2, result);
	}

	@Test
	public void switchGearDependOnSpeedShift0Return2() {
		// arrange
		int gearShift = 0;
		double actualVelocity = 0;

		Method method = null;
		try {
			method = GearControl.class.getDeclaredMethod(switchGearDependOnSpeedMethodName, int.class, double.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// act
		int result = -1;
		try {
			result = (int) method.invoke(this.gearControl, gearShift, actualVelocity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// assert
		Assert.assertEquals(2, result);
	}

	@Test
	public void switchGearDependOnSpeedShift1Return2() {
		// arrange
		int gearShift = 1;
		double actualVelocity = 0;

		Method method = null;
		try {
			method = GearControl.class.getDeclaredMethod(switchGearDependOnSpeedMethodName, int.class, double.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// act
		int result = -1;
		try {
			result = (int) method.invoke(this.gearControl, gearShift, actualVelocity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// assert
		Assert.assertEquals(2, result);
	}

	@Test
	public void switchGearDependOnSpeedShift2Return2() {
		// arrange
		int gearShift = 2;
		double actualVelocity = 0;

		Method method = null;
		try {
			method = GearControl.class.getDeclaredMethod(switchGearDependOnSpeedMethodName, int.class, double.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// act
		int result = -1;
		try {
			result = (int) method.invoke(this.gearControl, gearShift, actualVelocity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// assert
		Assert.assertEquals(2, result);
	}

	@Test
	public void switchGearDependOnSpeedShift2MaxVelocityReturn3() {
		// arrange
		int gearShift = 2;
		double actualVelocity = this.maxGearVelocity[gearShift];

		Method method = null;
		try {
			method = GearControl.class.getDeclaredMethod(switchGearDependOnSpeedMethodName, int.class, double.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// act
		int result = -1;
		try {
			result = (int) method.invoke(this.gearControl, gearShift, actualVelocity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// assert
		Assert.assertEquals(3, result);
	}

	@Test
	public void switchGearDependOnSpeedShift3MaxVelocityReturn4() {
		// arrange
		int gearShift = 3;
		double actualVelocity = this.maxGearVelocity[gearShift];

		Method method = null;
		try {
			method = GearControl.class.getDeclaredMethod(switchGearDependOnSpeedMethodName, int.class, double.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// act
		int result = -1;
		try {
			result = (int) method.invoke(this.gearControl, gearShift, actualVelocity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// assert
		Assert.assertEquals(4, result);
	}

	@Test
	public void switchGearDependOnSpeedShift4MaxVelocityReturn4() {
		// arrange
		int gearShift = 4;
		double actualVelocity = this.maxGearVelocity[gearShift];

		Method method = null;
		try {
			method = GearControl.class.getDeclaredMethod(switchGearDependOnSpeedMethodName, int.class, double.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// act
		int result = -1;
		try {
			result = (int) method.invoke(this.gearControl, gearShift, actualVelocity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// assert
		Assert.assertEquals(4, result);
	}

	@Test
	public void switchGearDependOnSpeedShift4MinVelocityReturn3() {
		// arrange
		int gearShift = 4;
		double actualVelocity = this.maxGearVelocity[gearShift - 1];

		Method method = null;
		try {
			method = GearControl.class.getDeclaredMethod(switchGearDependOnSpeedMethodName, int.class, double.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// act
		int result = -1;
		try {
			result = (int) method.invoke(this.gearControl, gearShift, actualVelocity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// assert
		Assert.assertEquals(3, result);
	}

	@Test
	public void switchGearDependOnSpeedShift3MinVelocityReturn2() {
		// arrange
		int gearShift = 3;
		double actualVelocity = this.maxGearVelocity[gearShift - 1];

		Method method = null;
		try {
			method = GearControl.class.getDeclaredMethod(switchGearDependOnSpeedMethodName, int.class, double.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// act
		int result = -1;
		try {
			result = (int) method.invoke(this.gearControl, gearShift, actualVelocity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// assert
		Assert.assertEquals(2, result);
	}

	@Test
	public void switchGearDependOnSpeedShift2MinVelocityReturn2() {
		// arrange
		int gearShift = 2;
		double actualVelocity = this.maxGearVelocity[gearShift - 1];

		Method method = null;
		try {
			method = GearControl.class.getDeclaredMethod(switchGearDependOnSpeedMethodName, int.class, double.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// act
		int result = -1;
		try {
			result = (int) method.invoke(this.gearControl, gearShift, actualVelocity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// assert
		Assert.assertEquals(2, result);
	}

}
