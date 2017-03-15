package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.Main;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CourseDisplay implements Runnable{

	private static final Logger logger = LogManager.getLogger();
	private JFrame frame = new JFrame("OE NIK Automated Car Project");
	private JPanel hmiJPanel;
	private Drawer drawer;

	public void refreshFrame() {
		frame.invalidate();
		hmiJPanel.invalidate();
		frame.validate();
		frame.repaint();
	}

	public void init(World world){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Graphics g=Drawer.getDrawer(world,Main.worldHeight,Main.worldWidth).getComposer().composeFrame();
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel worldObjectsJPanel = new JPanel() {
					  private static final long serialVersionUID = 1L;
					  public void paintComponent(Graphics g) {

						  for (IWorldObject object : world.getWorldObjects()) {
							  // draw objects
							  BufferedImage image;
							  try {
								  image = ImageIO.read(new File(ClassLoader.getSystemResource(object.getImageName()).getFile()));
                                                                  int segedx=((int)(object.getCenterX()+0.5d));
                                                                  int segedy=((int)(object.getCenterY()+0.5d));
								  g.drawImage(image,segedx, segedy, null);
							  } catch (IOException e) {
								  logger.error(e.getMessage());
							  }
						  }
					  }
				  };
		worldObjectsJPanel.setSize(340,400);
		mainPanel.add(worldObjectsJPanel,BorderLayout.CENTER);

		hmiJPanel = getSmiJPanel();
		mainPanel.add(hmiJPanel, BorderLayout.SOUTH);

		//Solve the duplicated key listener
		//addSmiKeyEventListenerToFrame();

		frame.setSize(world.getWidth()/2, world.getHeight()/2);
		frame.setSize(800,600);
		frame.add(mainPanel);
		frame.validate();
		frame.setVisible(true);
	}

	public JPanel getSmiJPanel() {
		JPanel hmiJPanel = new HmiJPanel();

		frame.addKeyListener(HmiJPanel.getHmi());
		//smiPanel.add(new Label("Hello SMI"));
		return hmiJPanel;
	}

	public void addSmiKeyEventListenerToFrame() {
		if(frame != null && HmiJPanel.getHmi() != null) {
			frame.addKeyListener(HmiJPanel.getHmi());
		}else{
			logger.error("JFrame frame or HmiJPanel.getHmi() returned null");
		}
	}

	@Override
	public void run() {
		int refreshRate=1000/Main.FPS;
		while (true)
		{
			try {
				Thread.sleep(refreshRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			drawer.getComposer().composeFrame();
			refreshFrame();
		}
	}
}
