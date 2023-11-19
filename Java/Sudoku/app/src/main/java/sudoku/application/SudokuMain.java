
package sudoku.application;

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
import sudoku.Sudoku;
import sudoku.hmi.NumGridPane;
import sudoku.hmi.SudokuMainMenuBar;
import sudoku.hmi.SudokuTimeline;
import sudoku.hmi.ViewPresenter;

public class SudokuMain extends Application {
	private static final Logger LOGGER = LogManager.getLogger(SudokuMain.class);

	public int value = 0;
	public long countUp = 0;

	BorderPane rootBorderPane;
	BorderPane mainViewBorderPane;
	public Scene scene;
	GridPane tableGridPane;
	public Sudoku sudoku;
	SudokuMainMenuBar menuBar;

	ViewPresenter viewPresenter;

	public ArrayList<Integer> boardList;
	public ArrayList<Integer> untouchedList;
	public Map<Integer, Button> boardButtonsByIntegerMap;
	Map<Integer, GridPane> gridPaneByIntegerMap;

	Image applicationIcon;
	HBox topHbox;
	Button clearButton;
	Button newGameButton;
	public NumGridPane numGridPane;

	Date start;
	public SudokuTimeline timeline;

	public Stage stage;

	/**
	 * Changes the CSS ids of the horizontal line
	 * 
	 * @param array an Array of CSS ids
	 * @param start the horizontal line number
	 */
	public void changeHorizontalIds(String[] array, int start) {
		for (int i = start * 9; i < start * 9 + 9; i++) {
			changeIdsHelper(array, i);
		}
	}

	/**
	 * Changes the CSS ids of the vertical line
	 * 
	 * @param array an Array of CSS ids
	 * @param start the vertical line number
	 */
	public void changeVerticalIds(String[] array, int start) {
		for (int i = start; i < start + 9 * 9; i += 9) {
			changeIdsHelper(array, i);
		}
	}

	/**
	 * Changes the CSS ids of a specific Sudoku board element according to its
	 * original state
	 * 
	 * @param array an Array of CSS ids
	 * @param i     the location of the button in the Sudoku board
	 */
	private void changeIdsHelper(String[] array, int i) {
		if (!(boardButtonsByIntegerMap.get(i).getText()).equals(String.valueOf(value)) || value == 0) {
			if (untouchedList.get(i) != 0) {
				boardButtonsByIntegerMap.get(i).setId(array[0]);
			} else if (boardList.get(i) != 0) {
				boardButtonsByIntegerMap.get(i).setId(array[1]);
			} else {
				boardButtonsByIntegerMap.get(i).setId(array[2]);
			}
		} else {
			boardButtonsByIntegerMap.get(i).setId(array[3]);
		}
	}

	/**
	 * Resets the game
	 */
	public void reset() {
		// Removes every buttons (GridPane) inside the main GridPane
		for (int i = 0; i < 9; i++) {
			tableGridPane.getChildren().remove(gridPaneByIntegerMap.get(i));
		}

		// Creates a new Sudoku board for the player
		sudoku.clear();
		sudoku.generateBoard();
		sudoku.generatePlayer();

		// Print out the solution
		System.out.println(sudoku.toString());

		// Get player's board
		boardList = sudoku.getPlayer();

		// List and maps of Buttons, GridPanes and value of the board
		untouchedList = new ArrayList<Integer>(boardList);
		boardButtonsByIntegerMap = new HashMap<Integer, Button>();
		gridPaneByIntegerMap = new HashMap<Integer, GridPane>();
	}

