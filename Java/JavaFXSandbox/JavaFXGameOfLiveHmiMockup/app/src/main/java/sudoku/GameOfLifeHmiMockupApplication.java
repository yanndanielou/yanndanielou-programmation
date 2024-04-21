package sudoku;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.layout.GridPane;

import javafx.scene.control.ScrollPane;

/***
 * Source:
 * https://steemit.com/programming/@satoshio/conway-s-game-of-life-implementation-in-javafx
 */
public class GameOfLifeHmiMockupApplication extends Application {
	private static final Logger LOGGER = LogManager.getLogger(GameOfLifeHmiMockupApplication.class);

	public Stage primaryStage;

	private static final int APPLICATION_WIDTH = 500;
	private static final int APPLICATION_HEIGHT = 500;
	private static int CELL_SIZE = 10;
	private static boolean SHOW_BORDERS = true;

	private static float ZOOM_FACTOR = 1;

	@Override
	public void start(Stage primaryStage) {

		// Creates a reference to the primaryStage to be
		// able to manipulate it in other methods
		this.primaryStage = primaryStage;

		defineApplicationIcon();


		MainBarMenu mainBarMenu = new MainBarMenu(this);

		GridPane gridPane = new GridPane();
		final Canvas canvas = new Canvas(APPLICATION_WIDTH, APPLICATION_HEIGHT);

		Button resetButton = new Button("Reset");
		Button stepButton = new Button("Step");
		Button runButton = new Button("Run");
		Button stopButton = new Button("Stop");
		Button toggleGridButton = new Button("Toggle Grid");
		Button zoomInButton = new Button("Zoom In");
		Button zoomOutButton = new Button("Zoom out");

		// ScrollPane scrollPane = new ScrollPane(canvas);
		ScrollPane scrollPane = new ScrollPane(gridPane);

		BorderPane mainViewBorderPane = new BorderPane();
		mainViewBorderPane.setTop(mainBarMenu);
		mainViewBorderPane.setCenter(scrollPane);
		
		HBox controlButtonsHBox = new HBox();

		controlButtonsHBox.getChildren().addAll(resetButton, stepButton, runButton, stopButton,
				toggleGridButton, zoomInButton, zoomOutButton);
		
		mainViewBorderPane.setBottom(controlButtonsHBox);
		
		Scene scene = new Scene(mainViewBorderPane, APPLICATION_WIDTH, APPLICATION_HEIGHT + 100);
		scene.getStylesheets().add("application.css");

		
		
		primaryStage.setScene(scene);
		primaryStage.show();

		int rows = (int) Math.floor(APPLICATION_HEIGHT / CELL_SIZE);
		int cols = (int) Math.floor(APPLICATION_WIDTH / CELL_SIZE);

		GraphicsContext graphics = canvas.getGraphicsContext2D();
		Life life = new Life(rows, cols, graphics, gridPane);
		life.init();

		AnimationTimer runAnimation = new AnimationTimer() {
			private long lastUpdate = 0;

			@Override
			public void handle(long now) {
				// only update once every second
				if ((now - lastUpdate) >= TimeUnit.MILLISECONDS.toNanos(500)) {
					life.tick();
					lastUpdate = now;
				}
			}
		};

		resetButton.setOnAction(l -> life.init());
		runButton.setOnAction(l -> runAnimation.start());
		stepButton.setOnAction(l -> life.tick());
		stopButton.setOnAction(l -> runAnimation.stop());
		toggleGridButton.setOnAction(e -> {
			SHOW_BORDERS = !SHOW_BORDERS;
			life.draw();
		});

		zoomInButton.setOnAction(e -> {
			/* ZOOM_FACTOR++; */
			ZOOM_FACTOR *= 1.5;
			canvas.setScaleX(ZOOM_FACTOR);
			canvas.setScaleY(ZOOM_FACTOR);

			CELL_SIZE *= 1.5;
			life.resizeCells(CELL_SIZE);

		});
		zoomOutButton.setOnAction(e -> {
			ZOOM_FACTOR /= 1.5;

			/*
			 * if (ZOOM_FACTOR >= 2) { ZOOM_FACTOR--; } else { ZOOM_FACTOR /= 2; }
			 * 
			 * 
			 */
			canvas.setScaleX(ZOOM_FACTOR);
			canvas.setScaleY(ZOOM_FACTOR);

			CELL_SIZE /= 1.5;
			life.resizeCells(CELL_SIZE);

		});
	}

