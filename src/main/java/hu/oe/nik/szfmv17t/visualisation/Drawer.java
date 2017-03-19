package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.Main;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.visualisation.interfaces.IWorldVisualization;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.*;
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
    private static ArrayList <BufferedImage> worldImages;
    private Drawer(World world)
    {}
    public static Drawer getDrawer(World world) throws IOException {
        if (instance==null) {
            worldImages=new ArrayList<BufferedImage>();
            instance = new Drawer(world);
            for (IWorldObject object:world.getWorldObjects()) {
                BufferedImage bimg = ImageIO.read(new File(ClassLoader.getSystemResource(object.getImageName()).getFile()));
                worldImages.add(bimg);
            }
        }
        return instance;
    }

    public FrameComposer getComposer(World world)
    {
        return FrameComposer.getComposer(world);
    }
    private static int t=0;
    public void DrawFrametoPanel(JPanel worldObjectsPanel, World world, JPanel mainPanel)
    {
        BorderLayout layout = (BorderLayout)mainPanel.getLayout();
        mainPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));;
        List<IWorldObject> toDraw=getComposer(world).composeFrame();

        worldObjectsPanel = new JPanel() {
            private static final long serialVersionUID = 1L;
            public void paintComponent(Graphics g) {
                int t2=0;
                BufferedImage image;
                Graphics2D g2d=(Graphics2D)g.create();
                for (IWorldObject object : toDraw) {
                    // draw objects
                    image = worldImages.get(t2++);

                    int segedx=((int)(object.getCenterX()-object.getWidth()/2));
                    int segedy=((int)(object.getCenterY()-object.getHeight()/2)+t);

                    PutDebugInformationOnImage(image, object);
                    AffineTransform transform = new AffineTransform();
                    transform.translate(segedx, segedy);
                    transform.rotate(object.getAxisAngle()+Math.PI/2);
                    //DEBUG OVERLAY
                    g2d.drawImage(image,transform, null);
                }
                g2d.dispose();
                t+=5;
            }
        };
        mainPanel.add(worldObjectsPanel,BorderLayout.CENTER);
    }
    private void PutDebugInformationOnImage (Image image, IWorldObject object) {
        Graphics2D g = (Graphics2D) image.getGraphics();

        String loc = String.format ("x: %.0f, y:%.0f", object.getCenterX(), object.getCenterY(), object.getAxisAngle());
        String rot = String.format ("%.3f (rad)", object.getAxisAngle());

        g.setColor(Color.red);
        g.drawRect(0, 0, image.getWidth(null) - 1, image.getHeight(null) - 1);

        g.setColor(Color.black);
        g.setFont(new Font("sans", Font.PLAIN, 15));
        g.drawString(loc, 3, 20);
        g.drawString(rot, 3, 35);
    }
}
