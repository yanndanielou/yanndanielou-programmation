package core;

import java.time.Instant;
import java.time.ZonedDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.gameboard.GameBoardDataModel;
import builders.gameboard.GameBoardDataModelBuilder;
import builders.genericobjects.GenericObjectsDataModel;
import builders.genericobjects.GenericObjectsDataModelBuilder;
import builders.scenariolevel.ScenarioLevelEnnemyCreationDataModel;
import constants.Constants;
import game.Game;
import hmi.SinkSubmarinesMainView;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.YellowSubMarine;
import moving_objects.weapon.SimpleAllyBomb;
import time.TimeManager;
import time.TimeManagerListener;

public class GameManager implements TimeManagerListener {

	private static GameManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);

	private GenericObjectsDataModelBuilder genericObjectsDataModelBuilder = null;
	private GameBoardDataModelBuilder gameBoardDataModelBuilder = null;
	private SinkSubmarinesMainView sinkSubmarinesMainView = null;
	private Game game = null;

	private GenericObjectsDataModel genericObjectsDataModel = null;
	private GameBoardDataModel gameBoardDataModel = null;

	private Instant lastAllyBombDroppedTime = null;
	
	int remainingLives;

	private GameManager() {
		TimeManager.getInstance().add_listener(this);
	}

	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}

	public void new_game(String game_board_data_model_json_file, String generic_objects_data_model_json_file) {
		gameBoardDataModelBuilder = new GameBoardDataModelBuilder(game_board_data_model_json_file);
		gameBoardDataModel = gameBoardDataModelBuilder.getGame_board_data_model();
		genericObjectsDataModelBuilder = new GenericObjectsDataModelBuilder(generic_objects_data_model_json_file);
		genericObjectsDataModel = genericObjectsDataModelBuilder.getGeneric_objects_data_model();
		TimeManager.getInstance().start();
		sinkSubmarinesMainView
				.initialize_from_game_board_data_model(gameBoardDataModelBuilder.getGame_board_data_model());
		game = new Game(gameBoardDataModelBuilder.getGame_board_data_model(),
				genericObjectsDataModelBuilder.getGeneric_objects_data_model());
		sinkSubmarinesMainView.getAllyBoatPanel().setAlly_boat(game.getAlly_boat());
		GameObjectsMovementOrchestor.getInstance();
		ScenarioLevelExecutor.getInstance().setGame(game);
		ScenarioLevelExecutor.getInstance().load_and_start_scenario("data/Level1Scenario.json");
	}

	@Override
	public void on_10ms_tick() {

	}

	@Override
	public void on_100ms_tick() {
	}

	@Override
	public void on_second_tick() {
	}

	public GenericObjectsDataModelBuilder getGenericObjectsDataModelBuilder() {
		return genericObjectsDataModelBuilder;
	}

	public GameBoardDataModelBuilder getGameBoardDataModelBuilder() {
		return gameBoardDataModelBuilder;
	}

	public SinkSubmarinesMainView getSinkSubmarinesMainView() {
		return sinkSubmarinesMainView;
	}

	public Game getGame() {
		return game;
	}

	public void setSinkSubmarinesMainView(SinkSubmarinesMainView sinkSubmarinesMainView) {
		this.sinkSubmarinesMainView = sinkSubmarinesMainView;
	}

	public SimpleSubMarine create_simple_submarine(
			ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel) {
		SimpleSubMarine submarine = new SimpleSubMarine(scenarioLevelEnnemyCreationDataModel,
				genericObjectsDataModel.getSimple_submarine_data_model(), gameBoardDataModel);

		game.addSimpleSubMarine(submarine);
		submarine.add_movement_listener(sinkSubmarinesMainView.getUnderWaterPanel());

		LOGGER.info("Simple submarine created!" + scenarioLevelEnnemyCreationDataModel
				+ genericObjectsDataModel.getSimple_submarine_data_model());

		return submarine;
	}

	public YellowSubMarine create_yellow_submarine(
			ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel) {
		YellowSubMarine submarine = new YellowSubMarine(scenarioLevelEnnemyCreationDataModel,
				genericObjectsDataModel.getYellow_submarine_data_model(), gameBoardDataModel);

		game.addYellowSubMarine(submarine);
		submarine.add_movement_listener(sinkSubmarinesMainView.getUnderWaterPanel());

		LOGGER.info("Yellow submarine created!" + scenarioLevelEnnemyCreationDataModel
				+ genericObjectsDataModel.getYellow_submarine_data_model());

		return submarine;
	}

	@Override
	public void on_50ms_tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_20ms_tick() {
		// TODO Auto-generated method stub

	}

	private boolean is_ally_bomb_drop_autorized() {
		boolean ally_bomb_drop_is_autorized = false;

		boolean minimum_delay_between_two_ally_bombs_dropped_fulfilled = false;
		boolean is_under_maximum_number_of_ally_bombs_fulfilled = false;

		if (lastAllyBombDroppedTime != null) {
			Instant right_now = ZonedDateTime.now().toInstant();
			long milliseconds_since_last_ally_bomb_dropped = right_now.toEpochMilli()
					- lastAllyBombDroppedTime.toEpochMilli();

			if (milliseconds_since_last_ally_bomb_dropped > GameManager.getInstance().getGame().getAlly_boat().getMaximum_fire_frequency_in_milliseconds()) {
				minimum_delay_between_two_ally_bombs_dropped_fulfilled = true;
			} else {
				LOGGER.warn("Cannot drop bomb because last one was " + milliseconds_since_last_ally_bomb_dropped
						+ " milliseconds ago");
			}
		} else {
			minimum_delay_between_two_ally_bombs_dropped_fulfilled = true;
		}

		is_under_maximum_number_of_ally_bombs_fulfilled = ScenarioLevelExecutor.getInstance()
				.getScenarioLevelDataModel().getMax_number_of_ally_bombs() > game.getSimple_ally_bombs().size();

		if (!is_under_maximum_number_of_ally_bombs_fulfilled) {
			LOGGER.warn("Cannot drop bomb because limit of bombs " + game.getSimple_ally_bombs().size()
					+ " already dropped");

		}

		ally_bomb_drop_is_autorized = minimum_delay_between_two_ally_bombs_dropped_fulfilled
				&& is_under_maximum_number_of_ally_bombs_fulfilled;

		if (ally_bomb_drop_is_autorized) {
			lastAllyBombDroppedTime = ZonedDateTime.now().toInstant();
		}

		return ally_bomb_drop_is_autorized;
	}

	public void dropSimpleAllyBoatAtLeftOfAllyBoat(int drop_x, int drop_y) {
		if (is_ally_bomb_drop_autorized()) {
			SimpleAllyBomb simpleAllyBomb = new SimpleAllyBomb(genericObjectsDataModel.getAlly_simple_bomb_data_model(),
					drop_x, drop_y);
			game.addSimpleAllyBomb(simpleAllyBomb);
			LOGGER.info("Drop simple ally bomb at " + drop_x + " and " + drop_y);
			simpleAllyBomb.add_movement_listener(sinkSubmarinesMainView.getAllyBoatPanel());
			simpleAllyBomb.add_movement_listener(sinkSubmarinesMainView.getUnderWaterPanel());
		}
	}

	public void dropSimpleAllyBoatAtLeftOfAllyBoat() {

		dropSimpleAllyBoatAtLeftOfAllyBoat(
				(int) game.getAlly_boat().getSurrounding_rectangle_absolute_on_complete_board().getX() - 5,
				(int) game.getAlly_boat().getSurrounding_rectangle_absolute_on_complete_board().getY() - 10);

	}

	public void dropSimpleAllyBoatAtRightOfAllyBoat() {

		dropSimpleAllyBoatAtLeftOfAllyBoat(
				(int) game.getAlly_boat().getSurrounding_rectangle_absolute_on_complete_board().getMaxX() + 5,
				(int) game.getAlly_boat().getSurrounding_rectangle_absolute_on_complete_board().getY() - 20);
	}
}
