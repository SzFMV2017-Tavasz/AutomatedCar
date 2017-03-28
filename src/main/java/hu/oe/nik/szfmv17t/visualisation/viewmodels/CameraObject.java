package hu.oe.nik.szfmv17t.visualisation.viewmodels;

import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.utils.Config;
import hu.oe.nik.szfmv17t.visualisation.Camera;

/**
 * Created by dlac0 on 2017. 03. 23..
 */
public class CameraObject
{
    private IWorldObject worldObject;

    private double X;

    private double Y;

    public IWorldObject getWorldObject() {
        return worldObject;
    }

    public void setWorldObject(IWorldObject worldObject) {
        this.worldObject = worldObject;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public CameraObject()
    {}

    public CameraObject(IWorldObject object, IWorldObject car, Camera camera)
    {
        setWorldObject(object);

        double carX = car.getCenterX();
        double carY = car.getCenterY();
        double objectX = object.getCenterX();
        double objectY = object.getCenterY();

        double offsetX = carX - objectX;
        double offsetY = carY - objectY;

        setX(camera.getX() + offsetX);
        setY(camera.getY() + offsetY);
    }

    public CameraObject(IWorldObject car, Camera camera)
    {
        setWorldObject(car);
        setX((camera.getWidth() / 2)*Config.SCALE);
        setY((camera.getHeight() / 2)* Config.SCALE);//multiplier, so id doesn't get down/upscaled at positioning, since center is always center regardless of scaling
    }
}
