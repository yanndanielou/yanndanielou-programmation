package tetris.game_objects.tetrominoes_types;

import java.util.ArrayList;
import java.util.List;

import geometry2d.integergeometry.IntegerPrecisionPoint;
import tetris.game.Game;
import tetris.game_objects.Mino;
import tetris.game_objects.patterns.Pattern;
import tetris.gameboard.MatrixCell;

public abstract class Tetromino {

	protected TetrominoRotationDirection direction;
	protected List<Mino> minos = new ArrayList<>();
	protected Pattern pattern;
	protected boolean locked = false;
	private TetrominoType tetriminoType;
	private Game game;
	
	@Deprecated
	public Tetromino() {
		// TODO Auto-generated constructor stub
	}

	public Tetromino(TetrominoType tetriminoType, Game game, MatrixCell upperLeftCornerOfNewTetriminoCenteredOnGameBoard, Pattern pattern) {
		this.tetriminoType = tetriminoType;
		this.game = game;
		for (IntegerPrecisionPoint minoCellCoordinate : pattern.getMinoCellsCoordinates()) {
			MatrixCell matrixCell = game.getGameBoard().getMatrixCellByXAndY(
					upperLeftCornerOfNewTetriminoCenteredOnGameBoard.getXAsInt() + minoCellCoordinate.getXAsInt(),
					upperLeftCornerOfNewTetriminoCenteredOnGameBoard.getYAsInt() + minoCellCoordinate.getYAsInt());
			Mino mino = new Mino(matrixCell);
			minos.add(mino);
		}
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public Pattern getPattern() {
		return pattern;
	}

}
