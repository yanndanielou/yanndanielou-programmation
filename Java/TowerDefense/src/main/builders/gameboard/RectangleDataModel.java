package main.builders.gameboard;

import main.geometry2d.integergeometry.IntegerPrecisionRectangle;

public class RectangleDataModel extends GameBoardNamedAreaDataModel {

	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;

	public IntegerPrecisionRectangle getRectangle() {
		return new IntegerPrecisionRectangle(x, y, width, height);
	}

}
