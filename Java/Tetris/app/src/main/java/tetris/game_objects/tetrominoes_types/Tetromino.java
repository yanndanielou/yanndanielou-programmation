package tetris.game_objects.tetrominoes_types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import geometry2d.integergeometry.IntegerPrecisionPoint;
import tetris.game.Game;
import tetris.game_objects.Mino;
import tetris.game_objects.patterns.Pattern;
import tetris.gameboard.MatrixCell;

public abstract class Tetromino {

	private class SortFromBottomToTop implements Comparator<Mino> {

		@Override
		public int compare(Mino o1, Mino o2) {
			return o2.getLocationOnMatrix().getYAsInt() - o1.getLocationOnMatrix().getYAsInt();
		}
	}

	protected TetrominoRotationDirection direction;
	protected List<Mino> minos = new ArrayList<>();
	protected Pattern pattern;
	private boolean locked = false;
	private TetrominoType tetriminoType;
	private Game game;

	@Deprecated
	public Tetromino() {
		// TODO Auto-generated constructor stub
	}

	public Tetromino(TetrominoType tetriminoType, Game game,
			MatrixCell upperLeftCornerOfNewTetriminoCenteredOnGameBoard, Pattern pattern) {
		this.tetriminoType = tetriminoType;
		this.game = game;
		for (IntegerPrecisionPoint minoCellCoordinate : pattern.getMinoCellsCoordinates()) {
			MatrixCell matrixCell = game.getGameBoard().getMatrixCellByXAndY(
					upperLeftCornerOfNewTetriminoCenteredOnGameBoard.getXAsInt() + minoCellCoordinate.getXAsInt(),
					upperLeftCornerOfNewTetriminoCenteredOnGameBoard.getYAsInt() + minoCellCoordinate.getYAsInt());
			Mino mino = new Mino(this, matrixCell);
			minos.add(mino);
		}
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public List<Mino> getMinos() {
		return minos;
	}

	public List<Mino> getMinosSortedFromBottomToTop() {
		List<Mino> allMinos = new ArrayList<Mino>(minos);
		Collections.sort(allMinos, new SortFromBottomToTop());
		return allMinos;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}
