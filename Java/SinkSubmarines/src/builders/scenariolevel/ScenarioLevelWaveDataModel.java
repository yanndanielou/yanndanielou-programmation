package builders.scenariolevel;

import java.util.ArrayList;

public class ScenarioLevelWaveDataModel {

	private ArrayList<ScenarioLevelEnnemyCreationDataModel> simple_submarines;
	private ArrayList<ScenarioLevelEnnemyCreationDataModel> yellow_submarines;
	private int wave_number;
	private String wave_name;

	public ScenarioLevelWaveDataModel() {
	}

	public ArrayList<ScenarioLevelEnnemyCreationDataModel> getSimple_submarines() {
		return simple_submarines;
	}

	public ArrayList<ScenarioLevelEnnemyCreationDataModel> getYellow_submarines() {
		return yellow_submarines;
	}

	public int getWave_number() {
		return wave_number;
	}

	public String getWave_name() {
		return wave_name;
	}

}
