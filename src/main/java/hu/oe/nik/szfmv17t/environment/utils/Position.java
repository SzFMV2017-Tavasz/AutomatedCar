package hu.oe.nik.szfmv17t.environment.utils;

import java.awt.geom.Point2D;
import java.util.Vector;

/**
 * Created by Budai Krisztián, Molnár Attila on 2017. 03. 04. Modified by: Budai
 * Krisztián, Molnár Attila on 2017. 04. 09.
 */
public class Position {

    private Vector2d leftUpperCorner, rightUpperCorner, leftLowerCorner, rightLowerCorner;
    private Vector2d center;
    private double width, height;
    private double axisAngleInRadian, directionAngleInRadian;
    private Vector2d originalRef;

    public Position(Vector2d leftUpperCorner,
                    double width,
                    double height,
                    double axisAngleInRadian,
                    double directionAngleInRadian,
                    Vector2d originalRef
    ) {
        this.leftUpperCorner = leftUpperCorner;
        this.width = width;
        this.height = height;
        this.axisAngleInRadian = axisAngleInRadian;
        this.directionAngleInRadian = directionAngleInRadian;
        this.center = new Vector2d(leftUpperCorner.getX() + (this.width / 2), leftUpperCorner.getY() + (this.height / 2));
        this.calculateCenter();
        this.calculateCorners();

        this.originalRef = originalRef;
    }

    private Vector2d rotatePoint(double posX, double posY, double angleInRadian) {
        Vector2d pointToRotateAround = this.leftUpperCorner;
        Vector2d pointToRotate = new Vector2d(posX, posY);

        return Vector2d.rotateAroundPoint(pointToRotate, pointToRotateAround, angleInRadian);
    }

    private void calculateCenter() {
        //this.centerX = this.minimumX + (this.width / 2);
        //this.centerY = this.minimumY + (this.height / 2);
        this.center = rotatePoint(leftUpperCorner.getX() + (this.width / 2), leftUpperCorner.getY() + (this.height / 2), this.axisAngleInRadian);
    }

    private void calculateCorners() {
        this.rightUpperCorner = rotatePoint(leftUpperCorner.getX() + this.width, leftUpperCorner.getY(), this.axisAngleInRadian);
        this.leftLowerCorner = rotatePoint(leftUpperCorner.getX(), leftUpperCorner.getY() + this.height, this.axisAngleInRadian);
        this.rightLowerCorner = rotatePoint(leftUpperCorner.getX() + this.width, leftUpperCorner.getY() + this.height, this.axisAngleInRadian);
    }

    public double getReferencePointX() {
        return this.leftUpperCorner.getX();
    }

    public double getReferencePointY() {
        return this.leftUpperCorner.getY();
    }

    public Vector2d getLeftUpperCorner() {
        return new Vector2d(this.getReferencePointX(), this.getReferencePointY());
    }

    public Vector2d getRightUpperCorner() {
        return this.rightUpperCorner;
    }

    public Vector2d getLeftLowerCorner() {
        return this.leftLowerCorner;
    }

    public Vector2d getRightLowerCorner() {
        return this.rightLowerCorner;
    }

    public double getAxisAngleInRadian() {
        return this.axisAngleInRadian;
    }

    public double getDirectionAngleInRadian() {
        return this.directionAngleInRadian;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public Vector2d getCenter() {
        return this.center;
    }

    public void setReferencePoint(Vector2d referencePoint) {
        Vector2d diff = this.leftUpperCorner.substract(referencePoint);
        this.originalRef = this.originalRef.substract(diff);

        this.leftUpperCorner = referencePoint;
        this.calculateCenter();
        this.calculateCorners();
    }

    public void setReferencePointX(double value) {
        double diff = this.leftUpperCorner.getX() - value;
        this.originalRef = this.originalRef.substract(new Vector2d(diff, 0));

        this.leftUpperCorner.setX(value);
        this.calculateCenter();
        this.calculateCorners();
    }

    public void setReferencePointY(double value) {
        double diff = this.leftUpperCorner.getY() - value;
        this.originalRef = this.originalRef.substract(new Vector2d(0, diff));

        this.leftUpperCorner.setY(value);
        this.calculateCenter();
        this.calculateCorners();
    }

    public void setAxisAngleInRadian(double value) {
        this.axisAngleInRadian = value;
        this.calculateCenter();
        this.calculateCorners();
    }

    public void setDirectionAngleInRadian(double value) {
        this.directionAngleInRadian = value;
        this.calculateCenter();
        this.calculateCorners();
    }

    public void rotate(double value) {
        this.axisAngleInRadian += value;
        this.directionAngleInRadian += value;
        this.calculateCenter();
        this.calculateCorners();
    }

    public Vector2d getCenterVisualisation ()
    {
        return new Vector2d(this.originalRef.getX() + this.width/2, this.originalRef.getY() + this.height/2);
    }
}
