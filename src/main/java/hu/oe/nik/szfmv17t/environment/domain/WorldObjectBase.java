package hu.oe.nik.szfmv17t.environment.domain;

import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.utils.Position;
import hu.oe.nik.szfmv17t.environment.utils.Vector2d;


/**
 * Created by Bábel Gellért, Budai Krisztián, Molnár Attila on 2017. 03. 04.
 * Modified by: Budai Krisztián, Molnár Attila on 2017. 04. 09.
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
            , double axisAngleInRadian
            , int zIndex
            , String imageFilePath
            , double directionAngleInRadian, double origPosX, double origPosY) {
        this.position = new Position(new Vector2d(positionX, positionY), width, height, axisAngleInRadian, directionAngleInRadian, new Vector2d(origPosX,origPosY));
        this.state = WorldObjectState.Untouched;
        this.imageFilePath = imageFilePath;
        this.zIndex = zIndex;
    }

    public double getAxisAngle ()
    {
        return  this.position.getAxisAngleInRadian();
    }

    public double getDirectionAngle ()
    {
        return  this.position.getDirectionAngleInRadian();
    }

    public double getCenterX ()
    {
        return this.position.getCenter().getX();
    }

    public double getCenterY ()
    {
        return this.position.getCenter().getY();
    }

    public double getCenterXVisual ()
    {
        return this.position.getCenterVisualisation().getX();
    }

    public double getCenterYVisual ()
    {
        return this.position.getCenterVisualisation().getY();
    }

    public double getReferencePointX() { return this.position.getReferencePointX();}

    public double getReferencePointY() {return this.position.getReferencePointY(); }

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

