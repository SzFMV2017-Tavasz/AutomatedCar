package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.automatedcar.hmi.AutomaticParkingStates;
import hu.oe.nik.szfmv17t.automatedcar.hmi.DirectionIndicatorStates;
import hu.oe.nik.szfmv17t.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv17t.environment.domain.Sign;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;

/**
 * Created by SebestyenMiklos on 2017. 03. 26..
 */
public class UltrasonicController extends SystemComponent {
	
	private static List<UltrasonicSensor> ultrasonicSensors;
	private AutomatedCar automatedCar;
	private World world;
	private List<IWorldObject>[] seenObjectsBySensor;
	private DirectionIndicatorStates indicator;
	private List<Boolean> activatedSensors;
	private AutomaticParkingStates parkingState;
	private boolean spaceFound;
	private ParkingSpaceType parkingSpaceType;
    
	public UltrasonicController(AutomatedCar auto, World world) {
		ultrasonicSensors = new ArrayList<UltrasonicSensor>();
		seenObjectsBySensor = (List<IWorldObject>[])new List[8];
		this.automatedCar = auto;
		this.world = world;
		initSensors();
		indicator = DirectionIndicatorStates.Default;
		activatedSensors = new ArrayList<Boolean>();
		for (int i = 0; i < ultrasonicSensors.size(); i++)
			activatedSensors.add(true);
		parkingState = AutomaticParkingStates.Off;
		parkingSpaceType = ParkingSpaceType.None;
	}

	// Alapértelmezetten autó felfele néz, óramutató járásával megegyezően vannak megszámozva a szenzorok
	// Ennek megfelelően autó jobb felső sarkánál az előre néző szenzor az 1-es számú
	private void initSensors() {
		for (int i = 1; i < 9; i++) {
			ultrasonicSensors.add(new UltrasonicSensor(i, automatedCar.getCenterX(), automatedCar.getCenterY(), automatedCar.getAxisAngle()));
		}
	}

