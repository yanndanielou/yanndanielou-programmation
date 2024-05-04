package sudoku;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SudokuSquareBoxAsGridPane extends GridPane {
	SudokuApplication sudokuApplication;
	public SudokuSquareBoxAsGridPane(SudokuApplication sudokuApplication, Game game, ViewPresenter viewPresenter,
			Stage stage) {
		this.sudokuApplication = sudokuApplication;
	}


}
