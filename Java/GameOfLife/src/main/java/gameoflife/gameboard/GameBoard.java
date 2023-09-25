package gameoflife.gameboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.gameboard.GenericGameBoard;
import game.gameboard.GenericIntegerGameBoardPoint;
import gameoflife.builders.gameboard.GameBoardDataModel;
import gameoflife.builders.gameboard.GameBoardModelBuilder;
import gameoflife.game.Game;

public class GameBoard extends GenericGameBoard {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoard.class);

	private GameBoardDataModel gameBoardDataModel;

	private Game game;


	public GameBoard(GameBoardModelBuilder gameBoardModelBuilder) {
		this.gameBoardDataModel = gameBoardModelBuilder.getGameBoardDataModel();
		afterConstructor();
	}

	public int getTotalWidth() {
		return (int) gameBoardDataModel.getGameBoardDimensions().getDimension().getWidth();
	}

	public int getTotalHeight() {
		return (int) gameBoardDataModel.getGameBoardDimensions().getDimension().getHeight();
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public GameBoardDataModel getGameBoardDataModel() {
		return gameBoardDataModel;
	}

	@Override
	protected GenericIntegerGameBoardPoint createGameBoardPoint(int row, int column) {
		return new Cell(game, row, column);
	}

}
