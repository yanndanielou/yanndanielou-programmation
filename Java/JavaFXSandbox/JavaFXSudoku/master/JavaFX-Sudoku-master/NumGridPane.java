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

public class NumGridPane extends GridPane {

	private Map<Integer, Button> numButtonsMap = new HashMap<Integer, Button>();


	/**
	 * Sets up the legend state by checking if any of the number has nine or more
	 * appearance in the player's Sudoku board
	 */
	public void setLegend() {
		for (int i = 1; i < 10; i++) {
			if (getNum(i) >= 9) {
				if (!numButtonsMap.get(i - 1).getId().equals("legendFull")) {
					numButtonsMap.get(i - 1).setId("legendFull");
				}
			} else if (i != value) {
				numButtonsMap.get(i - 1).setId("");
			} else {
				numButtonsMap.get(i - 1).setId("legend");
			}
		}
	}

}
