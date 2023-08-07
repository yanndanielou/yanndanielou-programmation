package builders;

import java.awt.Point;
import java.awt.Rectangle;

import common.RandomIntegerGenerator;
import geometry.IntegerRectangle;

public class RectangleDataModel {

	private String name;
	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;

	public String getName() {
		return name;
	}

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
