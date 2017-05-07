package hu.oe.nik.szfmv17t.automatedcar.hmi;

import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;

public class GasPedal extends CarControl {
    public static final int MAX_STATE = 100;
    public static final int MIN_STATE = 0;
    public static final int START_STATE = 0;
    public static final int DEFAULT_AMOUNT = 10;
    public static final int LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN = 1000;//milisecond

    private int amount;
    private int state;
    private boolean gasPedalReleased;

    public GasPedal() {
        super(new HmiTimer(),false);
        state = START_STATE;
        amount = DEFAULT_AMOUNT;
        gasPedalReleased = true;
    }

    public void acceleration() {
        startTimerIfNotStarted();
        long duration = timer.getDuration();
        //System.out.println(duration);
        if (!gasPedalReleased && duration >= LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN) {
            this.pedalToTheMetal();
            this.timerStop();
        } else if(gasPedalReleased){
            this.incraseGas();
            this.timerStop();
        }
    }

    public void deceleration() {
        startTimerIfNotStarted();
        long duration = timer.getDuration();
        //System.out.println(duration);
        if (!gasPedalReleased && duration >= LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN) {
            this.gasPedalRelease();
            this.timerStop();
        } else if(gasPedalReleased) {
            this.decraseGas();
            this.timerStop();
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


    public void setGasPedalReleased(boolean gasPedalReleased) {
        this.gasPedalReleased = gasPedalReleased;
    }


    public void setState(int state) {
        this.state = state;
    }
}
