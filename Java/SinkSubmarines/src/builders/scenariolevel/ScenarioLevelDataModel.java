package builders.scenariolevel;

import java.util.ArrayList;

public class ScenarioLevelDataModel {

	private ArrayList<ScenarioLevelWaveDataModel> waves;

	private int max_number_of_ally_bombs;

	public ScenarioLevelDataModel() {
	}

	public ArrayList<ScenarioLevelWaveDataModel> getWaves() {
		return waves;
	}

	public int getMax_number_of_ally_bombs() {
		return max_number_of_ally_bombs;
	}

}
