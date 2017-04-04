package hu.oe.nik.szfmv17t.environment;

import hu.oe.nik.szfmv17t.environment.domain.CollidableBase;
import hu.oe.nik.szfmv17t.environment.domain.Sign;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.domain.WorldObjectState;
import org.junit.Test;
import java.awt.Point;

import static junit.framework.Assert.assertEquals;

public class WorldTest {

    private World world;
    private World world2;

    @org.junit.Before
    public void setUp() throws Exception {
        /* stuff written here runs before the tests */
        world = new World("src/main/resources/test_world.xml");
        world2 = new World("");
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
    public void worldObjectsListSizeTest() throws Exception {
        assertEquals(world.getWorld().size(), 46);
    }

    @Test
    public void firstWorldObjectFromXmlTest() throws Exception {
        assertEquals(world.getWorld().get(0).getCenterX(), 1875.0); //PosX 1700 Size/2=175
        assertEquals(world.getWorld().get(0).getCenterY(), 319.0);  //PosY 144  Size/2=175
        assertEquals(world.getWorld().get(0).getAxisAngle(), 4.71); //270 degree == 4.71 rad
        assertEquals(world.getWorld().get(0).getHeight(), 350.0);
        assertEquals(world.getWorld().get(0).getWidth(), 350.0);
        assertEquals(world.getWorld().get(0).getState(), WorldObjectState.Untouched);
        assertEquals(world.getWorld().get(0).getZIndex(), 0);
        assertEquals(world.getWorld().get(0).getImageName(), "road_2lane_straight.png");
    }

    @Test
    public void lastTreeWorldObjectFromXmlTest() throws Exception {
        assertEquals(world.getWorld().get(40).getCenterX(), 375.0); //PosX 304 + Size/2 71
        assertEquals(world.getWorld().get(40).getCenterY(), 2712.0);  //PosY 2632 + Size/2 80
        assertEquals(world.getWorld().get(40).getAxisAngle(), 0.0); //0 degree == 0.0 rad m11=1 m12=0 m21=0 m22=1
        assertEquals(world.getWorld().get(40).getHeight(), 160.0);
        assertEquals(world.getWorld().get(40).getWidth(), 142.0);
        assertEquals(world.getWorld().get(40).getState(), WorldObjectState.Untouched);
        assertEquals(world.getWorld().get(40).getZIndex(), 2);
        assertEquals(world.getWorld().get(40).getImageName(), "tree.png");
    }

    @Test
    public void addObjectToWorldTest() throws Exception {
        assertEquals(world.getWorld().size(), 46);
        world.addObjectToWorld(new CollidableBase(21d, 42d, 0d, 0d, 0d, 1, "test.jpg", 100d, 10d, 10d));
        assertEquals(world.getWorld().size(), 47);
    }

    @Test
    public void emptyWorldNoException() throws Exception {
        boolean helps = false;
        try {
            world2.updateWorld();
        } catch (Exception e) {
            helps = true;
        }
        assertEquals(false, helps);
    }

    @Test
    public void nonEmptyWorldNoException() throws Exception {
        world2.addObjectToWorld(new Sign(0, 0, 0, 0, 0, 0, "", 0, 0, 0));
        boolean helps = false;
        try {
            world2.updateWorld();
        } catch (Exception e) {
            helps = true;
        }
        assertEquals(false, helps);
    }
        
    @Test
    public void sensorTriangleWithPointsTest() throws Exception {
        Sign testSign2 = new Sign(2, 3, 0, 0, 0, 0, "", 0, 0, 0); 
        world.addObjectToWorld(testSign2);  
        Point a = new Point(-6,3);
        Point b = new Point(10,-10);
        Point c = new Point(10,11);
        assertEquals(testSign2, world.checkSensorArea(a, b, c).get(0));
    }
}
