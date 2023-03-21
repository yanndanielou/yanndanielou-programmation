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

import builders.GameBoardAreaDataModel;
import builders.GameBoardDataModel;

public abstract class AbstractPanel extends JPanel {

	protected Color background_color = null;

	protected BufferedImage background_buffered_image = null;
	protected File background_image_file = null;
	protected String background_image_path = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5846785174444770001L;

	public AbstractPanel(Container parentContainer, int width, int height, Color color, JPanel pannel_above) {
		this.setSize(width, height);
		parentContainer.add(this);
		this.setBackground(color);

		int pannel_above_Y = pannel_above != null ? (int) pannel_above.getBounds().getY() : 0;
		int pannel_above_height = pannel_above != null ? pannel_above.getHeight() : 0;
		this.setBounds(0, pannel_above_Y + pannel_above_height, this.getSize().width, this.getSize().height);
	}

	public AbstractPanel(Container parentContainer, GameBoardDataModel gameBoardDataModel,
			GameBoardAreaDataModel game_board_area_data_model, JPanel pannel_above) {

		this.setSize(gameBoardDataModel.getWidth(), game_board_area_data_model.getHeight());
		parentContainer.add(this);

		if (game_board_area_data_model.getBackground_color_definition() != null) {
			background_color = game_board_area_data_model.getBackground_color_as_awt_color();
			this.setBackground(background_color);
		} else {
			background_image_path = game_board_area_data_model.getBackground_image_path();
			background_image_file = new File(background_image_path);
			try {
				background_buffered_image = ImageIO.read(background_image_file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ImageIcon image_icon = new ImageIcon(background_buffered_image);
			JLabel background_image_as_label = new JLabel(image_icon);
			this.add(background_image_as_label);
		}

		int pannel_above_Y = pannel_above != null ? (int) pannel_above.getBounds().getY() : 0;
		int pannel_above_height = pannel_above != null ? pannel_above.getHeight() : 0;
		this.setBounds(0, pannel_above_Y + pannel_above_height, this.getSize().width, this.getSize().height);
	}

}
