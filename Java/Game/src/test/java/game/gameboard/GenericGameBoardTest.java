package game.gameboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GenericGameBoardTest {

	protected GenericGameBoardForTest gameBoard;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Nested
	public class SquareGameBoard {

		@BeforeEach
		void setUp() throws Exception {
			gameBoard = new GenericGameBoardForTest(10, 10);
		}

		@AfterEach
		void tearDown() throws Exception {
		}

		@Nested
		public class MiddleCell {
			protected GenericIntegerGameBoardPoint gameBoardPoint;

			@BeforeEach
			void setUp() throws Exception {
				gameBoardPoint = gameBoard.getGameBoardPointByXAndY(5, 5);
			}

			@Test
			public void testNorth() {
				GenericIntegerGameBoardPoint neighbour = gameBoard.getNeighbourGameBoardPoint(gameBoardPoint, NeighbourGameBoardPointDirection.NORTH);
				assertEquals(gameBoardPoint.getX(), neighbour.getX());
			}
			
			@ParameterizedTest
			@CsvSource({
			    "NORTH,5,4",
			    "NORTH_EAST,6,4",
			    "EAST,6,5",
			    "SOUTH_EAST,6,6",
			    "SOUTH,5,6",
			    "SOUTH_WEST,4,6",
			    "WEST,4,5",
			    "NORTH_WEST,4,4"
			})
			void testNeighbour(NeighbourGameBoardPointDirection direction, int expectedX, int expectedY) {
				GenericIntegerGameBoardPoint neighbour = gameBoard.getNeighbourGameBoardPoint(gameBoardPoint, direction);
				assertEquals(expectedX, neighbour.getX());
				assertEquals(expectedY, neighbour.getY());
			}
		}
	}

}
