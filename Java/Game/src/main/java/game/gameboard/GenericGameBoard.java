package game.gameboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import geometry2d.integergeometry.IntegerPrecisionPoint;
import main.common.exceptions.BadLogicException;

public abstract class GenericGameBoard {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GenericGameBoard.class);

	private Map<Integer, Map<Integer, GenericIntegerGameBoardPoint>> gameBoardPointPerXAndY = new HashMap<>();

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
		LOGGER.info("createInitialGameBoardPoints: begin");

		for (int x = 0; x < getTotalWidth(); x++) {

			Map<Integer, GenericIntegerGameBoardPoint> gameBoardOfOneXPerY;
			if (gameBoardPointPerXAndY.containsKey(x)) {
				gameBoardOfOneXPerY = gameBoardPointPerXAndY.get(x);
			} else {
				gameBoardOfOneXPerY = new HashMap<>();
				gameBoardPointPerXAndY.put(x, gameBoardOfOneXPerY);
			}

			for (int y = 0; y < getTotalHeight(); y++) {
				GenericIntegerGameBoardPoint gameBoardPoint = createGameBoardPoint(x, y);
				gameBoardOfOneXPerY.put(y, gameBoardPoint);
				allGameBoardPointAsOrderedList.add(gameBoardPoint);
			}
		}

	}

	protected abstract GenericIntegerGameBoardPoint createGameBoardPoint(int x, int y);

	public abstract int getTotalWidth();

	public abstract int getTotalHeight();

	public ArrayList<GenericIntegerGameBoardPoint> getAllGameBoardPointsAsOrderedList() {
		return allGameBoardPointAsOrderedList;
	}

	public GenericIntegerGameBoardPoint getGameBoardPoint(IntegerPrecisionPoint point) {
		return getGameBoardPointByXAndY(point.getXAsInt(), point.getYAsInt());
	}

	public List<GenericIntegerGameBoardPoint> getGameBoardPoints(List<IntegerPrecisionPoint> points) {
		List<GenericIntegerGameBoardPoint> gameBoardPoints = new ArrayList<>();
		for (IntegerPrecisionPoint point : points) {
			gameBoardPoints.add(getGameBoardPoint(point));
		}
		return gameBoardPoints;
	}

	public GenericIntegerGameBoardPoint getGameBoardPointByXAndY(int x, int y) {
		Map<Integer, GenericIntegerGameBoardPoint> gameBoardPointsOfX = gameBoardPointPerXAndY.get(x);

		if (gameBoardPointsOfX == null) {
			throw new BadLogicException("Cannot find x :" + x + " to search y:" + y);
		}

		GenericIntegerGameBoardPoint gameBoardPoint = gameBoardPointsOfX.get(y);

		if (gameBoardPoint == null) {
			throw new BadLogicException("Cannot find y :" + y + " inside x:" + x);
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
			if (!isReferencePointInTopLine && !isReferencePointInRightExtremityColumn) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX + 1, referenceGameBoardPointY - 1);
			}
			break;
		case EAST:
			if (!isReferencePointInRightExtremityColumn) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX + 1, referenceGameBoardPointY);
			}
			break;
		case SOUTH_EAST:
			if (!isReferencePointInBottomLine && !isReferencePointInRightExtremityColumn) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX + 1, referenceGameBoardPointY + 1);
			}
			break;
		case SOUTH:
			if (!isReferencePointInBottomLine) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX, referenceGameBoardPointY + 1);
			}
			break;
		case SOUTH_WEST:
			if (!isReferencePointInBottomLine && !isReferencePointInLeftExtremityColumn) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX - 1, referenceGameBoardPointY + 1);
			}
			break;
		case WEST:
			if (!isReferencePointInLeftExtremityColumn) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX - 1, referenceGameBoardPointY);
			}
			break;
		default: // NORTH_WEST
			if (!isReferencePointInTopLine && !isReferencePointInLeftExtremityColumn) {
				neighbour = getGameBoardPointByXAndY(referenceGameBoardPointX - 1, referenceGameBoardPointY - 1);
			}
			break;
		}
		return neighbour;
	}
}
