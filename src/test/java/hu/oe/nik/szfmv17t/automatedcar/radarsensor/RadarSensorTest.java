package hu.oe.nik.szfmv17t.automatedcar.radarsensor;

import hu.oe.nik.szfmv17t.environment.utils.Position;
import hu.oe.nik.szfmv17t.environment.utils.Triangle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RadarSensorTest {

    private RadarSensor radarSensor;
    private Position carPosition;

    @Before
    public void setUp() throws Exception {
        radarSensor = new RadarSensor();
        carPosition = new Position(2,3,2,4,0, 0);
    }

    @Test
    public void radarSensorAtZeroDegreesContainsPoint(){
        Triangle sensorArea = radarSensor.calculateCoordinates(carPosition,0);

        Assert.assertTrue(sensorArea.contains(3,0));
    }

    @Test
    public void radarSensorAtZeroDegreesNotContainsPoint(){
        Triangle sensorArea = radarSensor.calculateCoordinates(carPosition,0);

        Assert.assertFalse(sensorArea.contains(1,5));
    }

    @Test
    public void radarSensorAtNinetyDegreesNotContainsPoint(){
        Triangle sensorArea = radarSensor.calculateCoordinates(carPosition,Math.toRadians(90));

        Assert.assertFalse(sensorArea.contains(6,6));
    }

    @Test
    public void radarSensorAtNinetyDegreesContainsPoint(){
        Triangle sensorArea = radarSensor.calculateCoordinates(carPosition,Math.toRadians(90));

        Assert.assertTrue(sensorArea.contains(6,5));
    }

    @Test
    public void radarSensorAtTwoHundredDegreesContainsPoint(){
        Triangle sensorArea = radarSensor.calculateCoordinates(carPosition,Math.toRadians(200));

        Assert.assertTrue(sensorArea.contains(1,8));
    }

    @Test
    public void radarSensorAtTwoHundredDegreesNotContainsPoint(){
        Triangle sensorArea = radarSensor.calculateCoordinates(carPosition,Math.toRadians(200));

        Assert.assertFalse(sensorArea.contains(2,4));
    }
}
