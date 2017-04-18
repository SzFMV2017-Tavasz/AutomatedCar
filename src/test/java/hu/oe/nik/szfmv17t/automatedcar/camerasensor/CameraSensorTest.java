package hu.oe.nik.szfmv17t.automatedcar.camerasensor;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.domain.Road;
import hu.oe.nik.szfmv17t.environment.utils.Resizer;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by winifred on 2017.04.13..
 */
public class CameraSensorTest {

    private Resizer resizer;
    private AutomatedCar car;
    //90 fokkal elforgatva a car2
    private AutomatedCar car2;
    private CameraSensor cameraSensor;
    Road road1;

    private double viewAngle;
    private double viewDistanceInMeter;
    private double addingOffsetDistanceInMeter;
    private double addingOffsetDistanceInCoordinates;
    private double viewDistanceInCoordinates;

    private Point center;
    private Point leftPoint;
    private Point rightPoint;

    @Before
    public void setUp() {
        resizer = Resizer.getResizer();
        car = new AutomatedCar(100, 100, 108, 240, 0d, 0, "car_1_white.png", 200d, 0d, 0d);
        car2 = new AutomatedCar(100, 100, 108, 240, 0d, 0, "car_1_white.png", 200d, 0d, 1.57d);
        road1 = new Road(0, 0, 350, 350, 0, 0, "road_2lane_straight.png", 0, 1, 1, 1);

        cameraSensor = new CameraSensor(car);

        viewAngle = 60;
        viewDistanceInMeter = 110;
        addingOffsetDistanceInMeter = cameraSensor.calculateOffsetDistance();

        addingOffsetDistanceInCoordinates = resizer.meterToCoordinate(addingOffsetDistanceInMeter);
        viewDistanceInCoordinates = resizer.meterToCoordinate(viewDistanceInMeter);

        center = new Point((int) car.getCenterX(), (int) car.getCenterY());
    }

    @Test
    public void calculateOffsetDistanceTest() {
        double result = cameraSensor.calculateOffsetDistance();
        assertTrue(addingOffsetDistanceInMeter == result);
    }

    @Test
    public void calculateCenterPointTest() {
        Point resultPoint = cameraSensor.calculateCenterPoint(car);
        assertEquals(center, resultPoint);
    }

    @Test
    public void calculateLeftCornerPointTest() {
        double leftUpperXBase = center.getX() - addingOffsetDistanceInCoordinates;
        double leftUpperYBase = center.getY() - viewDistanceInCoordinates;

        //double rotation = Math.toRadians(360);
        double[] coordinates = {leftUpperXBase, leftUpperYBase};
        double angleOfRotationInDeg = (360 - Math.toDegrees(car.getDirectionAngle()));
        AffineTransform.getRotateInstance(Math.toRadians(angleOfRotationInDeg), center.getX(), center.getY()).transform(coordinates, 0, coordinates, 0, 1);

        double leftUpperCornerX = coordinates[0];
        double leftUpperCornerY = coordinates[1];
        leftPoint = new Point((int) leftUpperCornerX, (int) leftUpperCornerY);

        Point resultPoint = cameraSensor.calculateLeftCornerPoint(car, center);
        assertEquals(leftPoint, resultPoint);
    }

    @Test
    public void calculateRightCornerPointTest() {
        double rightUpperXBase = center.getX() + addingOffsetDistanceInCoordinates;
        double rightUpperYBase = center.getY() - viewDistanceInCoordinates;

        double angleOfRotationInDeg = (360 - Math.toDegrees(car.getDirectionAngle()));
        double[] coordinates = {rightUpperXBase, rightUpperYBase};
        AffineTransform.getRotateInstance(Math.toRadians(angleOfRotationInDeg), center.getX(), center.getY()).transform(coordinates, 0, coordinates, 0, 1);

        double rightUpperCornerX = coordinates[0];
        double rightUpperCornerY = coordinates[1];
        rightPoint = new Point((int) rightUpperCornerX, (int) rightUpperCornerY);

        Point resultPoint = cameraSensor.calculateRightCornerPoint(car, center);
        assertEquals(rightPoint, resultPoint);
    }

    @Test
    public void calculateLeftCornerPointTest2() {
        //car.directionAngle = 90 fok
        Point car2Center = cameraSensor.calculateCenterPoint(car2);

        double leftUpperXBase = car2Center.getX() - addingOffsetDistanceInCoordinates;
        double leftUpperYBase = car2Center.getY() - viewDistanceInCoordinates;

        double[] coordinates = {leftUpperXBase, leftUpperYBase};
        double angleOfRotationInDeg = (360 - Math.toDegrees(car2.getDirectionAngle()));
        AffineTransform.getRotateInstance(Math.toRadians(angleOfRotationInDeg), car2Center.getX(), car2Center.getY()).transform(coordinates, 0, coordinates, 0, 1);

        double leftUpperCornerX = coordinates[0];
        double leftUpperCornerY = coordinates[1];
        leftPoint = new Point((int) leftUpperCornerX, (int) leftUpperCornerY);

        Point resultPoint = cameraSensor.calculateLeftCornerPoint(car2, car2Center);
        assertEquals(leftPoint, resultPoint);
    }

    @Test
    public void calculateRightCornerPointTest2() {
        //car.directionAngle = 90 fok
        Point car2Center = cameraSensor.calculateCenterPoint(car2);
        double rightUpperXBase = car2Center.getX() + addingOffsetDistanceInCoordinates;
        double rightUpperYBase = car2Center.getY() - viewDistanceInCoordinates;

        double[] coordinates = {rightUpperXBase, rightUpperYBase};
        double angleOfRotationInDeg = (360 - Math.toDegrees(car2.getDirectionAngle()));
        AffineTransform.getRotateInstance(Math.toRadians(angleOfRotationInDeg), car2Center.getX(), car2Center.getY()).transform(coordinates, 0, coordinates, 0, 1);

        double rightUpperCornerX = coordinates[0];
        double rightUpperCornerY = coordinates[1];
        rightPoint = new Point((int) rightUpperCornerX, (int) rightUpperCornerY);
        Point resultPoint = cameraSensor.calculateRightCornerPoint(car2, car2Center);
        assertEquals(rightPoint,resultPoint);
    }

    @Test
    public void carDistanceFromObjectInCoordinateTest() {
        double expected = 250.88;
        double result = cameraSensor.carDistanceFromObjectInCoordinate(car, road1);
        result = Math.round(result * 100) / 100.00;

        assertTrue(expected == result);
    }

}
