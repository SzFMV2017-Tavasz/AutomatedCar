package hu.oe.nik.szfmv17t.automatedcar.hmi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by SebestyenMiklos on 2017. 03. 05..
 */
public class SteeringWheelTest {

    SteeringWheel steeringWheel;
    DirectionIndicator indicator;
    @Before
    public void setUp() throws Exception {
        indicator = new DirectionIndicator();
        steeringWheel = new SteeringWheel(indicator);
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
        assertTrue( steeringWheel.isSteeringWheelLeftToCenter() );
        steeringWheel.steerRight();
    }

    @Test
    public void steerRight() throws Exception {
        steeringWheel.steerRight();
        assertTrue( steeringWheel.isSteeringWheelRightToCenter() );
        steeringWheel.steerLeft();
    }

    @Test
    public void automaticIndicationLeft() throws Exception {
        while(steeringWheel.getState()!=steeringWheel.getSteeringStateForIndicationLeft()) {
            steeringWheel.steerLeft();
        }
        assertTrue( indicator.GetDirectionIndicatorState() == DirectionIndicatorStates.Left );
        steeringWheel.steerRelease();
    }

    @Test
    public void automaticIndicationFromLeftBackwards() throws Exception {
        while(steeringWheel.getState()!=steeringWheel.getSteeringStateForIndicationLeft()) {
            steeringWheel.steerLeft();
        }
        steeringWheel.steerLeft();
        steeringWheel.steerRight();
        assertTrue( indicator.GetDirectionIndicatorState() == DirectionIndicatorStates.Default );
        steeringWheel.steerRelease();
    }

    @Test
    public void automaticIndicationFromLeftBackwardsWhileOtherIndicationActive() throws Exception {
        while(steeringWheel.getState()!=steeringWheel.getSteeringStateForIndicationLeft()) {
            steeringWheel.steerLeft();
        }
        steeringWheel.steerLeft();
        indicator.IndicatingRight();
        indicator.IndicatingRight();
        steeringWheel.steerRight();
        assertTrue( indicator.GetDirectionIndicatorState() == DirectionIndicatorStates.Right );
        steeringWheel.steerRelease();
    }

    @Test
    public void automaticIndicationRight() throws Exception {
        while(steeringWheel.getState()!=steeringWheel.getSteeringStateForIndicationRight()) {
            steeringWheel.steerRight();
        }
        assertTrue( indicator.GetDirectionIndicatorState() == DirectionIndicatorStates.Right );
        steeringWheel.steerRelease();
    }

    @Test
    public void automaticIndicationFromRightBackwards() throws Exception {
        while(steeringWheel.getState()!=steeringWheel.getSteeringStateForIndicationRight()) {
            steeringWheel.steerRight();
        }
        steeringWheel.steerRight();
        steeringWheel.steerLeft();
        assertTrue( indicator.GetDirectionIndicatorState() == DirectionIndicatorStates.Default );
        steeringWheel.steerRelease();
    }

    @Test
    public void automaticIndicationFromRightBackwardsWhileOtherIndicationActive() throws Exception {
        while(steeringWheel.getState()!=steeringWheel.getSteeringStateForIndicationRight()) {
            steeringWheel.steerRight();
        }
        steeringWheel.steerRight();
        indicator.IndicatingLeft();
        indicator.IndicatingLeft();
        steeringWheel.steerLeft();
        assertTrue( indicator.GetDirectionIndicatorState() == DirectionIndicatorStates.Left );
        steeringWheel.steerRelease();
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