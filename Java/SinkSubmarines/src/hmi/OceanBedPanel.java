package hmi;

import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JPanel;

import builders.gameboard.GameBoardDataModel;
import core.GameManager;
import moving_objects.GameObjectListerner;
import moving_objects.boats.GameObjectGraphicalRepresentationManager;
import moving_objects.boats.SubMarine;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.Weapon;

public class OceanBedPanel extends AbstractPanel implements GameObjectListerner {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2898026190790239124L;

	public OceanBedPanel(Container parentContainer, int window_width, GameBoardDataModel gameBoardDataModel,
			JPanel pannel_above) {

		super(parentContainer, gameBoardDataModel, gameBoardDataModel.getOcean_bed_game_board_area_data_model(),
				pannel_above);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (SimpleAllyBomb simpleAllyBomb : GameManager.getInstance().getGame().getSimple_ally_bombs()) {
			int bomb_x = (int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getX();
			int bomb_y = (int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getY();

			int this_y = getY();
			if (simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getY() >= this_y) {

//				LOGGER.info("Display simpleAllyBomb at x:" + bomb_x + " and y:" + bomb_y);
				g.drawImage(
						GameObjectGraphicalRepresentationManager.getInstance().getSimpleAllyBombImage(simpleAllyBomb),
						bomb_x, bomb_y,
						(int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getWidth(),
						(int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getHeight(), null);
			} else {
				// LOGGER.info("Do not display simpleAllyBomb at x:" + bomb_x + " and y:" +
				// bomb_y);
			}
		}

	}

	@Override
	public void on_ally_boat_moved() {
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
	}

	@Override
	public void on_weapon_destruction(Weapon weapon) {
	}

}
