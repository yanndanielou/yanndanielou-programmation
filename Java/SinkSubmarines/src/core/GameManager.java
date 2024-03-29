package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.game.GameDataModel;
import builders.game.GameDataModelBuilder;
import builders.genericobjects.GenericObjectsDataModel;
import builders.genericobjects.GenericObjectsDataModelBuilder;
import builders.scenariolevel.ScenarioLevelEnnemyCreationDataModel;
import constants.Constants;
import game.DifficultyLevel;
import game.Game;
import game_board.GameBoard;
import hmi.SinkSubmarinesMainViewFrame;
import moving_objects.boats.AllyBoat;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.SubMarine;
import moving_objects.boats.YellowSubMarine;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;
import time.TimeManager;
import time.TimeManagerListener;

public class GameManager implements TimeManagerListener {

	private static GameManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);

	private SinkSubmarinesMainViewFrame sinkSubmarinesMainView = null;
	private Game game = null;

	private GenericObjectsDataModel genericObjectsDataModel = null;

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

		GenericObjectsDataModelBuilder genericObjectsDataModelBuilder = new GenericObjectsDataModelBuilder(
				game_data_model.getGeneric_objects_data_model_json_file());
		genericObjectsDataModel = genericObjectsDataModelBuilder.getGeneric_objects_data_model();
		sinkSubmarinesMainView.initialize_from_game_board_data_model();

		GameBoard gameBoard = new GameBoard(sinkSubmarinesMainView.getGameBoardPanel().getWidth(),
				sinkSubmarinesMainView.getGameBoardPanel().getHeight());

		gameBoard.compute_game_areas_height(
				sinkSubmarinesMainView.getGameBoardPanel().getComplete_game_board_as_buffered_image());

		set_game(new Game(genericObjectsDataModelBuilder.getGeneric_objects_data_model(),
				game_data_model.getNumber_of_lives(), gameBoard));

		sinkSubmarinesMainView.register_to_game(game);

		sinkSubmarinesMainView.register_to_ally_boat(game.getAlly_boat());
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

	public Game getGame() {
		return game;
	}

	public void setSinkSubmarinesMainView(SinkSubmarinesMainViewFrame sinkSubmarinesMainView) {
		this.sinkSubmarinesMainView = sinkSubmarinesMainView;
	}

	public SimpleSubMarine create_simple_submarine(
			ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel) {
		SimpleSubMarine submarine = new SimpleSubMarine(scenarioLevelEnnemyCreationDataModel,
				genericObjectsDataModel.getSimple_submarine_data_model(), game);

		game.addSimpleSubMarine(submarine);
		sinkSubmarinesMainView.register_to_simple_submarine(submarine);

		LOGGER.info("Simple submarine created!" + scenarioLevelEnnemyCreationDataModel
				+ genericObjectsDataModel.getSimple_submarine_data_model());

		return submarine;
	}

	public YellowSubMarine create_yellow_submarine(
			ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel) {
		YellowSubMarine submarine = new YellowSubMarine(scenarioLevelEnnemyCreationDataModel,
				genericObjectsDataModel.getYellow_submarine_data_model(), game);

		game.addYellowSubMarine(submarine);
		sinkSubmarinesMainView.register_to_yellow_submarine(submarine);

		LOGGER.info("Yellow submarine created!" + scenarioLevelEnnemyCreationDataModel
				+ genericObjectsDataModel.getYellow_submarine_data_model());

		return submarine;
	}

	@Override
	public void on_50ms_tick() {
	}

	private boolean is_ally_bomb_drop_autorized() {

		AllyBoat ally_boat = game.getAlly_boat();

		boolean ally_bomb_drop_is_autorized = ally_boat.is_allowed_to_fire();

		if (ally_bomb_drop_is_autorized) {
			ally_boat.on_fire();
		}

		return ally_bomb_drop_is_autorized;
	}

	public void dropSimpleAllyBomb(int drop_x, int drop_y, int x_speed) {
		if (is_ally_bomb_drop_autorized()) {
			SimpleAllyBomb simpleAllyBomb = new SimpleAllyBomb(genericObjectsDataModel.getAlly_simple_bomb_data_model(),
					drop_x, drop_y, x_speed, game, game.getAlly_boat());
			game.addSimpleAllyBomb(simpleAllyBomb);
			LOGGER.info("Drop simple ally bomb at " + drop_x + " and " + drop_y);
			sinkSubmarinesMainView.register_to_simple_ally_bomb(simpleAllyBomb);
			AllyBoat ally_boat = game.getAlly_boat();
			simpleAllyBomb.add_movement_listener(ally_boat);

		}
	}

	public void dropSimpleAllyBombAtLeftOfAllyBoat() {
		AllyBoat ally_boat = game.getAlly_boat();
		double dropped_bomb_x = Math.max(ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getX() - 10,
				0);
		int x_speed = Constants.MAXIMUM_HORIZONTAL_SPEED_FOR_NEXT_ALLY_BOMB
				* game.getNext_ally_bomb_horizontal_speed_relative_percentage() / 100 * -1
				- 2 * Math.abs(ally_boat.getX_speed());
		dropSimpleAllyBomb((int) dropped_bomb_x,
				(int) ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getY() + 5, x_speed);
	}

	public void dropSimpleAllyBombAtRightOfAllyBoat() {
		AllyBoat ally_boat = game.getAlly_boat();
		double dropped_bomb_x = Math.min(ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getMaxX() + 10,
				game.getGameboard().getWidth());
		int x_speed = Constants.MAXIMUM_HORIZONTAL_SPEED_FOR_NEXT_ALLY_BOMB
				* game.getNext_ally_bomb_horizontal_speed_relative_percentage() / 100 * 1
				+ 2 * Math.abs(ally_boat.getX_speed());
		dropSimpleAllyBomb((int) dropped_bomb_x,
				(int) ally_boat.getSurrounding_rectangle_absolute_on_complete_board().getY() + 5, x_speed);
	}

	public SimpleSubmarineBomb fire_simple_submarine_bomb(SubMarine simpleSubMarine, int x, int y,
			int ammunition_y_speed) {
		SimpleSubmarineBomb sumbmarineBomb = new SimpleSubmarineBomb(
				genericObjectsDataModel.getSimple_submarine_bomb_data_model(), x, y, ammunition_y_speed, game,
				simpleSubMarine);
		game.addSimpleSubmarineBomb(sumbmarineBomb);
		LOGGER.debug("Fire simple submarine bomb at " + x + " and " + y);
		sinkSubmarinesMainView.register_to_simple_submarine_bomb(sumbmarineBomb);
		return sumbmarineBomb;
	}

	public FloatingSubmarineBomb fire_floating_submarine_bomb(SubMarine subMarine, int x, int y,
			int ammunition_y_speed) {
		FloatingSubmarineBomb sumbmarineBomb = new FloatingSubmarineBomb(
				genericObjectsDataModel.getFloating_submarine_bomb_data_model(), x, y, ammunition_y_speed, game,
				subMarine);
		game.addFloatingSubmarineBomb(sumbmarineBomb);
		LOGGER.info("Fire floating submarine bomb at " + x + " and " + y);
		sinkSubmarinesMainView.register_to_floating_submarine_bomb(sumbmarineBomb);

		return sumbmarineBomb;
	}

	public void abort_current_game() {
		if (game != null) {
			game.notify_game_cancelled();
			set_game(null);
		}
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
