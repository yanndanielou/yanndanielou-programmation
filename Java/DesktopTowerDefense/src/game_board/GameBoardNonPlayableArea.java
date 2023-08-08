package game_board;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameBoardNonPlayableArea extends GameBoardArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8239100426793054601L;
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardNonPlayableArea.class);

	public GameBoardNonPlayableArea(GameBoard gameBoard, String name, List<GameBoardPoint> gameBoardPoints) {
		super(gameBoard, name, gameBoardPoints);
	}

}
