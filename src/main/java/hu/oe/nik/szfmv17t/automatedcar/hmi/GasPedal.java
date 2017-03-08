package hu.oe.nik.szfmv17t.automatedcar.hmi;

public class GasPedal {
	private final int MAX_STATE = 100;
	private final int MIN_STATE = 0;
	private final int START_STATE = 0;
	private final int DEFAULT_AMOUNT = 10;
	private final int GAS_PEDAL_RELEASE_DEFAULT_VALUE = 5;

	private int amount;
	private int state;

	public GasPedal() {
		state = START_STATE;
		amount = DEFAULT_AMOUNT;
	}

	public GasPedal(int amount) {
		state = START_STATE;
		this.amount = amount;
	}

	public void Accelerate() {
		this.state += amount;
	}

	public void DecreaseGas() {
		if (this.state - amount >= MIN_STATE)
			this.state -= amount;
	}

	public void GasPedalRelease() {
		if (this.state - GAS_PEDAL_RELEASE_DEFAULT_VALUE >= MIN_STATE)
			this.state -= GAS_PEDAL_RELEASE_DEFAULT_VALUE;
	}

	public void PedalToTheMetal() {
		this.state = MAX_STATE;
	}

	public int getState() {
		return this.state;
	}
}
