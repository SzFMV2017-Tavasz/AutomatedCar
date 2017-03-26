package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.automatedcar.hmi.SteeringWheel;
import hu.oe.nik.szfmv17t.environment.utils.Position;
import hu.oe.nik.szfmv17t.environment.utils.Vector2d;

public class SteeringControl {
	
	private final double MAX_STEERING_ANGLE = 45;
	
	private int max;
	private double steerAngle;
	private int wheelState;
	
	public SteeringControl(){
		this.max = SteeringWheel.maxLeft;
	}
	
	public Vector2d calculateDirectionVector(Position carPosition){		
		double lengthOfAdjacentSide = calculateLengthOfAdjacentSide(this.steerAngle, carPosition);
		
		Position thirdPointOfTriangle = calculateThirdPointOfTriangle(lengthOfAdjacentSide,this.wheelState,carPosition);
				
		Position newTopLeft = rotatePointAroundTurnPoint(thirdPointOfTriangle, this.steerAngle, new Position(carPosition.getMinimumX(), carPosition.getMinimumY(), 0d, 0d, 0d, 0d));
		
		return new Vector2d(newTopLeft.getMinimumX(),newTopLeft.getMinimumY());
	}
	
	private Position rotatePointAroundTurnPoint(Position turnPoint,double delta, Position pointToTurn) {
		double newX = Math.cos(delta) * (pointToTurn.getMinimumX()-turnPoint.getMinimumX()) - Math.sin(delta) * (pointToTurn.getMinimumY()-turnPoint.getMinimumY()) + turnPoint.getMinimumX();
		double newY = Math.sin(delta) * (pointToTurn.getMinimumX()-turnPoint.getMinimumX()) + Math.cos(delta) * (pointToTurn.getMinimumY()-turnPoint.getMinimumY()) + turnPoint.getMinimumY();;
		return new Position(newX, newY, 0d, 0d, 0d, 0d);
	}

	private Position calculateThirdPointOfTriangle(double lengthOfAdjacentSide, int wheelState, Position carPosition) {
		if(wheelState > 0){
			return new Position(carPosition.getCenter().getX() + lengthOfAdjacentSide, carPosition.getMaximumY(), 0d, 0d, 0d, 0d);	
		}
		return new Position(carPosition.getCenter().getX() - lengthOfAdjacentSide, carPosition.getMaximumY(), 0d, 0d, 0d, 0d);		
	}
	
	private double calculateLengthOfAdjacentSide(double delta, Position carPosition){
		return carPosition.getHeight() / Math.tan(delta);
	}

	public double calculateWheelAngle(int wheelState){
		this.wheelState = wheelState;
		if(wheelState != 0){
			this.steerAngle = Math.toRadians(steer(wheelState));
			return this.steerAngle;
		}
		return 0;
	}
	
	private double steer(int wheelState){
		return ((double)wheelState/max)*MAX_STEERING_ANGLE;
	}
}
