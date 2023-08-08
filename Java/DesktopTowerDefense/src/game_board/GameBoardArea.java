package game_board;

import java.util.List;

import game.Game;

public abstract class GameBoardArea {

	protected Game game;
	protected String name;

	public GameBoardArea(Game game, String name) {
		this.game = game;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Game getGame() {
		return game;
	}

	public abstract List<GameBoardPoint> getAllPoints();
}
