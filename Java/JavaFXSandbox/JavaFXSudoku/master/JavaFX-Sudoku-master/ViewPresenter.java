import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

public class ViewPresenter {
	private SudokuMain sudokuMain;

	public ViewPresenter(SudokuMain sudokuMain) {
		this.sudokuMain = sudokuMain;
	}

	public void setOnMouseExitedBoardButtonsByIntegerMap(int pos) {
		if (!sudokuMain.sudoku.checkBoard(sudokuMain.boardList) && sudokuMain.value != 0) {
			sudokuMain.changeHorizontalIds(new String[] { "preset", "", "zero", "number" }, pos / 9);
			sudokuMain.changeVerticalIds(new String[] { "preset", "", "zero", "number" }, pos % 9);
			sudokuMain.scene.setCursor(Cursor.DEFAULT);
		}
	}

	public void onClearButtonAction() {
		sudokuMain.boardList = new ArrayList<Integer>(sudokuMain.untouchedList);
		for (int i = 0; i < 81; i++) {
			if (sudokuMain.boardList.get(i) != Integer.valueOf(sudokuMain.boardButtonsByIntegerMap.get(i).getText())) {
				sudokuMain.boardButtonsByIntegerMap.get(i).setText(String.valueOf(sudokuMain.boardList.get(i)));
				sudokuMain.boardButtonsByIntegerMap.get(i).setId("zero");
			}
		}

		sudokuMain.setLegend();
	}

	public void onNewGameButtonAction(int value) {

		if (value != 0) {
			sudokuMain.numGridPane.numButtonsMap.get(value - 1).setId("");
			sudokuMain.value = 0;
		}
		sudokuMain.timeline.stop();
		sudokuMain.stage.setTitle("Sudoku - Time: 0");
		sudokuMain.reset();
		sudokuMain.generateBoard();
		sudokuMain.startTimer();
		sudokuMain.setLegend();
	}

}
