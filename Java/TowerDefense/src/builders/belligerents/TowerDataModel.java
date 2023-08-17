package builders.belligerents;

import java.awt.Dimension;
import java.util.ArrayList;

public class TowerDataModel {

	private String description;
	private Integer height;
	private Integer width;
	private Integer maximumNumberOfAliveAmmunitionsAllowed;

	private ArrayList<TowerLevelDataModel> levels;

	public ArrayList<TowerLevelDataModel> getLevels() {
		return levels;
	}

	public String getDescription() {
		return description;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getMaximumNumberOfAliveAmmunitionsAllowed() {
		return maximumNumberOfAliveAmmunitionsAllowed;
	}

	public Dimension getDimension() {
		return new Dimension(width, height);
	}

}
