package moving_objects.boats;

import java.awt.Rectangle;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.gameboard.GameBoardDataModel;
import builders.genericobjects.GenericObjectDataModel;
import builders.scenariolevel.ScenarioLevelEnnemyCreationDataModel;
import builders.scenariolevel.SubmarineFireStrategyType;
import game.Game;
import game_board.GameBoard;
import moving_objects.listeners.GameObjectListerner;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;
import moving_objects.weapon.Weapon;
import time.TimeManager;
import time.TimeManagerListener;

public abstract class SubMarine extends Belligerent implements TimeManagerListener, GameObjectListerner {
	private static final Logger LOGGER = LogManager.getLogger(SubMarine.class);

	protected SubmarineFireStrategyType fireStrategyType;

	protected int score_prize_money_on_destruction = 0;

	public SubMarine(ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel,
			GenericObjectDataModel simple_submarine_data_model, GameBoardDataModel gameBoardDataModel, Game game) {

		super(new Rectangle(scenarioLevelEnnemyCreationDataModel.getX(),
				scenarioLevelEnnemyCreationDataModel.getDepth(), simple_submarine_data_model.getWidth(),
				simple_submarine_data_model.getHeight()),
				scenarioLevelEnnemyCreationDataModel.getMaximum_fire_frequency_in_milliseconds(),
				scenarioLevelEnnemyCreationDataModel.getAmmunition_y_speed(), game);

		this.fireStrategyType = scenarioLevelEnnemyCreationDataModel.getFire_strategy_type();

		this.score_prize_money_on_destruction = scenarioLevelEnnemyCreationDataModel
				.getScore_prize_money_on_destruction();

		setX_speed(scenarioLevelEnnemyCreationDataModel.getSpeed());
		setMax_number_of_living_bombs(
				scenarioLevelEnnemyCreationDataModel.getMaximum_number_of_alive_ammunitions_allowed());

		TimeManager.getInstance().add_listener(this);

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

	private boolean check_if_in_geographical_position_is_inside_board() {
		boolean geographical_position_is_inside_board = false;

		GameBoard gameboard = getGame().getGameboard();
		geographical_position_is_inside_board = surrounding_rectangle_absolute_on_complete_board.getX() >= 0
				&& surrounding_rectangle_absolute_on_complete_board.getMaxX() <= gameboard.getWidth();

		return geographical_position_is_inside_board;
	}

	private boolean check_if_in_geographical_position_of_fire_according_to_strategy() {
		boolean is_in_geographical_position_of_fire_according_to_strategy = false;

		AllyBoat ally_boat = getGame().getAlly_boat();
		double ally_boat_left_end_x = ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getX();
		double distance_between_left_ally_boat_and_right_of_submarine = surrounding_rectangle_absolute_on_complete_board
				.getMaxX() - ally_boat_left_end_x;
		double ally_boat_right_end_x = ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getMaxX();
		double distance_between_right_ally_boat_and_left_of_submarine = surrounding_rectangle_absolute_on_complete_board
				.getX() - ally_boat_right_end_x;
		boolean right_of_submarine_is_at_least_right_of_left_of_ally_boat = distance_between_left_ally_boat_and_right_of_submarine >= 0;
		boolean left_of_submarine_is_at_least_left_of_right_of_ally_boat = distance_between_right_ally_boat_and_left_of_submarine <= 0;
		switch (fireStrategyType) {
		case FIRE_AS_SOON_AS_POSSIBLE:
			is_in_geographical_position_of_fire_according_to_strategy = true;
			break;
		case FIRE_WHEN_ALLY_BOAT_IS_ABOVE:
			is_in_geographical_position_of_fire_according_to_strategy = right_of_submarine_is_at_least_right_of_left_of_ally_boat
					&& left_of_submarine_is_at_least_left_of_right_of_ally_boat;
			break;
		case FIRE_WHEN_ALLY_BOAT_IS_ABOVE_OR_NEARLY_ABOVE:
			is_in_geographical_position_of_fire_according_to_strategy = distance_between_left_ally_boat_and_right_of_submarine > -100
					&& distance_between_right_ally_boat_and_left_of_submarine > -100;
			break;
		case FIRE_WHEN_ALLY_BOAT_IS_ABOVE_LAUNCH_POSITION:
			double middle_of_submarine = (surrounding_rectangle_absolute_on_complete_board.getX()
					+ surrounding_rectangle_absolute_on_complete_board.getMaxX() / 2);
			is_in_geographical_position_of_fire_according_to_strategy = middle_of_submarine > ally_boat_left_end_x
					&& middle_of_submarine < ally_boat_right_end_x;
			break;
		case FIRE_WITH_RANDOM_INTERVAL:
			Random rand = new Random();
			is_in_geographical_position_of_fire_according_to_strategy = rand.nextBoolean();
			break;
		default:
			break;
		}
		return is_in_geographical_position_of_fire_according_to_strategy;
	}

	private boolean check_if_must_fire() {

		boolean must_fire = allowed_to_fire && !is_completely_destroyed() && !is_being_destroyed()
				&& is_minimal_time_since_last_fire_fulfilled() && !has_reached_maximum_number_of_living_bombs()
				&& check_if_in_geographical_position_is_inside_board()
				&& check_if_in_geographical_position_of_fire_according_to_strategy();

		return must_fire;
	}

	@Override
	public void on_10ms_tick() {
	}

	@Override
	public void on_20ms_tick() {
	}

	@Override
	public void on_50ms_tick() {

		if (check_if_must_fire()) {
			LOGGER.debug(this + " must fire");
			fire();
			on_fire();
		}

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
	protected void water_surface_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	public void impact_now() {
		game.addScore(score_prize_money_on_destruction);
	}

	@Override
	public void notify_destruction() {
		for (GameObjectListerner objectListerner : movement_listeners) {
			objectListerner.on_yellow_submarine_destruction(this);
		}
	}

	@Override
	public void on_submarine_destruction(SubMarine subMarine) {
	}

	@Override
	public void on_pause() {
	}

	public abstract void fire();

	@Override
	public void on_ally_boat_moved(AllyBoat allyBoat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_submarine_moved(SimpleSubMarine simpleSubMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_ally_bomb_moved(SimpleAllyBomb simpleAllyBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_yellow_submarine_moved(YellowSubMarine yellowSubMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_submarine_bomb_moved(SimpleSubmarineBomb simpleSubmarineBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_floating_bomb_moved(FloatingSubmarineBomb floatingSubmarineBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_yellow_submarine_destruction(SubMarine subMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_listen_to_simple_ally_bomb(SimpleAllyBomb simpleAllyBomb) {
		// TODO Auto-generated method stub

	}

}