package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Mariusz on 2017.03.15..
 */
public class FrameComposer {
    private Camera camera;
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
            List<IWorldObject> worldObjects = world.getWorldObjects();
            IWorldObject car = getCar(worldObjects);
            calculateCameraPosition(car);
            List<IWorldObject> visibleObjects = getVisibleObjects(worldObjects);
        }
        catch (Exception e)
        {}
        //return getVisibleObjects(worldObjects);
        return world.getWorldObjects();
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
    private void calculateCameraPosition(IWorldObject carObject)
    {
        camera.setX(carObject.getCenterX());
        camera.setY(carObject.getCenterY());
    }

    private List<IWorldObject> getVisibleObjects(List<IWorldObject> worldObjects)
    {
        List<IWorldObject> visibleObjects = new List<IWorldObject>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<IWorldObject> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(IWorldObject iWorldObject) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends IWorldObject> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends IWorldObject> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public IWorldObject get(int index) {
                return null;
            }

            @Override
            public IWorldObject set(int index, IWorldObject element) {
                return null;
            }

            @Override
            public void add(int index, IWorldObject element) {

            }

            @Override
            public IWorldObject remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<IWorldObject> listIterator() {
                return null;
            }

            @Override
            public ListIterator<IWorldObject> listIterator(int index) {
                return null;
            }

            @Override
            public List<IWorldObject> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        Rectangle cameraRectangle = camera.getCameraRectangle();

        for (IWorldObject object: worldObjects)
        {
            Rectangle objectRectangle = composeRectangleFromWorldObject(object);
            if (rectangleOverlaps(cameraRectangle,objectRectangle))
                visibleObjects.add(object);
        }
        return visibleObjects;
    }

    Rectangle composeRectangleFromWorldObject(IWorldObject object)
    {
        Rectangle objectRectangle = new Rectangle();
        objectRectangle.x = (int)object.getCenterX();
        objectRectangle.y = (int)object.getCenterY();
        objectRectangle.width = (int)object.getWidth();
        objectRectangle.height = (int)object.getHeight();
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

