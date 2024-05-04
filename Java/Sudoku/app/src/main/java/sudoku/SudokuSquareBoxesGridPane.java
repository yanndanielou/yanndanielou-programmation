package sudoku;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SudokuSquareBoxesGridPane extends GridPane {
	SudokuApplication sudokuApplication;
	public SudokuSquareBoxesGridPane(SudokuApplication sudokuApplication, Game game, ViewPresenter viewPresenter,
			Stage stage) {
		setVgap(10);
		setHgap(10);
		setAlignment(Pos.CENTER);
		this.sudokuApplication = sudokuApplication;
	}

	public void reset() {
		// Removes every buttons (GridPane) inside the main GridPane
		for (int i = 0; i < 9; i++) {
			getChildren().remove(sudokuApplication.grid.get(i));

		}

	}

}
