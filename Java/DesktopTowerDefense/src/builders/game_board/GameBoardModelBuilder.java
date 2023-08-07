package builders.game_board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

import game.Game;
import game_board.GameBoard;
import game_board.GameBoardAttackersEntryArea;
import game_board.GameBoardAttackersExitArea;
import game_board.GameBoardWallRectangle;

public class GameBoardModelBuilder {
	private Gson gson = new Gson();

	private GameBoardDataModel gameBoardDataModel;

	public GameBoardDataModel getGameBoardDataModel() {
		return gameBoardDataModel;
	}

	public GameBoardModelBuilder(String game_board_data_model_json_file) {
		BufferedReader br = null;

		try {

			br = new BufferedReader(new FileReader(game_board_data_model_json_file));

		} catch (IOException e) {
			e.printStackTrace();
		}
		gameBoardDataModel = gson.fromJson(br, GameBoardDataModel.class);
	}

	public void buildAllAreas(Game game, GameBoard gameBoard) {

		for (RectangleDataModel wallDataModel : gameBoardDataModel.getWallsAsRectangles()) {
			GameBoardWallRectangle wall = new GameBoardWallRectangle(game, wallDataModel);
			gameBoard.addWall(wall);
		}
		for (RectangleDataModel attackersEntryAreaDataModel : gameBoardDataModel.getAttackersEntryAreasAsRectangles()) {
			GameBoardAttackersEntryArea attackersEntryArea = new GameBoardAttackersEntryArea(game,
					attackersEntryAreaDataModel);
			gameBoard.addGameBoardAttackersEntryArea(attackersEntryArea);
		}
		for (RectangleDataModel attackersExitAreaDataModel : gameBoardDataModel.getAttackersExitAreasAsRectangles()) {
			GameBoardAttackersExitArea attackersExitArea = new GameBoardAttackersExitArea(game,
					attackersExitAreaDataModel);
			gameBoard.addGameBoardAttackersExitArea(attackersExitArea);
		}
	}

}
