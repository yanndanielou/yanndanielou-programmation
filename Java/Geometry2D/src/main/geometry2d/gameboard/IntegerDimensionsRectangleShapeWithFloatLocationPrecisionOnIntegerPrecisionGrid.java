package main.geometry2d.gameboard;

import java.awt.geom.Rectangle2D;

import main.geometry2d.integergeometry.IntegerPrecisionRectangle;

public class IntegerDimensionsRectangleShapeWithFloatLocationPrecisionOnIntegerPrecisionGrid {
	protected Rectangle2D.Float floatPrecisionRectangle;
	protected IntegerPrecisionRectangle integerPrecisionRectangle;

	public IntegerDimensionsRectangleShapeWithFloatLocationPrecisionOnIntegerPrecisionGrid() {
	}

	public IntegerDimensionsRectangleShapeWithFloatLocationPrecisionOnIntegerPrecisionGrid(
			IntegerPrecisionRectangle integerPrecisionRectangle) {
		this.integerPrecisionRectangle = new IntegerPrecisionRectangle(integerPrecisionRectangle);
		this.floatPrecisionRectangle = new Rectangle2D.Float(integerPrecisionRectangle.getX(),
				integerPrecisionRectangle.getY(), integerPrecisionRectangle.getWidth(),
				integerPrecisionRectangle.getHeight());
	}

	public IntegerPrecisionRectangle getIntegerPrecisionRectangle() {
		return integerPrecisionRectangle;
	}

	public Rectangle2D.Float getFloatPrecisionRectangle() {
		return floatPrecisionRectangle;
	}

	public int getWidth() {
		return integerPrecisionRectangle.getWidth();
	}

	public int getHeight() {
		return integerPrecisionRectangle.getHeight();
	}

	private void syncIntegerPrecisionRectangleLocationAccordingToFloatPrecisionRectangle() {

		int newXAsInt = Math.round(Math.round(floatPrecisionRectangle.getX()));
		int newYAsInt = Math.round(Math.round(floatPrecisionRectangle.getY()));

		integerPrecisionRectangle.setLocation(newXAsInt, newYAsInt);

	}

	public void translate(float dx, float dy) {
		floatPrecisionRectangle.setRect(floatPrecisionRectangle.getX() + dx, floatPrecisionRectangle.getY() + dy,
				integerPrecisionRectangle.getWidth(), integerPrecisionRectangle.getHeight());

		syncIntegerPrecisionRectangleLocationAccordingToFloatPrecisionRectangle();
	}

}
