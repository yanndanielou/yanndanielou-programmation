package tetris.gameboard;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.gameboard.GenericIntegerGameBoardPoint;
import tetris.game.CellListener;
import tetris.game.Game;

public class MatrixCell extends GenericIntegerGameBoardPoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7353270231660749618L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MatrixCell.class);

	private ArrayList<CellListener> gameBoardPointListeners = new ArrayList<>();

	public MatrixCell(Game game, int x, int y) {
		super(x, y);
	}

	public void addGameBoardPointListener(CellListener gameBoardPointListener) {
		gameBoardPointListeners.add(gameBoardPointListener);
	}



}
