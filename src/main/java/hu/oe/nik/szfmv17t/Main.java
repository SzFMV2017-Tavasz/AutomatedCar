package hu.oe.nik.szfmv17t;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.automatedcar.hmi.HMI;
import hu.oe.nik.szfmv17t.environment.World;
import hu.oe.nik.szfmv17t.visualisation.CourseDisplay;
import hu.oe.nik.szfmv17t.visualisation.HmiJPanel;

public class Main {

	private static final Logger logger = LogManager.getLogger();
	public static final int CYCLE_PERIOD = 200;

	public static void main(String[] args) {
		CourseDisplay vis = new CourseDisplay();

		// create the world
		World w = new World(800,600);

		// create an automated car
		AutomatedCar car = new AutomatedCar(20,20, "bosch1.png");

		//create HMI - Human machine interface
		HMI hmi = new HMI();
		HmiJPanel.setHmi(hmi);

		// add car to the world
		w.addObjectToWorld(car);

		// init visualisation module with the world
		vis.init(w);

		while(true) {
			try {
				car.drive();
				vis.refreshFrame();
				Thread.sleep(CYCLE_PERIOD);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
