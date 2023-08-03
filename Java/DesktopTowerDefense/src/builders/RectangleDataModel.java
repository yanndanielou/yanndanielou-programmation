package builders;

import java.awt.Rectangle;

public class RectangleDataModel {

	private String name;
	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;

	public String getName() {
		return name;
	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}

}
