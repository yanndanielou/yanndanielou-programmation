package gameboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.gameboard.GameBoardNamedAreaDataModel;
import builders.gameboard.RectangleDataModel;
import geometry.IntegerRectangle;

public class GameBoardAttackersEntryArea extends GameBoardArea {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardAttackersEntryArea.class);

	public GameBoardAttackersEntryArea(GameBoard gameBoard, RectangleDataModel rectangleDataModel) {
		super(gameBoard, rectangleDataModel);
	}

	public GameBoardAttackersEntryArea(GameBoard gameBoard, IntegerRectangle rectangleInImageWithRGB,
			GameBoardNamedAreaDataModel gameBoardNamedAreaDataModel) {
		super(gameBoard, rectangleInImageWithRGB, gameBoardNamedAreaDataModel);
	}
}
