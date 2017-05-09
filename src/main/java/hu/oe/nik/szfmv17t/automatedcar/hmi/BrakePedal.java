package hu.oe.nik.szfmv17t.automatedcar.hmi;

public class BrakePedal{
    public static final int MAX_STATE = 100;
    public static final int MIN_STATE = 0;
    public static final int START_STATE = 0;
    public static final int DEFAULT_AMOUNT = 10;
    public static final int LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN = 1000; // milisecond
    private DirectionIndicator directionIndicator;
    
    public HmiTimer timer;
    private int amount;
    private int state;
    
    public BrakePedal(DirectionIndicator directionIndicator){
        state = START_STATE;
        amount = DEFAULT_AMOUNT;
        timer = new HmiTimer();
        this.directionIndicator = directionIndicator;
    }
    
    public void braking(){
        if (timer.getDuration() >= LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN) {
        	emergencyBrake();
        }
        else{
        	increaseBrake();
            
        }
    }
    
    public void releasingBrake(){
        if (timer.getDuration() >= LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN) {
        	releaseBrake();
        }
        else{
        	decreaseBrake();
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
        state = MAX_STATE;
        directionIndicator.IndicatingBreakdown();
    }
    
    private void releaseBrake(){
        state = MIN_STATE;
    }

    public int getState() {
        return state;
    }
    
    public void start() {
        timer.Start();
    }

    public void setState(int state) {
        this.state = state;
    }
}