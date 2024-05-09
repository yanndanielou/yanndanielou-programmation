package tetris.game_objects.tetrominoes_types;

import tetris.game.Game;
import tetris.gameboard.MatrixCell;

public class TetroMinoFactory {

	public static Tetromino createTetromino(TetrominoType tetrominoType, Game game,
			MatrixCell upperLeftCornerOfNewTetriminoCenteredOnGameBoard) {
		switch (tetrominoType) {
		case I_STRAIGHT_LONG_STICK:
			return new TetrominoIStraightLongStick(tetrominoType, game, upperLeftCornerOfNewTetriminoCenteredOnGameBoard);
		case O_SQUARE:
			return new TetrominoOSquare(tetrominoType, game, upperLeftCornerOfNewTetriminoCenteredOnGameBoard);
		default:
			return null;
		}
	}

}
