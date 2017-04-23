package hu.oe.nik.szfmv17t.automatedcar.hmi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by SebestyenMiklos on 2017. 03. 05..
 */
public class SteeringWheelTest {

    private int timeStep;
    SteeringWheel steeringWheel;

    @Before
    public void setUp() throws Exception {
        steeringWheel = new SteeringWheel();
        timeStep = steeringWheel.getTimeStep();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getState() throws Exception {
        assertTrue( steeringWheel.getState() == 0 );
    }

    @Test
    public void quickLeft() throws Exception {
            steeringWheel.quickLeft();
            assertTrue( steeringWheel.getState() == SteeringWheel.maxLeft );
    }

    @Test
    public void quickRight() throws Exception {
        steeringWheel.quickRight();
        assertTrue( steeringWheel.getState() == SteeringWheel.maxRight );
    }

    @Test
    public void steerLeft() throws Exception {
        steeringWheel.steerLeft();
        Thread.sleep(timeStep+1);
        steeringWheel.steerLeft();
        assertTrue( steeringWheel.isSteeringWheelLeftToCenter() );
        steeringWheel.steerRight();
    }

    @Test
    public void steerRight() throws Exception {
        steeringWheel.steerRight();
        Thread.sleep(timeStep+1);
        steeringWheel.steerRight();
        assertTrue( steeringWheel.isSteeringWheelRightToCenter() );
        steeringWheel.steerLeft();
    }

    @Test
    public void steerReleaseFromLeft() throws Exception {
        steeringWheel.steerLeft();
        steeringWheel.steerLeft();
        steeringWheel.steerRelease();
        assertTrue( steeringWheel.isSteeringWheelCentered() );

    }

    @Test
    public void steerReleaseFromRight() throws Exception {
        steeringWheel.steerRight();
        steeringWheel.steerRight();
        steeringWheel.steerRelease();
        assertTrue( steeringWheel.isSteeringWheelCentered() );

    }



}