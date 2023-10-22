package builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;

public class SandboxModelBuilder {

	private Gson gson = new Gson();

	private SandboxDataModel gameBoardDataModel;

	public SandboxDataModel getGameBoardDataModel() {
		return gameBoardDataModel;
	}

	public SandboxModelBuilder() {
		String gameBoardDataModelJsonFile = "./SandboxDataModel.json";

		InputStream inputStream = getClass().getResourceAsStream(gameBoardDataModelJsonFile);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		/*
		 * try { reader = new BufferedReader(new
		 * FileReader(gameBoardDataModelJsonFile)); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
		gameBoardDataModel = gson.fromJson(bufferedReader, SandboxDataModel.class);

	}

}
