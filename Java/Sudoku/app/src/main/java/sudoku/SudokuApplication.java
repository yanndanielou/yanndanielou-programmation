package sudoku;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SudokuApplication extends Application {
	private static final Logger LOGGER = LogManager.getLogger(SudokuApplication.class);

	public int currentlySelectedDigit = 0;

	public BorderPane mainViewBorderPane;
	public BorderPane rootBorderPane;
	public Scene scene;
	public SudokuSquareBoxesGridPane sudokuSquareBoxesGridPane;
	public Sudoku sudoku;
	public Game game;
	public MainBarMenu mainBarMenu;

	public ArrayList<Integer> board, untouchedCells;
	public Map<Integer, Button> sudokuCellButton;
	public Map<Integer, SudokuSquareBoxAsGridPane> grid;

	public TopHBox topHbox;

	public DigitsBottomGridPane digitsBottomGridPane;

	public GameDurationTimeLine timeline;

	public Stage stage;

	public ViewPresenter viewPresenter;

	/**
	 * Changes the CSS ids of the horizontal line
	 * 
	 * @param array an Array of CSS ids
	 * @param start the horizontal line number
	 */
	public void changeHorizontalIds(String[] array, int start) {
		LOGGER.info(() -> "changeHorizontalIds:" + array + " and start:" + start);
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
		LOGGER.info(() -> "changeVerticalIds:" + array + " and start:" + start);
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
	public void changeIdsHelper(String[] array, int i) {
		LOGGER.info(() -> "changeIdsHelper:" + array + " and i:" + i);

		if (!(sudokuCellButton.get(i).getText()).equals(String.valueOf(currentlySelectedDigit))
				|| currentlySelectedDigit == 0) {
			if (untouchedCells.get(i) != 0) {
				sudokuCellButton.get(i).setId(array[0]);
			} else if (board.get(i) != 0) {
				sudokuCellButton.get(i).setId(array[1]);
			} else {
				sudokuCellButton.get(i).setId(array[2]);
			}
		} else {
			sudokuCellButton.get(i).setId(array[3]);
		}
	}

	/**
	 * Resets the game
	 */
	public void reset() {

		LOGGER.info(() -> "reset");

		sudokuSquareBoxesGridPane.reset();
		
		// Creates a new Sudoku board for the player
		sudoku.clear();
		sudoku.generateBoard();
		sudoku.generatePlayer();

		// Print out the sol-ution
		System.out.println(sudoku.toString());
		LOGGER.info(() -> "sudoku" + sudoku.toString());

		// Get player's board
		board = sudoku.getPlayer();
		LOGGER.info(() -> "board" + board);

		// List and maps of Buttons, GridPanes and value of the board
		untouchedCells = new ArrayList<Integer>(board);
		sudokuCellButton = new HashMap<Integer, Button>();
		grid = new HashMap<Integer, SudokuSquareBoxAsGridPane>();
	}

	/**
	 * Returns the number of elements of the specified number
	 * 
	 * @param num the number researched
	 * @return a number of elements equal to the parameter
	 */
	public int getNumberOfOccurenceInBoardOfNum(int num) {
		int count = 0;
		for (int p = 0; p < 81; p++) {
			if (Integer.valueOf(sudokuCellButton.get(p).getText()) == num) {
				count++;
			}
		}

		final int countTotal = count;
		LOGGER.info(() -> "getNumberOfOccurenceInBoardOfNum " + num + " = " + countTotal);
		return count;
	}

	/**
	 * Generates the board, in terms of GUI
	 */
	public void generateBoard() {
		// Each block
		for (int i = 0; i < 9; i++) {

			grid.put(i, new SudokuSquareBoxAsGridPane(this, game, viewPresenter, stage));

			int t = i % 3 * 3 + (i / 3) * 27;
			int temp = 0;

			// Each element in that block
			for (int j = t; j < t + 20; j += 9, temp++) {

				// Each row of the block
				for (int k = 0; k < 3; k++) {

					// Index of current element
					final int pos = j + k;

					// New Button
					sudokuCellButton.put(pos, new Button());

					if (board.get(pos) == 0) {
						sudokuCellButton.get(pos).setId("zero");

						sudokuCellButton.get(pos).setOnAction(e -> {
							if (currentlySelectedDigit != 0) {
								if (sudokuCellButton.get(pos).getText().equals(String.valueOf(currentlySelectedDigit))) {
									sudokuCellButton.get(pos).setText("0");
									board.set(pos, 0);
									sudokuCellButton.get(pos).setId("helperZero");
								} else {
									sudokuCellButton.get(pos).setText(String.valueOf(currentlySelectedDigit));
									sudokuCellButton.get(pos).setId("");
									board.set(pos, currentlySelectedDigit);
								}

								for (int l = 0; l < 81; l++) {
									if (!sudokuCellButton.get(l).getId().equals("number") && sudokuCellButton.get(l).getText()
											.equals(String.valueOf(currentlySelectedDigit))) {
										sudokuCellButton.get(l).setId("number");
									}
								}

								digitsBottomGridPane.updateDigitSelectionInBottomButtonsState();
							}

							// Checks if the game is done
							if (sudoku.checkBoard(board)) {
								timeline.stop();

								Alert alert = new Alert(AlertType.NONE,
										"You just completed the sudoku board in "
												+ game.gameDurationInMilliseconds / 1000
												+ " seconds. Do you want to play again?",
										ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
								alert.showAndWait();

								if (alert.getResult() == ButtonType.YES) {
									topHbox.newGameButton.fire();
								} else if (alert.getResult() == ButtonType.NO) {
									stage.close();
								} else if (alert.getResult() == ButtonType.CANCEL) {
									topHbox.clearButton.fire();
								}
							}
						});

					} else {
						sudokuCellButton.get(pos).setId("preset");
					}

					sudokuCellButton.get(pos).setOnMouseEntered(e -> {
						if (!sudoku.checkBoard(board) && currentlySelectedDigit != 0) {
							changeHorizontalIds(new String[] { "helper", "helper", "helperZero", "numberHelper" },
									pos / 9);
							changeVerticalIds(new String[] { "helper", "helper", "helperZero", "numberHelper" },
									pos % 9);
							if (board.get(pos) == 0) {
								scene.setCursor(Cursor.HAND);
							}
						}
					});

					sudokuCellButton.get(pos).setOnMouseExited(e -> {
						if (!sudoku.checkBoard(board) && currentlySelectedDigit != 0) {
							changeHorizontalIds(new String[] { "preset", "", "zero", "number" }, pos / 9);
							changeVerticalIds(new String[] { "preset", "", "zero", "number" }, pos % 9);
							scene.setCursor(Cursor.DEFAULT);
						}
					});

					sudokuCellButton.get(pos).setText(String.valueOf(board.get(pos)));
					grid.get(i).add(sudokuCellButton.get(pos), k, temp);
				}
			}
			sudokuSquareBoxesGridPane.add(grid.get(i), i % 3, i / 3);
			LOGGER.info(() -> "Add ");
		}
	}

	/**
	 * Counts the time elapsed in seconds from the start of the game, from 0 to
	 * infinite and display that number in the title bard
	 */
	public void startTimer() {
		game.start();
		timeline = new GameDurationTimeLine(this, stage);

	}

	@Override
	public void start(Stage primaryStage) {

		// Creates a reference to the primaryStage to be
		// able to manipulate it in other methods
		stage = primaryStage;

		game = new Game(this);

		viewPresenter = new ViewPresenter(this, game, stage);
		game.setViewPresenter(viewPresenter);

		// Starts the timer
		startTimer();

		// Layout of the board
		sudokuSquareBoxesGridPane = new SudokuSquareBoxesGridPane(this, game, viewPresenter, stage);

		// Layout of the nine numbers at the bottom (legend)
		digitsBottomGridPane = new DigitsBottomGridPane(this, viewPresenter);

		// Layout of the top two buttons
		topHbox = new TopHBox(this);

		// Main layout of the Game
		rootBorderPane = new BorderPane();
		rootBorderPane.setTop(topHbox);
		rootBorderPane.setCenter(sudokuSquareBoxesGridPane);
		rootBorderPane.setBottom(digitsBottomGridPane);
		
		mainBarMenu = new MainBarMenu(this);
		
		mainViewBorderPane = new BorderPane();
		mainViewBorderPane.setTop(mainBarMenu);
		mainViewBorderPane.setCenter(rootBorderPane);

		// Generates the Sudoku board
		sudoku = new Sudoku();
		sudoku.generateBoard();
		sudoku.generatePlayer();

		// Prints out the solution
		System.out.println(sudoku.toString());

		defineApplicationIcon();

		// Get player's board
		board = sudoku.getPlayer();

		// List and maps of buttons, GridPanes and value of the board
		untouchedCells = new ArrayList<Integer>(board);
		sudokuCellButton = new HashMap<Integer, Button>();
		grid = new HashMap<Integer, SudokuSquareBoxAsGridPane>();
		digitsBottomGridPane.digitSelectionInBottomButton = new HashMap<Integer, Button>();

		// Generates the GUI for the board
		generateBoard();

		// Sets up the legend (nine numbers at the bottom)
		for (int i = 0; i < 9; i++) {
			digitsBottomGridPane.digitSelectionInBottomButton.put(i, new Button());
			digitsBottomGridPane.digitSelectionInBottomButton.get(i).setText(String.valueOf(i + 1));
			digitsBottomGridPane.add(digitsBottomGridPane.digitSelectionInBottomButton.get(i), i, 0);

			final int lo = i + 1;

			digitsBottomGridPane.digitSelectionInBottomButton.get(i).setOnAction(e -> {

				if (currentlySelectedDigit == Integer
						.valueOf(digitsBottomGridPane.digitSelectionInBottomButton.get(lo - 1).getText())) {
					if (getNumberOfOccurenceInBoardOfNum(currentlySelectedDigit) < 9) {
						digitsBottomGridPane.digitSelectionInBottomButton.get(currentlySelectedDigit - 1).setId("");
					}

					for (int k = 0; k < 81; k++) {
						if ((sudokuCellButton.get(k).getText()).equals(String.valueOf(currentlySelectedDigit))) {
							if (untouchedCells.get(k) != 0) {
								sudokuCellButton.get(k).setId("preset");
							} else if (board.get(k) != 0) {
								sudokuCellButton.get(k).setId("");
							}
						}
					}

					currentlySelectedDigit = 0;
					LOGGER.info(() -> "currentlySelectedDigit set to " + currentlySelectedDigit);
				} else {
					if (currentlySelectedDigit != 0 && getNumberOfOccurenceInBoardOfNum(currentlySelectedDigit) < 9) {
						digitsBottomGridPane.digitSelectionInBottomButton.get(currentlySelectedDigit - 1).setId("");
					}

					currentlySelectedDigit = lo;
					LOGGER.info(() -> "currentlySelectedDigit set to " + currentlySelectedDigit);
					digitsBottomGridPane.digitSelectionInBottomButton.get(currentlySelectedDigit - 1).setId("legend");

					for (int k = 0; k < 81; k++) {
						if ((sudokuCellButton.get(k).getText()).equals(String.valueOf(currentlySelectedDigit))) {
							sudokuCellButton.get(k).setId("number");
						} else {
							if (untouchedCells.get(k) != 0) {
								sudokuCellButton.get(k).setId("preset");
							} else if (board.get(k) != 0) {
								sudokuCellButton.get(k).setId("");
							}
						}
					}
				}

				if (getNumberOfOccurenceInBoardOfNum(currentlySelectedDigit) >= 9 && currentlySelectedDigit != 0) {
					digitsBottomGridPane.digitSelectionInBottomButton.get(currentlySelectedDigit - 1)
							.setId("legendFull");
				}
			});

			digitsBottomGridPane.digitSelectionInBottomButton.get(i).setOnMouseEntered(e -> {
				scene.setCursor(Cursor.HAND);
			});

			digitsBottomGridPane.digitSelectionInBottomButton.get(i).setOnMouseExited(e -> {
				scene.setCursor(Cursor.DEFAULT);
			});
		}

		// Sets up the state of the legend (nine numbers at the bottom) according to the
		// player's Sudoku board
		digitsBottomGridPane.updateDigitSelectionInBottomButtonsState();

		// Sets the scene to the BorderPane layout and links the CSS file
		scene = new Scene(mainViewBorderPane, 350, 500);
		scene.getStylesheets().add("application.css");

		// Sets the stage, sets its title, displays it, and restricts its minimal size
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sudoku - Time: 0");
		primaryStage.show();
		primaryStage.setMinHeight(primaryStage.getHeight());
		primaryStage.setMinWidth(primaryStage.getWidth());
	}

	private void defineApplicationIcon() {
		// Application icon
		Image applicationIcon = new Image("sudoku.png");
		stage.getIcons().add(applicationIcon);
	}

	/**
	 * Main method for the Sudoku game
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
