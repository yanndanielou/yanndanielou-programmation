package builders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.core.io.FileSystemResource;

import com.google.gson.Gson;

public class SandboxModelBuilder {

	private Gson gson = new Gson();

	private SandboxDataModel gameBoardDataModel;

	public SandboxDataModel getGameBoardDataModel() {
		return gameBoardDataModel;
	}

	public SandboxModelBuilder() {

		System.out.println(getClass().getName());

		BufferedReader bufferedReader = null;

		String gameBoardDataModelJsonFile = "SandboxDataModel.json";

		FileSystemResource fileSystemResource = new FileSystemResource(gameBoardDataModelJsonFile);
		System.out.println("fileSystemResource:" + fileSystemResource);
		try {
			URL url = fileSystemResource.getURL();
			System.out.println("URL:" + url);
			bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e) {
			e.printStackTrace();

		}

		gameBoardDataModel = gson.fromJson(bufferedReader, SandboxDataModel.class);

	}

}
