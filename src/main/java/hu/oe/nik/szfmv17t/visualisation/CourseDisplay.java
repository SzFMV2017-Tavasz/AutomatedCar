package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.Main;
import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CourseDisplay implements Runnable{

	private static final Logger logger = LogManager.getLogger();
	private JFrame frame = new JFrame("OE NIK Automated Car Project");
	private JPanel hmiJPanel;
	private JPanel mainPanel;
	private JPanel worldObjectsJPanel;
	//private Drawer drawer;
	private World world;
	public void refreshFrame() {
		frame.invalidate();
		hmiJPanel.invalidate();
		//mainPanel.invalidate();
		frame.validate();
		frame.repaint();
	}

	public void init(World world){
		this.world=world;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Graphics g=Drawer.getDrawer(world,Main.worldHeight,Main.worldWidth).getComposer().composeFrame();
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		worldObjectsJPanel = new JPanel() {
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
		//worldObjectsJPanel.setPreferredSize(new Dimension(600,400));

		mainPanel.add(worldObjectsJPanel,BorderLayout.CENTER);
		hmiJPanel = getSmiJPanel();
		mainPanel.setPreferredSize(new Dimension(700,500));
		mainPanel.add(hmiJPanel, BorderLayout.SOUTH);

		FillMainpanel(mainPanel);
		SizeFrame(frame);
		//Solve the duplicated key listener
		//addSmiKeyEventListenerToFrame();


		frame.add(mainPanel);
		frame.validate();
		frame.setVisible(true);
		frame.setResizable(false);

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
	private void FillMainpanel(JPanel mainPanel)
	{
		mainPanel.add(filler(),BorderLayout.LINE_END);
		mainPanel.add(filler(),BorderLayout.LINE_START);
		mainPanel.add(filler(),BorderLayout.PAGE_START);
	}
	private void SizeFrame(JFrame frame)
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		frame.setSize(width,height-50);
	}
	private JPanel filler()
	{
		JPanel filler=new JPanel();
		filler.setOpaque(true);
		filler.setBackground(Color.orange);
		filler.setPreferredSize(new Dimension(200, 40));
		return filler;
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
			Drawer.getDrawer(world).DrawFrametoPanel(worldObjectsJPanel,world,mainPanel);
			refreshFrame();
		}
	}
}
