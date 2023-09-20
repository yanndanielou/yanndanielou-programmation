package main.core;

import java.awt.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.gameboard.NeighbourGameBoardPointDirection;
import geometry2d.integergeometry.IntegerPrecisionRectangle;
import main.belligerents.Attacker;
import main.belligerents.FlyingAttacker;
import main.belligerents.NormalAttacker;
import main.belligerents.Tower;
import main.builders.GameObjectsDataModel;
import main.builders.GameObjectsModelBuilder;
import main.builders.belligerents.AttackerDataModel;
import main.builders.belligerents.TowerDataModel;
import main.builders.gameboard.GameBoardModelBuilder;
import main.builders.weapons.BombDataModel;
import main.constants.Constants;
import main.game.Game;
import main.gameboard.GameBoard;
import main.gameboard.GameBoardAttackersEntryArea;
import main.gameboard.GameBoardAttackersExitArea;
import main.gameboard.GameBoardPoint;
import main.gameboard.GameBoardPredefinedConstructionLocation;
import main.hmi.TowerDefenseMainViewGeneric;

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
		GameObjectsDataModel gameObjectsDataModel = gameObjectsModelBuilder.getGameObjectsDataModel();
		game = new Game(this, gameBoard, gameObjectsDataModel);
		gameBoard.generatePredefinedConstructionLocations(gameObjectsDataModel.getSimpleTowerDataModel());
		desktopTowerDefenseMainView.registerToGame(game);
		attackerMovementOrchestor = new AttackerMovementOrchestor(game);
		desktopTowerDefenseMainView.registerToPlayer(game.getPlayer());
		computeNeighboursOfEachGameBoardPoint();
	}

	private void computeNeighboursOfEachGameBoardPoint() {

		LOGGER.info("computeNeighboursOfEachGameBoardPoint: begin");

		GameBoard gameBoard = game.getGameBoard();

		for (GameBoardPoint gameBoardPoint : gameBoard.getAllGameBoardPointsAsOrderedList()) {

			for (NeighbourGameBoardPointDirection direction : NeighbourGameBoardPointDirection.values()) {
				GameBoardPoint neighbourSquare = gameBoard.getNeighbourGameBoardPoint(gameBoardPoint, direction);
				if (neighbourSquare != null) {
					gameBoardPoint.setNeighbour(direction, neighbourSquare);
				}
			}
		}
		LOGGER.info("computeNeighboursOfEachGameBoardPoint: end");
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
		IntegerPrecisionRectangle rectangleDefinedArea = predefinedConstructionLocation.getRectangleDefinedArea();
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
		AttackerDataModel normalAttackerDataModel = gameObjectsDataModel.getNormalAttackerDataModel();

		IntegerPrecisionRectangle creationAreaRectangle = attackersEntryArea.getRectangleDefinedArea();

		Point attackerExitPoint = exitArea.getRectangleDefinedArea().getOneRandomPointAllowingSubRectangleToFit(
				normalAttackerDataModel.getWidth(), normalAttackerDataModel.getHeight());

		NormalAttacker attacker = new NormalAttacker(normalAttackerDataModel, game, creationAreaRectangle.getX(),
				creationAreaRectangle.getY(), attackerExitPoint, evolutionLevel);
		return attacker;
	}

	public Attacker createFlyingAttacker(GameBoardAttackersEntryArea attackersEntryArea,
			GameBoardAttackersExitArea exitArea, int evolutionLevel) {
		GameObjectsDataModel gameObjectsDataModel = game.getGameObjectsDataModel();
		AttackerDataModel attackerDataModel = gameObjectsDataModel.getFlyingAttackerDataModel();

		IntegerPrecisionRectangle creationAreaRectangle = attackersEntryArea.getRectangleDefinedArea();

		Point attackerExitPoint = exitArea.getRectangleDefinedArea().getOneRandomPointAllowingSubRectangleToFit(
				attackerDataModel.getWidth(), attackerDataModel.getHeight());

		FlyingAttacker attacker = new FlyingAttacker(attackerDataModel, game, creationAreaRectangle.getX(),
				creationAreaRectangle.getY(), attackerExitPoint, evolutionLevel);
		return attacker;
	}

	public TowerDefenseMainViewGeneric getDesktopTowerDefenseMainView() {
		return desktopTowerDefenseMainView;
	}

}
