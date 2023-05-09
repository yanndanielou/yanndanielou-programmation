package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.Constants;
import game.DifficultyLevel;
import game.Game;
import game_board.GameField;
import hmi.TetrisMainViewFrame;
import time.TimeManager;

public class GameManager {

	private static GameManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);

	private Game game = null;
	private GameField gameField = null;
	private TetrisMainViewFrame tetrisMainViewFrame;

	private GameManager() {

	}

	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}

	public static boolean hasInstance() {
		return instance != null;
	}

	public static boolean hasGameInProgress() {
		return hasInstance() && getInstance().getGame() != null;
	}

	public void new_game(DifficultyLevel difficulty_level_chosen) {
		LOGGER.info("New game with difficulty:" + difficulty_level_chosen);
		gameField = new GameField(Constants.GAMEFIELD_WIDTH, Constants.GAMEFIELD_HEIGHT);
		game = new Game(gameField);
		game.add_game_status_listener(tetrisMainViewFrame);
	}

	public Game getGame() {
		return game;
	}

	public void pause_or_resume_current_game() {
		LOGGER.info("Pause or resume game");

		if (game.isPaused()) {
			resume_current_game();
		} else {
			pause_current_game();
		}
	}

	private void resume_current_game() {
		LOGGER.info("Resume current game");
		game.setPaused(false);
		TimeManager.getInstance().start();
	}

	private void pause_current_game() {
		LOGGER.info("Pause current game");
		game.setPaused(true);
		TimeManager.getInstance().stop();

	}

	public void abort_current_game() {
		// TODO Auto-generated method stub

	}

	public TetrisMainViewFrame getTetrisMainViewFrame() {
		return tetrisMainViewFrame;
	}

	public void setTetrisMainViewFrame(TetrisMainViewFrame tetrisMainViewFrame) {
		this.tetrisMainViewFrame = tetrisMainViewFrame;
	}

}
