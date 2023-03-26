package hmi;

import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JPanel;

import builders.gameboard.GameBoardDataModel;
import core.GameManager;
import moving_objects.GameObjectListerner;
import moving_objects.boats.GameObjectGraphicalRepresentationManager;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.weapon.SimpleAllyBomb;

public class UnderWaterPanel extends AbstractPanel implements GameObjectListerner {

	private static final long serialVersionUID = 6917913385357901059L;

	// private JLabel simple_submarine_image_as_label = null;

	public UnderWaterPanel(Container parentContainer, int window_width, GameBoardDataModel gameBoardDataModel,
			JPanel pannel_above) {

		super(parentContainer, gameBoardDataModel, gameBoardDataModel.getUnder_water_game_board_area_data_model(),
				pannel_above);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (SimpleSubMarine simple_submarine : GameManager.getInstance().getGame().getSimple_submarines()) {
			int boat_x = (int) simple_submarine.getSurrounding_rectangle_absolute_on_complete_board().getX();
			int boat_y = (int) simple_submarine.getSurrounding_rectangle_absolute_on_complete_board().getY();

			g.drawImage(
					GameObjectGraphicalRepresentationManager.getInstance().getSimpleSubmarineImage(simple_submarine),
					boat_x, boat_y,
					(int) simple_submarine.getSurrounding_rectangle_absolute_on_complete_board().getWidth(),
					(int) simple_submarine.getSurrounding_rectangle_absolute_on_complete_board().getHeight(), null);
		}

		for (SimpleAllyBomb simpleAllyBomb : GameManager.getInstance().getGame().getSimple_ally_bombs()) {
			int bomb_x = (int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getX();
			int bomb_y = (int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getY();

			if (simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getY() > 0) {

				g.drawImage(
						GameObjectGraphicalRepresentationManager.getInstance().getSimpleAllyBombImage(simpleAllyBomb),
						bomb_x, bomb_y,
						(int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getWidth(),
						(int) simpleAllyBomb.getSurrounding_rectangle_absolute_on_complete_board().getHeight(), null);
			}
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

	@Override
	public void on_simple_ally_bomb_moved() {
		this.repaint();

	}

}
