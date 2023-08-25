package main.builders.gameboard;

import main.geometry2d.integergeometry.IntegerRectangle;

public class RectangleDataModel extends GameBoardNamedAreaDataModel {

	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;

	public IntegerRectangle getRectangle() {
		return new IntegerRectangle(x, y, width, height);
	}

}
