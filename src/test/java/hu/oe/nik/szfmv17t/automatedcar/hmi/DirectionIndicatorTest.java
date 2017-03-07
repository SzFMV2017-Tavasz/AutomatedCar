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
    public void indicating() throws Exception {
        DirectionIndicatorStates currentState = directionIndicator.GetDirectionIndicatorState();
        assertTrue(currentState == DirectionIndicatorStates.Default || currentState == DirectionIndicatorStates.Left || currentState == DirectionIndicatorStates.Right || currentState == DirectionIndicatorStates.BreakDown);
    }

}