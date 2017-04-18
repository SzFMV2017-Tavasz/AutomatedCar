package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

import java.util.ArrayList;
import java.util.List;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;

/**
 * Created by SebestyenMiklos on 2017. 03. 26..
 */
public class UltrasonicController extends SystemComponent {

	private static List<UltrasonicSensor> ultrasonicSensors;
	private AutomatedCar automatedCar;
	private List<IWorldObject> allSeenObjectsBuffer;
	private World world;

	public UltrasonicController(AutomatedCar auto, World world) {
		ultrasonicSensors = new ArrayList<UltrasonicSensor>();
		allSeenObjectsBuffer = new ArrayList<IWorldObject>();
		this.automatedCar = auto;
		this.world = world;
		initSensors();
	}

	// Alapértelmezetten autó felfele néz, óramutató járásával megegyezően vannak megszámozva a szenzorok
	// Ennek megfelelően autó jobb felső sarkánál az előre néző szenzor az 1-es számú
	private void initSensors() {
		for(int i = 1; i < 9; i++) {
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
			System.out.println("=== BEGIN Ultrasonic Sensor triangles requesting objects ===");
			for(UltrasonicSensor us : ultrasonicSensors) {
				System.out.println(us.getSensorViewTriangle().toString());
				allSeenObjectsBuffer.addAll(world.checkSensorArea(us.getSensorViewTriangle()));
			}
			System.out.println("=== END Ultrasonic Sensor triangles requesting objects ===");
			for(IWorldObject wo : allSeenObjectsBuffer) {
				System.out.println(wo.getClass().getSimpleName() + " X: " + wo.getCenterX() + " Y: " + wo.getCenterY());
			}
			allSeenObjectsBuffer = new ArrayList<IWorldObject>();
		}
	}

	@Override
	public void receiveSignal(Signal s) {

	}

	public static UltrasonicSensor getUltrasonicSensor(int sensorNumber) {
		return ultrasonicSensors.get(sensorNumber);
	}
}
