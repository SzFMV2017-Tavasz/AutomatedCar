package hu.oe.nik.szfmv17t.automatedcar.hmi;

public class GasPedal {
	private final int MAX_STATE = 100;
	private final int MIN_STATE = 0;
	private final int START_STATE = 0;
	private final int DEFAULT_AMOUNT = 10;
	private final int GAS_PEDAL_RELEASE_DEFAULT_VALUE = 5;

	public HmiTimer hmiTimerForPedalToTheMetal;
	private int amount;
	private int state;

	public GasPedal() {
		state = START_STATE;
		amount = DEFAULT_AMOUNT;
		hmiTimerForPedalToTheMetal = new HmiTimer();
	}

	public GasPedal(int amount) {
		state = START_STATE;
		this.amount = amount;
		hmiTimerForPedalToTheMetal = new HmiTimer();
	}

	public void Accelerate() {
		if(this.state + amount <= MAX_STATE)
			this.state += amount;
		else
			this.state = MAX_STATE;
	}

	public void DecreaseGas() {
		if(this.state - amount >= MIN_STATE)
			this.state -= amount;
		else
			this.state = MIN_STATE;
	}

	public void GasPedalRelease() {
		if(this.state - GAS_PEDAL_RELEASE_DEFAULT_VALUE >= MIN_STATE)
			this.state -= GAS_PEDAL_RELEASE_DEFAULT_VALUE;
	}

	public void PedalToTheMetal() {
		this.state = MAX_STATE;
	}

	public int getState() {
		return this.state;
	}
}
