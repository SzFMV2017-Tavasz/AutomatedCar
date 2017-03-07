package hu.oe.nik.szfmv17t.automatedcar.hmi;

import javax.annotation.Resource.AuthenticationType;

/**
 * Created by gabi1_000 on 2017.03.04..
 */
public class GearStick {

    private AutoGearStates autoGearState;
    private int autoGearStateIndex;
    private int manualGearState;
    private final int maxGear = 6;
    private HmiTimer timer;
    private int gearChangeTime;
    private final AutoGearStates lastGearState = AutoGearStates.D;
    private final AutoGearStates firstGearState = AutoGearStates.P;

    public int GetManualGearState() {
        return manualGearState;
    }

    public AutoGearStates getAutoGearState() {
        return autoGearState;
    }

    public GearStick(int newGearChangeTime) {
        autoGearState = AutoGearStates.P;
        autoGearStateIndex = autoGearState.ordinal();
        manualGearState = 0;
        timer = new HmiTimer();
        gearChangeTime = newGearChangeTime;
    }

    public void gearDown() {
        GearChangeTimer();
        if (manualGearState > 0) {
            manualGearState--;
        } else {
            manualGearState = 0;
        }
    }

    public void gearUp() {
        GearChangeTimer();
        if (manualGearState < maxGear) {
            manualGearState++;
        } else {
            manualGearState = maxGear;
        }
    }

    public void setGearState(AutoGearStates newGearState) {
        autoGearState = newGearState;
        autoGearStateIndex = autoGearState.ordinal();
    }

    public void gearUpAutomatic(){
    	if(autoGearState != lastGearState){
    		autoGearStateIndex++;
    		autoGearState = AutoGearStates.values()[autoGearStateIndex];
    	}
    }
    
    public void gearDownAutomatic(){
    	if(autoGearState != firstGearState){
    		autoGearStateIndex--;
    		autoGearState = AutoGearStates.values()[autoGearStateIndex];
    	}
    }
    
    private void GearChangeTimer() {
        timer.Start();
        while (timer.getDuration() != gearChangeTime) {
            //TODO: abort accelerating for this time
        }
    }

}
