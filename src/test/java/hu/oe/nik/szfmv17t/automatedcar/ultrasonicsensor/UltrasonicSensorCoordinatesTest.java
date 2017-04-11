package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gabi1_000 on 2017.04.02..
 */
public class UltrasonicSensorCoordinatesTest {

    private UltrasonicSensorCoordinates coordinates;
    private double testX;
    private double testY;

    @Before
    public void setUp() throws Exception {
        coordinates = new UltrasonicSensorCoordinates();
        testX = 3;
        testY = 4;
        coordinates.setMainCoordinates(testX,testY);
        coordinates.setLeftCoordinates(testX,testY);
        coordinates.setRightCoordinates(testX,testY);
    }

    @Test
    public void getMainX() throws Exception {
        assertTrue(coordinates.getMainX() == testX);
    }

    @Test
    public void getMainY() throws Exception {
        assertTrue(coordinates.getMainY() == testY);
    }

    @Test
    public void getLeftX() throws Exception {
        assertTrue(coordinates.getLeftX() == testX);
    }

    @Test
    public void getLeftY() throws Exception {
        assertTrue(coordinates.getLeftY() == testY);
    }

    @Test
    public void getRightX() throws Exception {
        assertTrue(coordinates.getRightX() == testX);
    }

    @Test
    public void getRightY() throws Exception {
        assertTrue(coordinates.getRightY() == testY);
    }

}