package towerdefense.builders.belligerents;

import java.util.ArrayList;
import java.util.List;

public class AttackerDataModel extends GameObjectDataModel {

	private Boolean imune;
	private Boolean flying;

	private ArrayList<AttackerLevelDataModel> levels;

	public List<AttackerLevelDataModel> getLevels() {
		return levels;
	}

	public Boolean getImune() {
		return imune;
	}

	public Boolean isFlying() {
		return flying;
	}

}
