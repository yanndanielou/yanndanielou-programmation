package sudoku;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TopHBox extends HBox {

	public Button clearButton, newGameButton;

	public TopHBox(SudokuApplication sudokuApplication) {
		// Clear button
		clearButton = new Button("Clear");
		clearButton.setOnAction(e -> {
			sudokuApplication.board = new ArrayList<Integer>(sudokuApplication.untouchedCells);
			for (int i = 0; i < 81; i++) {
				if (sudokuApplication.board.get(i) != Integer.valueOf(sudokuApplication.boardText.get(i).getText())) {
					sudokuApplication.boardText.get(i).setText(String.valueOf(sudokuApplication.board.get(i)));
					sudokuApplication.boardText.get(i).setId("zero");
				}
			}

			sudokuApplication.digitsBottomGridPane.setLegend();
		});

		// New game button
		newGameButton = new Button("New Game");
		newGameButton.setOnAction(e -> {
			if (sudokuApplication.currentlySelectedDigit != 0) {
				sudokuApplication.digitsBottomGridPane.numButtons.get(sudokuApplication.currentlySelectedDigit - 1).setId("");
				sudokuApplication.currentlySelectedDigit = 0;
			}
			sudokuApplication.timeline.stop();
			sudokuApplication.stage.setTitle("Sudoku - Time: 0");
			sudokuApplication.reset();
			sudokuApplication.generateBoard();
			sudokuApplication.startTimer();
			sudokuApplication.digitsBottomGridPane.setLegend();
		});

		setSpacing(10);
		setPadding(new Insets(16, 0, 0, 0));
		setAlignment(Pos.CENTER);
		getChildren().addAll(newGameButton, clearButton);
	}

}
