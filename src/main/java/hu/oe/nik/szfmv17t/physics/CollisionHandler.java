package hu.oe.nik.szfmv17t.physics;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.domain.Sign;
import hu.oe.nik.szfmv17t.environment.domain.Tree;
import hu.oe.nik.szfmv17t.environment.domain.WorldObjectState;
import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;
import hu.oe.nik.szfmv17t.physics.interfaces.ICollisionHandler;

public class CollisionHandler implements ICollisionHandler {

    @Override
    public void handleCollision(ICollidableObject objectOne, ICollidableObject objectTwo) {
        if(objectOne instanceof  AutomatedCar){
            if(objectTwo instanceof  Sign){
                carCollisionWithSign((AutomatedCar)objectOne,(Sign)objectTwo);                
            }
            else if(objectTwo instanceof Tree){
                carCollisionWithTree((AutomatedCar)objectOne,(Tree)objectTwo);
            }
        }
    }

    private void carCollisionWithTree(AutomatedCar car, Tree tree) {
        double newVelocity = calculateNewVelocity(car.getSpeed(),car.getMass(),tree.getMass());
        car.getPowertrainSystem().getSpeedControl().setActualVelocity(newVelocity);
        car.setState(WorldObjectState.Destroyed);
        tree.setState(WorldObjectState.Damaged);
    }

    private void carCollisionWithSign(AutomatedCar car, Sign sign) {
        double newVelocity = calculateNewVelocity(car.getSpeed(),car.getMass(),sign.getMass());
        car.setSpeed(newVelocity);
        sign.setState(WorldObjectState.Destroyed);
        car.setState(WorldObjectState.Damaged);
    }

    private double calculateNewVelocity(double speedOne, double massOne, double massTwo) {
        double initialMomentum = speedOne * massOne;
        double finalVelocity = initialMomentum / (massOne + massTwo);
        return  finalVelocity;
    }
}
