package hu.oe.nik.szfmv17t.automatedcar.radarsensor;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
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
	
	public boolean willWeCollideWithStaticObjects(List<Entity> detectedEntitesInPossibleCollision, AutomatedCar car)
	{		
		Position carPosition = car.getPositionObj(); 
		double carAxisAngle = car.getAxisAngle();
		double carSpeed = car.getSpeed();
		
		Point defaultStartPoint = new Point((int)carPosition.getCenter().getX(), (int)carPosition.getMinimumY());
		Point carRotatedStartPoint = rotatePoint(carPosition.getCenter().getX(),carPosition.getCenter().getY(),carAxisAngle, defaultStartPoint);
		
		for (Entity ent: detectedEntitesInPossibleCollision)
		{			
			//If the entity is static
			if (ent.getCurrentState().getPosition() == ent.getPreviousState().getPosition())
			{
				
				
				double tavolsag = howManyMetersUntilCollision(ent.getCurrentState().getPosition(), new Vector2d(carRotatedStartPoint.getX(), carRotatedStartPoint.getY()));
				double mennyiIdoVeszfekkelLefekezni = carSpeed/9.5;
				double mennyiIdoAmigOdaerunk = tavolsag/carSpeed;
				
				if (mennyiIdoVeszfekkelLefekezni >= mennyiIdoAmigOdaerunk)
					return true;
			}
		}
		return false;
	}
	
	public boolean willWeCollideWithDynamicObjects(List<Entity> detectedEntitesInPossibleCollision, AutomatedCar car)
	{
		
		Position carPosition = car.getPositionObj(); 
		double carAxisAngle = car.getAxisAngle();
		double carSpeed = car.getSpeed();
		
		
		Point defaultStartPoint = new Point((int)carPosition.getCenter().getX(), (int)carPosition.getMinimumY());
		Point carRotatedStartPoint = rotatePoint(carPosition.getCenter().getX(),carPosition.getCenter().getY(),carAxisAngle, defaultStartPoint);
		Point defaultEndPoint = new Point((int)(carPosition.getCenter().getX()), (int)(defaultStartPoint.getY()-viewLengthInCoordinates));
		Point carRotatedEndPoint = rotatePoint(carPosition.getCenter().getX(),carPosition.getCenter().getY(),carAxisAngle, defaultEndPoint);
		
		
		Vector2d carDirectionVector = new Vector2d(carRotatedEndPoint.getX() - carRotatedStartPoint.getX(), carRotatedEndPoint.getY() - carRotatedStartPoint.getY());
		Vector2d carNormalVector = new Vector2d(-carDirectionVector.getY(),carDirectionVector.getX());
		double carLineEquationRightSide = carRotatedStartPoint.getX()*carNormalVector.getX() + carRotatedEndPoint.getY()*carNormalVector.getY();
		Vector2d carLineEquationLeftSide = new Vector2d(carNormalVector.getX(), carNormalVector.getY());
		
		for (Entity ent: detectedEntitesInPossibleCollision) {
			if (ent.isKnown())
			{				
				//If the entity is dynamic
				if ((ent.getCurrentState().getPosition() != ent.getPreviousState().getPosition()))
				{
					Vector2d objectDirectionVector = ent.getDirection();
					//Talán a getCurrentState megfelelőbb lenne
					Vector2d objectStartingPoint = ent.getPreviousState().getPosition();
					
					//Object's line is determined with direction vector
					Vector2d objNormalVector = new Vector2d(-objectDirectionVector.getY(),objectDirectionVector.getX());
					double objectLineEquationRightSide = objectStartingPoint.getX() * objNormalVector.getX() + objectStartingPoint.getY() * objNormalVector.getY();
					Vector2d objectLineEquationLeftSide = new Vector2d(objNormalVector.getX(), objNormalVector.getY());
					
					//Our car's line is determined with direction vector
					vonalMelyikOldalan melyiken = melyikOldalon(carRotatedStartPoint,carRotatedEndPoint,ent);
					
					if (vizsgalhatoE(car.getDirectionAngle(), ent, melyiken))
					{
						//Cramer's rule will be used to solve the equations
						Vector2d collisionPoint = twoTimesTwoMatrixSolver(objectLineEquationLeftSide, objectLineEquationRightSide, carLineEquationLeftSide, carLineEquationRightSide);
					
						double metersUntilCollisionCar = howManyMetersUntilCollision(
								new Vector2d(carRotatedStartPoint.getX(),carRotatedStartPoint.getY()), collisionPoint);
						
						double metersUntilCollisionObj = howManyMetersUntilCollision(
								new Vector2d(ent.getPreviousState().getPosition().getX(), ent.getPreviousState().getPosition().getY()), collisionPoint);
						
						double timeUntilCarReaches = howMuchTimeUntilCollision(metersUntilCollisionCar, carSpeed);
						double timeUntilObjReaches = howMuchTimeUntilCollision(metersUntilCollisionObj, ent.getSpeed());
						
	
						return (willWeBump(timeUntilCarReaches, carSpeed, timeUntilObjReaches, car.getHeight()));
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean vizsgalhatoE(double autoSzoge, Entity vizsgalando, vonalMelyikOldalan melyiken)
	{
		autoSzoge %= 2*Math.PI;
		double entitasSzoge = Math.atan(vizsgalando.getDirection().getY() / vizsgalando.getDirection().getX());
		entitasSzoge %= 2*Math.PI;
		
		/*Ez önmagában még nem elegendő
		 * El kell dönteni azt is hogy a vonal melyik oldalán van az entity
		 * */
		
		if (melyiken == vonalMelyikOldalan.bal)
		{
			if (autoSzoge < entitasSzoge && ((autoSzoge + Math.PI) % (2*Math.PI)) > entitasSzoge)
				return true;
			return false;
		} 
		else if (melyiken == vonalMelyikOldalan.jobb)
		{
			if (autoSzoge > entitasSzoge && ((autoSzoge + Math.PI) % (2*Math.PI)) < entitasSzoge)
				return true;
			return false;
		}
		else if (melyiken == vonalMelyikOldalan.rajta)
		{
			/*Ez esetben nem tudjuk eldönteni hogy melyik ponton metszi az egyenest
			 * Ergo nincs értelme a további kódnak
			*/
			return false;
		}
		return false;
	}
	
	private vonalMelyikOldalan melyikOldalon(Point p0, Point p1, Entity pont)
	{
		//http://stackoverflow.com/questions/22668659/calculate-on-which-side-of-a-line-a-point-is
		double ertek = (p1.x - p0.x)*(pont.getCurrentState().getPosition().getY()-p0.y) - (pont.getCurrentState().getPosition().getX()-p0.x)*(p1.y-p0.y);
		
		if (ertek > 0)
			return vonalMelyikOldalan.bal;
		else if (ertek < 0)
			return vonalMelyikOldalan.jobb;
		else
			return vonalMelyikOldalan.rajta;
	}
	
	private enum vonalMelyikOldalan { bal, jobb, rajta; }
	
	
	private boolean willWeBump(double timeUntilCarReaches, double carSpeed, double timeUntilObjReaches, double carLength)
	{
		//Because our car has spatial size we need to take in consideration that, with some error percentage
		
		//1 percent error
		carLength *= 1.01;
		
		double plusTime = carLength/carSpeed;

		/*The car and the object collides if the time until the object(other one)
		 * reaches the intersection is in the interval 
		 * [the car's bumper reached the point, the car's rare end reached the point]
		*/
		if ((timeUntilCarReaches + plusTime >= timeUntilObjReaches) 
			&& (timeUntilCarReaches <= timeUntilObjReaches))
		{
			return true;
		}
		return false;
	}
	
	private double howMuchTimeUntilCollision(double howManyMeters, double speed) 
    {
    	return Math.abs(howManyMeters / speed);
    }
    
    private double howManyMetersUntilCollision(Vector2d startingPoint, Vector2d endingPoint) 
    {
    	double x = endingPoint.getX() - startingPoint.getX();
    	double y = endingPoint.getY() - startingPoint.getY();
    	return resizer.coordinateToMeter(Math.sqrt(x * x + y * y));
    }
    
    private Vector2d twoTimesTwoMatrixSolver(Vector2d firstEquation, double c1, Vector2d secondEquation, double c2)
    {
    	//http://www.intmath.com/matrices-determinants/1-determinants.php
    	double x = 
    			(c1*secondEquation.getY() - c2*firstEquation.getX()) / (firstEquation.getX()*secondEquation.getY() - firstEquation.getY()*secondEquation.getX());
    	
    	double y =
    			(c2*firstEquation.getX() - c1*secondEquation.getX()) / (firstEquation.getX()*secondEquation.getY()-firstEquation.getY()*secondEquation.getX());
    	//CollisionPoint
    	return new Vector2d(x,y);
    }
}