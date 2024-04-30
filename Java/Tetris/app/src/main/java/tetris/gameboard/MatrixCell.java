package tetris.gameboard;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.gameboard.GenericIntegerGameBoardPoint;
import tetris.game.CellListener;
import tetris.game.Game;
import tetris.game_objects.Mino;

public class MatrixCell extends GenericIntegerGameBoardPoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7353270231660749618L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MatrixCell.class);

	private ArrayList<CellListener> gameBoardPointListeners = new ArrayList<>();

	private Mino mino;

	public MatrixCell(Game game, int x, int y) {
		super(x, y);
	}

	public void addGameBoardPointListener(CellListener gameBoardPointListener) {
		gameBoardPointListeners.add(gameBoardPointListener);
	}

	public Mino getMino() {
		return mino;
	}

	public void setMino(Mino mino) {
		this.mino = mino;
		
		gameBoardPointListeners.forEach((gameStatusListener) -> gameStatusListener.onCellContentChanged(this, mino));
	}

	public void setVoid() {
		setMino(null);
	}

}
