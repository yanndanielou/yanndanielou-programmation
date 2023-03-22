package game_board;

import builders.gameboard.GameBoardDataModel;
import builders.gameboard.GameBoardDataModelBuilder;

public class GameBoard {

	private int width = 0;
	private int height = 0;

	private AllyBoatBoardArea allyBoatBoardArea = null;

	public GameBoard(GameBoardDataModel gameBoardDataModel) {
		width = gameBoardDataModel.getWidth();

		height = gameBoardDataModel.getSky_game_board_area_data_model().getHeight()
				+ gameBoardDataModel.getAlly_boat_game_board_area_data_model().getHeight()
				+ gameBoardDataModel.getUnder_water_game_board_area_data_model().getHeight();
	}

}