    @Override
    public void loop() {

		List<IWorldObject> allObjectsSeenBySensors = new ArrayList<IWorldObject>();

        searchingModeLeft();
        searchingModeRight();
        parkingModeOn();

        //System.out.println("=== BEGIN Ultrasonic Sensor triangles requesting objects ===");

        for (int i = 0; i < ultrasonicSensors.size(); i++) {
            if (activatedSensors.get(i)) {
                VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.ULTRASONIC_SENSOR_ID, (int) ultrasonicSensors.get(i).getSensorNumber()));
                ultrasonicSensors.get(i).calculateCoordinates(i + 1, automatedCar.getAxisAngle(), automatedCar.getCenterX(), automatedCar.getCenterY());

                //System.out.println(ultrasonicSensors.get(i).getSensorViewTriangle().toString());

                if (world != null) {
					List<IWorldObject> allSeenObjects = new ArrayList<IWorldObject>();
					allSeenObjects.addAll(world.checkSensorArea(ultrasonicSensors.get(i).getSensorViewTriangle()));
					seenObjectsBySensor[i] = new ArrayList<IWorldObject>();
					seenObjectsBySensor[i] = allSeenObjects;
					allObjectsSeenBySensors.addAll(allSeenObjects);
				}
            }
        }

        //System.out.println("=== END Ultrasonic Sensor triangles requesting objects ===");

        IWorldObject closestBollard = getClosestBallardForParkingType(allObjectsSeenBySensors);
		for (IWorldObject wo : allObjectsSeenBySensors) {
			chooseParkingSpaceType(closestBollard, getUltrasonicSensor(2));

			System.out.println(wo.getImageName() + " X: " + wo.getCenterX() + " Y: " + wo.getCenterY());

			System.out.println(parkingSpaceType);
		}

		/*System.out.println("---Closest Object Detected by Ultrasonic Sensor---");
		IWorldObject closestObject = getClosestObject();
		if(closestObject != null){
			System.out.println(closestObject.getImageName() + " X: " + closestObject.getCenterX() + " Y: " + closestObject.getCenterY());
		}*/
		demoParking();
        seenObjectsBySensor = (List<IWorldObject>[])new List[8];
        for (int i = 0; i < activatedSensors.size(); i++)
            deactivateSensor(i+1);
    }

    @Override
    public void receiveSignal(Signal s) {
		if(s.getId()==PowertrainSystem.SMI_Indication)
			indicator = DirectionIndicatorStates.values()[(int) s.getData()];
		if(s.getId()==PowertrainSystem.Parking_State)
			parkingState = AutomaticParkingStates.values()[(int) s.getData()];
    }

    public static UltrasonicSensor getUltrasonicSensor(int sensorNumber){
        return ultrasonicSensors.get(sensorNumber - 1);
    }
    
    public IWorldObject getClosestObject(){
    	IWorldObject closestObject = null;
    	boolean isFirst = true;
    	
    	for (int i = 0; i < seenObjectsBySensor.length; i++) {
    		if(seenObjectsBySensor[i] != null) {
				for(IWorldObject wo : seenObjectsBySensor[i]) {
					try {
						if (isFirst) {
							closestObject = wo;
							isFirst = false;
						}
						UltrasonicSensor sensor = getUltrasonicSensor(i + 1);
						double newDistance = getDistance(wo.getCenterX(), wo.getCenterY(), sensor.getCoordinates().getMainX(), sensor.getCoordinates().getMainY());
						double closestDistance = getDistance(closestObject.getCenterX(), closestObject.getCenterY(), sensor.getCoordinates().getMainX(), sensor.getCoordinates().getMainY());

						if (newDistance < closestDistance) {
							closestObject = wo;
						}
					} catch (Exception e) {
						System.out.println(e);
						System.out.println(e.getMessage());
					}
				}
			}
		}
    	
    	return closestObject;
    }

	public IWorldObject getClosestBallardForParkingType(List<IWorldObject> allObjects) {
        IWorldObject closestObject = null;
        boolean isFirst = true;

        for (IWorldObject wo : allObjects) {
            if (wo.getClass() == Sign.class) {
                try {
                    if (isFirst) {
                        closestObject = wo;
                        isFirst = false;
                    }
                    double newDistance = getDistance(wo.getCenterX(), wo.getCenterY(), automatedCar.getCenterX(), automatedCar.getCenterY());
                    double closestDistance = getDistance(closestObject.getCenterX(), closestObject.getCenterY(), automatedCar.getCenterX(), automatedCar.getCenterY());

                    if (newDistance < closestDistance) {
                        closestObject = wo;
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println(e.getMessage());
                }
            }
        }

        return closestObject;
    }
    
    public double getDistance(double x1, double y1, double x2, double y2){ 
    	return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private void activateSensor(int id){
    	activatedSensors.remove(id-1);
    	activatedSensors.add(id-1,true);
	}
	private void deactivateSensor(int id){
		activatedSensors.remove(id-1);
		activatedSensors.add(id-1,false);
	}

	public boolean getSpaceFound(){return spaceFound;}

	private void searchingModeLeft(){
	    if(parkingState == AutomaticParkingStates.Searching && indicator == DirectionIndicatorStates.Left){
            activateSensor(6);
            activateSensor(7);
        }
    }

    private void searchingModeRight(){
        if(parkingState == AutomaticParkingStates.Searching && indicator == DirectionIndicatorStates.Right){
            activateSensor(2);
            activateSensor(3);
        }
    }

    private void parkingModeOn() {
        if (parkingState == AutomaticParkingStates.Parking) {
            for (int i = 0; i < activatedSensors.size(); i++)
                activateSensor(i);
        }
    }

	private void chooseParkingSpaceType(IWorldObject item, UltrasonicSensor sensor){
    	if(item != null) {
			double distance = getDistance(item.getCenterX(), item.getCenterY(), sensor.getCoordinates().getMainX(), sensor.getCoordinates().getMainY());
			if (distance > automatedCar.getHeight())
				parkingSpaceType = ParkingSpaceType.Perpendicular;
			else
				parkingSpaceType = ParkingSpaceType.Parallel;
		}
	}
	private void demoParking(){
		if (parkingState == parkingState.Searching) {
			VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.SMI_Gear, 1));
			VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.SMI_Gaspedal, 10));
			VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.SMI_SteeringWheel, 50));
		}
		if (automatedCar.getCenterY() - 300 > (2005 - 305) && automatedCar.getCenterY() - 300 < (2005 - 270)
				&& parkingState == parkingState.Searching) {
			VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.SMI_Gear, 3));
			VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.SMI_Gaspedal, 0));
			VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.SMI_BrakePedal, 50));
			VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.SMI_SteeringWheel, 0));
		}
	}
}
