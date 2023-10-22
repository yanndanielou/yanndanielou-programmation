package geometry2d.integergeometry;

import java.awt.Point;

public class IntegerPrecisionPoint extends Point {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6972482105132872818L;

	public IntegerPrecisionPoint() {
		super();
	}

	public IntegerPrecisionPoint(int x, int y) {
		super(x, y);
	}

	public IntegerPrecisionPoint(Point p) {
		super(p);
	}

	public int getXAsInt() {
		return (int) getX();
	}

	public int getYAsInt() {
		return (int) getY();
	}

	public int getRow() {
		return getYAsInt();
	}

	public int getColumn() {
		return getXAsInt();
	}

}
