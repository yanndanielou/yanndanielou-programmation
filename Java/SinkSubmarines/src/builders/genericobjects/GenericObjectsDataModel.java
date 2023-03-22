package builders.genericobjects;

import builders.gameboard.GameBoardAreaDataModel;

public class GenericObjectsDataModel {

	public int getWidth() {
		return width;
	}

	public GameBoardAreaDataModel getSky_game_board_area_data_model() {
		return sky_game_board_area_data_model;
	}

	public GameBoardAreaDataModel getAlly_boat_game_board_area_data_model() {
		return ally_boat_game_board_area_data_model;
	}

	public GameBoardAreaDataModel getUnder_water_game_board_area_data_model() {
		return under_water_game_board_area_data_model;
	}

	int width;

	private GameBoardAreaDataModel sky_game_board_area_data_model;
	private GameBoardAreaDataModel ally_boat_game_board_area_data_model;
	private GameBoardAreaDataModel under_water_game_board_area_data_model;

	public GenericObjectsDataModel() {
	}

}
