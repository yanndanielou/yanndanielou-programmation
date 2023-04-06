package moving_objects.boats;

import builders.gameboard.GameBoardDataModel;
import builders.genericobjects.GenericObjectDataModel;
import builders.scenariolevel.ScenarioLevelEnnemyCreationDataModel;
import core.GameManager;
import game.Game;
import moving_objects.GameObjectListerner;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;

public class SimpleSubMarine extends SubMarine {
	// private static final Logger LOGGER =
	// LogManager.getLogger(SimpleSubMarine.class);

	public SimpleSubMarine(ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel,
			GenericObjectDataModel simple_submarine_data_model, GameBoardDataModel gameBoardDataModel, Game game) {

		super(scenarioLevelEnnemyCreationDataModel, simple_submarine_data_model, gameBoardDataModel, game);
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
	protected void water_surface_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	public void impact_now() {
		this.current_destruction_timer_in_milliseconds = 5_000;
	}

	@Override
	public void fire() {
		SimpleSubmarineBomb bomb_fired = GameManager.getInstance().fire_simple_submarine_bomb(
				(int) (surrounding_rectangle_absolute_on_complete_board.getX()
						+ surrounding_rectangle_absolute_on_complete_board.getMaxX()) / 2,
				(int) (surrounding_rectangle_absolute_on_complete_board.getY() - 1), ammunition_y_speed);
		living_bombs.add(bomb_fired);
		bomb_fired.add_movement_listener(this);

	}


}