	/**
	 * Returns the number of elements of the specified number
	 * 
	 * @param num the number researched
	 * @return a number of elements equal to the parameter
	 */
	public int getNum(int num) {
		int count = 0;
		for (int p = 0; p < 81; p++) {
			if (Integer.valueOf(boardButtonsByIntegerMap.get(p).getText()) == num) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Generates the board, in terms of GUI
	 */
	public void generateBoard() {
		// Each block
		for (int i = 0; i < 9; i++) {

			gridPaneByIntegerMap.put(i, new GridPane());

			int t = i % 3 * 3 + (i / 3) * 27;
			int temp = 0;

			// Each element in that block
			for (int j = t; j < t + 20; j += 9, temp++) {

				// Each row of the block
				for (int k = 0; k < 3; k++) {

					// Index of current element
					final int pos = j + k;

					// New Button
					boardButtonsByIntegerMap.put(pos, new Button());

					if (boardList.get(pos) == 0) {
						boardButtonsByIntegerMap.get(pos).setId("zero");

						boardButtonsByIntegerMap.get(pos).setOnAction(e -> {
							if (value != 0) {
								if (boardButtonsByIntegerMap.get(pos).getText().equals(String.valueOf(value))) {
									boardButtonsByIntegerMap.get(pos).setText("0");
									boardList.set(pos, 0);
									boardButtonsByIntegerMap.get(pos).setId("helperZero");
								} else {
									boardButtonsByIntegerMap.get(pos).setText(String.valueOf(value));
									boardButtonsByIntegerMap.get(pos).setId("");
									boardList.set(pos, value);
								}

								for (int l = 0; l < 81; l++) {
									if (!boardButtonsByIntegerMap.get(l).getId().equals("number")
											&& boardButtonsByIntegerMap.get(l).getText()
													.equals(String.valueOf(value))) {
										boardButtonsByIntegerMap.get(l).setId("number");
									}
								}

								setLegend();
							}

							// Checks if the game is done
							if (sudoku.checkBoard(boardList)) {
								timeline.stop();

								Alert alert = new Alert(AlertType.NONE,
										"You just completed the sudoku board in " + countUp / 1000
												+ " seconds. Do you want to play again?",
										ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
								alert.showAndWait();

								if (alert.getResult() == ButtonType.YES) {
									newGameButton.fire();
								} else if (alert.getResult() == ButtonType.NO) {
									stage.close();
								} else if (alert.getResult() == ButtonType.CANCEL) {
									clearButton.fire();
								}
							}
						});

					} else {
						boardButtonsByIntegerMap.get(pos).setId("preset");
					}

					boardButtonsByIntegerMap.get(pos).setOnMouseEntered(e -> {
						LOGGER.info(() -> "boardTextMap " + pos + " setOnMouseEntered");
						if (!sudoku.checkBoard(boardList) && value != 0) {
							changeHorizontalIds(new String[] { "helper", "helper", "helperZero", "numberHelper" },
									pos / 9);
							changeVerticalIds(new String[] { "helper", "helper", "helperZero", "numberHelper" },
									pos % 9);
							if (boardList.get(pos) == 0) {
								scene.setCursor(Cursor.HAND);
							}
						}
					});

					boardButtonsByIntegerMap.get(pos).setOnMouseExited(e -> {
						viewPresenter.onMouseExitedBoardButtonsByIntegerMap(pos);
					});

					boardButtonsByIntegerMap.get(pos).setText(String.valueOf(boardList.get(pos)));
					gridPaneByIntegerMap.get(i).add(boardButtonsByIntegerMap.get(pos), k, temp);
				}
			}
			tableGridPane.add(gridPaneByIntegerMap.get(i), i % 3, i / 3);
		}
	}

	/**
	 * Sets up the legend state by checking if any of the number has nine or more
	 * appearance in the player's Sudoku board
	 */
	public void setLegend() {
		for (int i = 1; i < 10; i++) {
			Button button = numGridPane.numButtonsMap.get(i - 1);
			if (getNum(i) >= 9) {
				if (!button.getId().equals("legendFull")) {
					button.setId("legendFull");
				}
			} else if (i != value) {
				button.setId("");
			} else {
				button.setId("legend");
			}
		}
	}

	/**
	 * Counts the time elapsed in seconds from the start of the game, from 0 to
	 * infinite and display that number in the title bard
	 */
	public void startTimer() {
		start = Calendar.getInstance().getTime();
		timeline = new SudokuTimeline(this, start, stage);
	}

	@Override
	public void start(Stage primaryStage) {
		// Creates a reference to the primaryStage to be
		// able to manipulate it in other methods
		stage = primaryStage;

		// Clear button
		clearButton = new Button("Clear");
		clearButton.setOnAction(

				e -> {
					viewPresenter.onClearButtonAction();
				});

		// New game button
		newGameButton = new Button("New Game");
		newGameButton.setOnAction(e -> {
			viewPresenter.onNewGameButtonAction(value);

		});

		// Starts the timer
		startTimer();

		// Layout of the board
		tableGridPane = new GridPane();
		tableGridPane.setVgap(8);
		tableGridPane.setHgap(8);
		tableGridPane.setAlignment(Pos.CENTER);

		// Layout of the nine numbers at the bottom (legend)
		numGridPane = new NumGridPane();
		numGridPane.setHgap(2);
		numGridPane.setPadding(new Insets(0, 0, 16, 0));
		numGridPane.setAlignment(Pos.CENTER);

		viewPresenter = new ViewPresenter(this);

		// Layout of the top two buttons
		topHbox = new HBox();
		topHbox.setSpacing(10);
		topHbox.setPadding(new Insets(16, 0, 0, 0));
		topHbox.setAlignment(Pos.CENTER);
		topHbox.getChildren().addAll(newGameButton, clearButton);

		menuBar = new SudokuMainMenuBar();

		mainViewBorderPane = new BorderPane();
		mainViewBorderPane.setTop(menuBar);
		mainViewBorderPane.setCenter(topHbox);

		// Main layout of the Game
		rootBorderPane = new BorderPane();
		rootBorderPane.setTop(mainViewBorderPane);
		rootBorderPane.setCenter(tableGridPane);
		rootBorderPane.setBottom(numGridPane);

		// Generates the Sudoku board
		sudoku = new Sudoku();
		sudoku.generateBoard();
		sudoku.generatePlayer();

		// Prints out the solution
		System.out.println(sudoku.toString());

		// Application icon
		applicationIcon = new Image("file:resources/applicationIcon.png");
		primaryStage.getIcons().add(applicationIcon);

		// Get player's board
		boardList = sudoku.getPlayer();

		// List and maps of buttons, GridPanes and value of the board
		untouchedList = new ArrayList<Integer>(boardList);
		boardButtonsByIntegerMap = new HashMap<Integer, Button>();
		gridPaneByIntegerMap = new HashMap<Integer, GridPane>();
		numGridPane.numButtonsMap = new HashMap<Integer, Button>();

		// Generates the GUI for the board
		generateBoard();

		// Sets up the legend (nine numbers at the bottom)
		for (int i = 0; i < 9; i++) {
			numGridPane.numButtonsMap.put(i, new Button());
			Button selectDigitSelectionButton = numGridPane.numButtonsMap.get(i);
			selectDigitSelectionButton.setText(String.valueOf(i + 1));
			numGridPane.add(selectDigitSelectionButton, i, 0);

			final int lo = i + 1;

			selectDigitSelectionButton.setOnAction(

					e -> {

						// viewPresenter.onNumButtonActioned();

						Button genericDigitSelectionButton = numGridPane.numButtonsMap.get(lo - 1);
						viewPresenter.onGenericDigitSelectionButtonActivated(genericDigitSelectionButton, lo);
					});

			selectDigitSelectionButton.setOnMouseEntered(e -> {
				viewPresenter.onMouseEnteredToNumberSelection();
			});

			selectDigitSelectionButton.setOnMouseExited(e -> {
				viewPresenter.onMouseExitedToNumberSelection();

			});
		}

		// Sets up the state of the legend (nine numbers at the bottom) according to the
		// player's Sudoku board
		setLegend();

		// Sets the scene to the BorderPane layout and links the CSS file
		scene = new Scene(rootBorderPane, 350, 450);
		scene.getStylesheets().add("file:resources/application.css");

		// Sets the stage, sets its title, displays it, and restricts its minimal size
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sudoku - Time: 0");
		primaryStage.show();
		primaryStage.setMinHeight(primaryStage.getHeight());
		primaryStage.setMinWidth(primaryStage.getWidth());
	}

	/**
	 * Main method for the Sudoku game
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
