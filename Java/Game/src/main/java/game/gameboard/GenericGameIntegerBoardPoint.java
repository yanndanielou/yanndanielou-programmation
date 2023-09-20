package game.gameboard;

import java.util.Collection;
import java.util.EnumMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import geometry2d.integergeometry.IntegerPrecisionPoint;

public abstract class GenericGameIntegerBoardPoint extends IntegerPrecisionPoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7353270231660749618L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GenericGameIntegerBoardPoint.class);

	private EnumMap<NeighbourGameBoardPointDirection, GenericGameIntegerBoardPoint> neighbourPerDirection = new EnumMap<>(
			NeighbourGameBoardPointDirection.class);

	protected GenericGameIntegerBoardPoint(int line, int column) {
		super(column, line);
	}

	public void setNeighbour(NeighbourGameBoardPointDirection direction, GenericGameIntegerBoardPoint neighbour) {
		neighbourPerDirection.put(direction, neighbour);
	}

	public String getShortDescription() {
		return "GameBoardPoint :" + "[" + getXAsInt() + "," + getYAsInt() + "]";
	}

	public Collection<GenericGameIntegerBoardPoint> getNeighbours() {
		return neighbourPerDirection.values();
	}

}
