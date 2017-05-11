package hu.oe.nik.szfmv17t.automatedcar.camerasensor;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.domain.Road;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static org.junit.Assert.*;

/**
 * Created by winifred on 2017.04.17..
 */
public class CameraSensorControllerTest {

    private CameraSensorController cameraSensorController;
    private World world;
    private List<IWorldObject> worldObjects;
    private AutomatedCar car;
    private IWorldObject road1;
    private HashMap<IWorldObject, Double> expected;

    @Before
    public void setUp() {
        car = new AutomatedCar(100, 100, 108, 240, 0d, 0, "car_1_white.png", 200d, 0d, 0d, 0,0);
        cameraSensorController = new CameraSensorController(car, null);

        road1 = new Road(0, 0, 350, 350, 0, 0, "road_2lane_straight.png", 0, 1, 1, 1, 0,0);
        worldObjects = new ArrayList<IWorldObject>();
        worldObjects.add(road1);
    }

    @Test
    public void getDataOfCameraSensorTest() {
        expected = cameraSensorController.getDataOfCameraSensor(car, worldObjects);
        HashMap<IWorldObject, Double> result = cameraSensorController.getDataOfCameraSensor(car, worldObjects);

        assertTrue(expected.keySet().toArray()[0].equals(result.keySet().toArray()[0]));
    }


}
