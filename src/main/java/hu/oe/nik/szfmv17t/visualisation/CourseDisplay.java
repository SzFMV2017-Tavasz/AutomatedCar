package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.environment.domain.World;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldVisualisation;
import hu.oe.nik.szfmv17t.environment.utils.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class CourseDisplay implements Runnable{

	private static final Logger logger = LogManager.getLogger();
	private JFrame frame = new JFrame("OE NIK Automated Car Project");
	private JPanel hmiJPanel;
	private JPanel mainPanel;
	private JPanel worldObjectsJPanel;
	//private Drawer drawer;
	private IWorldVisualisation world;
	private BufferStrategy strategy;

	public void refreshFrame() {
		frame.invalidate();
		hmiJPanel.invalidate();
		//mainPanel.invalidate();
		//frame.pack();
		frame.validate();
		frame.repaint();
	}

	public void init(IWorldVisualisation world){

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.world=world;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.createBufferStrategy(4);
		strategy = frame.getBufferStrategy();
		worldObjectsJPanel = new JPanel();
		mainPanel.add(worldObjectsJPanel,BorderLayout.CENTER);
		hmiJPanel = getSmiJPanel();
		mainPanel.add(hmiJPanel, BorderLayout.SOUTH);
		SizeFrame(frame);
		//Solve the duplicated key listener
		//addSmiKeyEventListenerToFrame();
		frame.add(mainPanel);
		frame.validate();
		//frame.setVisible(true);
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

	private void SizeFrame(JFrame frame)
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		frame.setSize(Config.getScreenWidth,Config.getScreenHeight- 50);
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
		int refreshRate = 1000 / Config.FPS;
		while (true)
		{
			try {
				Thread.sleep(refreshRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Drawer.getDrawer(world).DrawFrametoPanel(worldObjectsJPanel,world,mainPanel);
			} catch (IOException e) {
				e.printStackTrace();
			}
			refreshFrame();
		}
	}
}
