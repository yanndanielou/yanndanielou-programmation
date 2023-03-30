package builders.scenariolevel;

import java.util.ArrayList;

public class ScenarioLevelDataModel {

	private ArrayList<ScenarioLevelEnnemyCreationDataModel> simple_submarines;
	private ArrayList<ScenarioLevelEnnemyCreationDataModel> yellow_submarines;
	private int max_number_of_ally_bombs;

	public ScenarioLevelDataModel() {
	}

	public ArrayList<ScenarioLevelEnnemyCreationDataModel> getSimple_submarines() {
		return simple_submarines;
	}

	public int getMax_number_of_ally_bombs() {
		return max_number_of_ally_bombs;
	}

	public ArrayList<ScenarioLevelEnnemyCreationDataModel> getYellow_submarines() {
		return yellow_submarines;
	}

}
