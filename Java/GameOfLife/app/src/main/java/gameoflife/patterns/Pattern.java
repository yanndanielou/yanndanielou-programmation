package gameoflife.patterns;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import geometry2d.integergeometry.IntegerPrecisionPoint;
import geometry2d.integergeometry.IntegerPrecisionRectangle;

public class Pattern {

	private List<IntegerPrecisionPoint> aliveCellsCoordinates = new ArrayList<>();
	private List<IntegerPrecisionPoint> deadCellsCoordinates = new ArrayList<>();

	public void addAliveCell(IntegerPrecisionPoint aliveCellCoordinate) {
		aliveCellsCoordinates.add(aliveCellCoordinate);
	}

	public void addDeadCell(IntegerPrecisionPoint aliveCellCoordinate) {
		deadCellsCoordinates.add(aliveCellCoordinate);
	}

	public IntegerPrecisionRectangle getRectangleBoundingBox() {
		List<IntegerPrecisionPoint> allCells = Stream
				.concat(aliveCellsCoordinates.stream(), deadCellsCoordinates.stream()).toList();

		IntegerPrecisionRectangle integerPrecisionRectangle = IntegerPrecisionRectangle.getRectangleBoundingBoxOfPoints(allCells);

		return integerPrecisionRectangle;
	}

	public List<IntegerPrecisionPoint> getAliveCellsCoordinates() {
		return aliveCellsCoordinates;
	}

	public List<IntegerPrecisionPoint> getDeadCellsCoordinates() {
		return deadCellsCoordinates;
	}

}
