package hu.oe.nik.szfmv17t.environment.domain;

import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;
import hu.oe.nik.szfmv17t.environment.utils.Position;
import hu.oe.nik.szfmv17t.environment.utils.Vector2d;

/**
 * Created by Bábel Gellért, Budai Krisztián, Molnár Attila on 2017. 03. 04..
 */
public class CollidableBase extends WorldObjectBase implements ICollidableObject {

    protected double mass;
    protected double speed;

    public CollidableBase(double positionX,
                          double positionY,
                          double width,
                          double height,
                          double axisAngle,
                          int zIndex,
                          String imageFilePath,
                          double mass,
                          double speed,
                          double directionAngle, double origPosX, double origPosY) {
        super(positionX, positionY, width, height, axisAngle, zIndex, imageFilePath, directionAngle, origPosX, origPosY);

        this.mass = mass;
        this.speed = speed;
    }


    public void updateWorldObject() {
        Vector2d direction = new Vector2d(Math.cos(this.getDirectionAngle()), Math.sin(this.getDirectionAngle()));
        position.setReferencePointX(position.getReferencePointX() + direction.getX() * getSpeed());
        position.setReferencePointY(position.getReferencePointY() + direction.getY() * getSpeed());
    }

    public double getMass() {
        return this.mass;
    }

    public double getSpeed() {
        return this.speed;
    }

    public Position getPositionObj() {
        return this.position;
    }

    public void setSpeed(double value) {
        this.speed = value;
    }

    public void rotate(double value) {
        this.position.rotate(value);
    }

    public void setAxisAngle(double value) {
        this.position.setAxisAngleInRadian(value);
    }

    public void setDirectionAngle(double value) {
        this.position.setDirectionAngleInRadian(value);
    }
}
