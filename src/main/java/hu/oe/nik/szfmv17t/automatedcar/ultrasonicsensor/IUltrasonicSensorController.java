package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

/**
 * Created by SebestyenMiklos on 2017. 03. 26..
 */
public interface IUltrasonicSensorController {
    public void increaseBrake(int value);
    public void decreaseBrake(int value);
    public void increaseGas(int value);
    public void decreaseGas(int value);
    public void steerLeft(int value);
    public void steerRight(int value);
    
}
