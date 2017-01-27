package hu.oe.nik.automatedcar.bus;

import static org.junit.Assert.assertEquals;

public class BusTest {
	Bus bus = Bus.getInstance();

	@org.junit.Before
	public void setUp() throws Exception {
		/* stuff written here runs before the tests */
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
	public void testACCMainSwitchGetterSetter(){
		bus.setACCMainSwitchState(false);
		assertEquals(bus.getACCMainSwitchState(), false);
		bus.setACCMainSwitchState(true);
		assertEquals(bus.getACCMainSwitchState(), true);
	}

	@org.junit.Test
	public void testGasPedalGetterSetter(){
		bus.setGasPedal(42);
		assertEquals(bus.getGasPedal(), 42);
		bus.setGasPedal(-42);
		assertEquals(bus.getGasPedal(), 0);
		bus.setGasPedal(4242);
		assertEquals(bus.getGasPedal(), 100);
	}

	@org.junit.Test
	public void testBrakePedalGetterSetter(){
		bus.setBrakePedal(42);
		assertEquals(bus.getBrakePedal(), 42);
		bus.setBrakePedal(-42);
		assertEquals(bus.getBrakePedal(), 0);
		bus.setBrakePedal(4242);
		assertEquals(bus.getBrakePedal(), 100);
	}

	@org.junit.Test
	public void testSteeringWheelAngleGetterSetter(){
		bus.setSteeringWheelAngle(42);
		assertEquals(bus.getSteeringWheelAngle(), 42);
		bus.setSteeringWheelAngle(-42);
		assertEquals(bus.getSteeringWheelAngle(), 0);
		bus.setSteeringWheelAngle(4242);
		assertEquals(bus.getSteeringWheelAngle(), 100);
	}

	@org.junit.Test
	public void testAccelerationGetterSetter(){
		bus.setAcceleration(42);
		assertEquals(bus.getAcceleration(), 42);
	}

	@org.junit.Test
	public void testVelocityGetterSetter(){
		bus.setVelocity(42);
		assertEquals(bus.getVelocity(), 42);
	}

	@org.junit.After
	public void tearDown() throws Exception {
        /* stuff written here runs after the tests */
	}
}
