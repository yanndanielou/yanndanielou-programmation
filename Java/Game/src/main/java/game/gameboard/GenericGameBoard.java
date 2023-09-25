package game.gameboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import geometry2d.integergeometry.IntegerPrecisionPoint;
import main.common.exceptions.BadLogicException;

public abstract class GenericGameBoard {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GenericGameBoard.class);

	private Map<Integer, Map<Integer, GenericIntegerGameBoardPoint>> gameBoardPointPerRowAndColumn = new HashMap<>();

	private ArrayList<GenericIntegerGameBoardPoint> allGameBoardPointAsOrderedList = new ArrayList<>();

	protected GenericGameBoard() {
	}

	protected void afterConstructor() {
		createInitialGameBoardPoints();
		computeNeighboursOfEachGameBoardPoint();
	}

	protected void computeNeighboursOfEachGameBoardPoint() {

		LOGGER.info("computeNeighboursOfEachGameBoardPoint: begin");

		for (GenericIntegerGameBoardPoint gameBoardPoint : getAllGameBoardPointsAsOrderedList()) {

			for (NeighbourGameBoardPointDirection direction : NeighbourGameBoardPointDirection.values()) {
				GenericIntegerGameBoardPoint neighbourSquare = getNeighbourGameBoardPoint(gameBoardPoint, direction);
				if (neighbourSquare != null) {
					gameBoardPoint.setNeighbour(direction, neighbourSquare);
				}
			}
		}
		LOGGER.info("computeNeighboursOfEachGameBoardPoint: end");
	}

	private void createInitialGameBoardPoints() {

		for (int row = 0; row < getTotalHeight(); row++) {

			Map<Integer, GenericIntegerGameBoardPoint> gameBoardOfOneRowPerColumn;
			if (gameBoardPointPerRowAndColumn.containsKey(row)) {
				gameBoardOfOneRowPerColumn = gameBoardPointPerRowAndColumn.get(row);
			} else {
				gameBoardOfOneRowPerColumn = new HashMap<>();
				gameBoardPointPerRowAndColumn.put(row, gameBoardOfOneRowPerColumn);
			}

			for (int column = 0; column < getTotalWidth(); column++) {
				GenericIntegerGameBoardPoint gameBoardPoint = createGameBoardPoint(row, column);
				gameBoardOfOneRowPerColumn.put(column, gameBoardPoint);
				allGameBoardPointAsOrderedList.add(gameBoardPoint);
			}

		}
	}

	protected abstract GenericIntegerGameBoardPoint createGameBoardPoint(int row, int column);

	public abstract int getTotalWidth();

	public abstract int getTotalHeight();

	public ArrayList<GenericIntegerGameBoardPoint> getAllGameBoardPointsAsOrderedList() {
		return allGameBoardPointAsOrderedList;
	}

	public GenericIntegerGameBoardPoint getGameBoardPoint(IntegerPrecisionPoint point) {
		return getGameBoardPointByRowAndColumn(point.getRow(), point.getColumn());
	}

	public List<GenericIntegerGameBoardPoint> getGameBoardPoints(List<IntegerPrecisionPoint> points) {
		List<GenericIntegerGameBoardPoint> gameBoardPoints = new ArrayList<>();
		for (IntegerPrecisionPoint point : points) {
			gameBoardPoints.add(getGameBoardPoint(point));
		}
		return gameBoardPoints;
	}

	public GenericIntegerGameBoardPoint getGameBoardPointByXAndY(int x, int y) {
		return getGameBoardPointByRowAndColumn(y, x);
	}

	public GenericIntegerGameBoardPoint getGameBoardPointByRowAndColumn(int row, int column) {

		Map<Integer, GenericIntegerGameBoardPoint> gameBoardPointOfRow = gameBoardPointPerRowAndColumn.get(row);

		if (gameBoardPointOfRow == null) {
			throw new BadLogicException("Cannot find row :" + row + " to search column:" + column);
		}

		GenericIntegerGameBoardPoint gameBoardPoint = gameBoardPointOfRow.get(column);

		if (gameBoardPoint == null) {
			throw new BadLogicException("Cannot find column :" + column + " inside row:" + row);
		}

		return gameBoardPoint;
	}

	public GenericIntegerGameBoardPoint getNeighbourGameBoardPoint(GenericIntegerGameBoardPoint referenceGameBoardPoint,
			NeighbourGameBoardPointDirection direction) {
		GenericIntegerGameBoardPoint neighbour = null;

		int referenceGameBoardPointColumn = referenceGameBoardPoint.getColumn();
		int referenceGameBoardPointRow = referenceGameBoardPoint.getRow();

		int referenceGameBoardPointX = referenceGameBoardPoint.getXAsInt();
		int referenceGameBoardPointY = referenceGameBoardPoint.getYAsInt();

		boolean isReferencePointInTopLine = referenceGameBoardPoint.getRow() == 0;
		
		@SuppressWarnings("unused")
		boolean isReferencePointInBottomLine = referenceGameBoardPoint.getRow() == getTotalHeight() - 1;

		@SuppressWarnings("unused")
		boolean isReferencePointInLeftExtremityColumn = referenceGameBoardPoint.getColumn() == 0;

		@SuppressWarnings("unused")
		boolean isReferencePointInRightExtremityColumn = referenceGameBoardPoint.getColumn() == getTotalWidth() - 1;

		boolean isReferenceGameBoardPointFirstOfColumn = referenceGameBoardPointRow == 0;
		boolean isReferenceGameBoardPointLastOfColumn = referenceGameBoardPointRow == getTotalWidth() - 1;

		boolean isReferenceGameBoardPointFirstOfRow = referenceGameBoardPointColumn == 0;
		boolean isReferenceGameBoardPointLastOfRow = referenceGameBoardPointColumn == getTotalHeight() - 1;

		// left gameBoardPoint
		switch (direction) {
		case NORTH:
			if (!isReferencePointInTopLine) {
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
}
