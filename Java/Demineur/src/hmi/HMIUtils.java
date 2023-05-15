package hmi;

import java.awt.Image;

import javax.swing.ImageIcon;

import constants.HMIConstants;

public class HMIUtils {

	private HMIUtils() {
	}

	public static ImageIcon get_scalled_icon(String image_path, int width, int height) {

		Image img = new ImageIcon(image_path).getImage();
		Image scalled_image = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		ImageIcon imageIcon = new ImageIcon(scalled_image);
		return imageIcon;

	}
}
