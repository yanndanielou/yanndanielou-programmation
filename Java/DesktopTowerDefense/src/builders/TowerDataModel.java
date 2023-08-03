package builders;

import java.util.ArrayList;

public class TowerDataModel {

	private String description;
	private Integer height;
	private Integer width;
	private Integer maximum_number_of_alive_ammunitions_allowed;

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

	public Integer getMaximum_number_of_alive_ammunitions_allowed() {
		return maximum_number_of_alive_ammunitions_allowed;
	}

}
