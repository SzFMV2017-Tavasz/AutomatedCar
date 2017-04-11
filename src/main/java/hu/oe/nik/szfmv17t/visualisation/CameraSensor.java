package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.utils.Resizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winifred on 2017.04.10..
 */
public class CameraSensor {

    private Resizer resizer;

    private double viewAngle;
    private double viewDistanceInMeter;
    private double viewDistance;

    //latomezo koordinatai
    private double centerX;
    private double centerY;
    private double leftUpperCornerX;
    private double leftUpperCornerY;
    private double rightUpperCornerX;
    private double rightUpperCornerY;

    //bal es jobb latomezo sarkahoz hozaadott tavolsag
    private double addingOffsetDistanceInMeter;
    private double addingOffsetDistance;

    //relevans objektumok
    private List<IWorldObject> relevantWorldObjects;

    public CameraSensor(IWorldObject carObject, List<IWorldObject> worldObjects) {
        resizer = Resizer.getResizer();
        this.viewAngle = 60;
        this.viewDistanceInMeter = 110;

        viewDistance = resizer.meterToCoordinate(viewDistanceInMeter);
        addingOffsetDistanceInMeter = calculateOffsetDistance();
        addingOffsetDistance = resizer.meterToCoordinate(addingOffsetDistanceInMeter);

        calculateFieldViewCoordinates(carObject);

        relevantWorldObjects = new ArrayList<IWorldObject>();
    }

    /*a latomezo 3 koordinatajat allitja be
     */
    private void calculateFieldViewCoordinates(IWorldObject car) {
        centerX = car.getCenterX();
        centerY = car.getCenterY();

        //direction angle kell??
        double carRotation = Math.toDegrees(car.getAxisAngle());

        //itt az auto forgasatol eltekintunk
        double leftUpperXBase = centerX - addingOffsetDistance;
        double leftUpperYBase = centerY - viewDistance;
        double rightUpperXBase = centerX + addingOffsetDistance;
        double rightUpperYBase = centerY - viewDistance;

        /*auto rotacios erteket figyelve
        az otlet
        https://prog.hu/tudastar/10748/pont-forgatasa-egy-masik-pont-korul-2d-ben
        a feljebb kiszamolt pontokat rotaljuk el a uj pontra
        * */
        leftUpperCornerX = (((leftUpperXBase - centerX) * Math.cos(carRotation)) - ((leftUpperYBase - centerY) * Math.sin(carRotation))) + centerX;
        leftUpperCornerY = (((leftUpperXBase - centerX) * Math.sin(carRotation)) + (leftUpperYBase - centerY) * Math.cos(carRotation)) + centerY;

        rightUpperCornerX = (((rightUpperXBase - centerX) * Math.cos(carRotation)) - ((rightUpperYBase - centerY) * Math.sin(carRotation))) + centerX;
        rightUpperCornerY = (((rightUpperXBase - centerX) * Math.sin(carRotation)) + (rightUpperYBase - centerY) * Math.cos(carRotation)) + centerY;

    }

    private double calculateOffsetDistance() {
        double baseOfTriangle;
        //haromszog atfogo
        double hypotenuse = viewDistanceInMeter / Math.cos(viewAngle / 2);
        baseOfTriangle = 2 * Math.tan(viewAngle / 2) * hypotenuse;
        return baseOfTriangle;
    }

    private double carDistanceFromObject(IWorldObject carObject, IWorldObject worldObject) {
        double result = 0;
        return result;
    }

    private List<IWorldObject> getRelevantWorldObjects() {
        List<IWorldObject> result = new ArrayList<IWorldObject>();

        return result;
    }
}
