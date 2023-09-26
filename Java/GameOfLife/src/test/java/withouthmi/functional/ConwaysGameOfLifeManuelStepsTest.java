package withouthmi.functional;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gameoflife.builders.gameboard.GameBoardDataModel;
import gameoflife.builders.gameboard.GameBoardModelBuilder;
import gameoflife.core.GameManager;
import gameoflife.game.Game;
import gameoflife.gameboard.GameBoard;

@ExtendWith(MockitoExtension.class)
class ConwaysGameOfLifeManuelStepsTest {
	
	int gameBoardWidth = 10;
	int gameBoardHeight = 10;
	

	@Mock
	protected GameManager gameManager;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	protected GameBoardModelBuilder gameBoardModelBuilder;
	
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	GameBoardDataModel gameBoardDataModel;
	
	protected GameBoard gameBoard;

	protected Game game;

		
	
		@Test
		void playOneStep() {
			//when(gameBoardModelBuilder.getGameBoardDataModel()).thenReturn(gameBoardDataModel);
			
			lenient().when(gameBoardModelBuilder.getGameBoardDataModel().getGameBoardDimensions().getWidth()).thenReturn(gameBoardWidth);
			lenient().when(gameBoardModelBuilder.getGameBoardDataModel().getGameBoardDimensions().getHeight()).thenReturn(gameBoardHeight);

			gameBoard = new GameBoard(gameBoardModelBuilder);
			game = new Game(gameManager, gameBoard);
			
			game.playOneStep();
		}

	

}