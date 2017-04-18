package hu.oe.nik.szfmv17t.visualisation.viewmodels;

import static org.junit.Assert.*;

import hu.oe.nik.szfmv17t.environment.utils.Config;
import hu.oe.nik.szfmv17t.visualisation.Camera;
import hu.oe.nik.szfmv17t.visualisation.mock.WorldObjectMock;

import org.junit.Test;

public class CameraObjectTest {
   @Test
   public void basePositionTest () {
      Config.SCALE = 1;

      Camera camera = new Camera();
      camera.setWidth(400);
      camera.setHeight(400);

      WorldObjectMock base = new WorldObjectMock(100, 100, 10, 10);

      CameraObject co = new CameraObject(base, camera);

      assertEquals(200, co.getX(), 0.001);
      assertEquals(200 + 54, co.getY(), 0.001);
   }

   @Test
   public void objectPositionAtBaseTest () {
      Config.SCALE = 1;

      Camera camera = new Camera ();
      camera.setX(0);
      camera.setX(0);
      camera.setWidth(400);
      camera.setHeight(400);

      WorldObjectMock base = new WorldObjectMock(100, 100, 10, 10);
      WorldObjectMock object = new WorldObjectMock(100, 100, 10, 10);

      CameraObject co = new CameraObject(object, base, camera);

      assertEquals(0, co.getX(), 0.001);
      assertEquals(0, co.getY(), 0.001);

      //assertEquals(200, co.getX(), 0.001);
      //assertEquals(200 + 54, co.getY(), 0.001);
   }

   @Test
   public void objectPositionTest () {
      Config.SCALE = 1;

      Camera camera = new Camera ();
      camera.setX(0);
      camera.setX(0);
      camera.setWidth(400);
      camera.setHeight(400);

      WorldObjectMock base = new WorldObjectMock(100, 100, 10, 10);
      WorldObjectMock object = new WorldObjectMock(90, 110, 10, 10);

      CameraObject co = new CameraObject(object, base, camera);

      assertEquals(-10, co.getX(), 0.001);
      assertEquals(10, co.getY(), 0.001);
      //assertEquals(190, co.getX(), 0.001);
      //assertEquals(210 + 54, co.getY(), 0.001);
   }
}