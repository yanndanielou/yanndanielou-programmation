package builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class GameDifficultyModelBuilder {
	private Gson gson = new Gson();

	private GameDifficultiesDataModel game_difficulties_data_model;

	public GameDifficultiesDataModel getGameDifficultiesDataModel() {
		return game_difficulties_data_model;
	}

	public GameDifficultyModelBuilder(String game_board_data_model_json_file) {
		BufferedReader br = null;

		try {
			
			br = new BufferedReader(new FileReader(game_board_data_model_json_file));

		} catch (IOException e) {
			e.printStackTrace();
		}
		game_difficulties_data_model = gson.fromJson(br, GameDifficultiesDataModel.class);
	}

}
