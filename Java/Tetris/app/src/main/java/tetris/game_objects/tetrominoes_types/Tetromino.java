package tetris.game_objects.tetrominoes_types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.exceptions.BadLogicException;
import geometry2d.integergeometry.IntegerPrecisionPoint;
import tetris.game.Game;
import tetris.game_objects.Mino;
import tetris.game_objects.patterns.Pattern;
import tetris.gameboard.MatrixCell;

public abstract class Tetromino {

	private static final Logger LOGGER = LogManager.getLogger(Tetromino.class);

	protected TetrominoRotationDirection direction;
	protected List<Mino> minos = new ArrayList<>();
	private boolean locked = false;
	private TetrominoType tetriminoType;
	private Game game;

	@Deprecated
	public Tetromino() {
		// TODO Auto-generated constructor stub
	}

	public Tetromino(TetrominoType tetriminoType, Game game,
			MatrixCell upperLeftCornerOfNewTetriminoCenteredOnGameBoard) {
		this.tetriminoType = tetriminoType;
		this.game = game;
		for (IntegerPrecisionPoint minoCellCoordinate : getPattern().getMinoCellsCoordinates()) {
			MatrixCell matrixCell = game.getGameBoard().getMatrixCellByXAndY(
					upperLeftCornerOfNewTetriminoCenteredOnGameBoard.getXAsInt() + minoCellCoordinate.getXAsInt(),
					upperLeftCornerOfNewTetriminoCenteredOnGameBoard.getYAsInt() + minoCellCoordinate.getYAsInt());
			Mino mino = new Mino(this, matrixCell);
			minos.add(mino);
		}
	}

	public void removeMino(Mino mino) {
		boolean removed = minos.remove(mino);
		if (!removed) {
			throw new BadLogicException("Could not remove " + mino + " in " + this);
		}
	}

	public Pattern getPattern() {
		return tetriminoType.getPattern();
	}

	public List<Mino> getMinos() {
		return minos;
	}

	public List<Mino> getMinosSortedFromBottomToTop() {
		List<Mino> allMinos = new ArrayList<Mino>(minos);
		Collections.sort(allMinos, new Comparator<Mino>() {
			@Override
			public int compare(Mino o1, Mino o2) {
				return o2.getLocationOnMatrix().getYAsInt() - o1.getLocationOnMatrix().getYAsInt();
			}
		});

		return allMinos;
	}

	public List<Mino> getMinosSortedFromRightToLeft() {
		List<Mino> allMinos = new ArrayList<Mino>(minos);
		Collections.sort(allMinos, new Comparator<Mino>() {
			@Override
			public int compare(Mino o1, Mino o2) {
				return o2.getLocationOnMatrix().getXAsInt() - o1.getLocationOnMatrix().getXAsInt();
			}
		});

		return allMinos;
	}

	public List<Mino> getMinosSortedFromLeftToRight() {
		List<Mino> allMinos = new ArrayList<Mino>(minos);
		Collections.sort(allMinos, new Comparator<Mino>() {
			@Override
			public int compare(Mino o1, Mino o2) {
				return o1.getLocationOnMatrix().getXAsInt() - o2.getLocationOnMatrix().getXAsInt();
			}
		});

		return allMinos;
	}

	public boolean isLocked() {
		return locked;
	}

	public void lock() {
		LOGGER.info(() -> "lock " + this);
		this.locked = true;
	}

	public TetrominoType getType() {
		return tetriminoType;
	}

}
