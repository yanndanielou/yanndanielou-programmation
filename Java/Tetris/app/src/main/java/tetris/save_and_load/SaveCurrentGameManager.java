package tetris.save_and_load;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import tetris.game.Game;

public class SaveCurrentGameManager {
	private static final Logger LOGGER = LogManager.getLogger(SaveCurrentGameManager.class);

	public void saveCurrentGame(Game game) {
		String outputJsonfilePath = "game saved.json";
		LOGGER.info(()-> " Save game to: " + outputJsonfilePath);
		try {
			new Gson().toJson(game, new FileWriter(outputJsonfilePath));
		} catch (JsonIOException | IOException e) {
			LOGGER.info(()-> "Could not save game. Exception: " + e.getClass() + e.getMessage() + e.getCause());
			e.printStackTrace();
		} catch (Exception e) {
			LOGGER.info(()-> "Could not save game. Exception: " + e.getClass() + e.getMessage() + e.getCause());
			e.printStackTrace();
		}
		LOGGER.info(()-> " Saved");
	}

}
