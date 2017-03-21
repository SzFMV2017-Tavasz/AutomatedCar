package hu.oe.nik.szfmv17t.environment.domain;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;
import hu.oe.nik.szfmv17t.environment.utils.Position;
import hu.oe.nik.szfmv17t.environment.utils.Vector2d;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
             double directionAngle) {
        super(positionX, positionY, width, height, axisAngle, zIndex, imageFilePath, directionAngle);

        this.mass = mass;
        this.speed = speed;
        way = new ArrayList<Vector2d>();
        wayGenerator();
    }

    List<Vector2d> way;
    Random random = new Random();

    private void step() {
        if (inTarget(new double[]{Math.round(this.getCenterX()),Math.round(this.getCenterY()), way.get(0).getX(), way.get(0).getY()})) {
            Vector2d temp = way.get(0);
            way.remove(0);
            way.add(way.size() - 1, temp);
        }
        Vector2d directionVector = way.get(0).substract(new Vector2d(this.getCenterX(), this.getCenterY())); //new double[]{(way.get(0)[0] - getCenterX()), (way.get(0)[1] - getCenterY())};

        double vectorLength =way.get(0).length(new Vector2d(this.getCenterX(), this.getCenterY())); //vectorLength(new double[]{(int)Math.round(this.getCenterX()), (int)Math.round(this.getCenterY()), way.get(0)[0], way.get(0)[1]});

        Vector2d oneStep = way.get(0).unitDirection(directionVector,vectorLength);
        //irányba kell állítani az elemet

        position.setPositionX(getCenterX() + oneStep.getX() * getSpeed());
        position.setPositionY(getCenterY() + oneStep.getY() * getSpeed());
    }

    public void updateWorldObject() {
        if (!(this instanceof AutomatedCar)) {
            step();
        }
    }

    //-----
    private void wayGenerator() {
        for (int i = 0; i < 10; i++) {
            way.add(new Vector2d(random.nextInt(4820), random.nextInt(2700)));
        }
    }

    private /*public*/ boolean inTarget(double[] xy) {
        if (xy[0] >= xy[2] && xy[1] >= xy[3]) {
            return true;
        }
        return false;
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
        this.position.setAxisAngle(value);
    }

    public void setDirectionAngle(double value) {
        this.position.setDirectionAngle(value);
    }
}
