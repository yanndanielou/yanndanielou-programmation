package gameoflife.builders.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class GameModelBuilder {
	private Gson gson = new Gson();


	public GameModelBuilder(String gameBoardDataModelJsonFile) {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(gameBoardDataModelJsonFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
