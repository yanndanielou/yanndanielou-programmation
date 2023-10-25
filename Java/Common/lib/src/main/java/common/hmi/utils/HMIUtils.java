package common.hmi.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

	public static JButton createJButtonFromImagePathAndClass(String imagePath, Class<?> class1) {
		return createJButtonFromImage(class1.getResourceAsStream(imagePath));
	}

	public static JButton createJButtonFromImage(String imagePath) {
		ImageIcon buttonIcon = null;

		// Try with
		InputStream stream = HMIUtils.class.getClassLoader().getResourceAsStream(imagePath);
		try {
			BufferedImage bufferedImage = ImageIO.read(stream);
			buttonIcon = new ImageIcon(bufferedImage);
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();

			buttonIcon = new ImageIcon(imagePath);
			if (buttonIcon.getIconHeight() <= 0) {
				buttonIcon = new ImageIcon(imagePath);
			}
		}
		JButton buttonToCreate = new JButton(buttonIcon);
		buttonToCreate.setSize(buttonIcon.getIconWidth(), buttonIcon.getIconHeight());
		buttonToCreate.setBorder(null);
		return buttonToCreate;
	}

	public static JLabel createJLabelFromImagePathAndClass(String imagePath, Class<?> class1) {
		InputStream resourceAsStream = class1.getResourceAsStream(imagePath);
		Image image;
		try {
			image = ImageIO.read(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		ImageIcon imageIcon = new ImageIcon(image);

		return createJLabelFromImageIcon(imageIcon);
	}

	public static JLabel createJLabelFromImageIcon(ImageIcon imageIcon) {
		if (imageIcon.getIconHeight() <= 0) {
			throw new FileSystemNotFoundException("createJLabelFromImageIcon: error");
		}
		JLabel labelToCreate = new JLabel(imageIcon);
		labelToCreate.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
		return labelToCreate;

	}

	public static JLabel createJLabelFromImage(String imagePath) {
		ImageIcon buttonIcon = new ImageIcon(imagePath);
		try {
			JLabel createJLabelFromImageIcon = createJLabelFromImageIcon(buttonIcon);
			return createJLabelFromImageIcon;
		} catch (FileSystemNotFoundException e) {
			throw new FileSystemNotFoundException("Invalid path:" + imagePath);
		}
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

	public static JButton createJButtonFromImage(InputStream stream) {
		// InputStreamReader inputStreamReader = new InputStreamReader(stream);
		Image image;
		try {
			image = ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		ImageIcon buttonIcon = new ImageIcon(image);

		JButton buttonToCreate = new JButton(buttonIcon);
		buttonToCreate.setSize(buttonIcon.getIconWidth(), buttonIcon.getIconHeight());
		buttonToCreate.setBorder(null);
		return buttonToCreate;
	}

}
