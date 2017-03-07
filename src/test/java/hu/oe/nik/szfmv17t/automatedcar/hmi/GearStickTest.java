package hu.oe.nik.szfmv17t.automatedcar.hmi;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gabi1_000 on 2017.03.05..
 */
public class GearStickTest {

    private GearStick gearStick;


    @Before
    public void setUp() throws Exception {
        gearStick = new GearStick(100);
    }



    @Test
    public void getManualGearState() throws Exception {
        int actualManualGear = gearStick.GetManualGearState();
        assertTrue(actualManualGear >= 0 || actualManualGear <= 6);
    }

    @Test
    public void getAutoGearState() throws Exception {
        AutoGearStates currentState = gearStick.getAutoGearState();
        assertTrue(currentState == AutoGearStates.P || currentState == AutoGearStates.R || currentState == AutoGearStates.N || currentState == AutoGearStates.D);
    }

    @Test
    public void gearDown() throws Exception {
        gearStick.gearDown();
        int currentManualGear = gearStick.GetManualGearState();
        assertTrue(currentManualGear >= 0);
    }

    @Test
    public void gearUp() throws Exception {
        gearStick.gearUp();
        int currentManualGear = gearStick.GetManualGearState();
        assertTrue(currentManualGear <= 6);
    }

    @Test
    public void setGearState() throws Exception {
        gearStick.setGearState(gearStick.getAutoGearState());
        AutoGearStates currentState = gearStick.getAutoGearState();
        assertTrue(currentState == AutoGearStates.P || currentState == AutoGearStates.R || currentState == AutoGearStates.N || currentState == AutoGearStates.D);
    }
    
    @Test
    public void automaticGearUp(){
    	gearStick.setGearState(AutoGearStates.P);
    	gearStick.gearUpAutomatic();
    	AutoGearStates currentState = gearStick.getAutoGearState();
    	assertEquals(currentState,AutoGearStates.R);
    }
    
    @Test
    public void automaticGeaDown(){
    	gearStick.setGearState(AutoGearStates.R);
    	gearStick.gearDownAutomatic();
    	AutoGearStates currentState = gearStick.getAutoGearState();
    	assertEquals(currentState,AutoGearStates.P);
    }
}