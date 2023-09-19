package game.builders;

import geometry2d.integergeometry.IntegerPrecisionRectangle;

public class RectangleDataModel extends GameBoardNamedAreaDataModel {

	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;

	public IntegerPrecisionRectangle getRectangle() {
		return new IntegerPrecisionRectangle(x, y, width, height);
	}

}
