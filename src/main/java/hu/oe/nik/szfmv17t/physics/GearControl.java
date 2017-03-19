package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.automatedcar.hmi.AutoGearStates;

public class GearControl {
	public static final double SHIFT_THRESHOLD = 5;

	private double[] gearMaxValocity;

	public GearControl(double[] gearMaxVelocity) {
		this.gearMaxValocity = gearMaxVelocity;
	}

	public int actualGearState(AutoGearStates autoGearState, int actualGear, double actualVelocity) {
		switch (autoGearState) {
		case R:
			return 0;
		case P:
			return 1;
		case N:
		case D:
			return switchGearDependOnSpeed(actualGear, actualVelocity);
		default:
			return -1;
		}
	}

	private int switchGearDependOnSpeed(int actualGear, double actualVelocity) {
		if (actualGear > 1) {
			if (actualGear < this.gearMaxValocity.length - 1
					&& Math.abs(actualVelocity - this.gearMaxValocity[actualGear]) <= this.SHIFT_THRESHOLD) {
				return ++actualGear;
			} else if (actualVelocity != 0
					&& Math.abs(actualVelocity - this.gearMaxValocity[actualGear - 1]) <= this.SHIFT_THRESHOLD) {
				return --actualGear;
			}
			return actualGear;
		}

		return 2;
	}
}
