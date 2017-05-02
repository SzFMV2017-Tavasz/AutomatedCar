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
		if(currWorldObject!=null)
		addBestFitEntityToList(currWorldObject);
	}
	lastEntites.clear();
	for (Entity ent : newEntites) {
		lastEntites.add(ent);
	}
	return this.newEntites;
}
private void addBestFitEntityToList(IWorldObject worldObject){
	Vector2d woCurrPos=new Vector2d(worldObject.getCenterX(),worldObject.getCenterY());
	
	
	for(Entity lastEnt:lastEntites){
		
		double currDistance=movementMath.distance2d(woCurrPos, lastEnt.getCurrentState().getPosition());
		
		
		if(lastEnt.getPredictedState().getStatus()==EntityStatus.known){
			double predDistance=movementMath.distance2d(woCurrPos, lastEnt.getPredictedState().getPosition());
			if(predDistance<=acceptedMaxPredictedMovementDeltaInCoordinates){
				refreshEntity(lastEnt, worldObject);
				newEntites.add(lastEnt);
				break;
			}
		}
		
		
		else if(currDistance<=acceptedMaxMovementDeltaInCoordinates){
			refreshEntity(lastEnt, worldObject);
			newEntites.add(lastEnt);
			break;
		}
		
		else {
			Entity newEntityToAdd= createNewEntity(worldObject);
			newEntites.add(newEntityToAdd);	
			break;
		}
	}
	if(lastEntites.isEmpty()){
		newEntites.add(createNewEntity(worldObject));
	}
	
}
private Entity createNewEntity(IWorldObject wo){	
	Entity resultEnt = new Entity();
	EntityState resultEntState= new EntityState(new Vector2d(wo.getCenterX(), wo.getCenterY()));
	//resultEntState.setStatus(EntityStatus.known);
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
	ent.updateSpeed();
	ent.updateDirection();
	
}

}
