package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

import java.util.ArrayList;
import java.util.List;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;

/**
 * Created by SebestyenMiklos on 2017. 03. 26..
 */
public class UltrasonicController extends SystemComponent {
	
	private static List<UltrasonicSensor> ultrasonicSensors;
	private AutomatedCar automatedCar;
	private List<ICollidableObject> allSeenObjectsBuffer;
    
	public UltrasonicController(AutomatedCar auto) {
		this.automatedCar = auto;
		initSensors();
	}

	// Alapértelmezetten autó felfele néz, óramutató járásával megegyezően vannak megszámozva a szenzorok
	// Ennek megfelelően autó jobb felső sarkánál az előre néző szenzor az 1-es számú
	private void initSensors(){
		ultrasonicSensors = new ArrayList<UltrasonicSensor>();
        ultrasonicSensors.add(new UltrasonicSensor(0, automatedCar.getCenterX()-15, automatedCar.getCenterY()-39));
        ultrasonicSensors.add(new UltrasonicSensor(1, automatedCar.getCenterX()+15, automatedCar.getCenterY()-39));
        ultrasonicSensors.add(new UltrasonicSensor(2, automatedCar.getCenterX()+17, automatedCar.getCenterY()-37));
        ultrasonicSensors.add(new UltrasonicSensor(3, automatedCar.getCenterX()+17, automatedCar.getCenterY()+37));
        ultrasonicSensors.add(new UltrasonicSensor(4, automatedCar.getCenterX()+15, automatedCar.getCenterY()+39));
        ultrasonicSensors.add(new UltrasonicSensor(5, automatedCar.getCenterX()-15, automatedCar.getCenterY()+39));
        ultrasonicSensors.add(new UltrasonicSensor(6, automatedCar.getCenterX()-17, automatedCar.getCenterY()+37));
        ultrasonicSensors.add(new UltrasonicSensor(7, automatedCar.getCenterX()-17, automatedCar.getCenterY()-37));

	}

	public void sensorPositionRefresh(AutomatedCar auto){
        for(UltrasonicSensor sensor : ultrasonicSensors){
            VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.ULTRASONIC_SENSOR_ID,(int)sensor.getSensorNumber()));
        }
	}

    @Override
    public void loop() {
        for(UltrasonicSensor sensor : ultrasonicSensors){
            VirtualFunctionBus.sendSignal(new Signal(PowertrainSystem.ULTRASONIC_SENSOR_ID,(int)sensor.getSensorNumber()));
        }
    }

    @Override
    public void receiveSignal(Signal s) {

    }

    public static UltrasonicSensor getUltrasonicSensor(int sensorNumber){
        return ultrasonicSensors.get(sensorNumber);
    }
}
