package tetris.gameboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.gameboard.GenericGameBoard;
import game.gameboard.GenericIntegerGameBoardPoint;
import tetris.builders.gameboard.GameBoardDataModel;
import tetris.game.Game;

public class Matrix extends GenericGameBoard {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Matrix.class);

	private GameBoardDataModel gameBoardDataModel;

	private Game game;

	protected int width;
	protected int height;

	public Matrix(GameBoardDataModel gameBoardDataModel) {
		this.gameBoardDataModel = gameBoardDataModel;
		afterConstructor();
	}

	public int getTotalWidth() {
		return (int) gameBoardDataModel.getGameBoardDimensions().getDimension().getWidth();
	}

	public int getTotalHeight() {
		return (int) gameBoardDataModel.getGameBoardDimensions().getDimension().getHeight();
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public MatrixCell getMatrixCellByXAndY(int x, int y) {
		return (MatrixCell) getGameBoardPointByXAndY(x, y);
	}

	@Override
	protected GenericIntegerGameBoardPoint createGameBoardPoint(int x, int y) {
		return new MatrixCell(game, x, y);
	}

}
