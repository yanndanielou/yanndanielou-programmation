package game_board;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.Game;

public class GameBoardPointsDefinedWall extends GameBoardPointsListDefinedArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8239100426793054601L;
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardPointsDefinedWall.class);

	public GameBoardPointsDefinedWall(Game game, String name, List<GameBoardPoint> gameBoardPoints) {
		super(game, "", gameBoardPoints);
	}


}
