package tetris.game_objects.patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import geometry2d.integergeometry.IntegerPrecisionPoint;
import geometry2d.integergeometry.IntegerPrecisionRectangle;

public class Pattern {

	private List<IntegerPrecisionPoint> minoPoints = new ArrayList<>();
	private List<IntegerPrecisionPoint> voidPoints = new ArrayList<>();

	public void addMinoPoint(IntegerPrecisionPoint minoPoint) {
		minoPoints.add(minoPoint);
	}

	public void addDeadCell(IntegerPrecisionPoint voidPoint) {
		voidPoints.add(voidPoint);
	}

	public IntegerPrecisionRectangle getRectangleBoundingBox() {
		List<IntegerPrecisionPoint> allCells = Stream
				.concat(minoPoints.stream(), voidPoints.stream()).toList();

		IntegerPrecisionRectangle integerPrecisionRectangle = IntegerPrecisionRectangle.getRectangleBoundingBoxOfPoints(allCells);

		return integerPrecisionRectangle;
	}

	public List<IntegerPrecisionPoint> getMinoCellsCoordinates() {
		return minoPoints;
	}

	public List<IntegerPrecisionPoint> getVoidCellsCoordinates() {
		return voidPoints;
	}

}
