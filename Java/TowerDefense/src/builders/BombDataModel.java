package builders;

import java.util.ArrayList;

public class BombDataModel {

	private String description;
	private Integer height;
	private Integer width;
	private String fixed_representation_image_path;
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

	public String getFixed_representation_image_path() {
		return fixed_representation_image_path;
	}

	public ArrayList<BombLevelDataModel> getLevels() {
		return levels;
	}

}
