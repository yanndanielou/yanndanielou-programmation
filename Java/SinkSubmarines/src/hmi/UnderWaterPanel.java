package hmi;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import builders.gameboard.GameBoardDataModel;
import core.GameManager;
import moving_objects.boats.AllyBoat;
import moving_objects.boats.GameObjectGraphicalRepresentationManager;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.SubMarine;
import moving_objects.boats.YellowSubMarine;
import moving_objects.listeners.GameObjectListerner;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;
import moving_objects.weapon.Weapon;
import time.TimeManager;
import time.TimeManagerListener;

@Deprecated
public class UnderWaterPanel extends AbstractPanel implements GameObjectListerner, TimeManagerListener {
	// private static final Logger LOGGER =
	// LogManager.getLogger(UnderWaterPanel.class);

	private static final long serialVersionUID = 6917913385357901059L;

	// private JLabel simple_submarine_image_as_label = null;
	private JLabel pause_label = null;

	public UnderWaterPanel(Container parentContainer, int window_width, GameBoardDataModel gameBoardDataModel,
			JPanel pannel_above) {

		super(parentContainer, gameBoardDataModel, gameBoardDataModel.getUnder_water_game_board_area_data_model(),
				pannel_above);

		TimeManager.getInstance().add_listener(this);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (GameManager.hasGameInProgress() && GameManager.getInstance().getGame().isPaused()) {
			pause_label = new JLabel("Paused");
			Font pause_label_font = new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, 40);
			pause_label.setFont(pause_label_font);
			// pause_label.setBounds(getWidth() / 2, getHeight() / 2,
			// pause_label.getWidth(), pause_label.getHeight());
			// pause_label.setVerticalAlignment(SwingConstants.CENTER);
			// pause_label.setHorizontalAlignment(SwingConstants.CENTER);
			pause_label.setSize(new Dimension(150, 100));
			pause_label.setLocation(getWidth() / 2 - pause_label.getWidth() / 2,
					getHeight() / 2 - pause_label.getHeight() / 2);
			pause_label.setForeground(Color.WHITE);

			this.add(pause_label);

		} else {
			if (pause_label != null) {
				this.remove(pause_label);
				pause_label = null;
			}
		}

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

		for (SimpleAllyBomb simpleAllyBomb : new ArrayList<SimpleAllyBomb>(
				GameManager.getInstance().getGame().getSimple_ally_bombs())) {
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

		for (FloatingSubmarineBomb submarine_bomb : new ArrayList<FloatingSubmarineBomb>(
				GameManager.getInstance().getGame().getFloating_submarine_bombs())) {
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
	public void on_ally_boat_moved(AllyBoat allyBoat) {
	}

	@Override
	public void on_simple_submarine_moved(SimpleSubMarine simpleSubMarine) {
		this.repaint();
	}

	@Override
	public void on_simple_ally_bomb_moved(SimpleAllyBomb simpleAllyBomb) {
	//	this.repaint();

	}

	@Override
	public void on_submarine_end_of_destruction_and_clean(SubMarine subMarine) {
		this.repaint();
	}

	@Override
	public void on_weapon_destruction(Weapon weapon) {
		this.repaint();
	}

	@Override
	public void on_10ms_tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_50ms_tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_100ms_tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_second_tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_pause() {
		this.repaint();
	}

	@Override
	public void on_yellow_submarine_moved(YellowSubMarine yellowSubMarine) {
		repaint();
	}

	@Override
	public void on_simple_submarine_bomb_moved(SimpleSubmarineBomb simpleSubmarineBomb) {
		repaint();
	}

	@Override
	public void on_floating_bomb_moved(FloatingSubmarineBomb floatingSubmarineBomb) {
		repaint();
	}

	@Override
	public void on_yellow_submarine_end_of_destruction_and_clean(YellowSubMarine yellowSubMarine) {
		repaint();
	}

	@Override
	public void on_listen_to_simple_ally_bomb(SimpleAllyBomb simpleAllyBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_ally_bomb_end_of_destruction_and_clean(SimpleAllyBomb simpleAllyBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_ally_bomb_beginning_of_destruction(SimpleAllyBomb simpleAllyBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_ally_boat_end_of_destruction_and_clean(AllyBoat allyBoat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_ally_boat_beginning_of_destruction(AllyBoat allyBoat) {
		// TODO Auto-generated method stub

	}

}
