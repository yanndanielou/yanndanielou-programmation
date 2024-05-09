package tetris.game_objects.patterns;

import geometry2d.integergeometry.IntegerPrecisionPoint;


public class TetroMinoIStraightLongStickPattern extends Pattern {
	public TetroMinoIStraightLongStickPattern() {
		addMinoPoint(new IntegerPrecisionPoint(0, 0));
		addMinoPoint(new IntegerPrecisionPoint(0, 1));
		addMinoPoint(new IntegerPrecisionPoint(0, 2));
		addMinoPoint(new IntegerPrecisionPoint(0, 3));
	}
}
