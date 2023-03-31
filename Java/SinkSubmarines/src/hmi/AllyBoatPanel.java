package hmi;

import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JPanel;

import builders.gameboard.GameBoardDataModel;
import core.GameManager;
import moving_objects.GameObjectListerner;
import moving_objects.boats.AllyBoat;
import moving_objects.boats.GameObjectGraphicalRepresentationManager;
import moving_objects.boats.SubMarine;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.Weapon;

public class AllyBoatPanel extends AbstractPanel implements GameObjectListerner {
	//private static final Logger LOGGER = LogManager.getLogger(AllyBoatPanel.class);

	private static final long serialVersionUID = 6917913385357901059L;

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
			g.drawImage(GameObjectGraphicalRepresentationManager.getInstance().getAllyBoatImage(ally_boat), boat_x, 0,
					null);

		}
		for (SimpleAllyBomb simpleAllyBomb : GameManager.getInstance().getGame().getSimple_ally_bombs()) {
			int bomb_x = (int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getX();
			int bomb_y = (int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getY();
			int bomb_height = (int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getHeight();

			if (simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getY() < 0) {
				int this_pannel_height = this.getHeight();
				int displayed_bomb_y = this_pannel_height - bomb_height + bomb_y;
//				LOGGER.info("Display simpleAllyBomb at x:" + bomb_x + " and y:" + bomb_y + " displayed at:" + displayed_bomb_y);

				g.drawImage(
						GameObjectGraphicalRepresentationManager.getInstance().getSimpleAllyBombImage(simpleAllyBomb),
						bomb_x, displayed_bomb_y,
						(int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getWidth(),
						(int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getHeight(), null);
			} else {
//				LOGGER.info("Do not display simpleAllyBomb at x:" + bomb_x + " and y:" + bomb_y);
			}
		}
	}

	@Override
	public void on_ally_boat_moved() {
		this.repaint();
	}

	@Override
	public void on_simple_submarine_moved() {
	}

	@Override
	public void on_simple_ally_bomb_moved() {
		this.repaint();
	}

	@Override
	public void on_submarine_destruction(SubMarine subMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_weapon_destruction(Weapon weapon) {
		// TODO Auto-generated method stub

	}

}
