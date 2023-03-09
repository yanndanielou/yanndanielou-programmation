package builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class GameBoardDataModelBuilder {
	private Gson gson = new Gson();

	public GameBoardDataModelBuilder() {
		String jsonString;
		BufferedReader br = null;

		try {

			br = new BufferedReader(new FileReader("data/GameBoardDataModel.json"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		GameBoardDataModel game_board_data_model = gson.fromJson(br, GameBoardDataModel.class);
		int pause = 1;
	}

}
