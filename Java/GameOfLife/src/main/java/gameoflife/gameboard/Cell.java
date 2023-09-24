package gameoflife.gameboard;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.gameboard.GenericGameIntegerBoardPoint;
import gameoflife.game.Game;
import gameoflife.game.GameBoardPointListener;

public class Cell extends GenericGameIntegerBoardPoint  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7353270231660749618L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Cell.class);

	private Game game;

	private ArrayList<GameBoardPointListener> gameBoardPointListeners = new ArrayList<>();

	public Cell(Game game, int line, int column) {
		super(column, line);
		this.game = game;
	}

	public void addGameBoardPointListener(GameBoardPointListener gameBoardPointListener) {
		gameBoardPointListeners.add(gameBoardPointListener);
	}


}
