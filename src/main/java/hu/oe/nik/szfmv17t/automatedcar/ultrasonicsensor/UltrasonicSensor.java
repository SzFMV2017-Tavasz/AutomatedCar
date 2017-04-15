package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

import java.awt.Point;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;
import hu.oe.nik.szfmv17t.environment.utils.Position;
import hu.oe.nik.szfmv17t.environment.utils.Resizer;
import hu.oe.nik.szfmv17t.environment.utils.SensorType;
import hu.oe.nik.szfmv17t.environment.utils.Triangle;

/**
 * Created by SebestyenMiklos on 2017. 03. 26..
 */
public class UltrasonicSensor {
	private int sensorNumber;
	private UltrasonicSensorCoordinates coordinates;
	private double viewLength;
	private double viewAngle;
	private double sensorDistanceFromCenter;
	private Resizer resizer;
	private double viewLengthInCoordinates;
	private double sensorTrianglePointsDistanceFromSensor;

	public UltrasonicSensor(int sensorNumber, double carMainCoordinateX, double carMainCoordinateY, double carAxisAngle, double length) {
		this.sensorNumber = sensorNumber;
		resizer = Resizer.getResizer();
		sensorDistanceFromCenter = 127;
		coordinates = new UltrasonicSensorCoordinates();
		calculateCoordinates(sensorNumber, carAxisAngle, carMainCoordinateX, carMainCoordinateY);
		viewLength = 3;
		viewLengthInCoordinates = resizer.meterToCoordinate(viewLength);
		viewAngle = 100;
		sensorTrianglePointsDistanceFromSensor = sensorTriangleBasicCalculation();
	}

	private double sensorTriangleBasicCalculation() {
		return viewLengthInCoordinates / Math.cos(viewAngle/2);
	}

	public void calculateCoordinates(int sensorNumber, double carAxisAngle, double carMainCoordinateX, double carMainCoordinateY) {
		double angle = 0;
		switch (sensorNumber) {
			case 1:
				angle = 20;
				break;
			case 2:
				angle = 25;
				break;
			case 3:
				angle = 155;
				break;
			case 4:
				angle = 160;
				break;
			case 5:
				angle = 200;
				break;
			case 6:
				angle = 205;
				break;
			case 7:
				angle = 335;
				break;
			case 8:
				angle = 340;
				break;
		}
		double sensorAngle = angle + carAxisAngle;
		sensorPositionCalculate(sensorAngle, carMainCoordinateX, carMainCoordinateY);
		sensorPointsCalculate(sensorAngle);
	}

	private void sensorPositionCalculate(double angleFROMCarAxisAngle, double carMainCoordinateX, double carMainCoordinateY) {
		double x = Math.sin(angleFROMCarAxisAngle) * sensorDistanceFromCenter;
		double y = Math.cos(angleFROMCarAxisAngle) * sensorDistanceFromCenter;
		coordinates.setMainCoordinates(carMainCoordinateX + x, carMainCoordinateY + y);
	}

	private void sensorPointsCalculate(double sensorAngle){
		double halfViewAngle = viewAngle/2;
		double leftX = Math.sin(sensorAngle-halfViewAngle)*sensorTrianglePointsDistanceFromSensor;
		double leftY = Math.cos(sensorAngle-halfViewAngle)*sensorTrianglePointsDistanceFromSensor;
		double rightX = Math.sin(sensorAngle+halfViewAngle)*sensorTrianglePointsDistanceFromSensor;
		double rightY = Math.cos(sensorAngle+halfViewAngle)*sensorTrianglePointsDistanceFromSensor;
		coordinates.setLeftCoordinates(leftX + coordinates.getMainX(),leftY+ coordinates.getMainY());
		coordinates.setRightCoordinates(rightX + coordinates.getMainX(),rightY + coordinates.getMainY());
	}

	public UltrasonicSensorCoordinates getCoordinates() {
		return coordinates;
	}

	public double getViewAngle() {
		return viewAngle;
	}

	public double getViewLength() {
		return viewLength;
	}

	public Object getSensorNumber() {
		return this.sensorNumber;
	}

	// ezt kell elvileg használni majd az objectek lekérdezéséhez
	public Triangle getSensorViewTriangle() {
		Point leftCord = new Point((int) coordinates.getLeftX(), (int) coordinates.getLeftY());
		Point rightCord = new Point((int) coordinates.getRightX(), (int) coordinates.getRightY());
		Point mainCore = new Point((int) coordinates.getMainX(), (int) coordinates.getMainY());
		return new Triangle(leftCord, rightCord, mainCore, SensorType.UltraSonic);
	}
}
