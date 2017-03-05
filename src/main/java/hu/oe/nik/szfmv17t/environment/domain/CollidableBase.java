package hu.oe.nik.szfmv17t.environment.domain;

import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;
import hu.oe.nik.szfmv17t.environment.utils.Position;

/**
 * Created by Bábel Gellért, Budai Krisztián, Molnár Attila on 2017. 03. 04..
 */
public class CollidableBase extends WorldObjectBase implements ICollidableObject {
    protected double mass;
    protected double speed;

    public CollidableBase (double positionX
            , double positionY
            , double width
            , double height
            , double axisAngle
            , int zIndex
            , String imageFilePath
            , double mass
            , double speed
            , double directionAngle){
        super (positionX, positionY, width, height, axisAngle, zIndex, imageFilePath, directionAngle);

        this.mass = mass;
        this.speed = speed;
    }

    public double getMass ()
    {
        return this.mass;
    }

    public double getSpeed ()
    {
        return this.speed;
    }

    public Position getPositionObj ()
    {
        return this.position;
    }

    public void setSpeed (double value)
    {
        this.speed = value;
    }

    public void rotate (double value)
    {
        this.position.rotate (value);
    }

    public void setAxisAngle (double value)
    {
        this.position.setAxisAngle(value);
    }

    public void setDirectionAngle (double value)
    {
        this.position.setDirectionAngle(value);
    }
}
