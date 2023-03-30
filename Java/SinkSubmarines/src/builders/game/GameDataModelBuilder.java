package builders.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class GameDataModelBuilder {
	private Gson gson = new Gson();

	private GameDataModel game_data_model;

	public GameDataModel getGame_data_model() {
		return game_data_model;
	}

	public GameDataModelBuilder(String game_board_data_model_json_file) {
		BufferedReader br = null;

		try {
			
			br = new BufferedReader(new FileReader(game_board_data_model_json_file));

		} catch (IOException e) {
			e.printStackTrace();
		}
		game_data_model = gson.fromJson(br, GameDataModel.class);
	}

}
