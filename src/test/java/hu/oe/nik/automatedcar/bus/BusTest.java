package hu.oe.nik.automatedcar.bus;

import static org.junit.Assert.assertEquals;

public class BusTest {
	Bus bus = Bus.getInstance();

	@org.junit.Before
	public void setUp() throws Exception {
	}

	@org.junit.Test
	public void isBusSingleton()
	{
		Bus first = Bus.getInstance();
		Bus second = Bus.getInstance();

		assertEquals(first == second, true);

		first.setACCMainSwitchState(true);

		assertEquals(first == second, true);
	}

	@org.junit.Test
	public void doesClassExist() throws Exception{
		assert(bus.getClass() != null);
	}

	@org.junit.Test
	public void hasBusDataMemberACCMainSwitchState(){
		//bus.ACCMainSwitchState = false;
		//assertEquals(bus.ACCMainSwitchState,false);
	}

	@org.junit.Test
	public void ACCMainSwitchGetterReturnsSetValue(){
		// bus.ACCMainSwitchState = false;
		bus.setACCMainSwitchState(false);
		assertEquals(bus.getACCMainSwitchState(), false);
		bus.setACCMainSwitchState(true);
		//bus.ACCMainSwitchState = true;
		assertEquals(bus.getACCMainSwitchState(), true);
	}

	@org.junit.Test
	public void ACCMainSwitchGetterReturnsSetterGivenValue(){
		bus.setACCMainSwitchState(false);
		assertEquals(bus.getACCMainSwitchState(), false);
		bus.setACCMainSwitchState(true);
		assertEquals(bus.getACCMainSwitchState(), true);
	}

	@org.junit.Test
	public void GasPedalTest(){
		bus.setGasPedal(42);
		assertEquals(bus.getGasPedal(), 42);
		bus.setGasPedal(-42);
		assertEquals(bus.getGasPedal(), 0);
		bus.setGasPedal(4242);
		assertEquals(bus.getGasPedal(), 100);
	}

	@org.junit.Test
	public void BrakePedalTest(){
		bus.setBrakePedal(42);
		assertEquals(bus.getBrakePedal(), 42);
		bus.setBrakePedal(-42);
		assertEquals(bus.getBrakePedal(), 0);
		bus.setBrakePedal(4242);
		assertEquals(bus.getBrakePedal(), 100);
	}

	@org.junit.Test
	public void SteeringWheelAngleTest(){
		bus.setSteeringWheelAngle(42);
		assertEquals(bus.getSteeringWheelAngle(), 42);
		bus.setSteeringWheelAngle(-42);
		assertEquals(bus.getSteeringWheelAngle(), 0);
		bus.setSteeringWheelAngle(4242);
		assertEquals(bus.getSteeringWheelAngle(), 100);
	}

	@org.junit.Test
	public void AccelerationTest(){
		bus.setAcceleration(42);
		assertEquals(bus.getAcceleration(), 42);
	}

	@org.junit.Test
	public void VelocityTest(){
		bus.setVelocity(42);
		assertEquals(bus.getVelocity(), 42);
	}

	@org.junit.After
	public void tearDown() throws Exception {
        /*GCC will remove bus*/
	}
}
