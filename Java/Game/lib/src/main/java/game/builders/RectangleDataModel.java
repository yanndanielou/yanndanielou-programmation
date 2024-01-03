package game.builders;

import geometry2d.integergeometry.IntegerPrecisionRectangle;

public class RectangleDataModel extends common.builders.RectangleDataModel {

	public IntegerPrecisionRectangle getRectangle() {
		return new IntegerPrecisionRectangle(x, y, width, height);
	}

}
