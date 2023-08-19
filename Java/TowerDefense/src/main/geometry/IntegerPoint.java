package main.geometry;

import java.awt.Point;

public class IntegerPoint extends Point {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6972482105132872818L;

	public IntegerPoint() {
		super();
	}

	public IntegerPoint(int x, int y) {
		super(x, y);
	}

	public IntegerPoint(Point p) {
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
