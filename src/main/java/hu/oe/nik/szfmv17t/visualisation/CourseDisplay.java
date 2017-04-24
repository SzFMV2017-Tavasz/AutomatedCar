package hu.oe.nik.szfmv17t.visualisation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv17t.environment.interfaces.IWorldVisualisation;
import hu.oe.nik.szfmv17t.environment.utils.Config;

public class CourseDisplay implements Runnable, ActionListener {

	private static final Logger logger = LogManager.getLogger();
	private JFrame frame = new JFrame("OE NIK Automated Car Project");
	private JPanel hmiJPanel;
	private JPanel keyPanel;
	private JPanel mainPanel;
	private JPanel worldObjectsJPanel;
	//private Drawer drawer;
	private IWorldVisualisation world;
	private BufferStrategy strategy;
	Timer timer = new Timer(1000 / Config.FPS, this);

	public void refreshFrame() {
		frame.invalidate();
		hmiJPanel.invalidate();
		//mainPanel.invalidate();
		//frame.pack();
		frame.validate();
		frame.repaint();
	}

	public void init(IWorldVisualisation world) {

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.world = world;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.createBufferStrategy(4);
		strategy = frame.getBufferStrategy();
		worldObjectsJPanel = new JPanel();
		mainPanel.add(worldObjectsJPanel, BorderLayout.CENTER);
		hmiJPanel = getSmiJPanel();
		keyPanel = new KeyPanel();
		mainPanel.add(hmiJPanel, BorderLayout.SOUTH);
		mainPanel.add(keyPanel, BorderLayout.WEST);

		SizeFrame(frame);
		//Solve the duplicated key listener
		//addSmiKeyEventListenerToFrame();
		frame.add(mainPanel);
		frame.validate();
		//frame.setVisible(true);
		frame.setResizable(false);
		timer.start();
	}

	public JPanel getSmiJPanel() {
		JPanel hmiJPanel = new HmiJPanel();

		frame.addKeyListener(HmiJPanel.getHmi());
		//smiPanel.add(new Label("Hello SMI"));
		return hmiJPanel;
	}

	public void addSmiKeyEventListenerToFrame() {
		if((frame != null) && (HmiJPanel.getHmi() != null)) {
			frame.addKeyListener(HmiJPanel.getHmi());
		} else {
			logger.error("JFrame frame or HmiJPanel.getHmi() returned null");
		}
	}

	private void SizeFrame(JFrame frame) {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		frame.setSize(Config.getScreenWidth, Config.getScreenHeight - 50);
	}

	private JPanel filler() {
		JPanel filler = new JPanel();
		filler.setOpaque(true);
		filler.setBackground(Color.orange);
		filler.setPreferredSize(new Dimension(200, 40));
		return filler;
	}

	@Override
	public void run() {
		/*int refreshRate = 1000 / Config.FPS;
		
		
			try {
				Drawer.getDrawer(world).DrawFrametoPanel(worldObjectsJPanel,world,mainPanel);
			} catch (IOException e) {
				e.printStackTrace();
			}
			refreshFrame();
			*/

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Drawer.getDrawer(world).DrawFrametoPanel(worldObjectsJPanel, world, mainPanel);
		} catch(IOException er) {
			er.printStackTrace();
		}
		refreshFrame();
	}
}
