package game;

import java.util.ArrayList;

import game_board.GameBoard;
import moving_objects.AllyBoat;

public class Game {

	private ArrayList<Level> levels = new ArrayList<Level>(); // Create an ArrayList object
	private GameBoard gameboard = null;
	private AllyBoat ally_boat = null;
	
	public Game() {
		gameboard = new GameBoard(600, 600);
		ally_boat = new AllyBoat();
	}

}
