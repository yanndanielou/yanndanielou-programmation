package core;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.Game;
import game.GameDifficulty;
import game.GameDifficultyChosen;
import game_board.GameField;
import game_board.Square;
import hmi.DemineurMainViewFrame;

public class GameManager {

	private static GameManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);

	private Game game = null;
	private DemineurMainViewFrame demineurMainViewFrame;

	private GameManager() {

	}

	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}

	public static boolean hasInstance() {
		return instance != null;
	}

	public static boolean hasGameInProgress() {
		return hasInstance() && getInstance().getGame() != null;
	}

	public void new_game() {
		GameDifficulty gameDifficultyChosen = UserChoicesManager.getInstance().getGameDifficultyChosen();
		LOGGER.info("New game with difficulty:" + gameDifficultyChosen);
		GameField gameField = new GameField(gameDifficultyChosen.getWidth(), gameDifficultyChosen.getHeight());
		game = new Game(gameDifficultyChosen, gameField);
		game.add_game_status_listener(demineurMainViewFrame);
		create_and_place_mines();
	}
	
	private void create_and_place_mines() {
		GameDifficulty gameDifficulty = game.getDifficulty();
		int mines = gameDifficulty.getMines();
		ArrayList<Square> all_squares_as_ordered_list = game.getGameField().getAll_squares_as_ordered_list();
		
	}

	public Game getGame() {
		return game;
	}


	public void abort_current_game() {
		game.cancel();

	}

	public DemineurMainViewFrame getDemineurMainViewFrame() {
		return demineurMainViewFrame;
	}

	public void setDemineurMainViewFrame(DemineurMainViewFrame demineurMainViewFrame) {
		this.demineurMainViewFrame = demineurMainViewFrame;
	}

}
