package sudoku;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
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

import javafx.scene.control.ScrollPane;

public class GameOfLifeHmiMockupApplication extends Application {
	private static final Logger LOGGER = LogManager.getLogger(GameOfLifeHmiMockupApplication.class);

	public Stage primaryStage;

	private static final int APPLICATION_WIDTH = 500;
	private static final int APPLICATION_HEIGHT = 500;
	private static final int CELL_SIZE = 10;

	@Override
	public void start(Stage primaryStage) {

		// Creates a reference to the primaryStage to be
		// able to manipulate it in other methods
		this.primaryStage = primaryStage;

		defineApplicationIcon();

		VBox root = new VBox(10);
		Scene scene = new Scene(root, APPLICATION_WIDTH, APPLICATION_HEIGHT + 100);
		
		MainBarMenu mainBarMenu = new MainBarMenu(this);
		

		
		final Canvas canvas = new Canvas(APPLICATION_WIDTH, APPLICATION_HEIGHT);

		Button reset = new Button("Reset");
		Button step = new Button("Step");
		Button run = new Button("Run");
		Button stop = new Button("Stop");

		ScrollPane scrollPane = new ScrollPane(canvas);
		

		BorderPane mainViewBorderPane = new BorderPane();
		mainViewBorderPane.setTop(mainBarMenu);
		mainViewBorderPane.setCenter(scrollPane);
		
		
		root.getChildren().addAll(mainViewBorderPane, new HBox(10, reset, step, run, stop));
		primaryStage.setScene(scene);
		primaryStage.show();

		int rows = (int) Math.floor(APPLICATION_HEIGHT / CELL_SIZE);
		int cols = (int) Math.floor(APPLICATION_WIDTH / CELL_SIZE);

		GraphicsContext graphics = canvas.getGraphicsContext2D();
		Life life = new Life(rows, cols, graphics);
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

		reset.setOnAction(l -> life.init());
		run.setOnAction(l -> runAnimation.start());
		step.setOnAction(l -> life.tick());
		stop.setOnAction(l -> runAnimation.stop());
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
		private Random random = new Random();
		private final GraphicsContext graphics;

		public Life(int rows, int cols, GraphicsContext graphics) {
			this.rows = rows;
			this.cols = cols;
			this.graphics = graphics;
			grid = new int[rows][cols];
		}

		public void init() {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					grid[i][j] = random.nextInt(2);
				}
			}
			draw();
		}

		private void draw() {
			// clear graphics
			graphics.setFill(Color.LAVENDER);
			graphics.fillRect(0, 0, APPLICATION_WIDTH, APPLICATION_HEIGHT);

			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					// first rect will end up becoming the border	
					graphics.setFill(Color.gray(0.5, 0.5));
					
					graphics.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
					graphics.setFill(grid[i][j] == 1 ?Color.PURPLE:Color.LAVENDER);
					graphics.fillRect((i * CELL_SIZE) + 1, (j * CELL_SIZE) + 1, CELL_SIZE - 2, CELL_SIZE - 2);
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
