package game.gameboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import geometry2d.integergeometry.IntegerPrecisionPoint;
import main.common.exceptions.BadLogicException;

public abstract class GenericGameBoard  {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GenericGameBoard.class);

	private Map<Integer, Map<Integer, GenericGameIntegerBoardPoint>> gameBoardPointPerRowAndColumn = new HashMap<>();

	private ArrayList<GenericGameIntegerBoardPoint> allGameBoardPointAsOrderedList = new ArrayList<>();


	protected GenericGameBoard() {
	}
	
	protected void afterConstructor() {
		createInitialGameBoardPoints();
		computeNeighboursOfEachGameBoardPoint();
	}
	
	protected void computeNeighboursOfEachGameBoardPoint() {

		LOGGER.info("computeNeighboursOfEachGameBoardPoint: begin");


		for (GenericGameIntegerBoardPoint gameBoardPoint : getAllGameBoardPointsAsOrderedList()) {

			for (NeighbourGameBoardPointDirection direction : NeighbourGameBoardPointDirection.values()) {
				GenericGameIntegerBoardPoint neighbourSquare = getNeighbourGameBoardPoint(gameBoardPoint, direction);
				if (neighbourSquare != null) {
					gameBoardPoint.setNeighbour(direction, neighbourSquare);
				}
			}
		}
		LOGGER.info("computeNeighboursOfEachGameBoardPoint: end");
	}

	private void createInitialGameBoardPoints() {

		for (int row = 0; row < getTotalHeight(); row++) {

			Map<Integer, GenericGameIntegerBoardPoint> gameBoardOfOneRowPerColumn;
			if (gameBoardPointPerRowAndColumn.containsKey(row)) {
				gameBoardOfOneRowPerColumn = gameBoardPointPerRowAndColumn.get(row);
			} else {
				gameBoardOfOneRowPerColumn = new HashMap<>();
				gameBoardPointPerRowAndColumn.put(row, gameBoardOfOneRowPerColumn);
			}

			for (int column = 0; column < getTotalWidth(); column++) {
				GenericGameIntegerBoardPoint gameBoardPoint = createGameBoardPoint(row, column);
				gameBoardOfOneRowPerColumn.put(column, gameBoardPoint);
			}

		}
	}
	
	protected abstract GenericGameIntegerBoardPoint createGameBoardPoint(int row, int column);

	public abstract int getTotalWidth();

	public abstract int getTotalHeight();

	public ArrayList<GenericGameIntegerBoardPoint> getAllGameBoardPointsAsOrderedList() {
		return allGameBoardPointAsOrderedList;
	}

	public GenericGameIntegerBoardPoint getGameBoardPoint(IntegerPrecisionPoint point) {
		return getGameBoardPointByRowAndColumn(point.getRow(), point.getColumn());
	}

	public List<GenericGameIntegerBoardPoint> getGameBoardPoints(List<IntegerPrecisionPoint> points) {
		List<GenericGameIntegerBoardPoint> gameBoardPoints = new ArrayList<>();
		for (IntegerPrecisionPoint point : points) {
			gameBoardPoints.add(getGameBoardPoint(point));
		}
		return gameBoardPoints;
	}

	public GenericGameIntegerBoardPoint getGameBoardPointByXAndY(int x, int y) {
		return getGameBoardPointByRowAndColumn(y, x);
	}

	public GenericGameIntegerBoardPoint getGameBoardPointByRowAndColumn(int row, int column) {

		Map<Integer, GenericGameIntegerBoardPoint> gameBoardPointOfRow = gameBoardPointPerRowAndColumn.get(row);

		if (gameBoardPointOfRow == null) {
			throw new BadLogicException("Cannot find row :" + row + " to search column:" + column);
		}

		GenericGameIntegerBoardPoint gameBoardPoint = gameBoardPointOfRow.get(column);

		if (gameBoardPoint == null) {
			throw new BadLogicException("Cannot find column :" + column + " inside row:" + row);
		}

		return gameBoardPoint;
	}

	public GenericGameIntegerBoardPoint getNeighbourGameBoardPoint(GenericGameIntegerBoardPoint referenceGameBoardPoint,
			NeighbourGameBoardPointDirection direction) {
		GenericGameIntegerBoardPoint neighbour = null;

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
}
