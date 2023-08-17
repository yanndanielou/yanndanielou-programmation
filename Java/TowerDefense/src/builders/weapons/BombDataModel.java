package builders.weapons;

import java.util.ArrayList;

public class BombDataModel {

	private String description;
	private Integer height;
	private Integer width;
	private String fixedRepresentationImagePath;
	private ArrayList<BombLevelDataModel> levels;

	public String getDescription() {
		return description;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getWidth() {
		return width;
	}

	public String getFixedRepresentationImagePath() {
		return fixedRepresentationImagePath;
	}

	public ArrayList<BombLevelDataModel> getLevels() {
		return levels;
	}

}
