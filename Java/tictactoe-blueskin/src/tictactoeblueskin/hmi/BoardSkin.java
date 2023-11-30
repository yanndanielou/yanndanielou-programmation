package tictactoeblueskin.hmi;

import javafx.scene.layout.GridPane;
import tictactoeblueskin.game.Board;


public class BoardSkin extends GridPane {
	public BoardSkin(Board board) {
		getStyleClass().add("board");

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				add(board.getSquare(i, j).getSkin(), i, j);
			}
		}
	}
}
