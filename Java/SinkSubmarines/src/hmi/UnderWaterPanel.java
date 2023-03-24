package hmi;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import builders.gameboard.GameBoardDataModel;
import core.GameManager;
import moving_objects.GameObjectListerner;
import moving_objects.SimpleSubMarine;

public class UnderWaterPanel extends AbstractPanel implements GameObjectListerner {

	private static final long serialVersionUID = 6917913385357901059L;

	private Container parentContainer = null;

	private BufferedImage simple_submarine_buffered_image = null;
	private File simple_submarine_image_file = null;
	private final String simple_submarine_image_path = "Images/simple_submarine.png";
	private JLabel simple_submarine_image_as_label = null;

	public UnderWaterPanel(Container parentContainer, int window_width, GameBoardDataModel gameBoardDataModel,
			JPanel pannel_above) {

		super(parentContainer, gameBoardDataModel, gameBoardDataModel.getUnder_water_game_board_area_data_model(),
				pannel_above);

		
		simple_submarine_image_file = new File(simple_submarine_image_path);

		try {
			simple_submarine_buffered_image = ImageIO.read(simple_submarine_image_file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (SimpleSubMarine simple_submarine : GameManager.getInstance().getGame().getSimple_submarines()) {
			int boat_x = (int) simple_submarine.getSurrounding_rectangle_absolute_on_complete_board().getX();
			g.drawImage(simple_submarine_buffered_image, boat_x, 0, null);
		}
	}

	@Override
	public void on_ally_boat_moved() {
		this.repaint();
	}

	@Override
	public void on_simple_submarine_moved() {
		this.repaint();
	}

}
