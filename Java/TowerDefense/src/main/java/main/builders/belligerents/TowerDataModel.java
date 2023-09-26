package main.builders.belligerents;

import java.awt.Dimension;
import java.util.ArrayList;

public class TowerDataModel extends GameObjectDataModel {

	private Integer maximumNumberOfAliveAmmunitionsAllowed;

	private ArrayList<TowerLevelDataModel> levels;

	public ArrayList<TowerLevelDataModel> getLevels() {
		return levels;
	}

	public Integer getMaximumNumberOfAliveAmmunitionsAllowed() {
		return maximumNumberOfAliveAmmunitionsAllowed;
	}

	public Dimension getDimension() {
		return new Dimension(width, height);
	}

}
