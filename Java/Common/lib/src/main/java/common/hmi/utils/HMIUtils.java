package main.common.hmi.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class HMIUtils {

	private HMIUtils() {
	}

	public static ImageIcon getScalledIcon(String imagePath, int width, int height) {

		Image img = new ImageIcon(imagePath).getImage();
		Image scalledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		ImageIcon imageIcon = new ImageIcon(scalledImage);
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

	public static BufferedImage getBufferedImageFromFilePath(String imagePath) {
		File imageFile = new File(imagePath);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(imageFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return bufferedImage;
	}
}
