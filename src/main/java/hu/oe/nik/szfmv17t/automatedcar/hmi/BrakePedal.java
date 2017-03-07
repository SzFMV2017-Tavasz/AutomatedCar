package hu.oe.nik.szfmv17t.automatedcar.hmi;

public class BrakePedal{
    public static final int MAX_STATE = 100;
    public static final int MIN_STATE = 0;
    public static final int START_STATE = 0;
    public static final int DEFAULT_AMOUNT = 10;
    public static final int LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN = 1000; // milisecond
    
    public HmiTimer timer;
    private int amount;
    private int state;
    
    public BrakePedal(){
        state = START_STATE;
        amount = DEFAULT_AMOUNT;
    }
    
    public void braking(){
        if (timer.getDuration() >= LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN) {
            releaseBrake();
        }
        else{
            decreaseBrake();
        }
    }
    
    public void releasingBrake(){
        if (timer.getDuration() >= LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN) {
            increaseBrake();
        }
        else{
            emergencyBrake();
        }
    }
    
    private void increaseBrake(){
        if(state + amount <= MAX_STATE){
            state += amount;
        }
    }
    
    private void decreaseBrake(){
        if(state - amount >= MIN_STATE){
            state -= amount;
        }
    }
    
    private void emergencyBrake(){
        state = 100;
    }
    
    private void releaseBrake(){
        state = 0;
    }

    public int getState() {
        return state;
    }
    
    public void start() {
        timer.Start();
    }
}