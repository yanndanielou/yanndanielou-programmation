package core;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.scenariolevel.ScenarioLevelDataModel;
import builders.scenariolevel.ScenarioLevelDataModelBuilder;
import builders.scenariolevel.ScenarioLevelEnnemyCreationDataModel;
import builders.scenariolevel.ScenarioLevelWaveDataModel;
import game.Game;
import time.TimeManager;
import time.TimeManagerListener;

public class ScenarioLevelExecutor implements TimeManagerListener {

	private static ScenarioLevelExecutor instance;
	private static final Logger LOGGER = LogManager.getLogger(ScenarioLevelExecutor.class);

	private ScenarioLevelDataModel current_scenario_level_data_model = null;
	private ScenarioLevelWaveDataModel current_scenario_Level_wave_data_model = null;
	private Game game = null;
	private int current_step_in_seconds = 0;
	private ArrayList<ScenarioLevelEnnemyCreationDataModel> simple_submarines_remaining_to_create = new ArrayList<ScenarioLevelEnnemyCreationDataModel>();
	private ArrayList<ScenarioLevelEnnemyCreationDataModel> yellow_submarines_remaining_to_create = new ArrayList<ScenarioLevelEnnemyCreationDataModel>();
	private String[] scenarios_levels_json_files = { "LevelScenario_Easy_001.json", "LevelScenario_Easy_002.json",
			"LevelScenario_Easy_003.json" };

	private ScenarioLevelExecutor() {
		TimeManager.getInstance().add_listener(this);
	}

	public static ScenarioLevelExecutor getInstance() {
		if (instance == null) {
			instance = new ScenarioLevelExecutor();
		}
		return instance;
	}

	public void load_and_start_scenario_from_json_file(String scenario_data_model_json_file) {
		ScenarioLevelDataModelBuilder scenarioLevelDataModelBuilder = new ScenarioLevelDataModelBuilder(
				scenario_data_model_json_file);
		loadScenario(scenarioLevelDataModelBuilder.getScenario_level_data_model());
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

	private int create_simple_submarines_if_needed(int current_step_in_seconds) {
		ArrayList<ScenarioLevelEnnemyCreationDataModel> simple_submarines_remaining_to_create_to_remove = new ArrayList<ScenarioLevelEnnemyCreationDataModel>();

		for (ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel : simple_submarines_remaining_to_create) {
			if (scenarioLevelEnnemyCreationDataModel.getCreation_delay_in_seconds() == current_step_in_seconds) {
				GameManager.getInstance().create_simple_submarine(scenarioLevelEnnemyCreationDataModel);
				simple_submarines_remaining_to_create_to_remove.add(scenarioLevelEnnemyCreationDataModel);
			}
		}

		simple_submarines_remaining_to_create.removeAll(simple_submarines_remaining_to_create_to_remove);
		return simple_submarines_remaining_to_create_to_remove.size();
	}

	private int create_yellow_submarines_if_needed(int current_step_in_seconds) {
		ArrayList<ScenarioLevelEnnemyCreationDataModel> yellow_submarines_remaining_to_create_to_remove = new ArrayList<ScenarioLevelEnnemyCreationDataModel>();

		for (ScenarioLevelEnnemyCreationDataModel scenarioLevelEnnemyCreationDataModel : yellow_submarines_remaining_to_create) {
			if (scenarioLevelEnnemyCreationDataModel.getCreation_delay_in_seconds() == current_step_in_seconds) {
				GameManager.getInstance().create_yellow_submarine(scenarioLevelEnnemyCreationDataModel);
				yellow_submarines_remaining_to_create_to_remove.add(scenarioLevelEnnemyCreationDataModel);
			}
		}

		yellow_submarines_remaining_to_create.removeAll(yellow_submarines_remaining_to_create_to_remove);
		return yellow_submarines_remaining_to_create.size();
	}

	private int create_objects_if_needed(int current_step_in_seconds) {
		int object_created = 0;
		object_created += create_simple_submarines_if_needed(current_step_in_seconds);
		object_created += create_yellow_submarines_if_needed(current_step_in_seconds);
		return object_created;
	}

	@Override
	public void on_second_tick() {
		current_step_in_seconds++;
		create_objects_if_needed(current_step_in_seconds);
		if (simple_submarines_remaining_to_create.isEmpty() && yellow_submarines_remaining_to_create.isEmpty()
				&& game.get_all_submarines().isEmpty()) {
			LOGGER.info("End of current wave " + current_scenario_Level_wave_data_model + " of scenario:"
					+ current_scenario_level_data_model);

			if (current_scenario_level_data_model.hasNextWaveAfter(current_scenario_Level_wave_data_model)) {
				current_scenario_Level_wave_data_model = current_scenario_level_data_model
						.getNextWaveAfter(current_scenario_Level_wave_data_model);
				loadWave(current_scenario_Level_wave_data_model);
			} else {
				LOGGER.info("Load next scenario");

			}
		}
	}

	private void loadScenario(ScenarioLevelDataModel scenario_Level_data_model) {
		LOGGER.info("Load scenario:" + scenario_Level_data_model.getScenario_level_name() + " number:"
				+ scenario_Level_data_model.getScenario_level_number());
		current_scenario_level_data_model = scenario_Level_data_model;
		loadWave(scenario_Level_data_model.getWaves().get(0));
		GameManager.getInstance().getGame().getAlly_boat()
				.setMax_number_of_living_bombs(current_scenario_level_data_model.getMax_number_of_ally_bombs());
		current_step_in_seconds = 0;
	}

	private void loadWave(ScenarioLevelWaveDataModel scenario_Level_wave_data_model) {
		LOGGER.info("Load wave:" + scenario_Level_wave_data_model.getWave_name() + " number:"
				+ scenario_Level_wave_data_model.getWave_number());
		simple_submarines_remaining_to_create.addAll(scenario_Level_wave_data_model.getSimple_submarines());
		yellow_submarines_remaining_to_create.addAll(scenario_Level_wave_data_model.getYellow_submarines());
		this.current_scenario_Level_wave_data_model = scenario_Level_wave_data_model;
		current_step_in_seconds = 0;
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

	@Override
	public void on_pause() {
	}
}
