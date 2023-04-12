package moving_objects.boats;

import java.awt.image.BufferedImage;

import builders.genericobjects.GenericObjectDataModel;
import builders.scenariolevel.ScenarioLevelEnnemyCreationDataModel;
import core.GameManager;
import game.Game;
import moving_objects.listeners.GameObjectListerner;
import moving_objects.weapon.FloatingSubmarineBomb;

public class YellowSubMarine extends SubMarine {
	// private static final Logger LOGGER =
	// LogManager.getLogger(YellowSubMarine.class);

	public YellowSubMarine(ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel,
			GenericObjectDataModel simple_submarine_data_model, Game game) {

		super(scenarioLevelEnnemyCreationDataModel, simple_submarine_data_model, game);

	}

	@Override
	public void notify_movement() {
		for (GameObjectListerner allyBoatListener : movement_listeners) {
			allyBoatListener.on_yellow_submarine_moved(this);
		}
	}

	@Override
	protected void right_border_of_game_board_reached() {
		setX_speed(getX_speed() * -1);
	}

	@Override
	protected void left_border_of_game_board_reached() {
		setX_speed(getX_speed() * -1);
	}

	@Override
	protected void ocean_bed_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void top_of_object_reaches_surface() {
		// TODO Auto-generated method stub

	}

	@Override
	public void impact_now() {
		super.impact_now();
		this.current_destruction_timer_in_milliseconds = 5_000;
	}

	@Override
	public void fire() {
		@SuppressWarnings("unused")
		FloatingSubmarineBomb bomb_fired = GameManager.getInstance().fire_floating_submarine_bomb(this,
				(int) (surrounding_rectangle_absolute_on_complete_board.getX()
						+ surrounding_rectangle_absolute_on_complete_board.getMaxX()) / 2,
				(int) (surrounding_rectangle_absolute_on_complete_board.getY() - 1), ammunition_y_speed);
	}

	@Override
	public BufferedImage get_graphical_representation_as_buffered_image() {
		return getYellowSubmarineImage(this);
	}

	@Override
	protected void rocks_reached() {
		// TODO Auto-generated method stub
		
	}

}
