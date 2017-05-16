package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.environment.domain.Car;
import hu.oe.nik.szfmv17t.environment.domain.Turn;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldVisualisation;
import hu.oe.nik.szfmv17t.environment.utils.Config;
import hu.oe.nik.szfmv17t.environment.utils.StringUtil;
import hu.oe.nik.szfmv17t.visualisation.interfaces.IWorldVisualization;
import hu.oe.nik.szfmv17t.visualisation.viewmodels.CameraObject;
import hu.oe.nik.szfmv17t.environment.domain.WorldObjectState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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

    private IWorldVisualization world;

    @Override
    public List<IWorldObject> getWorld() {
        return world.getWorld();
    }

    private static Drawer instance = null;
    private static HashMap<String, BufferedImage> worldImages;

    private Drawer(IWorldVisualisation world) {
    }

    public static Drawer getDrawer(IWorldVisualisation world) throws IOException {
        if (instance == null) {
            worldImages = new HashMap<>();
            instance = new Drawer(world);

            for (IWorldObject object : world.getWorld())
            {
                String imageName = object.getImageName();
                if ( !worldImages.containsKey(imageName) )
                {
                    BufferedImage bufferedImage = ImageIO.read(new File(ClassLoader.getSystemResource(object.getImageName()).getFile()));
                    worldImages.put(imageName, bufferedImage);
                }
            }
        }
        return instance;
    }

    public FrameComposer getComposer(IWorldVisualisation world, JPanel worldPanel) {
        return FrameComposer.getComposer(world, worldPanel);
    }


    public void DrawFrametoPanel(JPanel worldObjectsPanel, IWorldVisualisation world, JPanel mainPanel) {
        BorderLayout layout = (BorderLayout) mainPanel.getLayout();
        mainPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        FrameComposer fc = getComposer(world, worldObjectsPanel);
        fc.setCameraSize(worldObjectsPanel.getWidth(), worldObjectsPanel.getHeight());
        List<CameraObject> toDraw = fc.composeFrame();

        worldObjectsPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g) {
                BufferedImage image;
                Graphics2D g2d = (Graphics2D) g.create();
                for (CameraObject object : toDraw) {
                    // draw objects
                    IWorldObject wobject = object.getWorldObject();
                    WorldObjectState state = wobject.getState();
                    String imageName = wobject.getImageName();
                    if(state == WorldObjectState.Damaged || state == WorldObjectState.Destroyed){
                        if (!checkHashMap(imageName,state)){
                            try {
                                insertIntoHashMap(wobject,state);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        String strippedImageName = StringUtil.removeExtension(imageName);
                        image = worldImages.get(strippedImageName+"_"+state.toString()+".png");
                    }
                    else {
                        image = worldImages.get(imageName);
                    }
                    g2d.drawImage(image, getObjectTransformation(calculateDrawCornerX(object), calculateDrawCornerY(object), object.getWorldObject()), null);
                }
            }
        };
        mainPanel.add(worldObjectsPanel, BorderLayout.CENTER);
    }
    private AffineTransform getCornerRotateTransform(IWorldObject object)
    {
        int prefix = Car.class.isInstance(object) ? 1 : -1;
        if (calculateRotateBaseY(object)!=Double.MIN_VALUE){
            return AffineTransform.getRotateInstance(prefix *object.getAxisAngle(),calculateRotateBaseX(object),calculateRotateBaseY(object));
        }
        return AffineTransform.getRotateInstance(prefix *object.getAxisAngle());
    }
    private double calculateRotateBaseY(IWorldObject object)
    {
        if (Turn.class.isInstance(object))
        {
            if (object.getImageName()=="road_2lane_tjunctionright.png" || object.getImageName()=="road_2lane_tjunctionleft.png")
                return 0;
            else
               return (object.getHeight()) / Config.SCALE;
        }
        return Double.MIN_VALUE;
    }
    private double calculateRotateBaseX(IWorldObject object)
    {
        if (Turn.class.isInstance(object))
        {
            if (object.getImageName()=="road_2lane_tjunctionright.png" || object.getImageName()=="road_2lane_tjunctionleft.png")
                return object.getWidth()/Config.SCALE;
            else if (object.getImageName()=="road_2lane_90left.png" || object.getImageName()=="road_2lane_45left.png" )
                return (object.getWidth()-Config.roadWidth)/Config.SCALE;
            else if (object.getImageName()=="road_2lane_6left.png" ) {
                return (Config.roadWidth + object.getWidth()-Config.roadWidth) / Config.SCALE;
            }
            else
               return Config.roadWidth/Config.SCALE;
        }
        return Double.MIN_VALUE;
    }
    private double calculateDrawCornerY(CameraObject cameraObject)
    {
        IWorldObject worldObject = cameraObject.getWorldObject();
        double drawCornerY;

        if (Turn.class.isInstance(worldObject))
        {
            double baseY=(cameraObject.getY()-(worldObject.getHeight()/2));
            if (worldObject.getImageName()=="road_2lane_tjunctionright.png")
                drawCornerY=baseY + worldObject.getWidth();
            else if (worldObject.getImageName()=="road_2lane_tjunctionleft.png")
                drawCornerY=baseY;
            else
                drawCornerY = (baseY - worldObject.getHeight());
        }
        else
            drawCornerY = ((cameraObject.getY() - worldObject.getHeight()/2)) ;
        return drawCornerY/Config.SCALE;
    }
    private double calculateDrawCornerX(CameraObject cameraObject)
    {
        IWorldObject worldObject = cameraObject.getWorldObject();
        double drawCornerX;

        if (Turn.class.isInstance(worldObject))
        {
            double baseX=(cameraObject.getX()-(worldObject.getWidth()/2));
            if (worldObject.getImageName()=="road_2lane_tjunctionright.png" || worldObject.getImageName()=="road_2lane_tjunctionleft.png")
                drawCornerX = (baseX - worldObject.getWidth());
            else if (worldObject.getImageName()=="road_2lane_45left.png" || worldObject.getImageName()=="road_2lane_90left.png" )
                drawCornerX = (baseX - (worldObject.getWidth()-Config.roadWidth));
            else if (worldObject.getImageName()=="road_2lane_6left.png" ) {
                drawCornerX = (cameraObject.getX() - ((worldObject.getWidth()/2)+(worldObject.getWidth()-Config.roadWidth))) - Config.roadWidth;
            }
            else
                drawCornerX=baseX-Config.roadWidth;
        }
        else
            drawCornerX = ((int) (cameraObject.getX() - worldObject.getWidth() / 2)) ;
        return drawCornerX/Config.SCALE;
    }
    private AffineTransform getObjectTransformation(double drawCornerX,double drawCornerY, IWorldObject object)
    {
        AffineTransform transform=AffineTransform.getTranslateInstance(drawCornerX,drawCornerY);
        transform.concatenate(getCornerRotateTransform(object));
        transform.scale(Config.SCALENUM, Config.SCALENUM);
        return transform;
    }
    private void putDebugInformationOnImage(Image image, IWorldObject object) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        String loc = String.format ("x: %.0f, y:%.0f", object.getCenterXVisual(), object.getCenterYVisual(), object.getAxisAngle());
        String rot = String.format ("%.3f (rad)", object.getAxisAngle());

        g.setColor(Color.red);
        g.drawRect(0, 0, image.getWidth(null) - 1, image.getHeight(null) - 1);

        g.setColor(Color.black);
        g.setFont(new Font("sans", Font.PLAIN, 15));
        g.drawString(loc, 3, 20);
        g.drawString(rot, 3, 35);
    }

    private boolean checkHashMap (String imageName, WorldObjectState state) {
        return worldImages.containsKey(imageName+"_"+state.toString());
    }
    private void insertIntoHashMap(IWorldObject object, WorldObjectState state) throws IOException{
        String strippedImageName = StringUtil.removeExtension(object.getImageName());
        String imageName = strippedImageName+"_"+state.toString()+".png";

        BufferedImage bufferedImage = ImageIO.read(new File(ClassLoader.getSystemResource(imageName).getFile()));
        worldImages.put(imageName, bufferedImage);
    }
}
