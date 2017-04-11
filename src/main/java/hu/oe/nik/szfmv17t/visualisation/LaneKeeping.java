package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;

import java.util.List;

/**
 * Created by winifred on 2017.04.03..
 */
public class LaneKeeping {

    private final double bandWidth = (350 / 2);
    private final double difference = 13.5;

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

    public IWorldObject findRoad(IWorldObject carObject, List<IWorldObject> worldObjects) {
        IWorldObject car = carObject;

        double objRefX = 0.0;
        double objRefY = 0.0;
        double carRefX = car.getCenterX() - (car.getWidth() / 2);
        double carRefY = car.getCenterY() - (car.getHeight() / 2);

        for (IWorldObject object : worldObjects) {

            if (object.getImageName() == "road_2lane_straight.png") {

                objRefX = object.getCenterX() - (object.getWidth() / 2);
                objRefY = object.getCenterY() - (object.getHeight() / 2);
                //az ut bal felso sarkanak x-nel nagyobb es a jobb felso sarok x-nel kisebb az auto ref pontja
                if (objRefX < carRefX && (carRefX < objRefX + object.getWidth())) {
                    //ugyanez vizsgaljuk y-ra is
                    if (objRefY < carRefY && (carRefY < objRefY + object.getHeight())) {
                        //ha ezek teljesulnek, akkor rajta van az utszakaszon az auto
                        return object;
                    }
                }
            }
        }
        return null;
    }

    public boolean carOutTheRoad() {
        return true;
    }

}
