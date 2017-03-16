package hu.oe.nik.szfmv17t.automatedcar.hmi;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gabi1_000 on 2017.03.05..
 */
public class DirectionIndicatorTest {

    private DirectionIndicator directionIndicator;

    @Before
    public void setUp() throws Exception {
        directionIndicator = new DirectionIndicator();
    }

    @Test
    public void indicatingLeft() throws Exception {
        DirectionIndicatorStates currentState = directionIndicator.GetDirectionIndicatorState();
        assertTrue(currentState == DirectionIndicatorStates.Left || currentState == DirectionIndicatorStates.Default || currentState == DirectionIndicatorStates.BreakDown);
    }

    @Test
    public void indicatingRight() throws Exception {
        DirectionIndicatorStates currentState = directionIndicator.GetDirectionIndicatorState();
        assertTrue(currentState == DirectionIndicatorStates.Right || currentState == DirectionIndicatorStates.Default || currentState == DirectionIndicatorStates.BreakDown);
    }

    @Test
    public void indicatingBreakdown() throws Exception {
        DirectionIndicatorStates currentState = directionIndicator.GetDirectionIndicatorState();
        assertTrue(currentState == DirectionIndicatorStates.Default || currentState == DirectionIndicatorStates.BreakDown);
    }

}