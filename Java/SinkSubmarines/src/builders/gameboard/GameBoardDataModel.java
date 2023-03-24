package builders.gameboard;

public class GameBoardDataModel {

	int width;

	private GameBoardAreaDataModel sky_game_board_area_data_model;
	private GameBoardAreaDataModel ally_boat_game_board_area_data_model;
	private GameBoardAreaDataModel under_water_game_board_area_data_model;
	private GameBoardAreaDataModel ocean_bed_game_board_area_data_model;

	public GameBoardDataModel() {
	}

	public GameBoardAreaDataModel getOcean_bed_game_board_area_data_model() {
		return ocean_bed_game_board_area_data_model;
	}

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

}
