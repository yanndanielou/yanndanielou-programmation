package builders;

public class GameObjectsDataModel {

	private AttackerDataModel normal_attacker_data_model;
	private BombDataModel simple_tower_bomb_data_model;
	private TowerDataModel simple_tower_data_model;

	public AttackerDataModel getNormal_attacker_data_model() {
		return normal_attacker_data_model;
	}

	public BombDataModel getSimpleTowerBombDataModel() {
		return simple_tower_bomb_data_model;
	}

	public TowerDataModel getSimpleTowerDataModel() {
		return simple_tower_data_model;
	}

}
