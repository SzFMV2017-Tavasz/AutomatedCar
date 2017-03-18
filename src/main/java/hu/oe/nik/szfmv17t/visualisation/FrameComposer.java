package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;

import java.awt.*;
import java.util.List;

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
    private List<IWorldObject> getVisibleObjects()
    {
        return null;
    }
    public List<IWorldObject> composeFrame()
    {
        getVisibleObjects();
        getCar();
        calculateCameraPosition();
        return world.getWorldObjects();
    }
    private IWorldObject getCar()
    {
        for (IWorldObject element: world.getWorldObjects())
        {
            if(AutomatedCar.class.isInstance(element))
                return element;
        }
        return null;
    }
    private void calculateCameraPosition()
    {

    }
}
