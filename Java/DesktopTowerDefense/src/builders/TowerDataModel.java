package builders;

import java.util.ArrayList;

public class TowerDataModel {

	private String description;
	private Integer height;
	private Integer width;

	private ArrayList<TowerLevelDataModel> levels;

	public ArrayList<TowerLevelDataModel> getLevels() {
		return levels;
	}

}
