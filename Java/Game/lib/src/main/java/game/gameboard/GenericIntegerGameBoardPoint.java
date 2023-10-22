package game.gameboard;

import java.util.Collection;
import java.util.EnumMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import geometry2d.integergeometry.IntegerPrecisionPoint;

public class GenericIntegerGameBoardPoint extends IntegerPrecisionPoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7353270231660749618L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GenericIntegerGameBoardPoint.class);

	private EnumMap<NeighbourGameBoardPointDirection, GenericIntegerGameBoardPoint> neighbourPerDirection = new EnumMap<>(
			NeighbourGameBoardPointDirection.class);

	protected GenericIntegerGameBoardPoint(int x, int y) {
		super(x, y);
	}

	public void setNeighbour(NeighbourGameBoardPointDirection direction, GenericIntegerGameBoardPoint neighbour) {
		neighbourPerDirection.put(direction, neighbour);
	}

	public String getShortDescription() {
		return "GameBoardPoint :" + "[" + getXAsInt() + "," + getYAsInt() + "]";
	}

	public Collection<GenericIntegerGameBoardPoint> getNeighbours() {
		return neighbourPerDirection.values();
	}

}
