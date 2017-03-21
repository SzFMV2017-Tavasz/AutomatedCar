package hu.oe.nik.szfmv17t.environment.domain;

import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.utils.Position;


/**
 * Created by Bábel Gellért, Budai Krisztián, Molnár Attila on 2017. 03. 04..
 */
public abstract class WorldObjectBase implements IWorldObject {
    protected Position position;
    protected String imageFilePath;
    protected int zIndex;
    protected WorldObjectState state;

    public WorldObjectBase ( double positionX
                            , double positionY
                            , double width
                            , double height
                            , double axisAngle
                            , int zIndex
                            , String imageFilePath
                            , double directionAngle) {
        this.position = new Position(positionX, positionY, width, height, axisAngle, directionAngle);
        this.state = WorldObjectState.Untouched;
        this.imageFilePath = imageFilePath;
        this.zIndex = zIndex;
    }

    public double getAxisAngle ()
    {
        return  this.position.getAxisAngle();
    }

    public double getDirectionAngle ()
    {
        return  this.position.getDirectionAngle();
    }

    public double getCenterX ()
    {
        return this.position.getCenter().getX();
    }

    public double getCenterY ()
    {
        return this.position.getCenter().getY();
    }

    public String getImageName ()
    {
        return  this.imageFilePath;
    }

    public int getZIndex ()
    {
        return  this.zIndex;
    }

    public WorldObjectState getState ()
    {
        return  this.state;
    }

    public void setState (WorldObjectState value)
    {
        this.state = value;
    }
    
    public double getWidth()
    {
        return this.position.getWidth();
    }
    public double getHeight()
    {
        return this.position.getHeight();
    }
}

