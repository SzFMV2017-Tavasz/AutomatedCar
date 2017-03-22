package hu.oe.nik.szfmv17t.environment.domain;

import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldVisualisation;
import hu.oe.nik.szfmv17t.environment.utils.XmlParser;
import hu.oe.nik.szfmv17t.environment.utils.*;
import hu.oe.nik.szfmv17t.physics.interfaces.ICollisionHandler;
import java.util.ArrayList;
import java.util.List;

public class World implements IWorldVisualisation {

    private VirtualFunctionBus busz;
    private int width = 0;
    private int height = 0;
    private List<IWorldObject> worldObjects = new ArrayList<>();
    private XmlParser xmlParser;
    private ICollisionHandler collisionHandler;

    public void updateWorld() {
        for (IWorldObject object : worldObjects) {
            if (object instanceof CollidableBase) {
                ///TODO call collisiondetection
                ((CollidableBase) object).updateWorldObject();
                for(IWorldObject bObject : worldObjects)
                {
                    if (bObject instanceof CollidableBase&& CollisionDetector.collide((CollidableBase)object,(CollidableBase) bObject))
                    {
                        if (object!=bObject) {
                            collisionHandler.handleCollision((CollidableBase)object,(CollidableBase)bObject);
                        }
                    }
                }
            }
        }
    }

    public World(String pathToXml) {
        busz= VirtualFunctionBus.getInstance();
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
