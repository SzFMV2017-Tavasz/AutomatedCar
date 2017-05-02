package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

import java.awt.Point;

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

	public UltrasonicSensor(int sensorNumber, double carMainCoordinateX, double carMainCoordinateY, double carAxisAngle) {
		this.sensorNumber = sensorNumber;
		resizer = Resizer.getResizer();
		sensorDistanceFromCenter = 127;
		viewLength = 3;
		viewAngle = 100;
		viewLengthInCoordinates = resizer.meterToCoordinate(viewLength);
		sensorTrianglePointsDistanceFromSensor = sensorTriangleBasicCalculation();
		coordinates = new UltrasonicSensorCoordinates();
		calculateCoordinates(sensorNumber, carAxisAngle, carMainCoordinateX, carMainCoordinateY);
	}

	private double sensorTriangleBasicCalculation() {
		return viewLengthInCoordinates / Math.cos(viewAngle / 2);
	}

	public void calculateCoordinates(int sensorNumber, double carAxisAngle, double carMainCoordinateX, double carMainCoordinateY) {
		double angle = 0;
		switch(sensorNumber) {
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
		sensorPointsCalculate(carAxisAngle);
	}

	private void sensorPositionCalculate(double angleFROMCarAxisAngle, double carMainCoordinateX, double carMainCoordinateY) {
		double x = Math.sin(angleFROMCarAxisAngle * (Math.PI / 180)) * sensorDistanceFromCenter;
		double y = Math.cos(angleFROMCarAxisAngle * (Math.PI / 180)) * sensorDistanceFromCenter;
		coordinates.setMainCoordinates(carMainCoordinateX + x, carMainCoordinateY + y);
	}

	private void sensorPointsCalculate(double carAxisAngle) {
		double halfViewAngle = viewAngle / 2;
		double plusRotationAngle = 0;

		if((sensorNumber == 2) || (sensorNumber == 3)) {
			plusRotationAngle = 90;
		} else if((sensorNumber == 4) || (sensorNumber == 5)) {
			plusRotationAngle = 180;
		} else if((sensorNumber == 6) || (sensorNumber == 7)) {
			plusRotationAngle = 270;
		}

		double leftX = Math.sin(((carAxisAngle - halfViewAngle) + plusRotationAngle) * (Math.PI / 180)) * sensorTrianglePointsDistanceFromSensor;
		double leftY = Math.cos(((carAxisAngle - halfViewAngle) + plusRotationAngle) * (Math.PI / 180)) * sensorTrianglePointsDistanceFromSensor;
		double rightX = Math.sin(((carAxisAngle + halfViewAngle) + plusRotationAngle) * (Math.PI / 180)) * sensorTrianglePointsDistanceFromSensor;
		double rightY = Math.cos(((carAxisAngle + halfViewAngle) + plusRotationAngle) * (Math.PI / 180)) * sensorTrianglePointsDistanceFromSensor;
		coordinates.setLeftCoordinates(leftX + coordinates.getMainX(), leftY + coordinates.getMainY());
		coordinates.setRightCoordinates(rightX + coordinates.getMainX(), rightY + coordinates.getMainY());
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

	public int getSensorNumber() {
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
