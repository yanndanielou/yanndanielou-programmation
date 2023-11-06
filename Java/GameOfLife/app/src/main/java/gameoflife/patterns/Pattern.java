package gameoflife.patterns;

import java.util.ArrayList;
import java.util.List;

import geometry2d.integergeometry.IntegerPrecisionPoint;
import geometry2d.integergeometry.IntegerPrecisionRectangle;

public class Pattern {

	private IntegerPrecisionRectangle rectangleBoundingBox;
	private List<IntegerPrecisionPoint> aliveCellsCoordinates = new ArrayList<>();
	private List<IntegerPrecisionPoint> deadCellsCoordinates = new ArrayList<>();

	public void addAliveCell(IntegerPrecisionPoint aliveCellCoordinate) {
		aliveCellsCoordinates.add(aliveCellCoordinate);
	}

	public void addDeadCell(IntegerPrecisionPoint aliveCellCoordinate) {
		deadCellsCoordinates.add(aliveCellCoordinate);
	}

}
