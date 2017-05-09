package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.domain.*;
import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;
import hu.oe.nik.szfmv17t.physics.interfaces.ICollisionHandler;

public class CollisionHandler implements ICollisionHandler {

    private static final double PERCENTAGEOFDESTROYEDTATE = 0.25;

    @Override
    public void handleCollision(ICollidableObject objectOne, ICollidableObject objectTwo) {
        if(objectOne instanceof  AutomatedCar){
            if(objectTwo instanceof  Sign){
                collisionBaseHandler((AutomatedCar)objectOne, objectTwo);
                ((Sign) objectTwo).setState(WorldObjectState.Destroyed);
            }
            else if(objectTwo instanceof Tree){
                collisionBaseHandler((AutomatedCar)objectOne,objectTwo);
                ((Tree) objectTwo).setState(WorldObjectState.Damaged);
            }
            else if(objectTwo instanceof Car){
                collisionBaseHandler((AutomatedCar)objectOne,objectTwo);
                ((Car) objectTwo).setState(((AutomatedCar) objectOne).getState());
            }
            else if(objectTwo instanceof Pedestrian){
                collisionBaseHandler((AutomatedCar)objectOne,objectTwo);
                ((Pedestrian) objectTwo).setState(WorldObjectState.Destroyed);
            }
        }
    }

    private void collisionBaseHandler(AutomatedCar car, ICollidableObject collidedObject){
        double newVelocity = calculateNewVelocity(car.getSpeed(),car.getMass(),collidedObject.getMass());
        car.setState(setStatusOfCar(car.getSpeed(),newVelocity));
        car.getPowertrainSystem().getSpeedControl().setActualVelocity(newVelocity);
    }

    private double calculateNewVelocity(double speedOne, double massOne, double massTwo) {
        double initialMomentum = speedOne * massOne;
        double finalVelocity = initialMomentum / (massOne + massTwo);
        return finalVelocity;
    }

    private WorldObjectState setStatusOfCar(double prevSpeed, double newSpeed){
        if(prevSpeed * PERCENTAGEOFDESTROYEDTATE >= newSpeed){
            return WorldObjectState.Destroyed;
        }
        else{
            return WorldObjectState.Damaged;
        }
    }
}
