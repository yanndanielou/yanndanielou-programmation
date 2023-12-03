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

public class DigitsBottomGridPane extends GridPane {
	public Map<Integer, Button> digitSelectionInBottomButton;
	SudokuApplication sudokuApplication;
	ViewPresenter viewPresenter;

	public DigitsBottomGridPane(SudokuApplication sudokuApplication, ViewPresenter viewPresenter2) {
		this.sudokuApplication = sudokuApplication;
		this.viewPresenter = viewPresenter2;
		setHgap(2);
		setPadding(new Insets(0, 0, 16, 0));
		setAlignment(Pos.CENTER);
	}

	/**
	 * Sets up the legend state by checking if any of the number has nine or more
	 * appearance in the player's Sudoku board
	 */
	public void updateDigitSelectionInBottomButtonsState() {
		for (int i = 1; i < 10; i++) {
			if (sudokuApplication.getNumberOfOccurenceInBoardOfNum(i) >= 9) {
				if (!digitSelectionInBottomButton.get(i - 1).getId().equals("legendFull")) {
					digitSelectionInBottomButton.get(i - 1).setId("legendFull");
				}
			} else if (i != viewPresenter.currentlySelectedDigit) {
				digitSelectionInBottomButton.get(i - 1).setId("");
			} else {
				digitSelectionInBottomButton.get(i - 1).setId("legend");
			}
		}
	}

}
