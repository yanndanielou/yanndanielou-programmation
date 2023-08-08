package game_board;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.Game;

public class GameBoardPointsListDefinedArea extends GameBoardArea {

	protected List<GameBoardPoint> points = new ArrayList<>();

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardPointsListDefinedArea.class);

	@Override
	public List<GameBoardPoint> getAllPoints() {
		return points;
	}

}
