package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.Constants;
import game.Game;
import game_board.GameField;
import hmi.DemineurMainViewFrame;

public class GameManager {

	private static GameManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);

	private Game game = null;
	private GameField gameField = null;
	private DemineurMainViewFrame demineurMainViewFrame;

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

	public void new_game() {
		LOGGER.info("New game with difficulty:");
		gameField = new GameField(Constants.GAMEFIELD_WIDTH, Constants.GAMEFIELD_HEIGHT);
		game = new Game(gameField);
		game.add_game_status_listener(demineurMainViewFrame);
	}

	public Game getGame() {
		return game;
	}


	public void abort_current_game() {
		game.cancel();

	}

	public DemineurMainViewFrame getDemineurMainViewFrame() {
		return demineurMainViewFrame;
	}

	public void setDemineurMainViewFrame(DemineurMainViewFrame demineurMainViewFrame) {
		this.demineurMainViewFrame = demineurMainViewFrame;
	}

}
