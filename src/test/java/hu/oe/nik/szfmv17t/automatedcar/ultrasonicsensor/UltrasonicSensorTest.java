package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gabi1_000 on 2017.04.02..
 */
public class UltrasonicSensorTest {

    private UltrasonicSensor sensor;

    @Before
    public void setUp() throws Exception {
        int sensorNumber = 1;
        double mainX = 44;
        double mainY = 2;
        sensor = new UltrasonicSensor(sensorNumber,mainX,mainY);
    }

    @Test
    public void basicCalculationsOfTriangle() throws Exception {
        double firstCalculation = (180 - sensor.getViewAngle()) / 2;
        double secondCalculation = sensor.getViewLength() / Math.sin(firstCalculation);
        double thirdCalculation = 2 * secondCalculation * Math.cos(firstCalculation);
        assertTrue(sensor.getAdditionsToSidesInMeter() == (thirdCalculation/2));
    }

}