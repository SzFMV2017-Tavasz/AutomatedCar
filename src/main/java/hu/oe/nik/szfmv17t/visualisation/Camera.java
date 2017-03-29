package hu.oe.nik.szfmv17t.visualisation;

import java.awt.*;

/**
 * Created by Mariusz on 2017.03.15..
 */
public class Camera {
    private static Camera instance = null;

    public static Camera getInstance() {
        return instance;
    }

    public static void setInstance(Camera instance) {
        Camera.instance = instance;
    }

    private double x;
    private double y;
    private int width; //TEMP
    private int height; //TEMP
    private float scale;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }


    protected Camera() {
    }

    public static Camera getCamera() {
        if (instance == null)
            instance = new Camera();
        return instance;
    }

    public Rectangle getCameraRectangle() {
        Rectangle cameraRectangle = new Rectangle();
        cameraRectangle.width = getWidth();
        cameraRectangle.height = getHeight();
        cameraRectangle.x = (int) getX();
        cameraRectangle.y = (int) getY();
        return cameraRectangle;
    }
}
