package game.builders;

import java.awt.Dimension;

public class DimensionDataModel extends GameBoardNamedAreaDataModel {

	private Integer width;
	private Integer height;

	public Dimension getDimension() {
		return new Dimension(width, height);
	}
	
	public Integer getWidth() {
		return width;
	}
	
	public Integer getHeight() {
		return height;
	}

}
