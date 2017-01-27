package hu.oe.nik.automatedcar.bus;

public class Bus {
	/* fields */
	private boolean ACCMainSwitchState;

	private int Acceleration;
	private int Velocity;
	private int gasPedal;
	private int steeringWheelAngle;
	private int brakePedal;

	/* singleton parts */
	private static Bus instance = null;

	private Bus() {
		ACCMainSwitchState = false; //default main switch state value: off
	}

	public static Bus getInstance() {
		if (instance == null) {
			instance = new Bus();
		}

		return instance;
	}

	/* getters, setters */

	public int getAcceleration() {
		return Acceleration;
	}

	public void setAcceleration(int acceleration) {
		Acceleration = acceleration;
	}

	public int getVelocity() {
		return Velocity;
	}

	public void setVelocity(int velocity) {
		Velocity = velocity;
	}

	public int getGasPedal() {
		return gasPedal;
	}

	public void setGasPedal(int gasPedal) {
		if (gasPedal < 100 && gasPedal > 0) {
			this.gasPedal = gasPedal;
		} else if (gasPedal > 0) {
			this.gasPedal = 100;
		} else {
			this.gasPedal = 0;
		}
	}

	public int getBrakePedal() {
		return brakePedal;
	}

	public void setBrakePedal(int brakePedal) {
		if (brakePedal < 100 && brakePedal > 0) {
			this.brakePedal = brakePedal;
		} else if (brakePedal > 0) {
			this.brakePedal = 100;
		} else {
			this.brakePedal = 0;
		}
	}

	public int getSteeringWheelAngle() {
		return steeringWheelAngle;
	}

	public void setSteeringWheelAngle(int steeringWheelAngle) {
		if (steeringWheelAngle < 100 && steeringWheelAngle > 0) {
			this.steeringWheelAngle = steeringWheelAngle;
		} else if (steeringWheelAngle > 0) {
			this.steeringWheelAngle = 100;
		} else {
			this.steeringWheelAngle = 0;
		}
	}

	public boolean getACCMainSwitchState() {
		return ACCMainSwitchState;
	}

	public void setACCMainSwitchState(boolean ACCMainSwitchState) {
		this.ACCMainSwitchState = ACCMainSwitchState;
	}

}
