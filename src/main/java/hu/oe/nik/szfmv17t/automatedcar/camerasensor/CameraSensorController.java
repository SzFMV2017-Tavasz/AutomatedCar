package hu.oe.nik.szfmv17t.automatedcar.camerasensor;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.utils.Triangle;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by winifred on 2017.04.17..
 */
public class CameraSensorController extends SystemComponent {

    private CameraSensor cameraSensor;
    private AutomatedCar car;
    private World world;
    private Triangle fieldView;
    private List<IWorldObject> seenWorldObjects;
    private List<IWorldObject> relevantObjects;
    //relevans objektumok es a kiszamolt tavolsag eltarolasa
    private HashMap<IWorldObject, Double> cameraSensorStoredData;

    public CameraSensorController(AutomatedCar car, World world) {
        this.car = car;
        this.cameraSensor = new CameraSensor(car);
        seenWorldObjects = new ArrayList<IWorldObject>();
        relevantObjects = new ArrayList<IWorldObject>();
        cameraSensorStoredData = new HashMap<IWorldObject, Double>();
        this.world = world;
    }

    @Override
    public void loop() {
        VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.CAMERA_SENSOR_ID, null));
        fieldView = cameraSensor.getSensorFieldView(car);
        seenWorldObjects = world.checkSensorArea(fieldView);
        relevantObjects = cameraSensor.getRelevantWorldObjects(seenWorldObjects);
        cameraSensorStoredData = getDataOfCameraSensor(car, relevantObjects);
        //printOutInformation();
    }

    @Override
    public void receiveSignal(Signal s) {

    }

    /**
     * Kiszamolja az auto es a relevans objektum tavolsagat es eltarolja
     *
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

    private void printOutInformation() {
        System.out.println("\nCamera Sensor relevant objects: ");
        for (Map.Entry<IWorldObject, Double> entry : cameraSensorStoredData.entrySet()) {
            System.out.println(entry.getKey().getImageName() + " X:" + entry.getKey().getCenterX() + " Y:" + entry.getKey().getCenterY());
            System.out.println("Distance from car: " + entry.getValue());
        }
        System.out.println("End Camera Sensor relevant objects: ");
    }
}
