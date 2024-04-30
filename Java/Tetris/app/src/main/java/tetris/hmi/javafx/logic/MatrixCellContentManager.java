package tetris.hmi.javafx.logic;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import tetris.game_objects.Mino;
import tetris.game_objects.tetrominoes_types.TetrominoOSquare;
import tetris.game_objects.tetrominoes_types.TetrominoT;
import tetris.game_objects.tetrominoes_types.Tetromino;
import tetris.gameboard.MatrixCell;

public class MatrixCellContentManager {

	public static Background getMatrixCellBackground(MatrixCell matrixCell) {
		Mino mino = matrixCell.getMino();
		if (mino == null) {
			return new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
		} else {
			Tetromino parent = mino.getTetromino();

			if (parent instanceof TetrominoOSquare) {
				return new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY));
			} else if (parent instanceof TetrominoT) {
				return new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY));
			}

			return new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY));
		}
	}
}
