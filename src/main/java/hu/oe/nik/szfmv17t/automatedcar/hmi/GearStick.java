package hu.oe.nik.szfmv17t.automatedcar.hmi;


/**
 * Created by gabi1_000 on 2017.03.04..
 */
public class GearStick {

    private AutoGearStates autoGearState;
    private int manualGearState;
    private final int maxGear = 6;
    private HmiTimer timer;
    private int gearChangeTime;

    public int GetManualGearState() {
       return manualGearState;
    }

    public AutoGearStates GetAutoGearState() {
        return autoGearState;
    }

    public GearStick(int newGearChangeTime) {
        autoGearState = AutoGearStates.P;
        manualGearState = 0;
        timer = new HmiTimer();
        gearChangeTime = newGearChangeTime;
    }

    public void GearDown() {
        GearChangeTimer();
        if (manualGearState > 0)
            manualGearState--;
        else
            manualGearState = 0;
    }

    public void GearUp() {
        GearChangeTimer();
        if (manualGearState < maxGear)
            manualGearState++;
        else
            manualGearState = maxGear;
    }

    public void SetGearState(AutoGearStates newGearState) {
        autoGearState = newGearState;
    }

    private void GearChangeTimer() {
        timer.Start();
        while(timer.getDuration()!=gearChangeTime) {
            //TODO: abort accelerating for this time
        }
    }

}
