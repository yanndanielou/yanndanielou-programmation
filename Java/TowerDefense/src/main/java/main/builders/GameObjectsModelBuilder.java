package main.builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class GameObjectsModelBuilder {
	private Gson gson = new Gson();

	private GameObjectsDataModel gameObjectsDataModel;

	public GameObjectsModelBuilder(String gameBoardDataModelJsonFile) {
		BufferedReader br = null;

		try {

			br = new BufferedReader(new FileReader(gameBoardDataModelJsonFile));

		} catch (IOException e) {
			e.printStackTrace();
		}
		gameObjectsDataModel = gson.fromJson(br, GameObjectsDataModel.class);
	}

	public GameObjectsDataModel getGameObjectsDataModel() {
		return gameObjectsDataModel;
	}

}
