package towerdefense.core;

import java.awt.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import geometry2d.integergeometry.IntegerPrecisionRectangle;
import towerdefense.belligerents.Attacker;
import towerdefense.belligerents.FlyingAttacker;
import towerdefense.belligerents.NormalAttacker;
import towerdefense.belligerents.Tower;
import towerdefense.builders.GameObjectsDataModel;
import towerdefense.builders.GameObjectsModelBuilder;
import towerdefense.builders.belligerents.AttackerDataModel;
import towerdefense.builders.belligerents.TowerDataModel;
import towerdefense.builders.gameboard.GameBoardModelBuilder;
import towerdefense.builders.weapons.BombDataModel;
import towerdefense.constants.Constants;
import towerdefense.game.Game;
import towerdefense.gameboard.GameBoard;
import towerdefense.gameboard.GameBoardAttackersEntryArea;
import towerdefense.gameboard.GameBoardAttackersExitArea;
import towerdefense.gameboard.GameBoardPredefinedConstructionLocation;
import towerdefense.hmi.TowerDefenseMainViewGeneric;

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
