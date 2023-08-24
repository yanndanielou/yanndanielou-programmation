package main.gameboard;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.belligerents.Attacker;
import main.belligerents.Tower;
import main.belligerents.listeners.AttackerListener;
import main.belligerents.listeners.TowerListener;
import main.builders.belligerents.TowerDataModel;
import main.builders.gameboard.GameBoardDataModel;
import main.builders.gameboard.GameBoardModelBuilder;
import main.common.BadLogicException;
import main.game.Game;
import main.geometry.IntegerPoint;
import main.geometry.IntegerRectangle;

public class GameBoard implements TowerListener, AttackerListener {

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
		createInitialGameBoardPoints();
	}

	private void createInitialGameBoardPoints() {

		for (int row = 0; row < getTotalHeight(); row++) {

			Map<Integer, GameBoardPoint> gameBoardOfOneRowPerColumn;
			if (gameBoardPointPerRowAndColumn.containsKey(row)) {
				gameBoardOfOneRowPerColumn = gameBoardPointPerRowAndColumn.get(row);
			} else {
				gameBoardOfOneRowPerColumn = new HashMap<>();
				gameBoardPointPerRowAndColumn.put(row, gameBoardOfOneRowPerColumn);
			}

			for (int column = 0; column < getTotalWidth(); column++) {
				GameBoardPoint gameBoardPoint = new GameBoardPoint(game, row, column);
				gameBoardOfOneRowPerColumn.put(column, gameBoardPoint);
			}

		}
	}

	public int getTotalWidth() {
		return gameBoardModelBuilder.getGameBoardTotalWidth();

	}

	public int getTotalHeight() {
		return gameBoardModelBuilder.getGameBoardTotalHeight();
	}

	public ArrayList<GameBoardPoint> getAllGameBoardPointsAsOrderedList() {
		return allGameBoardPointAsOrderedList;
	}

	public GameBoardPoint getGameBoardPoint(IntegerPoint point) {
		return getGameBoardPointByRowAndColumn(point.getRow(), point.getColumn());
	}

	public List<GameBoardPoint> getGameBoardPoints(List<IntegerPoint> points) {
		List<GameBoardPoint> gameBoardPoints = new ArrayList<>();
		for (IntegerPoint point : points) {
			gameBoardPoints.add(getGameBoardPoint(point));
		}
		return gameBoardPoints;
	}

	public GameBoardPoint getGameBoardPointByXAndY(int x, int y) {
		return getGameBoardPointByRowAndColumn(y, x);
	}

	public GameBoardPoint getGameBoardPointByRowAndColumn(int row, int column) {

		Map<Integer, GameBoardPoint> gameBoardPointOfRow = gameBoardPointPerRowAndColumn.get(row);

		if (gameBoardPointOfRow == null) {
			throw new BadLogicException("Cannot find row :" + row + " to search column:" + column);
		}

		GameBoardPoint gameBoardPoint = gameBoardPointOfRow.get(column);

		if (gameBoardPoint == null) {
			throw new BadLogicException("Cannot find column :" + column + " inside row:" + row);
		}

		return gameBoardPoint;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public GameBoardPoint getNeighbourGameBoardPoint(GameBoardPoint referenceGameBoardPoint,
			NeighbourGameBoardPointDirection direction) {
		GameBoardPoint neighbour = null;

		int referenceGameBoardPointColumn = referenceGameBoardPoint.getColumn();
		int referenceGameBoardPointRow = referenceGameBoardPoint.getRow();

		int referenceGameBoardPointX = referenceGameBoardPoint.getXAsInt();
		int referenceGameBoardPointY = referenceGameBoardPoint.getYAsInt();

		boolean isReferenceGameBoardPointFirstOfColumn = referenceGameBoardPointRow == 0;
		boolean isReferenceGameBoardPointLastOfColumn = referenceGameBoardPointRow == getTotalWidth() - 1;

		boolean isReferenceGameBoardPointFirstOfRow = referenceGameBoardPointColumn == 0;
		boolean isReferenceGameBoardPointLastOfRow = referenceGameBoardPointRow == getTotalHeight() - 1;

		// left gameBoardPoint
		switch (direction) {
		case NORTH:
			if (!isReferenceGameBoardPointFirstOfRow) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX, referenceGameBoardPointY - 1);
			}
			break;
		case NORTH_EAST:
			if (!isReferenceGameBoardPointLastOfRow && !isReferenceGameBoardPointFirstOfColumn) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX + 1, referenceGameBoardPointY - 1);
			}
			break;
		case EAST:
			if (!isReferenceGameBoardPointLastOfRow) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX + 1, referenceGameBoardPointY);
			}
			break;
		case SOUTH_EAST:
			if (!isReferenceGameBoardPointLastOfRow && !isReferenceGameBoardPointLastOfColumn) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX + 1, referenceGameBoardPointY + 1);
			}
			break;
		case SOUTH:
			if (!isReferenceGameBoardPointLastOfColumn) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX, referenceGameBoardPointY + 1);
			}
			break;
		case SOUTH_WEST:
			if (!isReferenceGameBoardPointFirstOfRow && !isReferenceGameBoardPointLastOfColumn) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX - 1, referenceGameBoardPointY + 1);
			}
			break;
		case WEST:
			if (!isReferenceGameBoardPointFirstOfRow) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX - 1, referenceGameBoardPointY);
			}
			break;
		case NORTH_WEST:
			if (!isReferenceGameBoardPointFirstOfRow && !isReferenceGameBoardPointFirstOfColumn) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX - 1, referenceGameBoardPointY - 1);
			}
			break;
		}
		return neighbour;
	}

	public GameBoardDataModel getGameBoardDataModel() {
		return gameBoardDataModel;
	}

	@Override
	public void onListenToTower(Tower tower) {
		placeTower(tower);
	}

	private void placeTower(Tower tower) {
		for (IntegerPoint pointIt : tower.getAllPoints()) {
			GameBoardPoint gameBoardPoint = getGameBoardPoint(pointIt);
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
		for (IntegerPoint pointIt : attacker.getAllPoints()) {
			GameBoardPoint gameBoardPoint = getGameBoardPoint(pointIt);
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

	private void generatePredefinedConstructionLocations(IntegerRectangle construtionAreaRectangle,
			Dimension predefinedConstructionLocationsDimension) {

		List<IntegerRectangle> predefinedConstructionsToCreateRectangles = construtionAreaRectangle
				.getInnerSubRectangles(predefinedConstructionLocationsDimension);

		for (IntegerRectangle predefinedConstructionRectangle : predefinedConstructionsToCreateRectangles) {
			GameBoardPredefinedConstructionLocation predefinedConstructionLocation = new GameBoardPredefinedConstructionLocation(
					this, predefinedConstructionRectangle);
			predefinedConstructionLocations.add(predefinedConstructionLocation);
			LOGGER.debug(
					"add predefinedConstructionLocation" + predefinedConstructionLocation.getRectangleDefinedArea());
		}
	}

	private void generatePredefinedConstructionLocations(IntegerRectangle initialRectangle, int xTranslation,
			int yTranslation, Dimension predefinedConstructionLocationDimension) {
		Point translatedRectangleTopLeftPoint = new Point(initialRectangle.getTopLeftPoint());
		translatedRectangleTopLeftPoint.translate(xTranslation, yTranslation);

		Dimension translatedRectangleDimension = new Dimension(initialRectangle.getWidth() - xTranslation,
				initialRectangle.getHeight() - yTranslation);

		IntegerRectangle translatedRectangle = new IntegerRectangle(translatedRectangleTopLeftPoint,
				translatedRectangleDimension);

		generatePredefinedConstructionLocations(translatedRectangle, predefinedConstructionLocationDimension);
	}

	public void generatePredefinedConstructionLocations(TowerDataModel towerDataModel) {
		for (GameBoardInitiallyConstructibleMacroArea initiallyConstructibleMacroArea : initiallyConstructibleMacroAreas) {
			IntegerRectangle initiallyConstructibleMacroAreaRectangle = initiallyConstructibleMacroArea
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
}