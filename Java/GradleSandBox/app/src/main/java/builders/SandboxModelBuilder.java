package builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;

public class SandboxModelBuilder {

	private Gson gson = new Gson();

	private SandboxDataModel gameBoardDataModel;

	public SandboxDataModel getGameBoardDataModel() {
		return gameBoardDataModel;
	}

	public SandboxModelBuilder() {
		BufferedReader bufferedReader = null;

		String gameBoardDataModelJsonFile = "./datamodels/SandboxDataModel.json";

		try {
			bufferedReader = new BufferedReader(new FileReader(gameBoardDataModelJsonFile));
		} catch (IOException e) {
			e.printStackTrace();

			URL resource = SandboxModelBuilder.class.getResource(gameBoardDataModelJsonFile);
			InputStream inputStream = SandboxModelBuilder.class.getResourceAsStream(gameBoardDataModelJsonFile);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
		}

		gameBoardDataModel = gson.fromJson(bufferedReader, SandboxDataModel.class);

	}

}
