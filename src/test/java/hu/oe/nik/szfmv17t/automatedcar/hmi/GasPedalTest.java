package hu.oe.nik.szfmv17t.automatedcar.hmi;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GasPedalTest {

	private final int MAX_STATE = 100;
	private final int GAS_PUSHED_ONCE_VALUE_DEFAULT = 10;
	private final int GAS_PUSHED_FIVE_TIMES_DEFAULT = 50;
	private final int GAS_DECREASE_VALUE = 10;
	private final int GAS_PEDAL_RELEASE_VALUE = 5;

	GasPedal gasPedal;	
	@Before
	public void setUp() {
		gasPedal = new GasPedal();
	}
	
	@Test
	public void accelerateOnceTest(){
		gasPedal.Accelerate();
		int gasPedalState = gasPedal.getState();
		assertThat(gasPedalState, instanceOf(int.class));
		assertEquals(GAS_PUSHED_ONCE_VALUE_DEFAULT, gasPedalState);	
	}
	@Test
	public void accelerateFiveTimesTest(){
		gasPedal.Accelerate();
		gasPedal.Accelerate();
		gasPedal.Accelerate();
		gasPedal.Accelerate();
		gasPedal.Accelerate();

		int gasPedalState = gasPedal.getState();
		assertThat(gasPedalState, instanceOf(int.class));
		assertEquals(GAS_PUSHED_FIVE_TIMES_DEFAULT, gasPedalState);	
	}
	@Test
	public void decreaseGasTest(){
		//increase pedal state to later able to decrease
		gasPedal.Accelerate();
		
		int beforeDecreaseGasState = gasPedal.getState();
		gasPedal.DecreaseGas();
		int afterDecreaseGasState = gasPedal.getState();

		assertEquals(beforeDecreaseGasState - GAS_DECREASE_VALUE, afterDecreaseGasState);	
	}
	@Test
	public void gasPedalReleaseTest(){
		//increase pedal state to later able to decrease
		gasPedal.Accelerate();

		int beforeGasPedalReleased = gasPedal.getState();
		gasPedal.GasPedalRelease();
		int afterGasPedalReleased = gasPedal.getState();

		assertEquals(beforeGasPedalReleased - GAS_PEDAL_RELEASE_VALUE, afterGasPedalReleased);	
	}
	@Test
	public void pedalToTheMetalTest(){
		 gasPedal.PedalToTheMetal();
		 int pedalToTheMetalActualValue = gasPedal.getState();
		 assertEquals(MAX_STATE, pedalToTheMetalActualValue);
	}
}
