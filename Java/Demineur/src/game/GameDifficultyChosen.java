package game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameDifficultyChosen {
	private static final Logger LOGGER = LogManager.getLogger(GameDifficultyChosen.class);

	private DifficultyType difficultyType;

	private int width;
	private int height;
	private int mines;

	public GameDifficultyChosen() {
		difficultyType = DifficultyType.Beginner;

		switch (difficultyType) {
		case Beginner:
			width = 8;
			height = 8;
			mines = 10;
			break;
		case Intermediate:
			width = 16;
			height = 16;
			mines = 40;
			break;
		case Expert:
			width = 16;
			height = 30;
			mines = 99;
			break;
		default:
			break;

		}
	}

}
