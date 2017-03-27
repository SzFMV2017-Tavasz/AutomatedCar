package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldVisualisation;
import hu.oe.nik.szfmv17t.visualisation.viewmodels.CameraObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariusz on 2017.03.15..
 */
public class FrameComposer {
    private Camera camera = new Camera();
    private IWorldVisualisation world;
    private static FrameComposer instance=null;
    private FrameComposer(IWorldVisualisation world)
    {
        this.world=world;
    }

    public static FrameComposer getComposer(IWorldVisualisation world)
    {
        if (instance==null)
            instance=new FrameComposer(world);
        return instance;
    }

    public List<CameraObject> composeFrame()
    {
        try
        {
            List<IWorldObject> worldObjects = world.getWorld();
            IWorldObject car = getCar(worldObjects);
                if (car == null) throw new Exception("Car not found");
            setCameraPosition(car);
            List<IWorldObject> visibleObjects = getVisibleObjects(worldObjects);
            List<CameraObject> cameraObjects = calculateRelateivePosition(car, visibleObjects);

            return cameraObjects;
            //return visibleObjects;
            //return world.getWorld();
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
        }
        return null;
        //return world.getWorld();
    }
    private IWorldObject getCar(List<IWorldObject> objects)
    {
        for (IWorldObject element: objects)
        {
            if(AutomatedCar.class.isInstance(element))
                return element;
        }
        return null;
    }
    private void setCameraPosition(IWorldObject carObject)
    {
        camera.setX(carObject.getCenterX() - (carObject.getWidth()/2));
        camera.setY(carObject.getCenterY() - (carObject.getHeight()/2));
    }

    public void setCameraSize(int width, int height)
    {
        camera.setWidth(width);
        camera.setHeight(height);
    }

    private List<IWorldObject> getVisibleObjects(List<IWorldObject> worldObjects)
    {
        List<IWorldObject> visibleObjects = new ArrayList<IWorldObject>();
        Rectangle cameraRectangle = camera.getCameraRectangle();

        for (IWorldObject object: worldObjects)
        {
            Rectangle objectRectangle = getRectangleFromWorldObject(object);
            if (objectRectangle != null && rectangleOverlaps(cameraRectangle,objectRectangle))
                visibleObjects.add(object);
        }
        return visibleObjects;
    }

    private Rectangle getRectangleFromWorldObject(IWorldObject object)
    {
        if (object == null)
            return null;

        Rectangle objectRectangle = new Rectangle();
        objectRectangle.width = (int)object.getWidth();
        objectRectangle.height = (int)object.getHeight();
        objectRectangle.x = (int)object.getCenterX() - (objectRectangle.width / 2);
        objectRectangle.y = (int)object.getCenterY() - (objectRectangle.height / 2);

        return objectRectangle;
    }

    boolean rectangleOverlaps(Rectangle camera, Rectangle object)
    {
        return !(object.x> camera.x + camera.width ||
                object.x + object.width < camera.x ||
                object.y> camera.y + camera.height ||
                object.y + object.height< camera.y);
    }

    private List<CameraObject> calculateRelateivePosition(IWorldObject car, List<IWorldObject> visibleObjects)
    {
        if (car == null || visibleObjects == null)
            return null;

        List<CameraObject> visibleObjectsWithRelativePosition = new ArrayList<>();
        for (IWorldObject object: visibleObjects)
        {
            CameraObject relativeObject;

            if(AutomatedCar.class.isInstance(object))
                relativeObject = new CameraObject(car,camera);
            else
                relativeObject = new CameraObject(object,car,camera);

            visibleObjectsWithRelativePosition.add(relativeObject);
        }
        return visibleObjectsWithRelativePosition;
    }
}

