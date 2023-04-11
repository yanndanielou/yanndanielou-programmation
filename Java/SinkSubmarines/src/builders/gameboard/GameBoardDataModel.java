package builders.gameboard;

@Deprecated
public class GameBoardDataModel {

	private int width;

	private GameBoardAreaDataModel sky_game_board_area_data_model;
	private GameBoardAreaDataModel ally_boat_game_board_area_data_model;
	private GameBoardAreaDataModel under_water_game_board_area_data_model;
	private GameBoardAreaDataModel ocean_bed_game_board_area_data_model;
	private GameBoardAreaDataModel top_area_data_model;

	public GameBoardDataModel() {
	}

	public GameBoardAreaDataModel getOcean_bed_game_board_area_data_model() {
		return ocean_bed_game_board_area_data_model;
	}

	public int getWidth() {
		return width;
	}

	@Deprecated
	public int getHeight() {
		return top_area_data_model.getHeight() + sky_game_board_area_data_model.getHeight()
				+ ally_boat_game_board_area_data_model.getHeight() + under_water_game_board_area_data_model.getHeight()
				+ ocean_bed_game_board_area_data_model.getHeight();
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

	public GameBoardAreaDataModel getTop_area_data_model() {
		return top_area_data_model;
	}

}