	private void defineApplicationIcon() {
		// Application icon
		String applicationIconPath = "gameoflive_icon.png";
		LOGGER.info(() -> "defineApplicationIcon:" + applicationIconPath);
		Image applicationIcon = new Image(applicationIconPath);
		primaryStage.getIcons().add(applicationIcon);
	}

	/**
	 * Main method for the Sudoku game
	 */
	public static void main(String[] args) {
		launch(args);
	}

	private static class Life {
		private final int rows;
		private final int cols;
		private int[][] grid;
		private Button[][] gridButtons;
		private Random random = new Random();
		private final GraphicsContext graphics;
		private GridPane gridPane;

		public Life(int rows, int cols, GraphicsContext graphics, GridPane gridPane) {
			this.rows = rows;
			this.cols = cols;
			this.graphics = graphics;
			this.gridPane = gridPane;
			grid = new int[rows][cols];
			gridButtons = new Button[rows][cols];
		}

		public void resizeCells(int cellSize) {

			LOGGER.info(() -> "Resize cells: " + cellSize);
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					gridButtons[i][j].setMaxHeight(cellSize);
					gridButtons[i][j].setMinHeight(cellSize);
					gridButtons[i][j].setMaxWidth(cellSize);
					gridButtons[i][j].setMinWidth(cellSize);
				}
			}
		}

		public void init() {

			LOGGER.info(() -> "Init");
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					grid[i][j] = random.nextInt(2);
					gridButtons[i][j] = new Button();
					gridPane.add(gridButtons[i][j], i, j);
				}
			}
			resizeCells(CELL_SIZE);

			LOGGER.info(() -> "Will draw now");
			draw();
		}

		private void draw() {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (grid[i][j] == 1) {
						gridButtons[i][j].setId("aliveCell");
					} else {
						gridButtons[i][j].setId("deadCell");
					}
				}
			}
		}

		@Deprecated
		private void drawWithCanvas() {
			// clear graphics
			graphics.setFill(Color.LAVENDER);
			graphics.fillRect(0, 0, APPLICATION_WIDTH, APPLICATION_HEIGHT);

			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					// first rect will end up becoming the border

					if (SHOW_BORDERS) {
						graphics.setFill(Color.gray(0.5, 0.5));
						graphics.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
					}

					graphics.setFill(grid[i][j] == 1 ? Color.PURPLE : Color.LAVENDER);
					if (SHOW_BORDERS) {
						graphics.fillRect((i * CELL_SIZE) + 1, (j * CELL_SIZE) + 1, CELL_SIZE - 2, CELL_SIZE - 2);
					} else {
						graphics.fillRect((i * CELL_SIZE), (j * CELL_SIZE), CELL_SIZE, CELL_SIZE);
					}
				}
			}
		}

		public void tick() {
			int[][] next = new int[rows][cols];

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					int neighbors = countAliveNeighbors(i, j);

					if (neighbors == 3) {
						next[i][j] = 1;
					} else if (neighbors < 2 || neighbors > 3) {
						next[i][j] = 0;
					} else {
						next[i][j] = grid[i][j];
					}
				}
			}
			grid = next;
			draw();
		}

		private int countAliveNeighbors(int i, int j) {
			int sum = 0;
			int iStart = i == 0 ? 0 : -1;
			int iEndInclusive = i == grid.length - 1 ? 0 : 1;
			int jStart = j == 0 ? 0 : -1;
			int jEndInclusive = j == grid[0].length - 1 ? 0 : 1;

			for (int k = iStart; k <= iEndInclusive; k++) {
				for (int l = jStart; l <= jEndInclusive; l++) {
					sum += grid[i + k][l + j];
				}
			}

			sum -= grid[i][j];

			return sum;
		}
	}
}
