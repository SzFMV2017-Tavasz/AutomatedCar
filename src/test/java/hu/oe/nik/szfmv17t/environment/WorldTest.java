package hu.oe.nik.szfmv17t.environment;

import hu.oe.nik.szfmv17t.environment.domain.CollidableBase;
import hu.oe.nik.szfmv17t.environment.domain.World;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class WorldTest {

	private World world;

	@org.junit.Before
	public void setUp() throws Exception {
		/* stuff written here runs before the tests */
		world = new World("AutomatedCar/src/main/resources/test_world.xml");
	}

	@Test
	public void testWidthGetterSetter() throws Exception {
		assertEquals(world.getWidth(), 800);
		world.setWidth(1000);
		assertEquals(world.getWidth(), 1000);
	}

	@Test
	public void testHeightGetterSetter() throws Exception {
		assertEquals(world.getHeight(), 600);
		world.setHeight(800);
		assertEquals(world.getHeight(), 800);
	}


	@Test
	public void getWorldObjectsTest() throws Exception {
		assertEquals(world.getWorldObjects().size(), 0);
		world.addObjectToWorld(new CollidableBase(21d,42d,0d,0d,0d,1,"test.jpg",100d,10d,10d));
		assertEquals(world.getWorldObjects().size(), 1);
	}

	@Test
	public void addObjectToWorldTest() throws Exception {
		world.addObjectToWorld(new CollidableBase(21d,42d,0d,0d,0d,1,"test.jpg",100d,10d,10d));
		assertEquals(world.getWorldObjects().size(), 1);
		assertEquals(world.getWorldObjects().get(0).getCenterX(), 21d);
		assertEquals(world.getWorldObjects().get(0).getCenterY(), 42d);
		assertEquals(world.getWorldObjects().get(0).getImageName(), "test.jpg");
	}
        
}

