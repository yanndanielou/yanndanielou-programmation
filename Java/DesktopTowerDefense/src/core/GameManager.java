package core;

import java.awt.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import belligerents.NormalAttacker;
import belligerents.Tower;
import builders.AttackerDataModel;
import builders.BombDataModel;
import builders.GameBoardDataModel;
import builders.GameBoardModelBuilder;
import builders.GameObjectsDataModel;
import builders.GameObjectsModelBuilder;
import builders.RectangleDataModel;
import builders.TowerDataModel;
import constants.Constants;
import game.Game;
import game_board.GameBoard;
import game_board.GameBoardAttackersEntryArea;
import game_board.GameBoardAttackersExitArea;
import game_board.GameBoardPoint;
import game_board.GameBoardWallRectangle;
import game_board.NeighbourGameBoardPointDirection;
import geometry.IntegerRectangle;
import hmi.DesktopTowerDefenseMainViewGeneric;
import time.TimeManager;

public class GameManager {

	private static GameManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);

	private Game game = null;
	private DesktopTowerDefenseMainViewGeneric desktopTowerDefenseMainView;
	private AttackerMovementOrchestor attackerMovementOrchestor;

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
		gameBoardModelBuilder.buildAllAreas(game, gameBoard);
		GameObjectsModelBuilder gameObjectsModelBuilder = new GameObjectsModelBuilder(
				Constants.GAME_OBJECTS_JSON_DATA_MODEL_FILE_PATH);
		GameObjectsDataModel game_objects_data_model = gameObjectsModelBuilder.getGame_objects_data_model();
		game = new Game(gameBoard, game_objects_data_model);
		desktopTowerDefenseMainView.register_to_game(game);
		attackerMovementOrchestor = new AttackerMovementOrchestor(game);
		compute_neighbours_of_each_gameBoardPoint();
		// createGameBoardAreas();
		TimeManager.getInstance().start();
	}

	private void compute_neighbours_of_each_gameBoardPoint() {

		LOGGER.info("compute_neighbours_of_each_gameBoardPoint: begin");

		GameBoard gameBoard = game.getGameBoard();

		for (GameBoardPoint gameBoardPoint : gameBoard.getAll_gameBoardPoints_as_ordered_list()) {

			for (NeighbourGameBoardPointDirection direction : NeighbourGameBoardPointDirection.values()) {
				GameBoardPoint neighbourSquare = gameBoard.getNeighbourGameBoardPoint(gameBoardPoint, direction);
				if (neighbourSquare != null) {
					gameBoardPoint.setNeighbour(direction, neighbourSquare);
				}
			}
		}
		LOGGER.info("compute_neighbours_of_each_gameBoardPoint: end");
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

	private Tower createTower(TowerDataModel towerDataModel, BombDataModel bombDataModel, int evolutionLevel, int x,
			int y) {
		Tower tower = new Tower(towerDataModel, bombDataModel, game, evolutionLevel, x, y);

		return tower;
	}

	public Tower createSimpleTower(int evolutionLevel, int x, int y) {
		GameObjectsDataModel game_objects_data_model = game.getGame_objects_data_model();
		Tower tower = createTower(game_objects_data_model.getSimple_tower_data_model(),
				game_objects_data_model.getSimple_tower_bomb_data_model(), evolutionLevel, x, y);
		return tower;
	}

	public Attacker createNormalAttacker(RectangleDataModel creation_area, RectangleDataModel oneRandomExitArea,
			int evolutionLevel) {
		GameObjectsDataModel game_objects_data_model = game.getGame_objects_data_model();
		AttackerDataModel normal_attacker_data_model = game_objects_data_model.getNormal_attacker_data_model();

		IntegerRectangle creationAreaRectangle = creation_area.getRectangle();

		Point attackerExitPoint = oneRandomExitArea.getOneRandomPointAllowingSubRectangleToFit(
				normal_attacker_data_model.getWidth(), normal_attacker_data_model.getHeight());

		NormalAttacker attacker = new NormalAttacker(normal_attacker_data_model, game,
				(int) creationAreaRectangle.getX(), (int) creationAreaRectangle.getY(), attackerExitPoint,
				evolutionLevel);
		return attacker;
	}

	public DesktopTowerDefenseMainViewGeneric getDesktopTowerDefenseMainView() {
		return desktopTowerDefenseMainView;
	}

}
