package hmi.widgets;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hmi.DemineurMainViewFrame;

public class SevenSegmentLedsNumberDisplayFromDemineurImage extends JPanel {

	private static final long serialVersionUID = -5796505066525142407L;
	private static final Logger LOGGER = LogManager.getLogger(SevenSegmentLedsNumberDisplayFromDemineurImage.class);


	private ArrayList<ImageIcon> zero_to_nine_icons = new ArrayList<>();

	private ImageIcon empty_digit_icon;
	private ImageIcon horizontal_space_between_digits_icon;

	private ArrayList<JLabel> digits_as_label = new ArrayList<>();

	public SevenSegmentLedsNumberDisplayFromDemineurImage(int number_of_digits) {
		setVisible(true);

		for (int number = 0; number <= 9; number++) {
			ImageIcon imageIcon = new ImageIcon("Images/all_digits_as_7_segments_leds_number_" + number + ".png");
			zero_to_nine_icons.add(imageIcon);
		}

		empty_digit_icon = new ImageIcon("Images/all_digits_as_7_segments_leds_number_empty.png");
		horizontal_space_between_digits_icon = new ImageIcon(
				"Images/all_digits_as_7_segments_leds_horizontal_space_between_digits.png");

		setLayout(null);

		for (int digit = 1; digit <= number_of_digits; digit++) {
			JLabel digit_as_label = new JLabel(empty_digit_icon);
			digit_as_label.setSize(getDigitDimension());

			// digit_as_label.setLocation((int) ((digit - 1) *
			// (getDigitDimension().getWidth())), 0);

			digit_as_label.setLocation(
					(int) ((digit - 1)
							* (getDigitDimension().getWidth() + horizontal_space_between_digits_icon.getIconWidth())),
					0);
			add(digit_as_label);
			digits_as_label.add(digit_as_label);
			LOGGER.debug("Add digit at " + digit_as_label.getLocation() + " and size:" + digit_as_label.getSize());

			if (digit < number_of_digits) {
				JLabel horizontal_space_between_digits_as_label = new JLabel(horizontal_space_between_digits_icon);
				horizontal_space_between_digits_as_label.setSize(horizontal_space_between_digits_icon.getIconWidth(),
						horizontal_space_between_digits_icon.getIconHeight());
				horizontal_space_between_digits_as_label.setLocation(digit_as_label.getX() + digit_as_label.getWidth(),
						0);
				add(horizontal_space_between_digits_as_label);
				LOGGER.debug("Add horizontal_space_between_digits_as_label at " + horizontal_space_between_digits_as_label.getLocation() + " and size:" + horizontal_space_between_digits_as_label.getSize());
			}

		}
	}

	private Dimension getDigitDimension() {
		ImageIcon imageIcon = zero_to_nine_icons.get(0);
		Dimension dimension = new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
		return dimension;
	}

}
