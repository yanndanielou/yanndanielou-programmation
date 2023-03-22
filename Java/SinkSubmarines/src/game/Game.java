package game;

import java.util.ArrayList;

import builders.gameboard.GameBoardDataModel;
import builders.gameboard.GameBoardDataModelBuilder;
import builders.genericobjects.GenericObjectsDataModel;
import game_board.GameBoard;
import moving_objects.AllyBoat;

public class Game {

	private ArrayList<Level> levels = new ArrayList<Level>(); // Create an ArrayList object
	private GameBoard gameboard = null;
	private AllyBoat ally_boat = null;
	
	public Game(GameBoardDataModel gameBoardDataModel, GenericObjectsDataModel genericObjectsDataModel) {
		gameboard = new GameBoard(gameBoardDataModel);
		ally_boat = new AllyBoat();
	}

}
