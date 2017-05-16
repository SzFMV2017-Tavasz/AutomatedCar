package hu.oe.nik.szfmv17t.automatedcar.camerasensor;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.domain.Road;
import hu.oe.nik.szfmv17t.environment.domain.Turn;
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
    private IWorldObject turn1;
    private AutomatedCar car1;
    private AutomatedCar car2;
    private AutomatedCar car3;
    private AutomatedCar car4;
    private List<IWorldObject> worldObjects;

    @Before
    public void setUp() {
        road1 = new Road(0, 0, 350, 350, 0, 0, "road_2lane_straight.png", 0, 1, 1, 1, 0,0);
        road2 = new Road(700, 700, 350, 350, 0, 0, "road_2lane_straight.png", 0, 1, 1, 1, 0,0);
        turn1 = new Turn(1100, 1100, 403, 373, 0d, 0, "road_2lane_45left.png", 0, 1, 1, 1, 0,0);

        car1 = new AutomatedCar(100, 100, 108, 240, 0d, 0, "car_1_white.png", 200d, 0d, 0d, 0,0);
        car2 = new AutomatedCar(351, 100, 108, 240, 0d, 0, "car_1_white.png", 200d, 0d, 0d, 0,0);
        car3 = new AutomatedCar(1503, 1100, 108, 240, 0d, 0, "car_1_white.png", 200d, 0d, 0d, 0,0);
        car4 = new AutomatedCar(351, 100, 108, 240, 0d, 0, "car_1_white.png", 200d, 0d, 0d, 0,0);

        worldObjects = new ArrayList<IWorldObject>();
        worldObjects.add(road2);
        worldObjects.add(road1);
        worldObjects.add(car1);
        worldObjects.add(car2);
        worldObjects.add(car3);
        worldObjects.add(car4);
        worldObjects.add(turn1);
        laneKeeping = new LaneKeeping(worldObjects);
    }

    @Test
    public void findRoadTest() {
        IWorldObject result = laneKeeping.findRoad(car1, worldObjects);
        assertEquals(road1, result);
    }

    @Test
    public void notFindRoadTest() {
        IWorldObject result = laneKeeping.findRoad(car2, worldObjects);
        assertEquals(null, result);
    }

    @Test
    public  void findTurnTest(){
        IWorldObject result = laneKeeping.findRoad(car3,worldObjects);
        assertEquals(turn1,result);
    }

    @Test
    public  void notFindTurnTest(){
        IWorldObject result = laneKeeping.findRoad(car4,worldObjects);
        assertEquals(null,result);
    }


}
