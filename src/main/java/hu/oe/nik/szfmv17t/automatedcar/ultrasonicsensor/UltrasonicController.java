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

	// Alapértelmezetten autó felfele néz, óramutató járásával megegyezően vannak megszámozva a szenzorok
	// Ennek megfelelően autó jobb felső sarkánál az előre néző szenzor az 1-es számú
	private void initSensors(){
		ultrasonicSensors = new ArrayList<UltrasonicSensor>(){
			UltrasonicSensor US1 = new UltrasonicSensor(1, automatedCar.getCenterX()+15, automatedCar.getCenterY()-39);
			UltrasonicSensor US2 = new UltrasonicSensor(2, automatedCar.getCenterX()+17, automatedCar.getCenterY()-37);
			UltrasonicSensor US3 = new UltrasonicSensor(3, automatedCar.getCenterX()+17, automatedCar.getCenterY()+37);
			UltrasonicSensor US4 = new UltrasonicSensor(4, automatedCar.getCenterX()+15, automatedCar.getCenterY()+39);
			UltrasonicSensor US5 = new UltrasonicSensor(5, automatedCar.getCenterX()-15, automatedCar.getCenterY()+39);
			UltrasonicSensor US6 = new UltrasonicSensor(6, automatedCar.getCenterX()-17, automatedCar.getCenterY()+37);
			UltrasonicSensor US7 = new UltrasonicSensor(7, automatedCar.getCenterX()-17, automatedCar.getCenterY()-37);
			UltrasonicSensor US8 = new UltrasonicSensor(8, automatedCar.getCenterX()-15, automatedCar.getCenterY()-39);
		};
	}

	public void sensorPositionRefresh(AutomatedCar auto){

	}

    @Override
    public void loop() {

    }

    @Override
    public void receiveSignal(Signal s) {

    }
}
