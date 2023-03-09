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

public class UnderWaterPanel extends AbstractPanel {

	private static final long serialVersionUID = 6917913385357901059L;

	private Container parentContainer = null;

	private BufferedImage simple_submarine_buffered_image = null;
	private File simple_submarine_image_file = null;
	private final String simple_submarine_image_path = "Images/simple_submarine.png";
	private JLabel simple_submarine_image_as_label = null;

	public UnderWaterPanel(Container parentContainer, int window_width, JPanel pannel_above) {

		super(parentContainer, window_width, 400, Color.BLUE, pannel_above);


	}

}
