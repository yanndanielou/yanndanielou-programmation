package core;

import java.awt.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import belligerents.NormalAttacker;
import belligerents.Tower;
import builders.AttackerDataModel;
import builders.BombDataModel;
import builders.GameObjectsDataModel;
import builders.GameObjectsModelBuilder;
import builders.TowerDataModel;
import builders.game_board.GameBoardModelBuilder;
import constants.Constants;
import game.Game;
import game_board.GameBoard;
import game_board.GameBoardAttackersEntryArea;
import game_board.GameBoardAttackersExitArea;
import game_board.GameBoardPoint;
import game_board.GameBoardPredefinedConstructionLocation;
import game_board.NeighbourGameBoardPointDirection;
import geometry.IntegerRectangle;
import hmi.TowerDefenseMainViewGeneric;

public class GameManager {

	private static GameManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);

	private Game game = null;
	private TowerDefenseMainViewGeneric desktopTowerDefenseMainView;
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

	public void newGame() {
		LOGGER.info("New game");

		GameBoardModelBuilder gameBoardModelBuilder = new GameBoardModelBuilder(
				Constants.GAME_BOARD_JSON_DATA_MODEL_FILE_PATH);
		GameBoard gameBoard = new GameBoard(gameBoardModelBuilder);
		gameBoardModelBuilder.buildAllAreas(gameBoard);
		GameObjectsModelBuilder gameObjectsModelBuilder = new GameObjectsModelBuilder(
				Constants.GAME_OBJECTS_JSON_DATA_MODEL_FILE_PATH);
		GameObjectsDataModel gameObjectsDataModel = gameObjectsModelBuilder.getGame_objects_data_model();
		game = new Game(this, gameBoard, gameObjectsDataModel);
		gameBoard.generatePredefinedConstructionLocations(gameObjectsDataModel.getSimpleTowerDataModel());
		desktopTowerDefenseMainView.registerToGame(game);
		attackerMovementOrchestor = new AttackerMovementOrchestor(game);
		desktopTowerDefenseMainView.registerToPlayer(game.getPlayer());
		computeNeighboursOfEachGameBoardPoint();
	}

	private void computeNeighboursOfEachGameBoardPoint() {

		LOGGER.info("compute_neighbours_of_each_gameBoardPoint: begin");

		GameBoard gameBoard = game.getGameBoard();

		for (GameBoardPoint gameBoardPoint : gameBoard.getAllGameBoardPointsAsOrderedList()) {

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

	public void abortCurrentGame() {
		LOGGER.info("Abort current game");
		game.cancel();

	}

	public void setDesktopTowerDefenseMainView(TowerDefenseMainViewGeneric desktopTowerDefenseMainView) {
		this.desktopTowerDefenseMainView = desktopTowerDefenseMainView;
	}

	public Tower createTower(TowerDataModel towerDataModel,
			GameBoardPredefinedConstructionLocation predefinedConstructionLocation) {
		IntegerRectangle rectangleDefinedArea = predefinedConstructionLocation.getRectangleDefinedArea();
		Tower tower = new Tower(towerDataModel, null, game, 0, rectangleDefinedArea.getX(),
				rectangleDefinedArea.getY());
		return tower;
	}

	private Tower createTower(TowerDataModel towerDataModel, BombDataModel bombDataModel, int evolutionLevel, int x,
			int y) {
		Tower tower = new Tower(towerDataModel, bombDataModel, game, evolutionLevel, x, y);

		return tower;
	}

	public Tower createSimpleTower(int evolutionLevel, int x, int y) {
		GameObjectsDataModel gameObjectsDataModel = game.getGameObjectsDataModel();
		Tower tower = createTower(gameObjectsDataModel.getSimpleTowerDataModel(),
				gameObjectsDataModel.getSimpleTowerBombDataModel(), evolutionLevel, x, y);
		return tower;
	}

	public Attacker createNormalAttacker(GameBoardAttackersEntryArea attackersEntryArea,
			GameBoardAttackersExitArea exitArea, int evolutionLevel) {
		GameObjectsDataModel gameObjectsDataModel = game.getGameObjectsDataModel();
		AttackerDataModel normalAttackerDataModel = gameObjectsDataModel.getNormal_attacker_data_model();

		IntegerRectangle creationAreaRectangle = attackersEntryArea.getRectangleDefinedArea();

		Point attackerExitPoint = exitArea.getRectangleDefinedArea().getOneRandomPointAllowingSubRectangleToFit(
				normalAttackerDataModel.getWidth(), normalAttackerDataModel.getHeight());

		NormalAttacker attacker = new NormalAttacker(normalAttackerDataModel, game, creationAreaRectangle.getX(),
				creationAreaRectangle.getY(), attackerExitPoint, evolutionLevel);
		return attacker;
	}

	public TowerDefenseMainViewGeneric getDesktopTowerDefenseMainView() {
		return desktopTowerDefenseMainView;
	}

}
