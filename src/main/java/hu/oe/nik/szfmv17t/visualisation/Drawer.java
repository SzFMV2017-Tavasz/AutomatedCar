package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.Main;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.visualisation.interfaces.IWorldVisualization;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    private Drawer(World world)
    {}
    public static Drawer getDrawer(World world)
    {
        if (instance==null)
            instance = new Drawer(world);
        return instance;
    }

    public FrameComposer getComposer(World world)
    {
        return FrameComposer.getComposer(world);
    }

    static int t=0;
    public void DrawFrametoPanel(JPanel worldObjectsPanel,World world,JPanel mainPanel)
    {
        List<IWorldObject> toDraw=getComposer(world).composeFrame();
        t++;

        worldObjectsPanel = new JPanel() {
            private static final long serialVersionUID = 1L;
            public void paintComponent(Graphics g) {
                for (IWorldObject object : toDraw) {
                    // draw objects
                    BufferedImage image;
                    Graphics2D g2d=(Graphics2D)g;
                    try {
                        image = ImageIO.read(new File(ClassLoader.getSystemResource(object.getImageName()).getFile()));
                        int segedx=((int)(object.getCenterX()-object.getWidth()/2));
                        int segedy=((int)(object.getCenterY()-object.getHeight()/2));

                        g2d.drawImage(image,segedx, segedy, null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        BorderLayout layout = (BorderLayout)mainPanel.getLayout();
        mainPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        mainPanel.add(worldObjectsPanel,BorderLayout.CENTER);
        mainPanel.invalidate();
        mainPanel.validate();
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
