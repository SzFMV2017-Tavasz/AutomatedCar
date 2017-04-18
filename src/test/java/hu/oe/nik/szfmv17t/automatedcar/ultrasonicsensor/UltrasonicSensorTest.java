package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gabi1_000 on 2017.04.15..
 */
public class UltrasonicSensorTest {

    private UltrasonicSensor ultrasonicSensor;

    @Before
    public void setUp() throws Exception {
        ultrasonicSensor = new UltrasonicSensor(1,0,0,0);
    }

    @Test
    public void calculateCoordinates() throws Exception {
        ultrasonicSensor.calculateCoordinates(1,0,0,0);
        assertTrue(ultrasonicSensor.getCoordinates().getMainX()>0 && ultrasonicSensor.getCoordinates().getMainY()>0);
    }

    @Test
    public void getViewAngle() throws Exception {
        assertTrue(ultrasonicSensor.getViewAngle() == 100);
    }

    @Test
    public void getViewLength() throws Exception {
        assertTrue(ultrasonicSensor.getViewLength() == 3);
    }

    @Test
    public void getSensorNumber() throws Exception {
        assertTrue(ultrasonicSensor.getSensorNumber() == 1);
    }

}