package hu.oe.nik.szfmv17t;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.automatedcar.hmi.HMI;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.utils.Config;
import hu.oe.nik.szfmv17t.visualisation.CourseDisplay;
import hu.oe.nik.szfmv17t.visualisation.HmiJPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class Main {

	private static final Logger logger = LogManager.getLogger();
//	public static final int worldHeight = 600;
	//public static final int worldWidth= 800;
	//public static final int FPS=60;
	public static void main(String[] args) throws InterruptedException {
		CourseDisplay vis = new CourseDisplay();
		//not a pun
		// create the world
		World w = new World("src/main/resources/test_world.xml");
		// create an automated car NEW signature
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	//	int width = gd.getDisplayMode().getWidth();
	//	int height = gd.getDisplayMode().getHeight();
		AutomatedCar car = new AutomatedCar((double)Config.startPosition.getMinimumX(),(double)Config.startPosition.getMinimumY()-50,2,100d,100d,1,"car_1_white.png",100d,0d,0);

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
				Thread.sleep(Config.CYCLE_PERIOD);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
