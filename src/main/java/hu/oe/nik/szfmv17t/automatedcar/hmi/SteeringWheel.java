package hu.oe.nik.szfmv17t.automatedcar.hmi;

import hu.oe.nik.szfmv17t.Main;

/**
 * Created by SebestyenMiklos on 2017. 03. 05..
 */
public class SteeringWheel {
    private int state;
    private HmiTimer timer;
    private int steeringStep = 5;
    private int timeStep = Main.CYCLE_PERIOD*2;
    public static int maxLeft = -100;
    public static int maxRight = 100;
    private boolean timerStarted = false;

    public boolean isSteerReleased() {
        return steerReleased;
    }

    public void setSteerReleased(boolean steerReleased) {
        this.steerReleased = steerReleased;
    }

    private boolean steerReleased = true;

    public SteeringWheel() {
        this.state = 0;
        this.timer = new HmiTimer();
    }

    public int getTimeStep() {
        return timeStep;
    }

    public void steerLeft() {
        startTimerIfNotStarted();
        this.steerReleased = false;
        if(timer.getDuration() > timeStep) {
            if (state >= maxLeft + steeringStep) {
                state -= steeringStep;
            }
            this.start();
        }
    }

    public void autoSteerLeft(){
        if(timer.getDuration() > timeStep) {
            if (state >= maxLeft + steeringStep) {
                state -= steeringStep;
            }
            this.start();
        }
    }

    public void autoSteerRight() {
        if(timer.getDuration() > timeStep){
            if (state <= maxRight - steeringStep) {
                state += steeringStep;
            }
            this.start();
        }
    }

    public void steerRight() {
        startTimerIfNotStarted();
        this.steerReleased = false;
        if(timer.getDuration() > timeStep){
            if (state <= maxRight - steeringStep) {
                state += steeringStep;
            }
            this.start();
        }
    }

    public boolean steerRelease() {
        if(isSteeringWheelLeftToCenter()){
            return wheelToCenterFromLeft();
        }else if(isSteeringWheelRightToCenter()) {
            return wheelToCenterFromRight();
        }else{
            return false;
        }
    }

    private boolean wheelToCenterFromLeft() {
        if(!isSteeringWheelCentered()){
            autoSteerRight();
            return true;
        }
        return false;
    }
    private boolean wheelToCenterFromRight() {
        if(!isSteeringWheelCentered()){
            autoSteerLeft();
            return true;
        }
        return false;
    }

    public void quickLeft() {
        while(state != maxLeft){
            state -= steeringStep;
        }
    }

    public void quickRight() {
        while(state != maxRight){
            state += steeringStep;
        }
    }

    public int getState(){
        return this.state;
    }

    public boolean isSteeringWheelCentered() {
        if(this.state == 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean isSteeringWheelLeftToCenter() {
        if(this.state < 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean isSteeringWheelRightToCenter() {
        if(this.state > 0){
            return true;
        }else {
            return false;
        }
    }
    public void start() {
        timer.Start();
        timerStarted = true;
    }

    public void startTimerIfNotStarted(){
        if(!this.timerStarted){
            this.start();
        }
    }


}
