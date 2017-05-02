package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;

/**
 * Created by gabi1_000 on 2017.04.15..
 */
public class UltrasonicControllerTest {

	private UltrasonicController ultrasonicController;
	private AutomatedCar auto;

	@Before
	public void setUp() throws Exception {
		auto = new AutomatedCar(0, 0, 0, 0, 0, 0, "", 0, 0, 0);
		ultrasonicController = new UltrasonicController(auto, null);
	}

	@Test
	public void getUltrasonicSensor() throws Exception {
		assertTrue(ultrasonicController.getUltrasonicSensor(1) != null);
	}

	
	 @Test
	 public void distanceTest(){
		 assertTrue(ultrasonicController.getDistance(0, 0, 5, 0) == 5);
	 }
}