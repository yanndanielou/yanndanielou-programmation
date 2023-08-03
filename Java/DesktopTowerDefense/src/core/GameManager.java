package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Tower;
import builders.BombDataModel;
import builders.GameBoardModelBuilder;
import builders.GameObjectsDataModel;
import builders.GameObjectsModelBuilder;
import builders.TowerDataModel;
import constants.Constants;
import game.Game;
import game_board.GameBoard;
import hmi.DesktopTowerDefenseMainViewGeneric;

public class GameManager {

	private static GameManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);

	private Game game = null;
	private DesktopTowerDefenseMainViewGeneric desktopTowerDefenseMainView;

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

		GameBoardModelBuilder gameBoardModelBuilder = new GameBoardModelBuilder(
				Constants.GAME_BOARD_JSON_DATA_MODEL_FILE_PATH);
		GameBoard gameBoard = new GameBoard(gameBoardModelBuilder.getGameBoardDataModel());
		GameObjectsModelBuilder gameObjectsModelBuilder = new GameObjectsModelBuilder(
				Constants.GAME_OBJECTS_JSON_DATA_MODEL_FILE_PATH);
		GameObjectsDataModel game_objects_data_model = gameObjectsModelBuilder.getGame_objects_data_model();
		game = new Game(gameBoard, game_objects_data_model);
		desktopTowerDefenseMainView.register_to_game(game);

	}

	public Game getGame() {
		return game;
	}

	public void abort_current_game() {
		LOGGER.info("Abort current game");
		game.cancel();

	}

	public void setDesktopTowerDefenseMainView(DesktopTowerDefenseMainViewGeneric desktopTowerDefenseMainView) {
		this.desktopTowerDefenseMainView = desktopTowerDefenseMainView;
	}

	private Tower createTower(TowerDataModel towerDataModel, BombDataModel bombDataModel, int x, int y) {
		Tower tower = new Tower(towerDataModel, bombDataModel, game, x, y);

		return tower;
	}

	public Tower createSimpleTower(int x, int y) {
		GameObjectsDataModel game_objects_data_model = game.getGame_objects_data_model();
		Tower tower = createTower(game_objects_data_model.getSimple_tower_data_model(),
				game_objects_data_model.getSimple_tower_bomb_data_model(), x, y);
		return tower;
	}

	public DesktopTowerDefenseMainViewGeneric getDesktopTowerDefenseMainView() {
		return desktopTowerDefenseMainView;
	}

}
