package hu.oe.nik.szfmv17t.automatedcar.radarsensor;

import java.awt.Point;

import hu.oe.nik.szfmv17t.environment.utils.*;

public class RadarSensor {
	static private final double VIEW_LENGTH_IN_METER = 200;
	static private final double VIEW_ANGLE_IN_DEGREE = 60;
	static private final double VIEW_ANGLE_IN_RADIAN = Math.toRadians(VIEW_ANGLE_IN_DEGREE);
	private Resizer resizer;
	private double viewLengthInCoordinates;
	private double triangleAdjacentSideLength;

	public RadarSensor(Position carPosition, double carAxisAngle){
		resizer = Resizer.getResizer();
		viewLengthInCoordinates = resizer.meterToCoordinate(VIEW_LENGTH_IN_METER);
		triangleAdjacentSideLength = calculateAdjacentSideLength();
	}

	private double calculateSensorDistanceFromCenter(Position carPosition) {
		return	carPosition.getHeight() / 2.0;
	}

	private double calculateAdjacentSideLength() {
		return Math.tan(VIEW_ANGLE_IN_RADIAN / 2.0) * viewLengthInCoordinates;
	}

	public Triangle calculateCoordinates(Position carPosition, double carAxisAngle) {
		Point defaultSensorCoordinate = new Point((int)carPosition.getCenter().getX(), (int)carPosition.getMinimumY());
		Point rotatedSensorCoordinate = rotatePoint(carPosition.getCenter().getX(),carPosition.getCenter().getY(),carAxisAngle, defaultSensorCoordinate);

		Point defaultLeftCoordinate = new Point((int)(carPosition.getCenter().getX()-triangleAdjacentSideLength), (int)(defaultSensorCoordinate.getY()-viewLengthInCoordinates));
		Point rotatedLeftCoordinate = rotatePoint(carPosition.getCenter().getX(),carPosition.getCenter().getY(),carAxisAngle,defaultLeftCoordinate);

		Point defaultRightCoordinate = new Point((int)(carPosition.getCenter().getX()+triangleAdjacentSideLength), (int)(defaultSensorCoordinate.getY()-viewLengthInCoordinates));
		Point rotatedRightCoordinate = rotatePoint(carPosition.getCenter().getX(),carPosition.getCenter().getY(),carAxisAngle,defaultRightCoordinate);

		return new Triangle(rotatedSensorCoordinate,rotatedLeftCoordinate,rotatedRightCoordinate,SensorType.Radar);
	}

	private Point rotatePoint(double centerX, double centerY, double angle, Point pointToTurn){
		double newX = Math.cos(angle) * (pointToTurn.getX() - centerX) - Math.sin(angle) * (pointToTurn.getY() - centerY) + centerX;
		double newY = Math.sin(angle) * (pointToTurn.getX() - centerX) + Math.cos(angle) * (pointToTurn.getY() - centerY) + centerY;
		return new Point((int)newX,(int)newY);
	}
}
