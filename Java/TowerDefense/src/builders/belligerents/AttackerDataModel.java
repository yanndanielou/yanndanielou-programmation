package builders.belligerents;

import java.util.ArrayList;
import java.util.List;

public class AttackerDataModel {

	private String description;
	private Integer height;
	private Integer width;
	private Boolean imune;
	private Boolean flying;

	private ArrayList<AttackerLevelDataModel> levels;

	public List<AttackerLevelDataModel> getLevels() {
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

	public Boolean getImune() {
		return imune;
	}

	public Boolean getFlying() {
		return flying;
	}

}
