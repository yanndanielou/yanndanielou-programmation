package builders.game;

import java.util.ArrayList;

public class GameDataModel {

	private ArrayList<GameLevelScenariosDataModel> levels_scenarios_data_models_json_files;
	int number_of_lives;
	String game_board_data_model_json_file;
	String generic_objects_data_model_json_file;

	public GameDataModel() {
	}

	public ArrayList<GameLevelScenariosDataModel> getLevels_scenarios_data_models_json_files() {
		return levels_scenarios_data_models_json_files;
	}

	public int getNumber_of_lives() {
		return number_of_lives;
	}

	public String getGame_board_data_model_json_file() {
		return game_board_data_model_json_file;
	}

	public String getGeneric_objects_data_model_json_file() {
		return generic_objects_data_model_json_file;
	}

}
