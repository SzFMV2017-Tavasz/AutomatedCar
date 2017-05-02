package hu.oe.nik.szfmv17t.automatedcar.hmi;

/**
 * Created by SebestyenMiklos on 2017. 04. 23..
 */
public abstract class CarControl {
    protected HmiTimer timer;
    private boolean timerStarted;

    public CarControl(HmiTimer timer, boolean timerStarted) {
        this.timer = timer;
        this.timerStarted = timerStarted;
    }



    public void startTimerIfNotStarted(){
        if(!this.timerStarted){
            this.timerStart();
        }
    }

    public void timerStart() {
        timer.Start();
        timerStarted = true;
    }

    public void timerStop() {
        timerStarted = false;
    }
}
