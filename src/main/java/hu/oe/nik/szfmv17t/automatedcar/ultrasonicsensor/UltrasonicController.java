package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;

import java.util.ArrayList;
import java.util.List;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;

/**
 * Created by SebestyenMiklos on 2017. 03. 26..
 */
public class UltrasonicController extends SystemComponent {
	
	private List<UltrasonicSensor> ultrasonicSensors;
	private AutomatedCar automatedCar;
	private List<ICollidableObject> allSeenObjectsBuffer;
    
	public UltrasonicController(AutomatedCar auto) {
		this.automatedCar = auto;
		initSensors();
	}
	private void initSensors(){
		ultrasonicSensors = new ArrayList<UltrasonicSensor>();
		
	}
    @Override
    public void loop() {

    }

    @Override
    public void receiveSignal(Signal s) {

    }
}
