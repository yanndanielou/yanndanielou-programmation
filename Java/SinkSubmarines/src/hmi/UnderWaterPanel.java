package hmi;

import java.awt.Container;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import builders.gameboard.GameBoardDataModel;
import core.GameManager;
import moving_objects.GameObjectListerner;
import moving_objects.boats.GameObjectGraphicalRepresentationManager;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.SubMarine;
import moving_objects.boats.YellowSubMarine;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;
import moving_objects.weapon.Weapon;

public class UnderWaterPanel extends AbstractPanel implements GameObjectListerner {
	// private static final Logger LOGGER =
	// LogManager.getLogger(UnderWaterPanel.class);

	private static final long serialVersionUID = 6917913385357901059L;

	// private JLabel simple_submarine_image_as_label = null;

	public UnderWaterPanel(Container parentContainer, int window_width, GameBoardDataModel gameBoardDataModel,
			JPanel pannel_above) {

		super(parentContainer, gameBoardDataModel, gameBoardDataModel.getUnder_water_game_board_area_data_model(),
				pannel_above);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (SimpleSubMarine simple_submarine : new ArrayList<SimpleSubMarine>(
				GameManager.getInstance().getGame().getSimple_submarines())) {
			int boat_x = (int) simple_submarine.getSurrounding_rectangle_absolute_on_complete_board().getX();
			int boat_y = (int) simple_submarine.getSurrounding_rectangle_absolute_on_complete_board().getY();

			g.drawImage(
					GameObjectGraphicalRepresentationManager.getInstance().getSimpleSubmarineImage(simple_submarine),
					boat_x, boat_y,
					(int) simple_submarine.getSurrounding_rectangle_absolute_on_complete_board().getWidth(),
					(int) simple_submarine.getSurrounding_rectangle_absolute_on_complete_board().getHeight(), null);
		}

		for (YellowSubMarine yellow_submarine : new ArrayList<YellowSubMarine>(
				GameManager.getInstance().getGame().getYellow_submarines())) {
			int boat_x = (int) yellow_submarine.getSurrounding_rectangle_absolute_on_complete_board().getX();
			int boat_y = (int) yellow_submarine.getSurrounding_rectangle_absolute_on_complete_board().getY();

			g.drawImage(
					GameObjectGraphicalRepresentationManager.getInstance().getYellowSubmarineImage(yellow_submarine),
					boat_x, boat_y,
					(int) yellow_submarine.getSurrounding_rectangle_absolute_on_complete_board().getWidth(),
					(int) yellow_submarine.getSurrounding_rectangle_absolute_on_complete_board().getHeight(), null);
		}

		for (SimpleAllyBomb simpleAllyBomb : new ArrayList<SimpleAllyBomb>(GameManager.getInstance().getGame().getSimple_ally_bombs())) {
			int bomb_x = (int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getX();
			int bomb_y = (int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getY();

			if (simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getY() >= 0) {

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

		for (SimpleSubmarineBomb submarine_bomb : new ArrayList<SimpleSubmarineBomb>(
				GameManager.getInstance().getGame().getSimple_submarine_bombs())) {
			int bomb_x = (int) submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board().getX();
			int bomb_y = (int) submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board().getY();

			if (submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board().getY() >= 0) {

//				LOGGER.info("Display simpleAllyBomb at x:" + bomb_x + " and y:" + bomb_y);
				g.drawImage(
						GameObjectGraphicalRepresentationManager.getInstance()
								.getSimpleSubmarineBombImage(submarine_bomb),
						bomb_x, bomb_y,
						(int) submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board().getWidth(),
						(int) submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board().getHeight(), null);
			} else {
				// LOGGER.info("Do not display simpleAllyBomb at x:" + bomb_x + " and y:" +
				// bomb_y);
			}
		}

		for (FloatingSubmarineBomb submarine_bomb : new ArrayList<FloatingSubmarineBomb>(GameManager.getInstance().getGame().getFloating_submarine_bombs())) {
			int bomb_x = (int) submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board().getX();
			int bomb_y = (int) submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board().getY();

			if (submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board().getY() >= 0) {

//				LOGGER.info("Display simpleAllyBomb at x:" + bomb_x + " and y:" + bomb_y);
				g.drawImage(
						GameObjectGraphicalRepresentationManager.getInstance()
								.getFloatingSubmarineBombImage(submarine_bomb),
						bomb_x, bomb_y,
						(int) submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board().getWidth(),
						(int) submarine_bomb.getSurrounding_rectangle_absolute_on_complete_board().getHeight(), null);
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
		this.repaint();
	}

	@Override
	public void on_simple_ally_bomb_moved() {
		this.repaint();

	}

	@Override
	public void on_submarine_destruction(SubMarine subMarine) {
		this.repaint();
	}

	@Override
	public void on_weapon_destruction(Weapon weapon) {
		this.repaint();
	}

}
