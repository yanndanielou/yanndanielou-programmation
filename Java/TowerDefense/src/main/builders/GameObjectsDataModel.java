package main.builders;

import main.builders.belligerents.AttackerDataModel;
import main.builders.belligerents.TowerDataModel;
import main.builders.weapons.BombDataModel;

public class GameObjectsDataModel {

	private AttackerDataModel normalAttackerDataModel;
	private AttackerDataModel flyingAttackerDataModel;
	private BombDataModel simpleTowerBombDataModel;
	private TowerDataModel simpleTowerDataModel;

	public AttackerDataModel getNormalAttackerDataModel() {
		return normalAttackerDataModel;
	}

	public AttackerDataModel getFlyingAttackerDataModel() {
		return flyingAttackerDataModel;
	}

	public BombDataModel getSimpleTowerBombDataModel() {
		return simpleTowerBombDataModel;
	}

	public TowerDataModel getSimpleTowerDataModel() {
		return simpleTowerDataModel;
	}

}
