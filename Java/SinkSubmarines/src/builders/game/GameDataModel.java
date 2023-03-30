package builders.game;

import java.util.ArrayList;

public class GameDataModel {

	private ArrayList<GameDataModel> levels_scenarios_data_models;
	int number_of_lives;

	public GameDataModel() {
	}

	public ArrayList<GameDataModel> getLevels_scenarios_data_models() {
		return levels_scenarios_data_models;
	}

	public int getNumber_of_lives() {
		return number_of_lives;
	}

}
