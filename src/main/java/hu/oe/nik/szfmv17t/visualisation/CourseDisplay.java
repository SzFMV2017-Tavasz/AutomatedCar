package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.environment.World;
import hu.oe.nik.szfmv17t.environment.WorldObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CourseDisplay {

	private static final Logger logger = LogManager.getLogger();
	private JFrame frame = new JFrame("OE NIK Automated Car Project");

	public void refreshFrame() {
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}

	public void init(World world){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel worldObjectsJPanel = new JPanel() {
					  private static final long serialVersionUID = 1L;

					  public void paintComponent(Graphics g) {

						  for (WorldObject object : world.getWorldObjects()) {
							  // draw objects
							  BufferedImage image;
							  try {
								  image = ImageIO.read(new File(ClassLoader.getSystemResource(object.getImageFileName()).getFile()));
								  g.drawImage(image, object.getX(), object.getY(), null);
							  } catch (IOException e) {
								  logger.error(e.getMessage());
							  }
						  }
					  }
				  };

		mainPanel.add(worldObjectsJPanel,BorderLayout.CENTER);
		mainPanel.add(getSmiJPanel(), BorderLayout.SOUTH);

		addSmiKeyEventListenerToFrame();

		frame.setSize(world.getWidth(), world.getHeight());
		frame.add(mainPanel);
		frame.validate();
		frame.setVisible(true);
	}

	public JPanel getSmiJPanel() {
		JPanel smiPanel = new HmiJPanel();

		frame.addKeyListener(HmiJPanel.getHmi());
		//smiPanel.add(new Label("Hello SMI"));
		return  smiPanel;
	}

	public void addSmiKeyEventListenerToFrame() {
		if(frame != null && HmiJPanel.getHmi() != null) {
			frame.addKeyListener(HmiJPanel.getHmi());
		}else{
			logger.error("JFrame frame or HmiJPanel.getHmi() returned null");
		}
	}
}
