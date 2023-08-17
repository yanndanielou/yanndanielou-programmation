package gameboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.gameboard.GameBoardNamedAreaDataModel;
import builders.gameboard.RectangleDataModel;
import geometry.IntegerRectangle;

public class GameBoardAttackersExitArea extends GameBoardArea {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardAttackersExitArea.class);

	public GameBoardAttackersExitArea(GameBoard gameBoard, RectangleDataModel rectangleDataModel) {
		super(gameBoard, rectangleDataModel);
	}

	public GameBoardAttackersExitArea(GameBoard gameBoard, IntegerRectangle rectangleInImageWithRGB,
			GameBoardNamedAreaDataModel gameBoardNamedAreaDataModel) {
		super(gameBoard, rectangleInImageWithRGB, gameBoardNamedAreaDataModel);
	}

}
