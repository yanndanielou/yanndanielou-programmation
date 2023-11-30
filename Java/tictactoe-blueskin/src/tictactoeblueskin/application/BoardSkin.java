package tictactoeblueskin.application;

import tictactoeblueskin.game.Board;

import javafx.scene.layout.GridPane;


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
