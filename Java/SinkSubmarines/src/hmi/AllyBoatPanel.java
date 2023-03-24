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
import moving_objects.AllyBoat;
import moving_objects.GameObjectListerner;

public class AllyBoatPanel extends AbstractPanel implements GameObjectListerner {

	private static final long serialVersionUID = 6917913385357901059L;

	private Container parentContainer = null;

	private BufferedImage boat_buffered_image = null;
	private File boat_image_file = null;
	private final String boat_image_path = "Images/AllyBoat.png";
	private JLabel boat_image_as_label = null;

	private AllyBoat ally_boat = null;

	public AllyBoat getAlly_boat() {
		return ally_boat;
	}

	public void setAlly_boat(AllyBoat ally_boat) {
		this.ally_boat = ally_boat;
		ally_boat.add_movement_listener(this);
	}

	public AllyBoatPanel(Container parentContainer, int window_width, GameBoardDataModel gameBoardDataModel,
			JPanel pannel_above) {

		super(parentContainer, gameBoardDataModel, gameBoardDataModel.getAlly_boat_game_board_area_data_model(),
				pannel_above);

		boat_image_file = new File(boat_image_path);

		try {
			boat_buffered_image = ImageIO.read(boat_image_file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*
		 * boat_image_as_label = new JLabel("boat"); add(boat_image_as_label);
		 */

		/*
		 * ImageIcon image_icon = new ImageIcon(boat_buffered_image);
		 * 
		 * boat_image_as_label = new JLabel(image_icon); boat_image_as_label.setSize(5,
		 * 5); boat_image_as_label.setLocation(50, 50);
		 * 
		 * add(boat_image_as_label); boat_image_as_label.setLocation(20, 20);
		 */

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (ally_boat != null) {
			int boat_x = (int) ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getX();
			g.drawImage(boat_buffered_image, boat_x, 0, null);

		}
	}

	@Override
	public void on_ally_boat_moved() {
		this.repaint();
	}

	@Override
	public void on_simple_submarine_moved() {
	}
}
