package builders;

import builders.belligerents.AttackerDataModel;
import builders.belligerents.TowerDataModel;
import builders.weapons.BombDataModel;

public class GameObjectsDataModel {

	private AttackerDataModel normalAttackerDataModel;
	private BombDataModel simpleTowerBombDataModel;
	private TowerDataModel simpleTowerDataModel;

	public AttackerDataModel getNormalAttackerDataModel() {
		return normalAttackerDataModel;
	}

	public BombDataModel getSimpleTowerBombDataModel() {
		return simpleTowerBombDataModel;
	}

	public TowerDataModel getSimpleTowerDataModel() {
		return simpleTowerDataModel;
	}

}
