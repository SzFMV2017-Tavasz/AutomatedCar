package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.visualisation.viewmodels.CameraObject;
import org.junit.Test;

import hu.oe.nik.szfmv17t.visualisation.mock.WorldObjectMock;
import hu.oe.nik.szfmv17t.visualisation.mock.WorldVisualizationMock;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FrameComposerTest {
    @Test
    public void frameComposeReturnsCar () {
        if (!GraphicsEnvironment.isHeadless()) {
            AutomatedCar car = createCarAt(480, 800);

            List<IWorldObject> objects = new ArrayList<>();
            objects.add(car);

            FrameComposer fc = new FrameComposer(new WorldVisualizationMock(objects), new Camera());

            List<CameraObject> ret = fc.composeFrame();

            assertEquals(car, ret.get(0).getWorldObject());
        }
    }

    @Test(expected = CarNotFoundException.class)
    public void frameComposeThrowsException () {
        if (!GraphicsEnvironment.isHeadless()) {
            List<IWorldObject> objects = new ArrayList<>();

            FrameComposer fc = new FrameComposer(new WorldVisualizationMock(objects), new Camera());

            List<CameraObject> ret = fc.composeFrame();
        }
    }

    @Test
    public void visibleTest () {
        if (!GraphicsEnvironment.isHeadless()) {
            Camera camera = new Camera();
            AutomatedCar car = createCarAt(480, 800);

            WorldObjectMock visible = new WorldObjectMock(500, 810, 10, 10);

            List<IWorldObject> objects = new ArrayList<>();
            objects.add(car);
            objects.add(visible);

            FrameComposer fc = new FrameComposer(new WorldVisualizationMock(objects), camera);
            fc.setCameraSize(200, 200);

            List<CameraObject> ret = fc.composeFrame();

            assertEquals(2, ret.size());
        }
    }
    

    private AutomatedCar createCarAt (int x, int y) {
        return new AutomatedCar(x, y,108,240,0d,0,"car_1_white.png",200d,0d,0d);
    }
}

