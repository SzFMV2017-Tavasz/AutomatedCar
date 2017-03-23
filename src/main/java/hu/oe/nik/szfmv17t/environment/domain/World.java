package hu.oe.nik.szfmv17t.environment.domain;

import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldVisualisation;
import hu.oe.nik.szfmv17t.environment.utils.XmlParser;
import hu.oe.nik.szfmv17t.environment.utils.*;
import java.util.ArrayList;
import java.util.List;

public class World implements IWorldVisualisation {

    private int width = 0;
    private int height = 0;
    private List<IWorldObject> worldObjects = new ArrayList<>();
    private XmlParser xmlParser;

    public void updateWorld() {
        for (IWorldObject object : worldObjects) {
            if (object instanceof CollidableBase) {
                ///TODO call collisiondetection
                ((CollidableBase) object).updateWorldObject();
                for(IWorldObject bObject : worldObjects)
                {
                    if (bObject instanceof CollidableBase&& CollisionDetector.collide((CollidableBase)object,(CollidableBase) bObject))
                    {
                        //System.out.println("Ütközés: "+object+" "+bObject+"-vel ");//Need logic behind this
                    }
                }
            }
        }
    }

    public World(String pathToXml) {
        xmlParser = new XmlParser(pathToXml);
        width = xmlParser.getMapWidth();
        height = xmlParser.getMapHeight();
        worldObjects = xmlParser.getWorldObjects();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<IWorldObject> getWorld() {
        return worldObjects;
    }

    public void addObjectToWorld(IWorldObject o) {
        worldObjects.add(o);
    }
}
