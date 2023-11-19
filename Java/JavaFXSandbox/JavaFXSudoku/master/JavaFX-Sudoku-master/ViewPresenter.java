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
	private static final Logger LOGGER = LogManager.getLogger(ViewPresenter.class);

	private SudokuMain sudokuMain;

	public ViewPresenter(SudokuMain sudokuMain) {
		this.sudokuMain = sudokuMain;
	}

	public void onMouseExitedBoardButtonsByIntegerMap(int pos) {
		LOGGER.info(() -> "onMouseExitedBoardButtonsByIntegerMap:" + pos);
		if (!sudokuMain.sudoku.checkBoard(sudokuMain.boardList) && sudokuMain.value != 0) {
			sudokuMain.changeHorizontalIds(new String[] { "preset", "", "zero", "number" }, pos / 9);
			sudokuMain.changeVerticalIds(new String[] { "preset", "", "zero", "number" }, pos % 9);
			sudokuMain.scene.setCursor(Cursor.DEFAULT);
		}
	}

	public void onClearButtonAction() {
		LOGGER.info(() -> "onClearButtonAction");
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
		LOGGER.info(() -> "onNewGameButtonAction " + value);

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

	public void onMouseEnteredToNumberSelection() {
		LOGGER.info(() -> "onMouseEnteredToNumberSelection");
		sudokuMain.scene.setCursor(Cursor.HAND);
	}

	public void onMouseExitedToNumberSelection() {
		LOGGER.info(() -> "onMouseExitedToNumberSelection");
		sudokuMain.scene.setCursor(Cursor.DEFAULT);
	}

	public void onGenericDigitSelectionButtonActivated(Button genericDigitSelectionButton, int lo) {
		LOGGER.info(() -> "onGenericDigitSelectionButtonActivated");
		if (sudokuMain.value == Integer.valueOf(genericDigitSelectionButton.getText())) {
			if (sudokuMain.getNum(sudokuMain.value) < 9) {
				sudokuMain.numGridPane.numButtonsMap.get(sudokuMain.value - 1).setId("");
			}

			for (int k = 0; k < 81; k++) {
				if ((sudokuMain.boardButtonsByIntegerMap.get(k).getText()).equals(String.valueOf(sudokuMain.value))) {
					if (sudokuMain.untouchedList.get(k) != 0) {
						sudokuMain.boardButtonsByIntegerMap.get(k).setId("preset");
					} else if (sudokuMain.boardList.get(k) != 0) {
						sudokuMain.boardButtonsByIntegerMap.get(k).setId("");
					}
				}
			}

			sudokuMain.value = 0;
		} else {
			if (sudokuMain.value != 0 && sudokuMain.getNum(sudokuMain.value) < 9) {
				sudokuMain.numGridPane.numButtonsMap.get(sudokuMain.value - 1).setId("");
			}

			sudokuMain.value = lo;
			sudokuMain.numGridPane.numButtonsMap.get(sudokuMain.value - 1).setId("legend");

			for (int k = 0; k < 81; k++) {
				if ((sudokuMain.boardButtonsByIntegerMap.get(k).getText()).equals(String.valueOf(sudokuMain.value))) {
					sudokuMain.boardButtonsByIntegerMap.get(k).setId("number");
				} else {
					if (sudokuMain.untouchedList.get(k) != 0) {
						sudokuMain.boardButtonsByIntegerMap.get(k).setId("preset");
					} else if (sudokuMain.boardList.get(k) != 0) {
						sudokuMain.boardButtonsByIntegerMap.get(k).setId("");
					}
				}
			}
		}

		if (sudokuMain.getNum(sudokuMain.value) >= 9 && sudokuMain.value != 0) {
			sudokuMain.numGridPane.numButtonsMap.get(sudokuMain.value - 1).setId("legendFull");
		}

	}

}
