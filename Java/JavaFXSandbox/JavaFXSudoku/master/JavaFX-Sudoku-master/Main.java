import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

public class Main extends Application {

	private int value = 0;
	long countUp = 0;

	private BorderPane rootBorderPane;
	private BorderPane mainViewBorderPane;
	private Scene scene;
	private GridPane tableGridPane;
	private Sudoku sudoku;
	private SudokuMainMenuBar menuBar;

	private ArrayList<Integer> boardList, untouchedList;
	private Map<Integer, Button> boardTextMap;
	private Map<Integer, GridPane> gridMap;

	private Image applicationIcon;
	private HBox topHbox;
	private Button clearButton, newGameButton;
	private NumGridPane numGridPane;

	private Date start;
	private SudokuTimeline timeline;

	private Stage stage;

	/**
	 * Changes the CSS ids of the horizontal line
	 * 
	 * @param array an Array of CSS ids
	 * @param start the horizontal line number
	 */
	private void changeHorizontalIds(String[] array, int start) {
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
	private void changeVerticalIds(String[] array, int start) {
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
		if (!(boardTextMap.get(i).getText()).equals(String.valueOf(value)) || value == 0) {
			if (untouchedList.get(i) != 0) {
				boardTextMap.get(i).setId(array[0]);
			} else if (boardList.get(i) != 0) {
				boardTextMap.get(i).setId(array[1]);
			} else {
				boardTextMap.get(i).setId(array[2]);
			}
		} else {
			boardTextMap.get(i).setId(array[3]);
		}
	}

	/**
	 * Resets the game
	 */
	private void reset() {
		// Removes every buttons (GridPane) inside the main GridPane
		for (int i = 0; i < 9; i++) {
			tableGridPane.getChildren().remove(gridMap.get(i));
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
		boardTextMap = new HashMap<Integer, Button>();
		gridMap = new HashMap<Integer, GridPane>();
	}

	/**
	 * Returns the number of elements of the specified number
	 * 
	 * @param num the number researched
	 * @return a number of elements equal to the parameter
	 */
	private int getNum(int num) {
		int count = 0;
		for (int p = 0; p < 81; p++) {
			if (Integer.valueOf(boardTextMap.get(p).getText()) == num) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Generates the board, in terms of GUI
	 */
	private void generateBoard() {
		// Each block
		for (int i = 0; i < 9; i++) {

			gridMap.put(i, new GridPane());

			int t = i % 3 * 3 + (i / 3) * 27;
			int temp = 0;

			// Each element in that block
			for (int j = t; j < t + 20; j += 9, temp++) {

				// Each row of the block
				for (int k = 0; k < 3; k++) {

					// Index of current element
					final int pos = j + k;

					// New Button
					boardTextMap.put(pos, new Button());

					if (boardList.get(pos) == 0) {
						boardTextMap.get(pos).setId("zero");

						boardTextMap.get(pos).setOnAction(e -> {
							if (value != 0) {
								if (boardTextMap.get(pos).getText().equals(String.valueOf(value))) {
									boardTextMap.get(pos).setText("0");
									boardList.set(pos, 0);
									boardTextMap.get(pos).setId("helperZero");
								} else {
									boardTextMap.get(pos).setText(String.valueOf(value));
									boardTextMap.get(pos).setId("");
									boardList.set(pos, value);
								}

								for (int l = 0; l < 81; l++) {
									if (!boardTextMap.get(l).getId().equals("number")
											&& boardTextMap.get(l).getText().equals(String.valueOf(value))) {
										boardTextMap.get(l).setId("number");
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
						boardTextMap.get(pos).setId("preset");
					}

					boardTextMap.get(pos).setOnMouseEntered(e -> {
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

					boardTextMap.get(pos).setOnMouseExited(e -> {
						if (!sudoku.checkBoard(boardList) && value != 0) {
							changeHorizontalIds(new String[] { "preset", "", "zero", "number" }, pos / 9);
							changeVerticalIds(new String[] { "preset", "", "zero", "number" }, pos % 9);
							scene.setCursor(Cursor.DEFAULT);
						}
					});

					boardTextMap.get(pos).setText(String.valueOf(boardList.get(pos)));
					gridMap.get(i).add(boardTextMap.get(pos), k, temp);
				}
			}
			tableGridPane.add(gridMap.get(i), i % 3, i / 3);
		}
	}

	/**
	 * Sets up the legend state by checking if any of the number has nine or more
	 * appearance in the player's Sudoku board
	 */
	private void setLegend() {
		for (int i = 1; i < 10; i++) {
			if (getNum(i) >= 9) {
				if (!numGridPane.numButtonsMap.get(i - 1).getId().equals("legendFull")) {
					numGridPane.numButtonsMap.get(i - 1).setId("legendFull");
				}
			} else if (i != value) {
				numGridPane.numButtonsMap.get(i - 1).setId("");
			} else {
				numGridPane.numButtonsMap.get(i - 1).setId("legend");
			}
		}
	}

	/**
	 * Counts the time elapsed in seconds from the start of the game, from 0 to
	 * infinite and display that number in the title bard
	 */
	private void startTimer() {
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
		clearButton.setOnAction(e -> {
			boardList = new ArrayList<Integer>(untouchedList);
			for (int i = 0; i < 81; i++) {
				if (boardList.get(i) != Integer.valueOf(boardTextMap.get(i).getText())) {
					boardTextMap.get(i).setText(String.valueOf(boardList.get(i)));
					boardTextMap.get(i).setId("zero");
				}
			}

			setLegend();
		});

		// New game button
		newGameButton = new Button("New Game");
		newGameButton.setOnAction(e -> {
			if (value != 0) {
				numGridPane.numButtonsMap.get(value - 1).setId("");
				value = 0;
			}
			timeline.stop();
			stage.setTitle("Sudoku - Time: 0");
			reset();
			generateBoard();
			startTimer();
			setLegend();
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
		boardTextMap = new HashMap<Integer, Button>();
		gridMap = new HashMap<Integer, GridPane>();
		numGridPane.numButtonsMap = new HashMap<Integer, Button>();

		// Generates the GUI for the board
		generateBoard();

		// Sets up the legend (nine numbers at the bottom)
		for (int i = 0; i < 9; i++) {
			numGridPane.numButtonsMap.put(i, new Button());
			numGridPane.numButtonsMap.get(i).setText(String.valueOf(i + 1));
			numGridPane.add(numGridPane.numButtonsMap.get(i), i, 0);

			final int lo = i + 1;

			numGridPane.numButtonsMap.get(i).setOnAction(e -> {

				if (value == Integer.valueOf(numGridPane.numButtonsMap.get(lo - 1).getText())) {
					if (getNum(value) < 9) {
						numGridPane.numButtonsMap.get(value - 1).setId("");
					}

					for (int k = 0; k < 81; k++) {
						if ((boardTextMap.get(k).getText()).equals(String.valueOf(value))) {
							if (untouchedList.get(k) != 0) {
								boardTextMap.get(k).setId("preset");
							} else if (boardList.get(k) != 0) {
								boardTextMap.get(k).setId("");
							}
						}
					}

					value = 0;
				} else {
					if (value != 0 && getNum(value) < 9) {
						numGridPane.numButtonsMap.get(value - 1).setId("");
					}

					value = lo;
					numGridPane.numButtonsMap.get(value - 1).setId("legend");

					for (int k = 0; k < 81; k++) {
						if ((boardTextMap.get(k).getText()).equals(String.valueOf(value))) {
							boardTextMap.get(k).setId("number");
						} else {
							if (untouchedList.get(k) != 0) {
								boardTextMap.get(k).setId("preset");
							} else if (boardList.get(k) != 0) {
								boardTextMap.get(k).setId("");
							}
						}
					}
				}

				if (getNum(value) >= 9 && value != 0) {
					numGridPane.numButtonsMap.get(value - 1).setId("legendFull");
				}
			});

			numGridPane.numButtonsMap.get(i).setOnMouseEntered(e -> {
				scene.setCursor(Cursor.HAND);
			});

			numGridPane.numButtonsMap.get(i).setOnMouseExited(e -> {
				scene.setCursor(Cursor.DEFAULT);
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