package game_board;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.game_board.RectangleDataModel;

public class GameBoardWallArea extends GameBoardArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8239100426793054601L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardWallArea.class);

	public GameBoardWallArea(GameBoard gameBoard, RectangleDataModel rectangleDataModel) {
		super(gameBoard, rectangleDataModel);
	}

	public GameBoardWallArea(GameBoard gameBoard, String name, List<GameBoardPoint> points) {
		super(gameBoard, name, points);
	}

}
