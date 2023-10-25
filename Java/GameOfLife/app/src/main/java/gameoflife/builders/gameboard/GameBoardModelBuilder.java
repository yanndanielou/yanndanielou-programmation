package gameoflife.builders.gameboard;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

public class GameBoardModelBuilder {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardModelBuilder.class);

	private Gson gson = new Gson();

	private GameBoardDataModel gameBoardDataModel;

	public GameBoardDataModel getGameBoardDataModel() {
		return gameBoardDataModel;
	}

	public GameBoardModelBuilder(String gameBoardDataModelJsonFile) {
		InputStream resourceAsStream = getClass().getResourceAsStream(gameBoardDataModelJsonFile);
		LOGGER.info(() -> "GameBoardModelBuilder, resourceAsStream:" + resourceAsStream);

		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(resourceAsStream));
		} catch (Exception e) {
			e.printStackTrace();
		}
		gameBoardDataModel = gson.fromJson(br, GameBoardDataModel.class);

	}

}
