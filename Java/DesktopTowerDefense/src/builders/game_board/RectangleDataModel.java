package builders.game_board;

import java.awt.Point;

import common.RandomIntegerGenerator;
import geometry.IntegerRectangle;

public class RectangleDataModel extends GameBoardNamedAreaDataModel {

	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;

	public IntegerRectangle getRectangle() {
		return new IntegerRectangle(x, y, width, height);
	}

	public Point getOneRandomPointAllowingSubRectangleToFit(int subRectangleWidth, int subRectangleHeight) {
		int minX = x;
		int maxX = x + width - subRectangleWidth;

		int minY = y;
		int maxY = y + height - subRectangleHeight;

		int chosenX = RandomIntegerGenerator.getRandomNumberUsingNextInt(minX, maxX);
		int chosenY = RandomIntegerGenerator.getRandomNumberUsingNextInt(minY, maxY);

		return new Point(chosenX, chosenY);

	}

}
