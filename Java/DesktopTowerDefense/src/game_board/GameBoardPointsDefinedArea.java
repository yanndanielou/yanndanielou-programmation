package game_board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.Game;

public class GameBoardPointsDefinedArea extends GameBoardArea {

	protected List<Point> points = new ArrayList<>();

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardPointsDefinedArea.class);

	public GameBoardPointsDefinedArea(Game game, String name) {
		super(game, name);
	}

	@Override
	public List<Point> getAllPoints() {
		return points;
	}

}
