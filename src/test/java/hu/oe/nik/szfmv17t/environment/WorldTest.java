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
		world = new World("src/main/resources/test_world.xml");
	}

	@Test
	public void testWidthGetterSetter() throws Exception {
		assertEquals(world.getWidth(), 5120);
	}

	@Test
	public void testHeightGetterSetter() throws Exception {
		assertEquals(world.getHeight(), 3000);
	}


	@Test
	public void getWorldObjectsTest() throws Exception {
		assertEquals(world.getWorld().size(), 46);
	}

	@Test
	public void addObjectToWorldTest() throws Exception {
                assertEquals(world.getWorld().size(), 46);
		world.addObjectToWorld(new CollidableBase(21d,42d,0d,0d,0d,1,"test.jpg",100d,10d,10d));
		assertEquals(world.getWorld().size(), 47);
	}
        
}

