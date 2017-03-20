package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Mariusz on 2017.03.15..
 */
public class FrameComposer {
    private Camera camera = new Camera();
    private World world;
    private static FrameComposer instance=null;
    private FrameComposer(World world)
    {
        this.world=world;
    }

    public static FrameComposer getComposer(World world)
    {
        if (instance==null)
            instance=new FrameComposer(world);
        return instance;
    }

    public List<IWorldObject> composeFrame()
    {
        try
        {
            List<IWorldObject> worldObjects = world.getWorld();
            IWorldObject car = getCar(worldObjects);
                if (car == null) throw new Exception("Car not found");
            setCameraPosition(car);
            List<IWorldObject> visibleObjects = getVisibleObjects(worldObjects);
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
        }
        //return getVisibleObjects(worldObjects); //uncomment when appropriate
        return world.getWorld();
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
        camera.setX(carObject.getCenterX());
        camera.setY(carObject.getCenterY());
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
            Rectangle objectRectangle = composeRectangleFromWorldObject(object);
            if (objectRectangle != null && rectangleOverlaps(cameraRectangle,objectRectangle))
                visibleObjects.add(object);
        }
        return visibleObjects;
    }

    Rectangle composeRectangleFromWorldObject(IWorldObject object)
    {
        if (object == null)
            return null;

        Rectangle objectRectangle = new Rectangle();
        objectRectangle.width = (int)object.getWidth();
        objectRectangle.height = (int)object.getHeight();
        objectRectangle.x = (int)object.getCenterX() - objectRectangle.width / 2;
        objectRectangle.y = (int)object.getCenterY() - objectRectangle.height / 2;

        return objectRectangle;
    }

    boolean rectangleOverlaps(Rectangle camera, Rectangle object)
    {
        return !(object.x> camera.x + camera.width ||
                object.x + object.width < camera.x ||
                object.y> camera.y + camera.height ||
                object.y + object.height< camera.y);
    }
}

