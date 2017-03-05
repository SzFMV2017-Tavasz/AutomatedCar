package hu.oe.nik.szfmv17t.automatedcar.hmi;

public class BrakePedal{
    public int state;
    private int amount;
    
    public BrakePedal(){
        state = 0;
        amount = 10;
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
}