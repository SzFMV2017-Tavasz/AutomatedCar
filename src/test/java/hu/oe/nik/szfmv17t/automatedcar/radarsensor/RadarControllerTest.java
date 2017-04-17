package hu.oe.nik.szfmv17t.automatedcar.radarsensor;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.utils.Triangle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RadarControllerTest {

    private RadarController radarController;
    private AutomatedCar car;

    @Before
    public void setUp() throws Exception {
        AutomatedCar car = new AutomatedCar(480,800,108,240,0d,0,"car_1_white.png",200d,0d,0d);
        radarController = new RadarController(car);
    }

    @Test
    public void radarSensorIsNotNull(){
        RadarSensor sensor = null;
        try {
            Field radarSensorField = RadarController.class.getDeclaredField("radarSensor");
            radarSensorField.setAccessible(true);
            if(radarSensorField != null){
                sensor = (RadarSensor) radarSensorField.get(radarController);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(sensor != null);
    }
}
