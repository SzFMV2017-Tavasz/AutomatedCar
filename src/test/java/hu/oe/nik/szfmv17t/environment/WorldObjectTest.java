package hu.oe.nik.szfmv17t.environment;

import hu.oe.nik.szfmv17t.environment.domain.CollidableBase;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class WorldObjectTest {

	private IWorldObject worldObject;

	@org.junit.Before
	public void setUp() throws Exception {
		/* stuff written here runs before the tests */
		worldObject = new CollidableBase(21d,42d,0d,0d,0d,1,"test.jpg",100d,10d,10d, 0,0);
	}

	@Test
	public void testXGetterSetter(){
		assertEquals(worldObject.getCenterX(), 21d);
	}

	@Test
	public void testYGetterSetter(){
		assertEquals(worldObject.getCenterY(), 42d);
	}

	@Test
	public void testRotationGetterSetter(){
		assertEquals(worldObject.getAxisAngle(), 0d);
	}

	@Test
	public void testWidthGetterSetter(){
		assertEquals(worldObject.getWidth(), 0d);
	}

	@Test
	public void testHeightGetterSetter(){
		assertEquals(worldObject.getHeight(), 0d);
	}

	@Test
	public void testImageFileNameGetterSetter(){
		assertEquals(worldObject.getImageName(), "test.jpg");
	}
}