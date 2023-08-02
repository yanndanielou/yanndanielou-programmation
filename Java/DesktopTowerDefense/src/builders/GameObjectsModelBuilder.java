package builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class GameObjectsModelBuilder {
	private Gson gson = new Gson();

	private GameObjectsDataModel game_objects_data_model;

	public GameObjectsModelBuilder(String game_board_data_model_json_file) {
		BufferedReader br = null;

		try {

			br = new BufferedReader(new FileReader(game_board_data_model_json_file));

		} catch (IOException e) {
			e.printStackTrace();
		}
		game_objects_data_model = gson.fromJson(br, GameObjectsDataModel.class);
	}

	public GameObjectsDataModel getGame_objects_data_model() {
		return game_objects_data_model;
	}

}
