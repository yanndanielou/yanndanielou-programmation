package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.GameBoardModelBuilder;
import constants.Constants;
import game.Game;
import game_board.GameField;
import hmi.DesktopTowerDefenseMainViewGeneric;

public class GameManager {

	private static GameManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);

	private Game game = null;
	private DesktopTowerDefenseMainViewGeneric DesktopTowerDefenseMainView;

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
		LOGGER.info("New game");

		GameBoardModelBuilder gameBoardModelBuilder = new GameBoardModelBuilder(Constants.GAME_BOARD_JSON_DATA_MODEL_FILE_PATH);
		GameField gameField = new GameField(gameBoardModelBuilder.getGameBoardDataModel());
		game = new Game(gameField);
		DesktopTowerDefenseMainView.register_to_game(game);

	}

	public Game getGame() {
		return game;
	}

	public void abort_current_game() {
		LOGGER.info("Abort current game");
		game.cancel();

	}

	public void setDesktopTowerDefenseMainView(DesktopTowerDefenseMainViewGeneric DesktopTowerDefenseMainView) {
		this.DesktopTowerDefenseMainView = DesktopTowerDefenseMainView;
	}

}
