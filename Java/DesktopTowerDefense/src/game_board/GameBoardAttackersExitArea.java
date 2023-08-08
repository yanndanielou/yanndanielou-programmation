package game_board;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.game_board.GameBoardNamedAreaDataModel;
import builders.game_board.RectangleDataModel;
import geometry.IntegerRectangle;

public class GameBoardAttackersExitArea extends GameBoardArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3187118291142484504L;
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
