package builders.genericobjects;

public class GenericObjectsDataModel {

	private GenericObjectDataModel ally_boat_data_model;
	private GenericObjectDataModel simple_submarine_data_model;
	private GenericObjectDataModel yellow_submarine_data_model;
	private AllySimpleBombDataModel ally_simple_bomb_data_model;
	private GenericObjectDataModel simple_submarine_bomb_data_model;
	private GenericObjectDataModel floating_submarine_bomb_data_model;

	public GenericObjectsDataModel() {
	}

	public GenericObjectDataModel getAlly_boat_data_model() {
		return ally_boat_data_model;
	}

	public GenericObjectDataModel getSimple_submarine_data_model() {
		return simple_submarine_data_model;
	}

	public AllySimpleBombDataModel getAlly_simple_bomb_data_model() {
		return ally_simple_bomb_data_model;
	}

	public GenericObjectDataModel getYellow_submarine_data_model() {
		return yellow_submarine_data_model;
	}

	public GenericObjectDataModel getSimple_submarine_bomb_data_model() {
		return simple_submarine_bomb_data_model;
	}

	public GenericObjectDataModel getFloating_submarine_bomb_data_model() {
		return floating_submarine_bomb_data_model;
	}

}
