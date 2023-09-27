package towerdefense.gameboard;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.gameboard.GenericGameBoard;
import game.gameboard.GenericIntegerGameBoardPoint;
import geometry2d.integergeometry.IntegerPrecisionPoint;
import geometry2d.integergeometry.IntegerPrecisionRectangle;
import towerdefense.belligerents.Attacker;
import towerdefense.belligerents.Tower;
import towerdefense.belligerents.listeners.AttackerListener;
import towerdefense.belligerents.listeners.TowerListener;
import towerdefense.builders.belligerents.TowerDataModel;
import towerdefense.builders.gameboard.GameBoardDataModel;
import towerdefense.builders.gameboard.GameBoardModelBuilder;
import towerdefense.game.Game;

public class GameBoard extends GenericGameBoard implements TowerListener, AttackerListener {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoard.class);

	private Map<Integer, Map<Integer, GameBoardPoint>> gameBoardPointPerRowAndColumn = new HashMap<>();

	private GameBoardDataModel gameBoardDataModel;

	private ArrayList<GameBoardPoint> allGameBoardPointAsOrderedList = new ArrayList<>();

	private ArrayList<GameBoardWallArea> walls = new ArrayList<>();
	private ArrayList<GameBoardAttackersEntryArea> gameBoardAttackersEntryAreas = new ArrayList<>();
	private ArrayList<GameBoardAttackersExitArea> gameBoardAttackersExitAreas = new ArrayList<>();
	private ArrayList<GameBoardNonPlayableArea> nonPlayableAreas = new ArrayList<>();
	private ArrayList<GameBoardInitiallyConstructibleMacroArea> initiallyConstructibleMacroAreas = new ArrayList<>();
	private ArrayList<GameBoardPredefinedConstructionLocation> predefinedConstructionLocations = new ArrayList<>();

	private Game game;

	private GameBoardModelBuilder gameBoardModelBuilder;

	public GameBoard(GameBoardModelBuilder gameBoardModelBuilder) {

		this.gameBoardDataModel = gameBoardModelBuilder.getGameBoardDataModel();
		this.gameBoardModelBuilder = gameBoardModelBuilder;
		afterConstructor();
	}

	public int getTotalWidth() {
		return gameBoardModelBuilder.getGameBoardTotalWidth();

	}

	public int getTotalHeight() {
		return gameBoardModelBuilder.getGameBoardTotalHeight();
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public GameBoardDataModel getGameBoardDataModel() {
		return gameBoardDataModel;
	}

	@Override
	public void onListenToTower(Tower tower) {
		placeTower(tower);
	}

	private void placeTower(Tower tower) {
		for (IntegerPrecisionPoint pointIt : tower.getAllPoints()) {
			GameBoardPoint gameBoardPoint = (GameBoardPoint) getGameBoardPoint(pointIt);
			tower.addListener(gameBoardPoint);
		}

	}

	@Override
	public void onTowerRemoval(Tower tower) {
		// Auto-generated method stub

	}

	@Override
	public void onAttackerEndOfDestructionAndClean(Attacker attacker) {
		// Auto-generated method stub

	}

	@Override
	public void onListenToAttacker(Attacker attacker) {
		for (IntegerPrecisionPoint pointIt : attacker.getAllPoints()) {
			GameBoardPoint gameBoardPoint = (GameBoardPoint) getGameBoardPoint(pointIt);
			attacker.addListener(gameBoardPoint);
		}
	}

	@Override
	public void onAttackerMoved(Attacker attacker) {
		// Auto-generated method stub

	}

	@Override
	public void onAttackerBeginningOfDestruction(Attacker attacker) {
		// Auto-generated method stub

	}

	public void addWall(GameBoardWallArea wall) {
		walls.add(wall);
		wall.getAllPoints().forEach(gameBoardPoint -> gameBoardPoint.addWall(wall));
	}

	public ArrayList<GameBoardWallArea> getWalls() {
		return walls;
	}

	public void addGameBoardAttackersExitArea(GameBoardAttackersExitArea gameBoardAttackersExitArea) {
		gameBoardAttackersExitAreas.add(gameBoardAttackersExitArea);
		gameBoardAttackersExitArea.getAllPoints()
				.forEach(gameBoardPoint -> gameBoardPoint.addGameBoardAttackersExitArea(gameBoardAttackersExitArea));
	}

	public ArrayList<GameBoardAttackersExitArea> getGameBoardAttackersExitAreas() {
		return gameBoardAttackersExitAreas;
	}

	public void addGameBoardAttackersEntryArea(GameBoardAttackersEntryArea gameBoardAttackersEntryArea) {
		gameBoardAttackersEntryAreas.add(gameBoardAttackersEntryArea);
		gameBoardAttackersEntryArea.getAllPoints()
				.forEach(gameBoardPoint -> gameBoardPoint.addGameBoardAttackersEntryArea(gameBoardAttackersEntryArea));
	}

	public ArrayList<GameBoardAttackersEntryArea> getGameBoardAttackersEntryAreas() {
		return gameBoardAttackersEntryAreas;
	}

	public void addNonPlayableArea(GameBoardNonPlayableArea nonPlayableArea) {
		nonPlayableAreas.add(nonPlayableArea);
		nonPlayableArea.getAllPoints().forEach(gameBoardPoint -> gameBoardPoint.addNonPlayableArea(nonPlayableArea));
	}

	public void addGameBoardInitiallyConstructibleMacroArea(
			GameBoardInitiallyConstructibleMacroArea constructibleArea) {
		initiallyConstructibleMacroAreas.add(constructibleArea);
		constructibleArea.getAllPoints().forEach(
				gameBoardPoint -> gameBoardPoint.addGameBoardInitiallyConstructibleMacroArea(constructibleArea));
	}

	private void generatePredefinedConstructionLocations(IntegerPrecisionRectangle construtionAreaRectangle,
			Dimension predefinedConstructionLocationsDimension) {

		List<IntegerPrecisionRectangle> predefinedConstructionsToCreateRectangles = construtionAreaRectangle
				.getInnerSubRectangles(predefinedConstructionLocationsDimension);

		for (IntegerPrecisionRectangle predefinedConstructionRectangle : predefinedConstructionsToCreateRectangles) {
			GameBoardPredefinedConstructionLocation predefinedConstructionLocation = new GameBoardPredefinedConstructionLocation(
					this, predefinedConstructionRectangle);
			predefinedConstructionLocations.add(predefinedConstructionLocation);
			LOGGER.debug(() -> "add predefinedConstructionLocation"
					+ predefinedConstructionLocation.getRectangleDefinedArea());
		}
	}

	private void generatePredefinedConstructionLocations(IntegerPrecisionRectangle initialRectangle, int xTranslation,
			int yTranslation, Dimension predefinedConstructionLocationDimension) {
		Point translatedRectangleTopLeftPoint = new Point(initialRectangle.getTopLeftPoint());
		translatedRectangleTopLeftPoint.translate(xTranslation, yTranslation);

		Dimension translatedRectangleDimension = new Dimension(initialRectangle.getWidth() - xTranslation,
				initialRectangle.getHeight() - yTranslation);

		IntegerPrecisionRectangle translatedRectangle = new IntegerPrecisionRectangle(translatedRectangleTopLeftPoint,
				translatedRectangleDimension);

		generatePredefinedConstructionLocations(translatedRectangle, predefinedConstructionLocationDimension);
	}

	public void generatePredefinedConstructionLocations(TowerDataModel towerDataModel) {
		for (GameBoardInitiallyConstructibleMacroArea initiallyConstructibleMacroArea : initiallyConstructibleMacroAreas) {
			IntegerPrecisionRectangle initiallyConstructibleMacroAreaRectangle = initiallyConstructibleMacroArea
					.getRectangleDefinedArea();

			generatePredefinedConstructionLocations(initiallyConstructibleMacroAreaRectangle,
					towerDataModel.getDimension());

			generatePredefinedConstructionLocations(initiallyConstructibleMacroAreaRectangle,
					towerDataModel.getWidth() / 2, 0, towerDataModel.getDimension());
			generatePredefinedConstructionLocations(initiallyConstructibleMacroAreaRectangle, 0,
					towerDataModel.getHeight() / 2, towerDataModel.getDimension());
		}
	}

	public ArrayList<GameBoardPredefinedConstructionLocation> getPredefinedConstructionLocations() {
		return predefinedConstructionLocations;
	}

	@Override
	public void onAttackerEscape(Attacker attacker) {
		// Auto-generated method stub

	}

	@Override
	protected GenericIntegerGameBoardPoint createGameBoardPoint(int x, int y) {
		return new GameBoardPoint(game, x, y);
	}
}
