package hu.oe.nik.szfmv17t.automatedcar.radarsensor;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.utils.*;

public class RadarSensor {
	static private final double VIEW_LENGTH_IN_METER = 200;
	static private final double VIEW_ANGLE_IN_DEGREE = 60;
	static private final double VIEW_ANGLE_IN_RADIAN = Math.toRadians(VIEW_ANGLE_IN_DEGREE);
	private Resizer resizer;
	private double viewLengthInCoordinates;
	private double triangleAdjacentSideLength;

	public RadarSensor(){
		resizer = Resizer.getResizer();
		viewLengthInCoordinates = resizer.meterToCoordinate(VIEW_LENGTH_IN_METER);
		triangleAdjacentSideLength = calculateAdjacentSideLength();
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
	
	public List<IWorldObject> selectObjectsInCarLane(List<IWorldObject> allDetectedObjects,Position carPosition, double carAxisAngle,double laneWidthInMeter){
		Point lineEndpoints[]=calcLineEndpoints(carPosition,carAxisAngle);
		double pointA[]={lineEndpoints[0].getX(),lineEndpoints[0].getY()};
		double pointB[]={lineEndpoints[1].getX(),lineEndpoints[1].getY()};
		double laneWidthInMapUnits=resizer.meterToCoordinate(laneWidthInMeter);
		
		List<IWorldObject> objectsInCarLane=new ArrayList<IWorldObject>();
		
		for (IWorldObject currIWorldObject : allDetectedObjects) {
			double pointC[]={currIWorldObject.getCenterX(),currIWorldObject.getCenterY()};
			double detectedObjectCenterDistance=LineToPointDistance2D(pointA, pointB, pointC, false);
			if(detectedObjectCenterDistance<laneWidthInMapUnits)
			{
				objectsInCarLane.add(currIWorldObject);
			}
		}
		return objectsInCarLane;
	}
	
	private Point[] calcLineEndpoints(Position carPosition, double carAxisAngle){
		
		Point defaultStartPoint = new Point((int)carPosition.getCenter().getX(), (int)carPosition.getMinimumY());
		Point rotatedStartPoint = rotatePoint(carPosition.getCenter().getX(),carPosition.getCenter().getY(),carAxisAngle, defaultStartPoint);
		Point defaultEndPoint = new Point((int)(carPosition.getCenter().getX()), (int)(defaultStartPoint.getY()-viewLengthInCoordinates));
		Point rotatedEndPoint = rotatePoint(carPosition.getCenter().getX(),carPosition.getCenter().getY(),carAxisAngle, defaultEndPoint);
		
		Point lineEndpoints[]= {rotatedStartPoint,rotatedEndPoint};
		return lineEndpoints;
	}
	
	//Compute the dot product AB . AC
	private double DotProduct(double[] pointA, double[] pointB, double[] pointC)
	{
	    double[] AB = new double[2];
	    double[] BC = new double[2];
	    AB[0] = pointB[0] - pointA[0];
	    AB[1] = pointB[1] - pointA[1];
	    BC[0] = pointC[0] - pointB[0];
	    BC[1] = pointC[1] - pointB[1];
	    double dot = AB[0] * BC[0] + AB[1] * BC[1];

	    return dot;
	}

	//Compute the cross product AB x AC
	private double CrossProduct(double[] pointA, double[] pointB, double[] pointC)
	{
	    double[] AB = new double[2];
	    double[] AC = new double[2];
	    AB[0] = pointB[0] - pointA[0];
	    AB[1] = pointB[1] - pointA[1];
	    AC[0] = pointC[0] - pointA[0];
	    AC[1] = pointC[1] - pointA[1];
	    double cross = AB[0] * AC[1] - AB[1] * AC[0];

	    return cross;
	}

	//Compute the distance from A to B
	double Distance(double[] pointA, double[] pointB)
	{
	    double d1 = pointA[0] - pointB[0];
	    double d2 = pointA[1] - pointB[1];

	    return Math.sqrt(d1 * d1 + d2 * d2);
	}

	//Compute the distance from AB to C
	//if isSegment is true, AB is a segment, not a line.
	double LineToPointDistance2D(double[] pointA, double[] pointB, double[] pointC, 
	    boolean isSegment)
	{
	    double dist = CrossProduct(pointA, pointB, pointC) / Distance(pointA, pointB);
	    if (isSegment)
	    {
	        double dot1 = DotProduct(pointA, pointB, pointC);
	        if (dot1 > 0) 
	            return Distance(pointB, pointC);

	        double dot2 = DotProduct(pointB, pointA, pointC);
	        if (dot2 > 0) 
	            return Distance(pointA, pointC);
	    }
	    return Math.abs(dist);
	} 
}