package gameoflife.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gameoflife.builders.gameboard.GameBoardModelBuilder;
import gameoflife.constants.Constants;
import gameoflife.game.Game;
import gameoflife.gameboard.GameBoard;
import gameoflife.hmi.GameOfLifeMainViewGeneric;

public class GameManager {

	private static GameManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);

	private Game game = null;
	private GameOfLifeMainViewGeneric gameOfLifeMainView;

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

	public void newGame() {
		LOGGER.info("New game");

		GameBoardModelBuilder gameBoardModelBuilder = new GameBoardModelBuilder(
				Constants.GAME_BOARD_JSON_DATA_MODEL_FILE_PATH);
		LOGGER.info("Create gameboard");
		GameBoard gameBoard = new GameBoard(gameBoardModelBuilder);
		newGame(gameBoard);
	}
	
	public void newGame(GameBoard gameBoard) {
		LOGGER.info("Create gameboard");
		game = new Game(this, gameBoard);
		gameOfLifeMainView.registerToGame(game);
	}

	public Game getGame() {
		return game;
	}

	public void abortCurrentGame() {
		LOGGER.info("Abort current game");
		game.cancel();

	}

	public void setMainViewFrame(GameOfLifeMainViewGeneric desktopGameOfLifeMainView) {
		this.gameOfLifeMainView = desktopGameOfLifeMainView;
	}

	public GameOfLifeMainViewGeneric getDesktopGameOfLifeMainView() {
		return gameOfLifeMainView;
	}

}
