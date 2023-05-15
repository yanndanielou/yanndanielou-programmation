package core;

import java.util.ArrayList;

import builders.GameDifficultyDataModel;
import builders.GameDifficultyModelBuilder;
import constants.Constants;
import game.GameDifficulty;

public class UserChoicesManager {

	private static UserChoicesManager instance;

	private ArrayList<GameDifficultyDataModel> game_difficulty_data_models;
	private GameDifficulty custom_game_difficulty = null;

	private GameDifficulty gameDifficultyChosen = null;

	private UserChoicesManager() {

	}

	public static UserChoicesManager getInstance() {
		if (instance == null) {
			instance = new UserChoicesManager();
		}
		return instance;
	}

	public void initialise() {
		GameDifficultyModelBuilder gameDifficultyModelBuilder = new GameDifficultyModelBuilder(
				Constants.GAME_DIFFICULTY_JSON_DATA_MODEL_FILE_PATH);
		game_difficulty_data_models = gameDifficultyModelBuilder.getGameDifficultiesDataModel()
				.getGame_difficulty_data_models();

		gameDifficultyChosen = getGame_difficulty_data_models().get(0);

	}

	public ArrayList<GameDifficultyDataModel> getGame_difficulty_data_models() {
		return game_difficulty_data_models;
	}

	public GameDifficulty getGameDifficultyChosen() {
		return gameDifficultyChosen;
	}

	public GameDifficulty getCustom_game_difficulty() {
		return custom_game_difficulty;
	}

	public boolean hasCustom_game_difficulty() {
		return custom_game_difficulty != null;
	}

	public void selectGameDifficulty(GameDifficulty gameDifficulty) {
		this.gameDifficultyChosen = gameDifficulty;
	}

}
