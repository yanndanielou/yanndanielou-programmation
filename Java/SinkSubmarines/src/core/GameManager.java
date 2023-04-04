package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.game.GameDataModel;
import builders.game.GameDataModelBuilder;
import builders.gameboard.GameBoardDataModel;
import builders.gameboard.GameBoardDataModelBuilder;
import builders.genericobjects.GenericObjectsDataModel;
import builders.genericobjects.GenericObjectsDataModelBuilder;
import builders.scenariolevel.ScenarioLevelEnnemyCreationDataModel;
import game.DifficultyLevel;
import game.Game;
import hmi.SinkSubmarinesMainView;
import moving_objects.boats.AllyBoat;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.YellowSubMarine;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;
import time.TimeManager;
import time.TimeManagerListener;

public class GameManager implements TimeManagerListener {

	private static GameManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);

	private SinkSubmarinesMainView sinkSubmarinesMainView = null;
	private Game game = null;

	private GenericObjectsDataModel genericObjectsDataModel = null;
	private GameBoardDataModel gameBoardDataModel = null;

	private GameManager() {
		TimeManager.getInstance().add_listener(this);
	}

	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}

	public static boolean hasInstance() {
		return instance != null;
	}

	public static boolean hasGameInProgress() {
		return hasInstance() && getInstance().getGame() != null;
	}

	public void new_game(DifficultyLevel difficulty_level_chosen) {
		LOGGER.info("New game with difficulty:" + difficulty_level_chosen);

		String game_board_data_model_json_file = "GameDataModel_" + difficulty_level_chosen + ".json";

		GameDataModelBuilder gameDataModelBuilder = new GameDataModelBuilder("data/" + game_board_data_model_json_file);
		GameDataModel game_data_model = gameDataModelBuilder.getGame_data_model();

		GameBoardDataModelBuilder gameBoardDataModelBuilder = new GameBoardDataModelBuilder(
				game_data_model.getGame_board_data_model_json_file());
		gameBoardDataModel = gameBoardDataModelBuilder.getGame_board_data_model();
		GenericObjectsDataModelBuilder genericObjectsDataModelBuilder = new GenericObjectsDataModelBuilder(
				game_data_model.getGeneric_objects_data_model_json_file());
		genericObjectsDataModel = genericObjectsDataModelBuilder.getGeneric_objects_data_model();
		sinkSubmarinesMainView
				.initialize_from_game_board_data_model(gameBoardDataModelBuilder.getGame_board_data_model());

		set_game(new Game(gameBoardDataModelBuilder.getGame_board_data_model(),
				genericObjectsDataModelBuilder.getGeneric_objects_data_model(), game_data_model.getNumber_of_lives()));

		game.add_game_listener(sinkSubmarinesMainView.getTopPanel());

		sinkSubmarinesMainView.getAllyBoatPanel().setAlly_boat(game.getAlly_boat());
		GameObjectsMovementOrchestor.getInstance();
		ScenarioLevelExecutor.getInstance()
				.load_and_start_scenario_from_json_file(game_data_model.getLevels_scenarios_data_models_json_files());
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
				genericObjectsDataModel.getSimple_submarine_data_model(), gameBoardDataModel, game);

		game.addSimpleSubMarine(submarine);
		submarine.add_movement_listener(sinkSubmarinesMainView.getUnderWaterPanel());

		LOGGER.info("Simple submarine created!" + scenarioLevelEnnemyCreationDataModel
				+ genericObjectsDataModel.getSimple_submarine_data_model());

		return submarine;
	}

	public YellowSubMarine create_yellow_submarine(
			ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel) {
		YellowSubMarine submarine = new YellowSubMarine(scenarioLevelEnnemyCreationDataModel,
				genericObjectsDataModel.getYellow_submarine_data_model(), gameBoardDataModel, game);

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

	private boolean is_ally_bomb_drop_autorized() {
		boolean ally_bomb_drop_is_autorized = false;

		boolean minimum_delay_between_two_ally_bombs_dropped_fulfilled = game.getAlly_boat()
				.is_minimal_time_since_last_fire_fulfilled();

		boolean is_under_maximum_number_of_ally_bombs_fulfilled = game.getAlly_boat()
				.getMax_number_of_living_bombs() > game.getSimple_ally_bombs().size();

		if (!is_under_maximum_number_of_ally_bombs_fulfilled) {
			LOGGER.warn("Cannot drop bomb because limit of bombs " + game.getSimple_ally_bombs().size()
					+ " already dropped");
		}

		ally_bomb_drop_is_autorized = minimum_delay_between_two_ally_bombs_dropped_fulfilled
				&& is_under_maximum_number_of_ally_bombs_fulfilled;

		if (ally_bomb_drop_is_autorized) {
			game.getAlly_boat().on_fire();
		}

		return ally_bomb_drop_is_autorized;
	}

	public void dropSimpleAllyBoatAtLeftOfAllyBoat(int drop_x, int drop_y) {
		if (is_ally_bomb_drop_autorized()) {
			SimpleAllyBomb simpleAllyBomb = new SimpleAllyBomb(genericObjectsDataModel.getAlly_simple_bomb_data_model(),
					drop_x, drop_y, game);
			game.addSimpleAllyBomb(simpleAllyBomb);
			LOGGER.info("Drop simple ally bomb at " + drop_x + " and " + drop_y);
			simpleAllyBomb.add_movement_listener(sinkSubmarinesMainView.getAllyBoatPanel());
			simpleAllyBomb.add_movement_listener(sinkSubmarinesMainView.getUnderWaterPanel());
			simpleAllyBomb.add_movement_listener(sinkSubmarinesMainView.getOceanBedPanel());

		}
	}

	public void dropSimpleAllyBoatAtLeftOfAllyBoat() {
		AllyBoat ally_boat = game.getAlly_boat();
		double dropped_bomb_x = Math.max(ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getX() - 10,
				0);
		dropSimpleAllyBoatAtLeftOfAllyBoat((int) dropped_bomb_x,
				(int) ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getY() - 15);
	}

	public void dropSimpleAllyBoatAtRightOfAllyBoat() {
		AllyBoat ally_boat = game.getAlly_boat();
		double dropped_bomb_x = Math.min(ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getMaxX() + 10,
				game.getGameboard().getWidth());
		dropSimpleAllyBoatAtLeftOfAllyBoat((int) dropped_bomb_x,
				(int) ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getY() - 15);
	}

	public SimpleSubmarineBomb fire_simple_submarine_bomb(int x, int y, int ammunition_y_speed) {
		SimpleSubmarineBomb sumbmarineBomb = new SimpleSubmarineBomb(
				genericObjectsDataModel.getSimple_submarine_bomb_data_model(), x, y, ammunition_y_speed, game);
		game.addSimpleSubmarineBomb(sumbmarineBomb);
		LOGGER.info("Fire simple submarine bomb at " + x + " and " + y);
		sumbmarineBomb.add_movement_listener(sinkSubmarinesMainView.getAllyBoatPanel());
		sumbmarineBomb.add_movement_listener(sinkSubmarinesMainView.getUnderWaterPanel());
		return sumbmarineBomb;
	}

	public FloatingSubmarineBomb fire_floating_submarine_bomb(int x, int y, int ammunition_y_speed) {
		FloatingSubmarineBomb sumbmarineBomb = new FloatingSubmarineBomb(
				genericObjectsDataModel.getFloating_submarine_bomb_data_model(), x, y, ammunition_y_speed, game);
		game.addFloatingSubmarineBomb(sumbmarineBomb);
		LOGGER.info("Fire floating submarine bomb at " + x + " and " + y);
		sumbmarineBomb.add_movement_listener(sinkSubmarinesMainView.getAllyBoatPanel());
		sumbmarineBomb.add_movement_listener(sinkSubmarinesMainView.getUnderWaterPanel());
		return sumbmarineBomb;
	}

	public void abort_current_game() {
		set_game(null);
	}

	public void set_game(Game game) {

		if (game != null) {
			TimeManager.getInstance().start();
			this.game = game;
		} else {
			TimeManager.getInstance().stop();
			this.game = null;
		}

		ScenarioLevelExecutor.getInstance().setGame(game);
	}

	public void pause_or_resume_current_game() {
		LOGGER.info("Pause or resume game");

		if (game.isPaused()) {
			resume_current_game();
		} else {
			pause_current_game();
		}
	}

	private void resume_current_game() {
		LOGGER.info("Resume current game");
		game.setPaused(false);
		TimeManager.getInstance().start();
	}

	private void pause_current_game() {
		LOGGER.info("Pause current game");
		game.setPaused(true);
		TimeManager.getInstance().stop();

	}

	@Override
	public void on_pause() {
	}
}
