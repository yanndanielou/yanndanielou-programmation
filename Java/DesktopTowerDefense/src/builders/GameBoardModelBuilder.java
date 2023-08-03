package builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class GameBoardModelBuilder {
	private Gson gson = new Gson();

	private GameBoardDataModel game_board_data_model;

	public GameBoardDataModel getGameBoardDataModel() {
		return game_board_data_model;
	}

	public GameBoardModelBuilder(String game_board_data_model_json_file) {
		BufferedReader br = null;

		try {
			
			br = new BufferedReader(new FileReader(game_board_data_model_json_file));

		} catch (IOException e) {
			e.printStackTrace();
		}
		game_board_data_model = gson.fromJson(br, GameBoardDataModel.class);
	}

}
