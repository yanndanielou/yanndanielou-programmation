package builders.scenariolevel;

import java.util.ArrayList;

public class ScenarioLevelDataModel {

	private ArrayList<ScenarioLevelWaveDataModel> waves;

	private int max_number_of_ally_bombs;
	private int scenario_level_number;
	private String scenario_level_name;

	public ScenarioLevelDataModel() {
	}

	public ArrayList<ScenarioLevelWaveDataModel> getWaves() {
		return waves;
	}

	public int getMax_number_of_ally_bombs() {
		return max_number_of_ally_bombs;
	}

	public boolean hasNextWaveAfter(ScenarioLevelWaveDataModel current_wave) {
		ScenarioLevelWaveDataModel next_wave = getNextWaveAfter(current_wave);
		return next_wave != null;
	}

	public ScenarioLevelWaveDataModel getNextWaveAfter(ScenarioLevelWaveDataModel current_wave) {
		ScenarioLevelWaveDataModel next_wave = null;

		int index_of_current_wave = waves.indexOf(current_wave);

		if (index_of_current_wave < waves.size() - 1) {
			next_wave = waves.get(index_of_current_wave + 1);
		}

		return next_wave;
	}

	public String getScenario_level_name() {
		return scenario_level_name;
	}

	public int getScenario_level_number() {
		return scenario_level_number;
	}

}
