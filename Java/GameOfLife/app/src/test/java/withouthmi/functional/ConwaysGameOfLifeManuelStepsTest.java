package withouthmi.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Dimension;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
import util.CollectionUtils;

@ExtendWith(MockitoExtension.class)
class ConwaysGameOfLifeManuelStepsTest {

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

			@Nested
			public class StillLife {

				private static Stream<Arguments> provideStillLifeForms() {

				// @formatter:off
				return Stream.of(
						Arguments.of(CollectionUtils.asList(new MutablePair<Integer, Integer>(5, 5),new MutablePair<Integer, Integer>(5, 6),new MutablePair<Integer, Integer>(6, 5),new MutablePair<Integer, Integer>(6, 6)), "Block"),
						Arguments.of(CollectionUtils.asList(new MutablePair<Integer, Integer>(2, 1),new MutablePair<Integer, Integer>(1, 2),new MutablePair<Integer, Integer>(3, 2),new MutablePair<Integer, Integer>(2, 3)), "Tub"),
						Arguments.of(CollectionUtils.asList(new MutablePair<Integer, Integer>(2, 1),new MutablePair<Integer, Integer>(3, 1),new MutablePair<Integer, Integer>(1, 2),new MutablePair<Integer, Integer>(4, 2),new MutablePair<Integer, Integer>(2, 3),new MutablePair<Integer, Integer>(3, 3)), "Bee-Hive")
						);
				// @formatter:on	
				}

				@BeforeEach
				void setUp() throws Exception {

				}

				@AfterEach
				void tearDown() throws Exception {
					mainView.dispose();
					
					

				}

				@ParameterizedTest
				@MethodSource("provideStillLifeForms")
				void playOneStepChangesNothing(List<Pair<Integer, Integer>> listOfAliveCellsCoordinates, String name) {
					for (Pair<Integer, Integer> cellCoordinates : listOfAliveCellsCoordinates) {
						Integer left = cellCoordinates.getLeft();
						Cell cellByXAndY = gameBoard.getCellByXAndY(left, cellCoordinates.getRight());
						cellByXAndY.setAlive();
					}
					
					initiallyAliveCells = CollectionUtils.copy(gameBoard.getAllAliveCells());
					initiallyDeadCells = CollectionUtils.copy(gameBoard.getAllDeadCells());

					game.playOneStep();
					
					List<Cell> afterPlayAliveCells = CollectionUtils.copy(gameBoard.getAllAliveCells());
					List<Cell> afterPlayDeadCells = CollectionUtils.copy(gameBoard.getAllDeadCells());
					
					assertEquals(initiallyAliveCells, afterPlayAliveCells);
					assertEquals(initiallyDeadCells, afterPlayDeadCells);
				}
			}
		}
	}

}