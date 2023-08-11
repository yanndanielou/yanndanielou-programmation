package builders;

import java.util.ArrayList;

public class AttackerDataModel {

	private String description;
	private Integer height;
	private Integer width;
	private Boolean imune;
	private Boolean flying;

	private ArrayList<AttackerLevelDataModel> levels;

	public ArrayList<AttackerLevelDataModel> getLevels() {
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
