package core;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.gameboard.GameBoardDataModel;
import builders.gameboard.GameBoardDataModelBuilder;
import builders.genericobjects.GenericObjectsDataModel;
import builders.genericobjects.GenericObjectsDataModelBuilder;
import builders.scenariolevel.ScenarioLevelEnnemyCreationDataModel;
import game.Game;
import hmi.SinkSubmarinesMainView;
import moving_objects.GameObject;
import moving_objects.SimpleSubMarine;
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
		
	
		LOGGER.info("Simple submarine created!" + scenarioLevelEnnemyCreationDataModel + genericObjectsDataModel.getSimple_submarine_data_model());
		
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
}
