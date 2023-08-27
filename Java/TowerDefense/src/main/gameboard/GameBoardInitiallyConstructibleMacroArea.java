package main.gameboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.builders.gameboard.GameBoardNamedAreaDataModel;
import main.geometry2d.integergeometry.IntegerPrecisionRectangle;

public class GameBoardInitiallyConstructibleMacroArea extends GameBoardArea {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardInitiallyConstructibleMacroArea.class);

	public GameBoardInitiallyConstructibleMacroArea(GameBoard gameBoard, IntegerPrecisionRectangle rectangleInImageWithRGB,
			GameBoardNamedAreaDataModel gameBoardNamedAreaDataModel) {
		super(gameBoard, rectangleInImageWithRGB, gameBoardNamedAreaDataModel);
	}

}
