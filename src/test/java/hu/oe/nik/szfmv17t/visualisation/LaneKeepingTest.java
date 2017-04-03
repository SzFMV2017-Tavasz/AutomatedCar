package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.domain.Road;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by winifred on 2017.04.03..
 */
public class LaneKeepingTest {

    private LaneKeeping laneKeeping;
    private IWorldObject road1;
    private IWorldObject road2;
    private AutomatedCar car;
    private AutomatedCar car2;
    private List<IWorldObject> worldObjects;

    @Before
    public void setUp() {
        road1 = new Road(0, 0, 350, 350, 0, 0, "road_2lane_straight.png", 0, 0, 0, 0);
        road2 = new Road(700, 700, 350, 350, 0, 0, "road_2lane_straight.png", 0, 0, 0, 0);
        car = new AutomatedCar(100, 100, 108, 240, 0d, 0, "car_1_white.png", 200d, 0d, 0d);
        car2 = new AutomatedCar(350, 0, 108, 240, 0d, 0, "car_1_white.png", 200d, 0d, 0d);
        worldObjects = new ArrayList<IWorldObject>();
        worldObjects.add(road2);
        worldObjects.add(road1);
        worldObjects.add(car);
        worldObjects.add(car2);
        laneKeeping = new LaneKeeping(worldObjects);
    }

    @Test
    public void findRoadTest() {
        IWorldObject result = laneKeeping.findRoad(car, worldObjects);
        assertEquals(road1, result);
    }

    @Test
    public void notFindRoadTest() {
        IWorldObject result = laneKeeping.findRoad(car2, worldObjects);
        assertEquals(null, result);
    }


}
