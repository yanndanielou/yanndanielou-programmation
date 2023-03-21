package builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class GenericObjectsDataModelBuilder {
	private Gson gson = new Gson();

	private GameBoardDataModel game_board_data_model;

	public GameBoardDataModel getGame_board_data_model() {
		return game_board_data_model;
	}

	public GenericObjectsDataModelBuilder(String game_board_data_model_json_file) {
		BufferedReader br = null;

		try {
			
			br = new BufferedReader(new FileReader(game_board_data_model_json_file));

		} catch (IOException e) {
			e.printStackTrace();
		}
		game_board_data_model = gson.fromJson(br, GameBoardDataModel.class);
	}

}
