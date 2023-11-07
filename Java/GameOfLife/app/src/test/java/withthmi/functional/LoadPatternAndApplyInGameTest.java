package withthmi.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Dimension;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import gameoflife.builders.gameboard.GameBoardDataModel;
import gameoflife.builders.gameboard.GameBoardModelBuilder;
import gameoflife.core.GameManager;
import gameoflife.game.Game;
import gameoflife.gameboard.Cell;
import gameoflife.gameboard.GameBoard;
import gameoflife.hmi.GameOfLifeMainViewFrame;
import gameoflife.patterns.Pattern;
import gameoflife.patterns.loader.PlainTextFileFormatPatternLoader;
import geometry2d.integergeometry.IntegerPrecisionRectangle;
import util.CollectionUtils;

@ExtendWith(MockitoExtension.class)
class LoadPatternAndApplyInGameTest {

	@Mock
	protected GameManager gameManager;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	protected GameBoardModelBuilder gameBoardModelBuilder;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	GameBoardDataModel gameBoardDataModel;

	protected GameBoard gameBoard;

	protected Game game;

	@Nested
	public class WithHmi {
		GameOfLifeMainViewFrame mainView;

		@BeforeEach
		void setUp() throws Exception {
			mainView = new GameOfLifeMainViewFrame();
			GameManager.getInstance().setMainViewFrame(mainView);

			// Schedule a job for the event dispatch thread:
			// creating and showing this application's GUI.
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					mainView.createAndShowGUI();
				}
			});
		}

		@AfterEach
		void tearDown() throws Exception {
			mainView.dispose();
		}

		@Nested
		public class SquareGameBoard {

			int gameBoardWidth = 10;
			int gameBoardHeight = 10;

			List<Cell> initiallyAliveCells;
			List<Cell> initiallyDeadCells;

			@BeforeEach
			void setUp() throws Exception {
				Dimension gameBoardDimension = new Dimension(gameBoardWidth, gameBoardHeight);

				Mockito.when(gameBoardModelBuilder.getGameBoardDataModel().getGameBoardDimensions().getDimension())
						.thenReturn(gameBoardDimension);

				gameBoard = new GameBoard(gameBoardModelBuilder);
				GameManager gameManager = GameManager.getInstance();
				gameManager.newGame(gameBoard);
				game = gameManager.getGame();

			}

			@Test
			void gliderCells() {
				PlainTextFileFormatPatternLoader loader = new PlainTextFileFormatPatternLoader();

				Pattern pattern = loader.loadFile("app/src/main/resources/gameoflife/patterns/glider.cells");
				IntegerPrecisionRectangle rectangleBoundingBox = pattern.getRectangleBoundingBox();
				game.applyPattern(pattern, 0, 0);

				assertNotNull(pattern);
			}
		}
	}

}