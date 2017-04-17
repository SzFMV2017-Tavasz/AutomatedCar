package hu.oe.nik.szfmv17t.automatedcar.radarsensor;

import java.awt.Point;

import hu.oe.nik.szfmv17t.environment.utils.Resizer;
import hu.oe.nik.szfmv17t.environment.utils.SensorType;
import hu.oe.nik.szfmv17t.environment.utils.Triangle;

public class RadarSensor {
	private double viewLength;
	private double viewAngle;
	private double sensorDistanceFromCenter;
	private Resizer resizer;
	private double viewLengthInCoordinates;
	private double sensorTrianglePointsDistanceFromSensor;
	private RadarSensorCoordinates coordinates;
	
	public RadarSensor(double carMainCoordinateX, double carMainCoordinateY, double carAxisAngle){
		resizer = Resizer.getResizer();
		sensorDistanceFromCenter = 127;
		viewLength = 200;
		viewAngle = 60;
		viewLengthInCoordinates = resizer.meterToCoordinate(viewLength);
		sensorTrianglePointsDistanceFromSensor = sensorTriangleBasicCalculation();
		coordinates = new RadarSensorCoordinates();
		calculateCoordinates(carAxisAngle, carMainCoordinateX, carMainCoordinateY);
	}	
	private double sensorTriangleBasicCalculation() {
		return viewLengthInCoordinates / Math.cos(viewAngle/2);
	}
	public void calculateCoordinates(double carAxisAngle, double carMainCoordinateX, double carMainCoordinateY) {
		double sensorAngle = carAxisAngle;
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
		double leftX = Math.sin((carAxisAngle - halfViewAngle) * (Math.PI / 180)) * sensorTrianglePointsDistanceFromSensor;
		double leftY = Math.cos((carAxisAngle - halfViewAngle) * (Math.PI / 180)) * sensorTrianglePointsDistanceFromSensor;
		double rightX = Math.sin((carAxisAngle + halfViewAngle) * (Math.PI / 180)) * sensorTrianglePointsDistanceFromSensor;
		double rightY = Math.cos((carAxisAngle + halfViewAngle) * (Math.PI / 180)) * sensorTrianglePointsDistanceFromSensor;
		coordinates.setLeftCoordinates(leftX + coordinates.getMainX(), leftY + coordinates.getMainY());
		coordinates.setRightCoordinates(rightX + coordinates.getMainX(), rightY + coordinates.getMainY());
	}

	public RadarSensorCoordinates getCoordinates() {
		return coordinates;
	}

	public double getViewAngle() {
		return viewAngle;
	}

	public double getViewLength() {
		return viewLength;
	}

	public Triangle getSensorViewTriangle() {
		Point leftCord = new Point((int) coordinates.getLeftX(), (int) coordinates.getLeftY());
		Point rightCord = new Point((int) coordinates.getRightX(), (int) coordinates.getRightY());
		Point mainCore = new Point((int) coordinates.getMainX(), (int) coordinates.getMainY());
		return new Triangle(leftCord, rightCord, mainCore, SensorType.Radar);
	}
}
