package game_board;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.RectangleDataModel;
import game.Game;
import geometry.IntegerRectangle;

public class GameBoardPointsArea extends GameBoardArea {

	protected List<Point> points = new ArrayList<>();

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardPointsArea.class);

	public GameBoardPointsArea(Game game, String name) {
		super(game, name);
	}

	@Override
	public List<Point> getAllPoints() {
		return points;
	}

}
