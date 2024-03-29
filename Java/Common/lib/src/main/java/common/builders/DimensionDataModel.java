package common.builders;

import java.awt.Dimension;

public class DimensionDataModel extends NamedDataModel {

	private Integer width;
	private Integer height;

	public Dimension getDimension() {
		return new Dimension(width, height);
	}

}
