package tetris.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.builders.JsonDataModelBuilder;
import tetris.builders.gameboard.GameBoardDataModel;
import tetris.builders.gameboard.GameBoardModelBuilder;
import tetris.constants.Constants;
import tetris.game.Game;
import tetris.gameboard.Matrix;
import tetris.hmi.TetrisMainViewGeneric;

public class GameManager {

	private static GameManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);

	private Game game = null;
	private TetrisMainViewGeneric tetrisMainView;

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

		GameBoardDataModel gameBoardDataModel = new JsonDataModelBuilder<GameBoardDataModel>().loadDataModelFromJsonFile(this.getClass(), GameBoardDataModel.class, Constants.GAME_BOARD_JSON_DATA_MODEL_FILE_PATH);
		LOGGER.info("Create gameboard");
		Matrix gameBoard = new Matrix(gameBoardDataModel);
		newGame(gameBoard);
	}
	
	public void newGame(Matrix gameBoard) {
		LOGGER.info("Create gameboard");
		game = new Game(gameBoard);
		tetrisMainView.registerToGame(game);
	}

	public Game getGame() {
		return game;
	}

	public void abortCurrentGame() {
		LOGGER.info("Abort current game");
		game.cancel();

	}

	public void setMainViewFrame(TetrisMainViewGeneric desktoptetrisMainView) {
		this.tetrisMainView = desktoptetrisMainView;
	}

	public TetrisMainViewGeneric getDesktoptetrisMainView() {
		return tetrisMainView;
	}

}
