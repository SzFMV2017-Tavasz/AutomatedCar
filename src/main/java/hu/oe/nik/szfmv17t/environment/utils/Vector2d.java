package hu.oe.nik.szfmv17t.environment.utils;
import java.util.Vector;

/**
 * Created by Budai Krisztián, Molnár Attila on 2017. 03. 04.
 * Last modified by: Budai Krisztián, Molnár Attila on 2017. 04. 09.
 */

public class Vector2d {
    private double x,y;

    public Vector2d (double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX ()
    {
        return this.x;
    }

    public double getY ()
    {
        return this.y;
    }

    public void setX (double value)
    {
        this.x = value;
    }

    public void setY (double value)
    {
        this.y = value;
    }

    public static double dot (Vector2d v1, Vector2d v2)
    {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    public Vector2d unit ()
    {
        double length = this.length();
        return new Vector2d(this.x/length, this.y/length);
    }

    public double length () {
        return Math.sqrt(Math.pow(this.x,2) + Math.pow(this.y,2));
    }


    public Vector2d add (Vector2d v2)
    {
        return new Vector2d(this.x + v2.x, this.y + v2.y);
    }

    public Vector2d substract (Vector2d v2)
    {
        return  new Vector2d(this.x - v2.x, this.y - v2.y);
    }

    public static Vector2d rotateAroundPoint (Vector2d point, Vector2d rotationPoint, double angleInRadian)
    {
        double xInOrigin = point.getX() - rotationPoint.getX();
        double yInOrigin = point.getY() - rotationPoint.getY();

        double x = xInOrigin * Math.cos(angleInRadian) - yInOrigin * Math.sin(angleInRadian);
        double y = xInOrigin * Math.sin(angleInRadian) + yInOrigin * Math.cos(angleInRadian);

        x += rotationPoint.getX();
        y += rotationPoint.getY();

        return new Vector2d(x,y);
    }
}
