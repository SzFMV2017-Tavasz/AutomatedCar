package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.automatedcar.hmi.AutomaticParkingStates;
import hu.oe.nik.szfmv17t.automatedcar.hmi.DirectionIndicatorStates;
import hu.oe.nik.szfmv17t.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;

/**
 * Created by SebestyenMiklos on 2017. 03. 26..
 */
public class UltrasonicController extends SystemComponent {
	
	private static List<UltrasonicSensor> ultrasonicSensors;
	private AutomatedCar automatedCar;
	private World world;
	private Map<Integer, IWorldObject> seenObjectsBySensor;
	private DirectionIndicatorStates indicator;
	private List<Boolean> activatedSensors;
	private AutomaticParkingStates parkingState;
	private boolean spaceFound;
    
	public UltrasonicController(AutomatedCar auto, World world) {
		ultrasonicSensors = new ArrayList<UltrasonicSensor>();
		seenObjectsBySensor = new HashMap<Integer, IWorldObject>();
		this.automatedCar = auto;
		this.world = world;
		initSensors();
		indicator = DirectionIndicatorStates.Default;
		activatedSensors = new ArrayList<Boolean>();
		for(int i = 0;i<ultrasonicSensors.size();i++)
			activatedSensors.add(false);
		parkingState = AutomaticParkingStates.Off;
		spaceFound = false;
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
    	for(UltrasonicSensor sensor : ultrasonicSensors) {
			VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.ULTRASONIC_SENSOR_ID, (int) sensor.getSensorNumber()));
		}
		for(int i = 1; i < 9; i++) {
			ultrasonicSensors.get(i - 1).calculateCoordinates(i, automatedCar.getAxisAngle(), automatedCar.getCenterX(), automatedCar.getCenterY());
		}

		if(world != null) {
			//System.out.println("=== BEGIN Ultrasonic Sensor triangles requesting objects ===");
			for(UltrasonicSensor us : ultrasonicSensors) {
				//System.out.println(us.getSensorViewTriangle().toString());
				
				List<IWorldObject> allSeenObjects = new ArrayList<IWorldObject>();
				allSeenObjects.addAll(world.checkSensorArea(us.getSensorViewTriangle()));
				for(IWorldObject wo : allSeenObjects) {
					seenObjectsBySensor.put(us.getSensorNumber(), wo);
				}
			}
			//System.out.println("=== END Ultrasonic Sensor triangles requesting objects ===");

			for (Entry<Integer, IWorldObject> entry : seenObjectsBySensor.entrySet()) {
			    Integer sensor = entry.getKey();
			    IWorldObject wo = entry.getValue();
			    //System.out.println("Detected by sensor: " + sensor);
			    //System.out.println(wo.getClass().getSimpleName() + " X: " + wo.getCenterX() + " Y: " + wo.getCenterY());
			}
			//System.out.println("---Closest Object Detected by Ultrasonic Sensor---");
			IWorldObject closestObject = getClosestObject();
			if(closestObject != null){
				//System.out.println(closestObject.getClass().getSimpleName() + " X: " + closestObject.getCenterX() + " Y: " + closestObject.getCenterY());
			}
			
			seenObjectsBySensor = new HashMap<Integer, IWorldObject>();
		}
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
    	
    	for (Entry<Integer, IWorldObject> entry : seenObjectsBySensor.entrySet()) {
    		try{
			    Integer sensorNumber = entry.getKey();
			    IWorldObject wo = entry.getValue();
			    if(isFirst){
			    	closestObject = wo;
			    	isFirst = false;
			    }
			    UltrasonicSensor sensor = getUltrasonicSensor(sensorNumber);
			    double newDistance = getDistance(wo.getCenterX(), wo.getCenterY(), sensor.getCoordinates().getMainX(), sensor.getCoordinates().getMainY());
			    double closestDistance = getDistance(closestObject.getCenterX(), closestObject.getCenterY(), sensor.getCoordinates().getMainX(), sensor.getCoordinates().getMainY());
			    
			    if(newDistance < closestDistance){
			    	closestObject = wo;
			    }
    		}
    		catch(Exception e){
    			System.out.println(e);
    			System.out.println(e.getMessage());
    		}
		}
    	
    	return closestObject;
    }
    
    public double getDistance(double x1, double y1, double x2, double y2){ 
    	return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public void activateSensor(int id){
    	activatedSensors.remove(id-1);
    	activatedSensors.add(id-1,true);
	}
	public void deactivateSensor(int id){
		activatedSensors.remove(id-1);
		activatedSensors.add(id-1,false);
	}

	public boolean getSpaceFound(){return spaceFound;}

}
