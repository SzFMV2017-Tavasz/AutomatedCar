package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.Main;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.visualisation.interfaces.IWorldVisualization;

import java.util.List;

/**
 * Created by Mariusz on 2017.03.15..
 */
public class Drawer implements IWorldVisualization {
    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }
    private World world;
    @Override
    public List<IWorldObject> getWorld() {
        return world.getWorldObjects();
    }
    private static Drawer instance = null;
    private Drawer(World world,int frameWidth,int frameHeight)
    {}
    public static Drawer getDrawer(World world,int frameWidth,int frameHeight)
    {
        if (instance==null)
            instance = new Drawer(world,frameWidth,frameHeight);
        return instance;
    }

    public FrameComposer getComposer()
    {
        return FrameComposer.getComposer();
    }
   /* public void Loop() throws InterruptedException {
        int refreshRate=calculateRefresh(Main.FPS);
        while (true)
        {
            Thread.sleep(refreshRate);
            fc.composeFrame();
        }
    }*/
}
