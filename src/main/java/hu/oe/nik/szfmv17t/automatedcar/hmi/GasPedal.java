package hu.oe.nik.szfmv17t.automatedcar.hmi;

public class GasPedal {
    public static final int MAX_STATE = 100;
    public static final int MIN_STATE = 0;
    public static final int START_STATE = 0;
    public static final int DEFAULT_AMOUNT = 10;
    public static final int LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN = 1000;//milisecond

    public HmiTimer timer;
    private int amount;
    private int state;

    public GasPedal() {
        state = START_STATE;
        amount = DEFAULT_AMOUNT;
        timer = new HmiTimer();
    }

    public void acceleration() {
        if (timer.getDuration() >= LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN) {
            this.pedalToTheMetal();
        } else {
            this.incraseGas();
        }
    }

    public void deceleration() {
        if (timer.getDuration() >= LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN) {
            this.gasPedalRelease();
        } else {
            this.decraseGas();
        }

    }

    private void incraseGas() {
        if (this.state + amount <= MAX_STATE) {
            this.state += amount;
        } else {
            this.state = MAX_STATE;
        }
    }

    private void decraseGas() {
        if (this.state - amount >= MIN_STATE) {
            this.state -= amount;
        } else {
            this.state = MIN_STATE;
        }
    }

    private void gasPedalRelease() {
        this.state = MIN_STATE;
    }

    private void pedalToTheMetal() {
        this.state = MAX_STATE;
    }

    public int getState() {
        return this.state;
    }

    public void start() {
        timer.Start();
    }
}
