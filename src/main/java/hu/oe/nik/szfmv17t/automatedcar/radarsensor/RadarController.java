package hu.oe.nik.szfmv17t.automatedcar.radarsensor;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.utils.Resizer;
import hu.oe.nik.szfmv17t.environment.utils.Triangle;
import hu.oe.nik.szfmv17t.environment.domain.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RadarController extends SystemComponent{
	public static final int AVOID_ALERT = 100;
	private Resizer resizer;
	private RadarSensor radarSensor;
	private AutomatedCar automatedCar;
	private World world;
	private List<IWorldObject> detectedObjects;
	private List<IWorldObject> allObjectsInCarLane;
	private List<IWorldObject> possibleCollisionObjects;
	
	private EntityMovementAnalyzer objectTracker;
	List<Entity> detectedEntites;
	List<Entity> detectedEntitesInPossibleCollision;
	
	public RadarController (AutomatedCar car,World world) {
		resizer = Resizer.getResizer();
		this.automatedCar = car;
		this.world = world;
		detectedObjects = new ArrayList<IWorldObject>();
		allObjectsInCarLane = new ArrayList<IWorldObject>();
		possibleCollisionObjects = new ArrayList<IWorldObject>();
		
		this.objectTracker=new EntityMovementAnalyzer();
		initSensor();
	}

	private void initSensor(){
		radarSensor = new RadarSensor();
	}
    
	private IWorldObject getClosestObject(List<IWorldObject> allObjectsInCarLane){
		double mindistance=Double.MAX_VALUE;
		IWorldObject closestIWorldObject=allObjectsInCarLane.get(0);
		for (IWorldObject currIWorldObject : allObjectsInCarLane) {
			Point wObjCenter=new Point((int)currIWorldObject.getCenterX(),(int)currIWorldObject.getCenterY());
			double currDistance=wObjCenter.distance(automatedCar.getCenterX(),automatedCar.getCenterY());
			if(currDistance<mindistance){
				mindistance=currDistance;
				closestIWorldObject=currIWorldObject;
			}			
		}
		return closestIWorldObject;
	}
    @Override
    public void loop(){
        VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.RADAR_SENSOR_ID, null));
        Triangle sensorArea = radarSensor.calculateCoordinates(automatedCar.getPositionObj(), -automatedCar.getAxisAngle());
		detectedObjects = world.checkSensorArea(sensorArea);
		
		allObjectsInCarLane=radarSensor.selectObjectsInCarLane(detectedObjects, automatedCar.getPositionObj(),-automatedCar.getAxisAngle(),1.5);
		detectedEntites=objectTracker.updateEntityList(detectedObjects);
		
		objectTracker.predictEntities();
		
		logInformationOfDetectedObjectsByRadarSensor();
		logAllObjectsInCarLane();
		logDetectedEntities();
	//logClosestObjectInCarLane();
		
		possibleCollisionObjects = radarSensor.selectObjectsInCarLane(detectedObjects, automatedCar.getPositionObj(),-automatedCar.getAxisAngle(),automatedCar.getWidth()/2);
		detectedEntitesInPossibleCollision=objectTracker.updateEntityList(possibleCollisionObjects);
		
		if (radarSensor.willWeCollideWithStaticObjects(detectedEntitesInPossibleCollision, automatedCar)){
			logPossibleCollisions();
		}

		if(radarSensor.isAvoidableCollisionAlert()){
			VirtualFunctionBus.sendSignal(new Signal(AVOID_ALERT,1));
		}else{
			VirtualFunctionBus.sendSignal(new Signal(AVOID_ALERT,0));
		}
	}
    
    private void logPossibleCollisions()
    {
    	System.out.println("WE WILL COLLIDE!!!");
    	System.out.println("\nObjects that could collide with us in the future: ");
    	for (IWorldObject obj: possibleCollisionObjects)
    	{
    		Point detectedObject = new Point((int)obj.getCenterX(),(int)obj.getCenterY());
    		System.out.println(obj.getClass().getSimpleName() + " " + obj.getImageName() + " X:" + obj.getCenterX() + " Y:" + obj.getCenterY() + ", distance from car center: " + resizer.coordinateToMeter(detectedObject.distance(automatedCar.getCenterX(),automatedCar.getCenterY()))+ " m");
    	}
    }
    

	private void logInformationOfDetectedObjectsByRadarSensor(){
		System.out.println("\nDetected objects by the radar sensor: "+detectedObjects.size());
		for (IWorldObject obj: detectedObjects) {
			Point detectedObject = new Point((int)obj.getCenterX(),(int)obj.getCenterY());
			//System.out.println(obj.getClass().getSimpleName() + " " + obj.getImageName() + " X:" + obj.getCenterX() + " Y:" + obj.getCenterY() + ", distance from car center: " + resizer.coordinateToMeter(detectedObject.distance(automatedCar.getCenterX(),automatedCar.getCenterY()))+ " m");
			System.out.println(" X:" + obj.getCenterX() + " Y:" + obj.getCenterY() + " "+obj.getClass().getSimpleName() + " " + obj.getImageName() +  ", distance from car center: " + resizer.coordinateToMeter(detectedObject.distance(automatedCar.getCenterX(),automatedCar.getCenterY()))+ " m");

		}
	}
	
	private void logAllObjectsInCarLane(){
		System.out.println("\nOBJECTS IN CAR LANE "+allObjectsInCarLane.size());
		for (IWorldObject obj: allObjectsInCarLane) {
			Point detectedObject = new Point((int)obj.getCenterX(),(int)obj.getCenterY());
			//System.out.println("X:"+automatedCar.getCenterX()+" Y:"+automatedCar.getCenterY()+" "+ obj.getClass().getSimpleName() + " " + obj.getImageName() + " X:" + obj.getCenterX() + " Y:" + obj.getCenterY() + ", distance from car center: " + resizer.coordinateToMeter(detectedObject.distance(automatedCar.getCenterX(),automatedCar.getCenterY()))+ " m");
			System.out.println(" X:" + obj.getCenterX() + " Y:" + obj.getCenterY() + " "+obj.getClass().getSimpleName() + " " + obj.getImageName() +  ", distance from car center: " + resizer.coordinateToMeter(detectedObject.distance(automatedCar.getCenterX(),automatedCar.getCenterY()))+ " m");
		}
	}
	
	private void logClosestObjectInCarLane(){
		System.out.println("\nCLOSEST OBJECT IN CAR LANE: ");
		if(!allObjectsInCarLane.isEmpty())
		{
			IWorldObject theclosestObj= getClosestObject(allObjectsInCarLane);
			if(theclosestObj!=null){
				Point objCenter=new Point((int)theclosestObj.getCenterX(),(int)theclosestObj.getCenterY());
				System.out.print(theclosestObj.getImageName()+" "+objCenter.getClass().getSimpleName()+ " distance from car center: ");
				System.out.format("%.2f m%n",  resizer.coordinateToMeter(objCenter.distance(automatedCar.getCenterX(),automatedCar.getCenterY())));				
			}
		}
	}

	private void logDetectedEntities(){
		System.out.println("\nDETECTED ENTITIES: "+ detectedEntites.size());
		for (Entity ent: detectedEntites) {
			Point detectedObject = new Point((int)ent.getCurrentState().getPosition().getX(),(int)ent.getCurrentState().getPosition().getY());
			System.out.println("X:"+detectedObject.getX()+" Y:"+detectedObject.getY() + ", distance from car center: " + resizer.coordinateToMeter(detectedObject.distance(automatedCar.getCenterX(),automatedCar.getCenterY()))+ " m");
		}
	}
    @Override
    public void receiveSignal(Signal s) { }
}