package hu.oe.nik.szfmv17t.environment.domain;

import hu.oe.nik.szfmv17t.environment.utils.Vector2d;

import java.util.List;

/**
 * Created by Budai Krisztián, Molnár Attila on 2017. 04. 01.
 */
public class Npc extends CollidableBase {

    protected List<Vector2d> path;
    private int pathIndex;
    public Npc(double positionX,
               double positionY,
               double width,
               double height,
               double axisAngle,
               int zIndex,
               String imageFilePath,
               double mass,
               double speed,
               double directionAngle,
               List<Vector2d> path, double origPosX, double origPosY) {
        super(positionX, positionY, width, height, axisAngle, zIndex, imageFilePath, mass, speed, directionAngle, origPosX, origPosY);
        this.path = path;
        pathIndex = 0;
    }

    private void calculateDirectionAngle() {
        Vector2d destination = path.get(pathIndex);
        Vector2d directionVector = destination.substract(new Vector2d(this.getCenterX(), this.getCenterY())).unit();
        this.setDirectionAngle(Math.atan2(directionVector.getY(), directionVector.getX()));
        this.setAxisAngle(this.getDirectionAngle());
    }

    private void checkDestinationRange() {
        Vector2d destination = path.get(pathIndex);
        double destinationRange = destination.substract(new Vector2d(this.getCenterX(), this.getCenterY())).length();
        if (destinationRange < 20d) {
            setNextDestination();
        }
    }

    private void setNextDestination() {
        if (pathIndex + 1 == path.size()) {
            pathIndex = 0;
        } else {
            pathIndex++;
        }
    }

    @Override
    public void updateWorldObject(){
        checkDestinationRange();
        calculateDirectionAngle();
        super.updateWorldObject();
    }
}

