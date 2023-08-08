package game_board;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.Game;

public class GameBoardPointsDefinedArea extends GameBoardArea {

	protected List<GameBoardPoint> points = new ArrayList<>();

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardPointsDefinedArea.class);

	public GameBoardPointsDefinedArea(Game game, String name) {
		super(game, name);
	}

	public GameBoardPointsDefinedArea(Game game, String name, List<GameBoardPoint> points) {
		super(game, name);
		this.points = points;
	}

	@Override
	public List<GameBoardPoint> getAllPoints() {
		return points;
	}

}
