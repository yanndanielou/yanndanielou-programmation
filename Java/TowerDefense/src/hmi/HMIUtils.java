package hmi;

import java.awt.Image;
import java.nio.file.FileSystemNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class HMIUtils {

	private HMIUtils() {
	}

	public static ImageIcon getScalledIcon(String image_path, int width, int height) {

		Image img = new ImageIcon(image_path).getImage();
		Image scalled_image = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		ImageIcon imageIcon = new ImageIcon(scalled_image);
		return imageIcon;

	}

	public static JButton createJButtonFromImage(String imagePath) {
		ImageIcon buttonIcon = new ImageIcon(imagePath);
		if (buttonIcon.getIconHeight() <= 0) {
			throw new FileSystemNotFoundException("Invalid path:" + imagePath);
		}
		JButton buttonToCreate = new JButton(buttonIcon);
		buttonToCreate.setSize(buttonIcon.getIconWidth(), buttonIcon.getIconHeight());
		buttonToCreate.setBorder(null);
		return buttonToCreate;
	}

	public static JLabel createJLabelFromImage(String imagePath) {
		ImageIcon buttonIcon = new ImageIcon(imagePath);
		if (buttonIcon.getIconHeight() <= 0) {
			throw new FileSystemNotFoundException("Invalid path:" + imagePath);
		}
		JLabel labelToCreate = new JLabel(buttonIcon);
		labelToCreate.setSize(buttonIcon.getIconWidth(), buttonIcon.getIconHeight());
		return labelToCreate;
	}
}
