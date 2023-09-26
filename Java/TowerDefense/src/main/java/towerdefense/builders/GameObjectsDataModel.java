package towerdefense.builders;

import java.util.ArrayList;

import towerdefense.builders.belligerents.AttackerDataModel;
import towerdefense.builders.belligerents.TowerDataModel;
import towerdefense.builders.weapons.BombDataModel;

public class GameObjectsDataModel {

	private ArrayList<BombDataModel> weapons;
	private ArrayList<TowerDataModel> towers;
	private ArrayList<AttackerDataModel> attackers;

	@Deprecated
	public AttackerDataModel getNormalAttackerDataModel() {
		return attackers.get(0);
	}

	@Deprecated
	public AttackerDataModel getFlyingAttackerDataModel() {
		return attackers.get(1);
	}

	@Deprecated
	public BombDataModel getSimpleTowerBombDataModel() {
		return weapons.get(0);
	}

	@Deprecated
	public TowerDataModel getSimpleTowerDataModel() {
		return towers.get(0);
	}

}
