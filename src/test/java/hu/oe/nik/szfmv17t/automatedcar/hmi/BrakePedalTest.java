package hu.oe.nik.szfmv17t.automatedcar.hmi;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BrakePedalTest {
	BrakePedal brakePedal;	
	@Before
	public void setUp() {
        brakePedal = new BrakePedal();
	}
        
	@Test
	public void brakingOnceTest(){
        brakePedal.braking();
        int brakePedalState = brakePedal.getState();
        assertEquals(brakePedalState, BrakePedal.DEFAULT_AMOUNT);
	}
        
	@Test
	public void brakingFiveTimesTest(){
        brakePedal.braking();
        brakePedal.braking();
        brakePedal.braking();
        brakePedal.braking();
        brakePedal.braking();
        int brakePedalState = brakePedal.getState();
        assertThat(brakePedalState, instanceOf(int.class));
        assertEquals(BrakePedal.DEFAULT_AMOUNT * 5, brakePedalState);
	}
	@Test
	public void decreaseBrakeTest(){
        //increase pedal state to later able to decrease
        brakePedal.braking();
        int beforeReleasingBrakeState = brakePedal.getState();
        brakePedal.releasingBrake();
        int afterReleasingBrakeState = brakePedal.getState();
        assertTrue(afterReleasingBrakeState < beforeReleasingBrakeState);
	}
        
	@Test
	public void brakePedalReleaseTest(){
        brakePedal.start();
        try {
            Thread.sleep(BrakePedal.LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            brakePedal.releasingBrake();
            assertEquals(BrakePedal.MIN_STATE, brakePedal.getState());
        }
	}
	@Test
	public void emergencyBrakeTest(){
        brakePedal.start();
        try {
            Thread.sleep(BrakePedal.LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            brakePedal.braking();
            assertEquals(BrakePedal.MAX_STATE, brakePedal.getState());
        }
	}
}
