package hu.oe.nik.szfmv17t;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.automatedcar.hmi.HMI;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.visualisation.CourseDisplay;
import hu.oe.nik.szfmv17t.visualisation.HmiJPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	private static final Logger logger = LogManager.getLogger();
	public static final int CYCLE_PERIOD = 200;

	public static void main(String[] args) {
		CourseDisplay vis = new CourseDisplay();

		// create the world
		World w = new World("src/main/resources/test_world.xml");
		// create an automated car NEW signature
		AutomatedCar car = new AutomatedCar(250,250,108,240,0d,0,"car_1_white.png",100d,0d,0d);

		// create an automated car

		//create HMI - Human machine interface
		HMI hmi = new HMI();
		HmiJPanel.setHmi(hmi);


		// add car to the world
		w.addObjectToWorld(car);

		// init visualisation module with the world
		vis.init(w);
		Thread drawThread=new Thread(vis);
		drawThread.start();
		while(true) {
			try {
				car.drive();
				vis.refreshFrame();
				w.updateWorld();
				Thread.sleep(CYCLE_PERIOD);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}
	}
}