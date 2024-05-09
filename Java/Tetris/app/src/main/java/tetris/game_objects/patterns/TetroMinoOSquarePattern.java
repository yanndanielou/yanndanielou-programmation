package tetris.game_objects.patterns;

import geometry2d.integergeometry.IntegerPrecisionPoint;


public class TetroMinoOSquarePattern extends Pattern {
	public TetroMinoOSquarePattern() {
		addMinoPoint(new IntegerPrecisionPoint(0, 0));
		addMinoPoint(new IntegerPrecisionPoint(0, 1));
		addMinoPoint(new IntegerPrecisionPoint(1, 0));
		addMinoPoint(new IntegerPrecisionPoint(1, 1));
	}
}
