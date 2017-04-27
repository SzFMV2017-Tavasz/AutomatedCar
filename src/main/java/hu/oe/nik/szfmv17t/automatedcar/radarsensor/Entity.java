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
}
public Entity(){
	this.cyclePeriod=Main.CYCLE_PERIOD;
	this.setPreviousState(new EntityState());
	this.setCurrentState(new EntityState());
	this.setPredictedState(new EntityState());
	
}
public boolean isKnown(){
	return (this.getPreviousState().getStatus()==EntityStatus.known && this.getPredictedState().getStatus()==EntityStatus.known);
}
public void calcSpeed(){
	double deltaPos=movementMath.distance2d(this.getPreviousState().getPosition(), this.getPredictedState().getPosition());
	this.setSpeed(deltaPos/cyclePeriod);
}

public void PredictEntityState(){
	if(this.getPreviousState().getStatus()==EntityStatus.known && this.getCurrentState().getStatus()==EntityStatus.known){
		calcSpeed();
		calcDirection(this.getPreviousState().getPosition(), this.getCurrentState().getPosition());
		this.getPredictedState().setStatus(EntityStatus.known);		
	}
}
private void calcDirection(Vector2d a, Vector2d b){
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

