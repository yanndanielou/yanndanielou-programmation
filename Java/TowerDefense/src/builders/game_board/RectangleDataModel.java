package builders.game_board;

import geometry.IntegerRectangle;

public class RectangleDataModel extends GameBoardNamedAreaDataModel {

	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;

	public IntegerRectangle getRectangle() {
		return new IntegerRectangle(x, y, width, height);
	}

}
