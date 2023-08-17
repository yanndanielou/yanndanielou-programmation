package builders.gameboard;

import java.awt.Dimension;

public class DimensionDataModel extends GameBoardNamedAreaDataModel {

	private Integer width;
	private Integer height;

	public Dimension getDimension() {
		return new Dimension(width, height);
	}

}
