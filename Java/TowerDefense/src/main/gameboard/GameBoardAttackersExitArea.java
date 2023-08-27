package main.gameboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.builders.gameboard.GameBoardNamedAreaDataModel;
import main.builders.gameboard.RectangleDataModel;
import main.geometry2d.integergeometry.IntegerPrecisionRectangle;

public class GameBoardAttackersExitArea extends GameBoardArea {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardAttackersExitArea.class);

	public GameBoardAttackersExitArea(GameBoard gameBoard, RectangleDataModel rectangleDataModel) {
		super(gameBoard, rectangleDataModel);
	}

	public GameBoardAttackersExitArea(GameBoard gameBoard, IntegerPrecisionRectangle rectangleInImageWithRGB,
			GameBoardNamedAreaDataModel gameBoardNamedAreaDataModel) {
		super(gameBoard, rectangleInImageWithRGB, gameBoardNamedAreaDataModel);
	}

}
