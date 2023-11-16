package application;


import javafx.scene.layout.GridPane;


class BoardSkin extends GridPane {
	BoardSkin(Board board) {
		getStyleClass().add("board");

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				add(board.getSquare(i, j).getSkin(), i, j);
			}
		}
	}
}
