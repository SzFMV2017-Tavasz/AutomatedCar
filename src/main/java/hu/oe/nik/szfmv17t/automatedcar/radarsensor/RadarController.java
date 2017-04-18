package hu.oe.nik.szfmv17t.automatedcar.radarsensor;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv17t.environment.domain.CollidableBase;
import hu.oe.nik.szfmv17t.environment.domain.Tree;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.utils.Triangle;
import hu.oe.nik.szfmv17t.environment.domain.World;

import java.util.ArrayList;
import java.util.List;

public class RadarController extends SystemComponent{
	private RadarSensor radarSensor;
	private AutomatedCar automatedCar;
	private World world;
	private List<IWorldObject> detectedObjects;

	public RadarController (AutomatedCar car,World world) {
		this.automatedCar = car;
		this.world = world;
		detectedObjects = new ArrayList<IWorldObject>();
		initSensor();
	}

	private void initSensor(){
		radarSensor = new RadarSensor();
	}

    @Override
    public void loop() {
        VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.RADAR_SENSOR_ID, null));
        Triangle sensorArea = radarSensor.calculateCoordinates(automatedCar.getPositionObj(), -automatedCar.getAxisAngle());
		detectedObjects = world.checkSensorArea(sensorArea);
		System.out.println("\nDetected objects by the radar sensor: ");
		for (IWorldObject obj: detectedObjects) {
			System.out.println(obj.getClass().getSimpleName() + " X:" + obj.getCenterX() + " Y:" + obj.getCenterY());
		}
	}

    @Override
    public void receiveSignal(Signal s) { }
}
