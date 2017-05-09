package hu.oe.nik.szfmv17t;

import hu.oe.nik.szfmv17t.automatedcar.radarsensor.RadarController;
import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.automatedcar.camerasensor.CameraSensorController;
import hu.oe.nik.szfmv17t.environment.domain.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.automatedcar.hmi.HMI;
import hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor.UltrasonicController;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.visualisation.CourseDisplay;
import hu.oe.nik.szfmv17t.visualisation.HmiJPanel;

public class Main {

	private static final Logger logger = LogManager.getLogger();
	public static final int CYCLE_PERIOD = 20;

	public static final String world_1="src/main/resources/test_world.xml";
	public static final String world_2="src/main/resources/NewLevel.xml";
	public static final String world_3="src/main/resources/AdvancedLevel.xml";

	public static void main(String[] args) {
		CourseDisplay vis = new CourseDisplay();

		// create the world
		World w = new World(world_3);

		// create an automated car NEW signature
		AutomatedCar car = new AutomatedCar(2759, 3318, 108, 240, 0d, 3, "car_1_white.png", 200d, 0d, 0d);
		// create NPC vehicles
		Car npcBlueCar1 = new Car(2759, 3318 + 312 + 36, 108, 240, 0d, 3, "car_1_blue.png", 200d, 0d, 0d);
		Car npcWhiteCar1 = new Car(2761, 2703 + 36, 102, 208, 0d, 3, "car_2_white.png", 200d, 0d, 0d);
		Car npcRedCar1 = new Car(846 + 24,  2005 - 295 + 27 , 108, 240, 0d, 3, "car_1_red.png", 200d, 0d, 0d);
		Car npcWhiteCar2 = new Car(846 + (2*156) + 24,  2005 - 295 + 27 , 108, 240, 0d, 3, "car_1_white.png", 200d, 0d, 0d);
		Car npcRedCar2 = new Car(846 + (5*156) + 24,  2005 - 295 + 27 , 108, 240, 0d, 3, "car_1_red.png", 200d, 0d, 0d);
		Car npcBlueCar2 = new Car(846 + (6*156) + 24,  2005 - 295 + 27 , 108, 240, 0d, 3, "car_1_blue.png", 200d, 0d, 0d);
		Car npcBlackVan1 = new Car(846 + (8*156) + 18,  2005 - 295 + 3 , 108, 240, 0d, 3, "car_3_black.png", 200d, 0d, 0d);

		//create HMI - Human machine interface
		HMI hmi = new HMI();
		HmiJPanel.setHmi(hmi);

		//init Ultrasonic sensor system
		UltrasonicController usController = new UltrasonicController(car, w);

		RadarController radarController = new RadarController(car,w);
		VirtualFunctionBus.registerComponent(radarController);

		CameraSensorController  cameraSensorController = new CameraSensorController(car, w);
		VirtualFunctionBus.registerComponent(cameraSensorController);

		// add player controlled car to the world
		w.addObjectToWorld(car);
		// add NPC vehicles to the world
		w.addObjectToWorld(npcBlueCar1);
		w.addObjectToWorld(npcWhiteCar1);
		w.addObjectToWorld(npcRedCar1);
		w.addObjectToWorld(npcWhiteCar2);
		w.addObjectToWorld(npcRedCar2);
		w.addObjectToWorld(npcBlueCar2);
		w.addObjectToWorld(npcBlackVan1);


		// init visualisation module with the world
		vis.init(w);
		Thread drawThread = new Thread(vis);
		drawThread.start();
		while(true) {
			try {
				car.drive();
				hmi.setCarspeed(car.getSpeed());
				//vis.refreshFrame();
				w.updateWorld();
				Thread.sleep(CYCLE_PERIOD);
			} catch(Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
}
