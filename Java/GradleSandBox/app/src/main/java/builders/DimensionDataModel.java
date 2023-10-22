package builders;

import java.awt.Dimension;

public class DimensionDataModel  {

	private Integer width;
	private Integer height;

	public Dimension getDimension() {
		return new Dimension(width, height);
	}
	
}
