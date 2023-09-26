package game.gameboard;

import static org.junit.Assert.assertNull;
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
		public class Cell {
			protected GenericIntegerGameBoardPoint gameBoardPoint;

			@Nested
			public class TopLeftCornerCell {

				@BeforeEach
				void setUp() throws Exception {
					gameBoardPoint = gameBoard.getGameBoardPointByXAndY(0, 0);
				}

				@ParameterizedTest
				@CsvSource(value = { "NORTH,NIL,NIL", "NORTH_EAST,NIL,NIL", "EAST,1,0", "SOUTH_EAST,1,1", "SOUTH,0,1", "SOUTH_WEST,NIL,NIL",
						"WEST,NIL,NIL", "NORTH_WEST,NIL,NIL" }, nullValues = "NIL")
				void testNeighbour(NeighbourGameBoardPointDirection direction, Integer expectedX, Integer expectedY) {
					GenericIntegerGameBoardPoint neighbour = gameBoard.getNeighbourGameBoardPoint(gameBoardPoint,
							direction);
					if (expectedX == null || expectedY == null) {
						assertNull(neighbour);
					} else {
						assertEquals(expectedX.intValue(), neighbour.getX());
						assertEquals(expectedY.intValue(), neighbour.getY());
					}
				}
			}

			@Nested
			public class BottomRightCornerCell {

				@BeforeEach
				void setUp() throws Exception {
					gameBoardPoint = gameBoard.getGameBoardPointByXAndY(9, 9);
				}

				@ParameterizedTest
				@CsvSource(value = { "NORTH,9,8", "NORTH_EAST,NIL,NIL", "EAST,NIL,NIL", "SOUTH_EAST,NIL,NIL", "SOUTH,NIL,NIL", "SOUTH_WEST,NIL,NIL",
						"WEST,8,9", "NORTH_WEST,8,8" }, nullValues = "NIL")
				void testNeighbour(NeighbourGameBoardPointDirection direction, Integer expectedX, Integer expectedY) {
					GenericIntegerGameBoardPoint neighbour = gameBoard.getNeighbourGameBoardPoint(gameBoardPoint,
							direction);
					if (expectedX == null || expectedY == null) {
						assertNull(neighbour);
					} else {
						assertEquals(expectedX.intValue(), neighbour.getX());
						assertEquals(expectedY.intValue(), neighbour.getY());
					}
				}
			}
			@Nested
			public class MiddleCell {

				@BeforeEach
				void setUp() throws Exception {
					gameBoardPoint = gameBoard.getGameBoardPointByXAndY(5, 5);
				}

				@Test
				public void testNorth() {
					GenericIntegerGameBoardPoint neighbour = gameBoard.getNeighbourGameBoardPoint(gameBoardPoint,
							NeighbourGameBoardPointDirection.NORTH);
					assertEquals(gameBoardPoint.getX(), neighbour.getX());
				}

				@ParameterizedTest
				@CsvSource({ "NORTH,5,4", "NORTH_EAST,6,4", "EAST,6,5", "SOUTH_EAST,6,6", "SOUTH,5,6", "SOUTH_WEST,4,6",
						"WEST,4,5", "NORTH_WEST,4,4" })
				void testNeighbour(NeighbourGameBoardPointDirection direction, int expectedX, int expectedY) {
					GenericIntegerGameBoardPoint neighbour = gameBoard.getNeighbourGameBoardPoint(gameBoardPoint,
							direction);
					assertEquals(expectedX, neighbour.getX());
					assertEquals(expectedY, neighbour.getY());
				}
			}
		}
	}

}
