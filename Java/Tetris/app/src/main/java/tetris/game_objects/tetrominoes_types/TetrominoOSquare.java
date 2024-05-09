package tetris.game_objects.tetrominoes_types;

import tetris.game.Game;
import tetris.gameboard.MatrixCell;

public class TetrominoOSquare extends Tetromino {

	public TetrominoOSquare(TetrominoType tetriminoType, Game game, MatrixCell upperLeftCornerOfNewTetriminoCenteredOnGameBoard) {
		super(tetriminoType, game, upperLeftCornerOfNewTetriminoCenteredOnGameBoard);
	}

}
