package tetris.game_objects.tetrominoes_types;

import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tetris.builders.gameboard.GameBoardDataModel;
import tetris.game.Game;
import tetris.game.PauseReason;
import tetris.game_objects.Mino;
import tetris.gameboard.Matrix;
import tetris.gameboard.MatrixCell;

@ExtendWith(MockitoExtension.class)
class TetrominoTest {
	
	private static final Logger LOGGER = LogManager.getLogger(TetrominoTest.class);


	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	protected GameBoardDataModel gameBoardDataModel;

	
	private class TetrominoForTests extends Tetromino {

	}

	@Nested
	public class SortPerDirection {


		Dimension gameBoardDimensions;
		MatrixCell upperLeftCornerOfNewTetriminoCenteredOnGameBoard;
		private Game game;
		private Matrix gameBoard;
		
		@BeforeEach
		void setUp() throws Exception {
			gameBoardDimensions = new Dimension(20, 20);
			Mockito.when(gameBoardDataModel.getGameBoardDimensions().getDimension()).thenReturn(gameBoardDimensions);
			gameBoard = new Matrix(gameBoardDataModel);
			game = new Game(gameBoard);
			game.addPauseReason(PauseReason.UNIT_TESTS);
			upperLeftCornerOfNewTetriminoCenteredOnGameBoard = gameBoard.getMatrixCellByXAndY(5, 5);

		
		}
 
		@ParameterizedTest
		@EnumSource(TetrominoType.class)
		void fromBottomToTop(TetrominoType tetriminoType) {

			
			Tetromino tetromino = new Tetromino(tetriminoType, game, upperLeftCornerOfNewTetriminoCenteredOnGameBoard) {
			};
			
			List<Mino> minosSortedFromBottomToTop = tetromino.getMinosSortedFromBottomToTop();
			int minY = gameBoard.getTotalHeight();
			for(Mino mino : minosSortedFromBottomToTop) {
				LOGGER.info(()->"Mino " + mino +" y:" + mino.getLocationOnMatrix().getYAsInt());
				assertTrue(mino.getLocationOnMatrix().getYAsInt() <= minY);
				minY = mino.getLocationOnMatrix().getYAsInt();
			}
		}

		@ParameterizedTest
		@EnumSource(TetrominoType.class)
		void fromRightToLeft(TetrominoType tetriminoType) {

			
			Tetromino tetromino = new Tetromino(tetriminoType, game, upperLeftCornerOfNewTetriminoCenteredOnGameBoard) {
			};
			
			List<Mino> minosSortedFromBottomToTop = tetromino.getMinosSortedFromRightToLeft();
			int minX = gameBoard.getTotalWidth();
			for(Mino mino : minosSortedFromBottomToTop) {
				LOGGER.info(()->"Mino " + mino +" x:" + mino.getLocationOnMatrix().getXAsInt());
				assertTrue(mino.getLocationOnMatrix().getXAsInt() <= minX);
				minX = mino.getLocationOnMatrix().getXAsInt();
			}
		}


		@ParameterizedTest
		@EnumSource(TetrominoType.class)
		void fromLeftToRight(TetrominoType tetriminoType) {

			Tetromino tetromino = new Tetromino(tetriminoType, game, upperLeftCornerOfNewTetriminoCenteredOnGameBoard) {
			};
			
			List<Mino> minosSortedFromBottomToTop = tetromino.getMinosSortedFromLeftToRight();
			int maxX = 0;
			for(Mino mino : minosSortedFromBottomToTop) {
				LOGGER.info(()->"Mino " + mino +" x:" + mino.getLocationOnMatrix().getXAsInt());
				assertTrue(mino.getLocationOnMatrix().getXAsInt() >= maxX);
				maxX = mino.getLocationOnMatrix().getXAsInt();
			}
		}

	}

}
