package hu.oe.nik.szfmv17t.automatedcar.hmi;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GasPedalTest {


	GasPedal gasPedal;	
	@Before
	public void setUp() {
		gasPedal = new GasPedal();

	}

	
	@Test
	public void accelerateOnceTest(){
		gasPedal.acceleration();
		int gasPedalState = gasPedal.getState();
		assertEquals(gasPedalState, GasPedal.DEFAULT_AMOUNT);
	}
	@Test
	public void accelerateFiveTimesTest(){
		gasPedal.acceleration();
		gasPedal.acceleration();
		gasPedal.acceleration();
		gasPedal.acceleration();
		gasPedal.acceleration();
		int gasPedalState = gasPedal.getState();
		assertThat(gasPedalState, instanceOf(int.class));
		assertEquals(GasPedal.DEFAULT_AMOUNT * 5, gasPedalState);
	}
	@Test
	public void decreaseGasTest(){
		//increase pedal state to later able to decrease
		gasPedal.acceleration();
		int beforeDecreaseGasState = gasPedal.getState();
		gasPedal.deceleration();
		int afterDecreaseGasState = gasPedal.getState();
		assertTrue(afterDecreaseGasState < beforeDecreaseGasState);
	}
	@Test
	public void gasPedalReleaseTest(){
		gasPedal.timerStart();
		try {
			Thread.sleep(GasPedal.LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			gasPedal.deceleration();
			assertEquals(GasPedal.MIN_STATE, gasPedal.getState());
		}


	}
	@Test
	public void pedalToTheMetalTest(){
		gasPedal.setGasPedalReleased(false);
		gasPedal.acceleration();
		try {
			Thread.sleep(GasPedal.LENGTH_OF_BUTONPRESS_TO_MAX_OR_MIN);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			gasPedal.acceleration();
			assertEquals(GasPedal.MAX_STATE, gasPedal.getState());
		}
	}
}
