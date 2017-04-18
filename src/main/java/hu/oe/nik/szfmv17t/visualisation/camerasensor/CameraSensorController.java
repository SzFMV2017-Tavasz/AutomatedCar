package hu.oe.nik.szfmv17t.visualisation.camerasensor;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.utils.Triangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by winifred on 2017.04.17..
 */
public class CameraSensorController extends SystemComponent {

    private CameraSensor cameraSensor;
    private World world;  //addig amig nincs interfesz a tema2-tol
    private Triangle fieldView;
    private List<IWorldObject> worldObjects;
    private List<IWorldObject> relevantObjects;

    //relevans objektumok es a kiszamolt tavolsag eltarolasa
    private HashMap<IWorldObject, Double> cameraSensorStoredData;

    public CameraSensorController(AutomatedCar car) {
        this.cameraSensor = new CameraSensor(car);
       // relevantObjects = calculatesOfCameraSensor(car);
       // cameraSensorStoredData = getDataOfCameraSensor(car, relevantObjects);
    }

    @Override
    public void loop() {

    }

    @Override
    public void receiveSignal(Signal s) {

    }

    /**
     * Kiszamolja a latoteret(=haromszog)
     * visszaadja a relevans objektumokat
     * @param car
     * @return relevans objektum lista
     */
    List<IWorldObject> calculatesOfCameraSensor(AutomatedCar car) {
        List<IWorldObject> result = new ArrayList<IWorldObject>();
        fieldView = cameraSensor.getSensorFieldView(car);
        worldObjects = world.checkSensorArea(fieldView);
        result = cameraSensor.getRelevantWorldObjects(worldObjects);
        return result;
    }

    /**
     * Kiszamolja az auto es a relevans objektum tavolsagat es eltarolja
     * @param car
     * @param relevantObjectsList
     * @return HashMap<IWorldObject, Double>
     */
    public HashMap<IWorldObject, Double> getDataOfCameraSensor(AutomatedCar car, List<IWorldObject> relevantObjectsList) {
        HashMap<IWorldObject, Double> result = new HashMap<IWorldObject, Double>();
        double distanceInCoordinate;
        double distanceInMeter;

        for (IWorldObject element : relevantObjectsList) {
            distanceInCoordinate = cameraSensor.carDistanceFromObjectInCoordinate(car, element);
            distanceInMeter = cameraSensor.carDistanceFromObjectInMeter(distanceInCoordinate);
            result.put(element, distanceInMeter);
        }
        return result;
    }
}
