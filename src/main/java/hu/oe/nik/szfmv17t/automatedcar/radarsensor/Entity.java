package hu.oe.nik.szfmv17t.automatedcar.radarsensor;

import hu.oe.nik.szfmv17t.Main;
import hu.oe.nik.szfmv17t.environment.utils.Vector2d;

public class Entity {
	
int cyclePeriod;	
private EntityState previousState;
private EntityState currentState;
private EntityState predictedState;
private double speed;
private Vector2d direction;


public Entity(int cyclePeriod){
	this.cyclePeriod=cyclePeriod;
	this.setPreviousState(new EntityState());
	this.setCurrentState(new EntityState());
	this.setPredictedState(new EntityState());
	this.setDirection(new Vector2d(0, 0));
}
public Entity(){
	this.cyclePeriod=Main.CYCLE_PERIOD;
	this.setPreviousState(new EntityState());
	this.setCurrentState(new EntityState());
	this.setPredictedState(new EntityState());
	this.setDirection(new Vector2d(0, 0));
	
}
public boolean isKnown(){
	return (this.getPreviousState().getStatus()==EntityStatus.known && this.getPredictedState().getStatus()==EntityStatus.known);
}
public void updateSpeed(){
	double deltaPos=movementMath.distance2d(this.getPreviousState().getPosition(), this.getPredictedState().getPosition());
	this.setSpeed(deltaPos/cyclePeriod);
}

public void PredictEntityState(){
	if(this.getPreviousState().getStatus()==EntityStatus.known && this.getCurrentState().getStatus()==EntityStatus.known){
		this.getPredictedState().setStatus(EntityStatus.known);		
		predictPosition();				
	}
}

private void predictPosition(){
	this.getPredictedState().setPosition(new Vector2d(this.getCurrentState().getPosition().getX()+this.getDirection().getX(),this.getCurrentState().getPosition().getY()+this.getDirection().getY()));
}
public void updateDirection(){
	Vector2d a= this.previousState.getPosition();
	Vector2d b= this.currentState.getPosition();
	
	Vector2d directionVector=new Vector2d(b.getX()-a.getX(), b.getY()-a.getY());
	this.setDirection(directionVector);
}
public EntityState getPredictedState() {
	return predictedState;
}
public void setPredictedState(EntityState predictedState) {
	this.predictedState = predictedState;
}
public EntityState getCurrentState() {
	return currentState;
}
public void setCurrentState(EntityState currentState) {
	this.currentState = currentState;
}
public EntityState getPreviousState() {
	return previousState;
}
public void setPreviousState(EntityState previousState) {
	this.previousState = previousState;
}
public double getSpeed() {
	return speed;
}
public void setSpeed(double speed) {
	this.speed = speed;
}
public Vector2d getDirection() {
	return direction;
}
public void setDirection(Vector2d direction) {
	this.direction = direction;
}
}

