package sudoku;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class TopHBox extends HBox {

	public Button clearButton, newGameButton;

	public TopHBox(SudokuApplication sudokuApplication) {
		// Clear button
		clearButton = new Button("Clear");
		clearButton.setOnAction(e -> {
			sudokuApplication.board = new ArrayList<Integer>(sudokuApplication.untouchedCells);
			for (int i = 0; i < 81; i++) {
				if (sudokuApplication.board.get(i) != Integer.valueOf(sudokuApplication.sudokuCellButton.get(i).getText())) {
					sudokuApplication.sudokuCellButton.get(i).setText(String.valueOf(sudokuApplication.board.get(i)));
					sudokuApplication.sudokuCellButton.get(i).setId("zero");
				}
			}

			sudokuApplication.digitsBottomGridPane.updateDigitSelectionInBottomButtonsState();
		});

		// New game button
		newGameButton = new Button("New Game");
		newGameButton.setOnAction(e -> {
			if (sudokuApplication.currentlySelectedDigit != 0) {
				sudokuApplication.digitsBottomGridPane.digitSelectionInBottomButton.get(sudokuApplication.currentlySelectedDigit - 1).setId("");
				sudokuApplication.currentlySelectedDigit = 0;
			}
			sudokuApplication.timeline.stop();
			sudokuApplication.stage.setTitle("Sudoku - Time: 0");
			sudokuApplication.reset();
			sudokuApplication.generateBoard();
			sudokuApplication.startTimer();
			sudokuApplication.digitsBottomGridPane.updateDigitSelectionInBottomButtonsState();
		});

		setSpacing(10);
		setPadding(new Insets(16, 0, 0, 0));
		setAlignment(Pos.CENTER);
		getChildren().addAll(newGameButton, clearButton);
	}

}
