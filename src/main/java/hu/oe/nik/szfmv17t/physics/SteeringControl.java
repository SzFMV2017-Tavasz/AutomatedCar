package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.automatedcar.hmi.SteeringWheel;
import hu.oe.nik.szfmv17t.environment.utils.Position;
import hu.oe.nik.szfmv17t.environment.utils.Vector2d;

//Right is positive, left is negative
public class SteeringControl {

	private final double MAX_STEERING_ANGLE = 45;

	private int max;
	private double steerAngle;
	private int wheelState;

	public SteeringControl(){
		this.max = SteeringWheel.maxRight;
	}

	public double calculateDirectionVector(Position carPosition){
		if(wheelState != 0) {
			double lengthOfAdjacentSide = calculateLengthOfAdjacentSide(this.steerAngle, carPosition);

			Vector2d thirdPointOfTriangle = calculateThirdPointOfTriangle(lengthOfAdjacentSide, this.wheelState, carPosition);

			Vector2d newCenter = rotatePointAroundTurnPoint(thirdPointOfTriangle, this.steerAngle, new Vector2d(carPosition.getCenter().getX(), carPosition.getCenter().getY()));

			if(!(newCenter.getX() == carPosition.getCenter().getX() || newCenter.getY() == carPosition.getCenter().getY())) {
				return Math.toRadians(90) - Math.atan2(Math.abs(newCenter.getY() - carPosition.getCenter().getY()), Math.abs(newCenter.getX() - carPosition.getCenter().getX()));
			}
			else
				return 0;
		}
		else
			return 0;
	}

	private Vector2d rotatePointAroundTurnPoint(Vector2d turnPoint,double delta, Vector2d pointToTurn) {
		double newX = Math.cos(delta) * (pointToTurn.getX()-turnPoint.getX()) - Math.sin(delta) * (pointToTurn.getY()-turnPoint.getY()) + turnPoint.getX();
		double newY = Math.sin(delta) * (pointToTurn.getX()-turnPoint.getX()) + Math.cos(delta) * (pointToTurn.getY()-turnPoint.getY()) + turnPoint.getY();
		return new Vector2d(newX, newY);
	}

	private Vector2d calculateThirdPointOfTriangle(double lengthOfAdjacentSide, int wheelState, Position carPosition) {
		if(wheelState > 0){
			Vector2d newThirdPointOfTriangle = new Vector2d(carPosition.getCenter().getX()+lengthOfAdjacentSide, carPosition.getMaximumY());
			Vector2d bottomCenterPoint = rotatePointAroundTurnPoint(new Vector2d(carPosition.getCenter().getX(),carPosition.getCenter().getY()),carPosition.getDirectionAngle(),new Vector2d(carPosition.getCenter().getX(), carPosition.getMaximumY()));

			return rotatePointAroundTurnPoint(bottomCenterPoint, carPosition.getDirectionAngle(), newThirdPointOfTriangle);
		}
		return new Vector2d(carPosition.getCenter().getX() - lengthOfAdjacentSide, carPosition.getMaximumY());
	}

	private double calculateLengthOfAdjacentSide(double delta, Position carPosition) {
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