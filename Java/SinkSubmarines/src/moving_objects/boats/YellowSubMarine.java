package moving_objects.boats;

import builders.gameboard.GameBoardDataModel;
import builders.genericobjects.GenericObjectDataModel;
import builders.scenariolevel.ScenarioLevelEnnemyCreationDataModel;
import core.GameManager;
import game.Game;
import moving_objects.listeners.GameObjectListerner;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.Weapon;

public class YellowSubMarine extends SubMarine {
	// private static final Logger LOGGER =
	// LogManager.getLogger(YellowSubMarine.class);

	public YellowSubMarine(ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel,
			GenericObjectDataModel simple_submarine_data_model, GameBoardDataModel gameBoardDataModel, Game game) {

		super(scenarioLevelEnnemyCreationDataModel, simple_submarine_data_model, gameBoardDataModel, game);

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
	protected void water_surface_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	public void impact_now() {
		super.impact_now();
		this.current_destruction_timer_in_milliseconds = 5_000;
	}

	@Override
	public void fire() {
		FloatingSubmarineBomb bomb_fired = GameManager.getInstance().fire_floating_submarine_bomb(this,
				(int) (surrounding_rectangle_absolute_on_complete_board.getX()
						+ surrounding_rectangle_absolute_on_complete_board.getMaxX()) / 2,
				(int) (surrounding_rectangle_absolute_on_complete_board.getY() - 1), ammunition_y_speed);
		bomb_fired.add_movement_listener(this);
	}

	@Override
	public void on_simple_ally_bomb_end_of_destruction_and_clean(SimpleAllyBomb simpleAllyBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_weapon_destruction(Weapon weapon) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_yellow_submarine_end_of_destroy_and_clean(SubMarine subMarine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_simple_ally_bomb_begin_of_destruction(SimpleAllyBomb simpleAllyBomb) {
		// TODO Auto-generated method stub
		
	}

}
