package hu.oe.nik.szfmv17t.visualisation.camerasensor;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.domain.Road;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;

import java.util.List;

/**
 * Created by winifred on 2017.04.03..
 */
public class LaneKeeping {

    private final double bandWidth = (350 / 2);
    private final double difference = 13.5;
    private boolean leftSide;

    private List<IWorldObject> worldObjects;

    IWorldObject getCar(List<IWorldObject> objects) {

        for (IWorldObject element : objects) {
            if (AutomatedCar.class.isInstance(element)) {
                return element;
            }
        }
        return null;
    }

    public LaneKeeping(List<IWorldObject> worldObjects) {
        this.worldObjects = worldObjects;
    }

    //meghatarozza melyik utszakaszon van az auto
    public IWorldObject findRoad(IWorldObject carObject, List<IWorldObject> worldObjects) {
        IWorldObject car = carObject;

        for (IWorldObject object : worldObjects) {

            if (Road.class.isInstance(object)) {

                if (checkTheCarOnTheRoad(carObject, object) == true) {
                    return object;
                }
            }
        }
        return null;
    }

    private boolean checkTheCarOnTheRoad(IWorldObject carObject, IWorldObject object) {
        IWorldObject car = carObject;
        double objRefX = object.getCenterX() - (object.getWidth() / 2);
        double objRefY = object.getCenterY() - (object.getHeight() / 2);
        double carLeftCornerX = car.getCenterX() - (car.getWidth() / 2);
        double carLeftCornerY = car.getCenterY() - (car.getHeight() / 2);
        double carRightConerX = car.getCenterX() + (car.getWidth() / 2);
        double carRightConerY = car.getCenterY() + (car.getHeight() / 2);

        //az auto bal vagy jobb felso sarokpontja rajta van e az utszakaszon
        if ((objRefX <= carLeftCornerX && (carLeftCornerX <= objRefX + object.getWidth())) ||
                (objRefX <= carRightConerX && (carRightConerX <= objRefX + object.getWidth()))) {
            //ugyanez vizsgaljuk y-ra is
            if ((objRefY <= carLeftCornerY && (carLeftCornerY <= objRefY + object.getHeight())) ||
                    (objRefY <= carRightConerY && (carRightConerY <= objRefY + object.getHeight()))) {
                //ha ezek teljesulnek, akkor rajta van az utszakaszon az auto
                return true;
            }
        }
        return false;
    }

    //buszra kuldjuk a korrekcio merteket
    public int steeringWheelCorrection() {
        int correction = 0;
        return correction;
    }

    /*
    Meghatarozza, hogy a meghatarozott korrekciot eloiro savba kerult e az auto
    * */
    private boolean carOutTheRoad(IWorldObject carObject, IWorldObject roadObject) {

        leftSide = detectTrafficLane(carObject, roadObject);

        double carLeftCornerX = carObject.getCenterX() - (carObject.getWidth() / 2);
        double carRightConerX = carObject.getCenterX() + (carObject.getWidth() / 2);

        if (leftSide) {


        } else {
            if (roadObject.getImageName() == "road_2lane_straight.png") {
                //ha benne van az altalunk meghatarozott savban
                //kozepvonal menti sav
                if ((roadObject.getCenterX() < carLeftCornerX) && (roadObject.getCenterX() + difference) > carLeftCornerX) {
                    return true;
                }
                // utszel menti sav kozott van
                if (((roadObject.getCenterX() + roadObject.getWidth() / 2) > carRightConerX) &&
                        (roadObject.getCenterX() + roadObject.getWidth() / 2 - difference < carRightConerX)) {
                    return true;
                }
            }
        }
        return true;
    }

    //meghatarozza az utszakasz melyik oldalan halad az auto
    private boolean detectTrafficLane(IWorldObject carObject, IWorldObject roadObject) {

        if (roadObject.getImageName() == "road_2lane_straight.png") {
            //leftside
            if (carObject.getCenterX() <= roadObject.getCenterX() || carObject.getCenterY() <= roadObject.getCenterY()) {
                leftSide = true;
            } else {
                leftSide = false;
            }
        }
        if (roadObject.getImageName() == "road_2lane_90left.png") {

        }
        return false;
    }


}
