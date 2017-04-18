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
	private Resizer resizer;
	private RadarSensor radarSensor;
	private AutomatedCar automatedCar;
	private World world;
	private List<IWorldObject> detectedObjects;

	public RadarController (AutomatedCar car,World world) {
		resizer = Resizer.getResizer();
		this.automatedCar = car;
		this.world = world;
		detectedObjects = new ArrayList<IWorldObject>();
		initSensor();
	}

	private void initSensor(){
		radarSensor = new RadarSensor();
	}

    @Override
    public void loop(){
        VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.RADAR_SENSOR_ID, null));
        Triangle sensorArea = radarSensor.calculateCoordinates(automatedCar.getPositionObj(), -automatedCar.getAxisAngle());
		detectedObjects = world.checkSensorArea(sensorArea);
		logInformationOfDetectedObjectsByRadarSensor();
	}

	private void logInformationOfDetectedObjectsByRadarSensor(){
		System.out.println("\nDetected objects by the radar sensor: ");
		for (IWorldObject obj: detectedObjects) {
			Point detectedObject = new Point((int)obj.getCenterX(),(int)obj.getCenterY());
			System.out.println(obj.getClass().getSimpleName() + " " + obj.getImageName() + " X:" + obj.getCenterX() + " Y:" + obj.getCenterY() + ", distance from car center: " + resizer.coordinateToMeter(detectedObject.distance(automatedCar.getCenterX(),automatedCar.getCenterY()))+ " m");
		}
	}

    @Override
    public void receiveSignal(Signal s) { }
}