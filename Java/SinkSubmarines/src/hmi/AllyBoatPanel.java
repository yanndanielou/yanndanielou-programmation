package hmi;

import java.awt.Color;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AllyBoatPanel extends AbstractPanel {

	private static final long serialVersionUID = 6917913385357901059L;

	private Container parentContainer = null;

	private BufferedImage boat_buffered_image = null;
	private File boat_image_file = null;
	private final String boat_image_path = "Images/AllyBoat.png";
	private JLabel boat_image_as_label = null;

	public AllyBoatPanel(Container parentContainer, int window_width, JPanel pannel_above) {

		super(parentContainer, window_width, 30, Color.GREEN, pannel_above);

		boat_image_file = new File(boat_image_path);

		try {
			boat_buffered_image = ImageIO.read(boat_image_file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		boat_image_as_label = new JLabel(new ImageIcon(boat_buffered_image));
		boat_image_as_label.setLocation(50, 50);

		add(boat_image_as_label);

		/*
		 * for(int i = 0; i< 10000;i++) {
		 * boat_image_as_label.setLocation(boat_image_as_label.getX() + i,
		 * boat_image_as_label.getY()); try { Thread.sleep(1); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */

	}

}
