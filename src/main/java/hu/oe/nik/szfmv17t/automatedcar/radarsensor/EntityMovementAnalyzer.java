package hu.oe.nik.szfmv17t.automatedcar.radarsensor;

import java.util.ArrayList;
import java.util.List;

import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.utils.Resizer;
import hu.oe.nik.szfmv17t.environment.utils.Vector2d;

public class EntityMovementAnalyzer {
List<Entity> lastEntites;
List<Entity> newEntites;
Resizer resizer;
double acceptedMaxMovementDeltaInCoordinates;
double acceptedMaxPredictedMovementDeltaInCoordinates;

public EntityMovementAnalyzer() {
	lastEntites=new ArrayList<Entity>();
	newEntites=new ArrayList<Entity>();
	resizer=Resizer.getResizer();
	acceptedMaxMovementDeltaInCoordinates=resizer.meterToCoordinate(1.6); //TODO ennek meghatározására lehetne metódust csinálni, ami kiszámítja, hogy ha pl 300-al halad egy objektum, akkor az mennyit mozdul el cycleperiod idő alatt
	acceptedMaxPredictedMovementDeltaInCoordinates=resizer.meterToCoordinate(0.5);
}
public List<Entity> updateEntityList(List<IWorldObject> newWorldObjects){
	newEntites.clear();
	for(IWorldObject currWorldObject:newWorldObjects){
		addBestFitEntityToList(currWorldObject);
	}
	
	this.lastEntites=newEntites;
	return this.newEntites;
}
private void addBestFitEntityToList(IWorldObject worldObject){
	Vector2d woCurrPos=new Vector2d(worldObject.getCenterX(),worldObject.getCenterY());
	
	
	for(Entity ent:lastEntites){
		
		double currDistance=movementMath.distance2d(woCurrPos, ent.getCurrentState().getPosition());
		
		
		if(ent.getPredictedState().getStatus()==EntityStatus.known){
			double predDistance=movementMath.distance2d(woCurrPos, ent.getPredictedState().getPosition());
			if(predDistance<=acceptedMaxPredictedMovementDeltaInCoordinates){
				refreshEntity(ent, worldObject);
				newEntites.add(ent);
			}
		}
		
		
		else if(currDistance<=acceptedMaxMovementDeltaInCoordinates){
			refreshEntity(ent, worldObject);
			newEntites.add(ent);
		}
		
		else newEntites.add(createNewEntity(worldObject));		
	}
	
}
private Entity createNewEntity(IWorldObject wo){	
	Entity resultEnt = new Entity();
	EntityState resultEntState= new EntityState(new Vector2d(wo.getCenterX(), wo.getCenterY()));
	resultEnt.setCurrentState(resultEntState);
	return resultEnt;
}
public void predictEntities(){
	for(Entity ent:lastEntites){
		ent.PredictEntityState();
	}
}
private void refreshEntity(Entity ent,IWorldObject worldObject){
	EntityState newState=new EntityState(new Vector2d(worldObject.getCenterX(), worldObject.getCenterY()));
	newState.setStatus(EntityStatus.known);
	ent.getCurrentState().setStatus(EntityStatus.known);
	ent.setPreviousState(ent.getCurrentState());
	ent.setCurrentState(newState);
}

}
