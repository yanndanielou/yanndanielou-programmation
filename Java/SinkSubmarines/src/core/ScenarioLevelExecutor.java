package core;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.gameboard.GameBoardDataModelBuilder;
import builders.genericobjects.GenericObjectsDataModelBuilder;
import builders.scenariolevel.ScenarioLevelDataModel;
import builders.scenariolevel.ScenarioLevelDataModelBuilder;
import builders.scenariolevel.ScenarioLevelEnnemyCreationDataModel;
import game.Game;
import hmi.SinkSubmarinesMainView;
import moving_objects.GameObject;
import time.TimeManager;
import time.TimeManagerListener;

public class ScenarioLevelExecutor implements TimeManagerListener {

	private static ScenarioLevelExecutor instance;
	private static final Logger LOGGER = LogManager.getLogger(ScenarioLevelExecutor.class);

	private ScenarioLevelDataModel scenarioLevelDataModel = null;
	private Game game = null;
	private int current_step_in_seconds = 0;
	private ArrayList<ScenarioLevelEnnemyCreationDataModel> simple_submarines_remaining_to_create = new ArrayList<ScenarioLevelEnnemyCreationDataModel>();

	private ScenarioLevelExecutor() {
		TimeManager.getInstance().add_listener(this);
	}

	public static ScenarioLevelExecutor getInstance() {
		if (instance == null) {
			instance = new ScenarioLevelExecutor();
		}
		return instance;
	}

	public void load_and_start_scenario(String scenario_data_model_json_file) {
		ScenarioLevelDataModelBuilder scenarioLevelDataModelBuilder = new ScenarioLevelDataModelBuilder(
				scenario_data_model_json_file);
		scenarioLevelDataModel = scenarioLevelDataModelBuilder.getScenario_level_data_model();
		simple_submarines_remaining_to_create.addAll(scenarioLevelDataModel.getSimple_submarines());
	}

	public Game getGame() {
		return game;
	}

	@Override
	public void on_10ms_tick() {

	}

	@Override
	public void on_100ms_tick() {
	}

	@Override
	public void on_second_tick() {
		current_step_in_seconds++;
		ArrayList<ScenarioLevelEnnemyCreationDataModel> scenarioLevelEnnemyCreationDataModel_to_remove = new ArrayList<ScenarioLevelEnnemyCreationDataModel>();

		for (ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel : simple_submarines_remaining_to_create) {
			if (scenarioLevelEnnemyCreationDataModel.getCreation_delay_in_seconds() == current_step_in_seconds) {
				GameManager.getInstance().create_simple_submarine(scenarioLevelEnnemyCreationDataModel);
				scenarioLevelEnnemyCreationDataModel_to_remove.add(scenarioLevelEnnemyCreationDataModel);
			}
		}

		simple_submarines_remaining_to_create.removeAll(scenarioLevelEnnemyCreationDataModel_to_remove);
	}

	@Override
	public void on_50ms_tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_20ms_tick() {
		// TODO Auto-generated method stub

	}

	public void setGame(Game game) {
		this.game = game;
	}
}
