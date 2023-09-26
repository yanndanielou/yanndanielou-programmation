package main.builders.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class GamesModelBuilder {
	private Gson gson = new Gson();

	private GamesDataModel gamesDataModel;

	public GamesModelBuilder(String gameBoardDataModelJsonFile) {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(gameBoardDataModelJsonFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		gamesDataModel = gson.fromJson(br, GamesDataModel.class);
	}

	public GamesDataModel getGamesDataModel() {
		return gamesDataModel;
	}
}
