package hu.oe.nik.szfmv17t.automatedcar.hmi;

public class BrakePedal{
    private int state;
    private int amount;
    
    public BrakePedal(){
        state = 0;
        amount = 10;
    }
    
    public BrakePedal(int changeAmount){
        state = 0;
        amount = changeAmount;
    }
    
    public void IncreaseBrake(){
        state += amount;
    }
    
    public void DecreaseBreak(){
        state -= amount;
    }
    
    public void EmergencyBreaking(){
        state = 100;
    }
    
    public void ReleaseBrake(){
        state = 0;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }
}