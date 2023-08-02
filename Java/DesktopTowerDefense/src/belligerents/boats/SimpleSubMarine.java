package belligerents.boats;

import java.awt.image.BufferedImage;

import builders.genericobjects.GenericObjectDataModel;
import builders.scenariolevel.ScenarioLevelEnnemyCreationDataModel;
import core.GameManager;
import game.Game;
import belligerents.listeners.GameObjectListerner;
import belligerents.weapon.SimpleSubmarineBomb;

public class SimpleSubMarine extends SubMarine {
	// private static final Logger LOGGER =
	// LogManager.getLogger(SimpleSubMarine.class);

	public SimpleSubMarine(ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel,
			GenericObjectDataModel simple_submarine_data_model, Game game) {

		super(scenarioLevelEnnemyCreationDataModel, simple_submarine_data_model, game);
		/*
		 * super(new Rectangle(scenarioLevelEnnemyCreationDataModel.getX(),
		 * scenarioLevelEnnemyCreationDataModel.getDepth(),
		 * simple_submarine_data_model.getWidth(),
		 * simple_submarine_data_model.getHeight()),
		 * scenarioLevelEnnemyCreationDataModel.getMaximum_fire_frequency_in_seconds(),
		 * scenarioLevelEnnemyCreationDataModel.getFire_strategy_type(),
		 * scenarioLevelEnnemyCreationDataModel.getAmmunition_y_speed());
		 */
	}

	@Override
	public void notify_movement() {
		for (GameObjectListerner allyBoatListener : movement_listeners) {
			allyBoatListener.on_simple_submarine_moved(this);
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
		this.current_destruction_timer_in_milliseconds = 1_500;
		super.impact_now();
	}

	@Override
	public void fire() {
		@SuppressWarnings("unused")
		SimpleSubmarineBomb bomb_fired = GameManager.getInstance().fire_simple_submarine_bomb(this,
				(int) (surrounding_rectangle_absolute_on_complete_board.getX()
						+ surrounding_rectangle_absolute_on_complete_board.getMaxX()) / 2,
				(int) (surrounding_rectangle_absolute_on_complete_board.getY() - 1), ammunition_y_speed);
		// bomb_fired.add_movement_listener(this);

	}

	@Override
	public BufferedImage get_graphical_representation_as_buffered_image() {
		return getSimpleSubmarineImage(this);
	}

	@Override
	protected void rocks_reached() {
		// TODO Auto-generated method stub
		
	}

}
