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

    public void DrawFrametoPanel(JPanel worldObjectsPanel,World world,JPanel mainPanel)
    {
        List<IWorldObject> toDraw=getComposer(world).composeFrame();

        worldObjectsPanel = new JPanel() {
            private static final long serialVersionUID = 1L;
            public void paintComponent(Graphics g) {
                for (IWorldObject object : toDraw) {
                    // draw objects
                    BufferedImage image;
                    Graphics2D g2d=(Graphics2D)g;
                    try {
                        image = ImageIO.read(new File(ClassLoader.getSystemResource(object.getImageName()).getFile()));

                        int segedx=((int)(object.getCenterX()+0.5d));
                        int segedy=((int)(object.getCenterY()+0.5d));

                        image = transformImage(object,image);       
                        //DEBUG OVERLAY
                        PutDebugInformationOnImage(image, object);  
                        g2d.drawImage(image, segedx, segedy, null);
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

    private BufferedImage transformImage(IWorldObject object, BufferedImage image){
        AffineTransform transform = new AffineTransform();
        double rot = object.getAxisAngle();
        if (rot>=0)
            transform.rotate(rot, image.getWidth()/2, image.getHeight()/2);
        else
            transform.rotate((2*Math.PI) + (Math.PI/2) + rot, image.getWidth()/2, image.getHeight()/2);

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage bimage = op.filter(image, null);
        return bimage;

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

   /* public void Loop() throws InterruptedException {
        int refreshRate=calculateRefresh(Main.FPS);
        while (true)
        {
            Thread.sleep(refreshRate);
            fc.composeFrame();
        }
    }*/
}
