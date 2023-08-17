package gameboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.gameboard.GameBoardNamedAreaDataModel;
import geometry.IntegerRectangle;

public class GameBoardInitiallyConstructibleMacroArea extends GameBoardArea {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardInitiallyConstructibleMacroArea.class);

	public GameBoardInitiallyConstructibleMacroArea(GameBoard gameBoard, IntegerRectangle rectangleInImageWithRGB,
			GameBoardNamedAreaDataModel gameBoardNamedAreaDataModel) {
		super(gameBoard, rectangleInImageWithRGB, gameBoardNamedAreaDataModel);
	}

}
