package core;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.game.GameLevelScenariosDataModel;
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

	private Game game = null;
	private int current_step_in_seconds = 0;
	private ArrayList<ScenarioLevelEnnemyCreationDataModel> simple_submarines_remaining_to_create = new ArrayList<ScenarioLevelEnnemyCreationDataModel>();
	private ArrayList<ScenarioLevelEnnemyCreationDataModel> yellow_submarines_remaining_to_create = new ArrayList<ScenarioLevelEnnemyCreationDataModel>();
	private ArrayList<GameLevelScenariosDataModel> remaining_levels_scenarios_data_models_json_files;

	private ScenarioLevelExecutor() {
		TimeManager.getInstance().add_listener(this);
	}

	public static ScenarioLevelExecutor getInstance() {
		if (instance == null) {
			instance = new ScenarioLevelExecutor();
		}
		return instance;
	}

	public void load_and_start_scenario_from_json_file(
			ArrayList<GameLevelScenariosDataModel> levels_scenarios_data_models_json_files) {
		this.remaining_levels_scenarios_data_models_json_files = levels_scenarios_data_models_json_files;
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

			if (game.getCurrent_scenario_Level_wave_data_model() != null) {
				LOGGER.info("End of current wave " + game.getCurrent_scenario_Level_wave_data_model() + " of scenario:"
						+ game.getCurrent_scenario_level_data_model());
			}

			if (game.getCurrent_scenario_Level_wave_data_model() != null
					&& game.getCurrent_scenario_level_data_model() != null
					&& game.getCurrent_scenario_level_data_model()
							.hasNextWaveAfter(game.getCurrent_scenario_Level_wave_data_model())) {
				game.setCurrent_scenario_Level_wave_data_model(game.getCurrent_scenario_level_data_model()
						.getNextWaveAfter(game.getCurrent_scenario_Level_wave_data_model()));
				loadWave(game.getCurrent_scenario_Level_wave_data_model());
			} else if (game.getFloating_submarine_bombs().isEmpty() && game.getSimple_submarine_bombs().isEmpty()) {
				LOGGER.info("Load next scenario");
				load_next_scenario();
			} else {
				game.setCurrent_scenario_Level_wave_data_model(null);
			}
		}
	}

	private void load_next_scenario() {
		if (remaining_levels_scenarios_data_models_json_files != null
				&& !remaining_levels_scenarios_data_models_json_files.isEmpty()) {
			GameLevelScenariosDataModel gameLevelScenariosDataModel = remaining_levels_scenarios_data_models_json_files
					.get(0);
			remaining_levels_scenarios_data_models_json_files.remove(gameLevelScenariosDataModel);
			LOGGER.info(
					"Load level scenario file:" + gameLevelScenariosDataModel.getLevel_scenario_data_model_json_file());
			ScenarioLevelDataModelBuilder scenarioLevelDataModelBuilder = new ScenarioLevelDataModelBuilder(
					gameLevelScenariosDataModel.getLevel_scenario_data_model_json_file());
			loadScenario(scenarioLevelDataModelBuilder.getScenario_level_data_model());
		} else {
			game.game_over();
		}
	}

	private void loadScenario(ScenarioLevelDataModel scenario_Level_data_model) {
		LOGGER.info("Load scenario:" + scenario_Level_data_model.getScenario_level_name() + " number:"
				+ scenario_Level_data_model.getScenario_level_number());
		loadWave(scenario_Level_data_model.getWaves().get(0));
		game.setCurrent_scenario_level_data_model(scenario_Level_data_model);
		current_step_in_seconds = 0;
	}

	private void loadWave(ScenarioLevelWaveDataModel scenario_Level_wave_data_model) {
		LOGGER.info("Load wave:" + scenario_Level_wave_data_model.getWave_name() + " number:"
				+ scenario_Level_wave_data_model.getWave_number());
		simple_submarines_remaining_to_create.addAll(scenario_Level_wave_data_model.getSimple_submarines());
		yellow_submarines_remaining_to_create.addAll(scenario_Level_wave_data_model.getYellow_submarines());
		game.setCurrent_scenario_Level_wave_data_model(scenario_Level_wave_data_model);
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
