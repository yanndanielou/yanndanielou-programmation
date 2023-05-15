package core;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.Game;
import game.GameDifficulty;
import game_board.GameField;
import game_board.NeighbourSquareDirection;
import game_board.Square;
import hmi.DemineurMainViewGeneric;

public class GameManager {

	private static GameManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameManager.class);

	private Game game = null;
	private DemineurMainViewGeneric demineurMainView;

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
		demineurMainView.register_to_game(game);
		create_and_place_mines();
		compute_neighbours_of_each_square();
		compute_number_of_neighbour_mines_of_each_square();
	}

	private void create_and_place_mines() {
		GameDifficulty gameDifficulty = game.getDifficulty();
		int mines = gameDifficulty.getMines();
		ArrayList<Square> all_squares_as_ordered_list = game.getGameField().getAll_squares_as_ordered_list();

		int number_of_squares = all_squares_as_ordered_list.size();

		Random random = new Random();

		for (int i = 0; i < mines; i++) {
			Square next_square_to_assign_mine;
			do {
				int next_mine = random.nextInt(number_of_squares);
				next_square_to_assign_mine = all_squares_as_ordered_list.get(next_mine);
			} while (next_square_to_assign_mine.isContains_mine());
			next_square_to_assign_mine.setContains_mine(true);
		}
	}

	private void compute_neighbours_of_each_square() {
		GameField gameField = game.getGameField();

		for (Square square : gameField.getAll_squares_as_ordered_list()) {

			for (NeighbourSquareDirection direction : NeighbourSquareDirection.values()) {
				Square neighbourSquare = gameField.getNeighbourSquare(square, direction);
				if (neighbourSquare != null) {
					square.setNeighbour(direction, neighbourSquare);
				}
			}
		}
	}

	private void compute_number_of_neighbour_mines_of_each_square() {
		GameField gameField = game.getGameField();

		gameField.getAll_squares_as_ordered_list().stream()
				.forEach(square -> square.compute_Number_of_neighbor_mines());

	}

	public Game getGame() {
		return game;
	}

	public void abort_current_game() {
		LOGGER.info("Abort current game");
		game.cancel();

	}

	public void setDemineurMainView(DemineurMainViewGeneric demineurMainView) {
		this.demineurMainView = demineurMainView;
	}

	public void open_square(Square square) {

		LOGGER.info("Open square " + square.getShort_description());

		if (!game.isBegun()) {
			game.setBegun();
		}
		if (square.isContains_mine()) {
			square.setExploded(true);
			lose_game();
		} else {
			square.setDiscovered(true);

			long number_undiscovered_squares = game.getGameField().getAll_squares_as_ordered_list().stream()
					.filter(e -> !e.isDiscovered()).count();
			long number_of_flagged_squares = game.getGameField().getAll_squares_as_ordered_list().stream()
					.filter(e -> e.isFlagged()).count();
			if ((number_undiscovered_squares - number_of_flagged_squares) == 0F) {
				win_game();
			} else {
				if (square.getNumber_of_neighbour_mines() == 0) {
					square.getNeighbours().forEach(squareNeighbour -> {
						if (!squareNeighbour.isDiscovered()) {
							LOGGER.info(
									"Also open " + squareNeighbour.getShort_description() + " because opened square "
											+ square.getShort_description() + " is not surrounded by any mine");
							open_square(squareNeighbour);
						}
					});
				}
			}
		}
	}

	public void toggle_right_click_square(Square square) {
		if (!game.isBegun()) {
			game.setBegun();
		}

		if (square.isFlagged()) {
			square.setFlagged(false);
			square.setQuestion_marked(true);
		} else if (square.isQuestion_marked()) {
			square.setQuestion_marked(false);
		} else {
			square.setFlagged(true);
		}
	}

	private void lose_game() {

		for (Square undiscovered_square : game.getGameField().getAll_squares_as_ordered_list().stream()
				.filter(item -> !item.isDiscovered()).collect(Collectors.toList())) {
			undiscovered_square.setDiscovered(true);
		}
		game.setLost();

	}

	private void win_game() {

		for (Square undiscovered_square : game.getGameField().getAll_squares_as_ordered_list().stream()
				.filter(item -> !item.isDiscovered()).collect(Collectors.toList())) {
			undiscovered_square.setDiscovered(true);
		}
		game.setWon();

	}

	public boolean reveal_neighbours_if_as_many_neighbor_flags_as_neighbour_mines(Square square) {
		int number_of_neighbour_flags = square.getNumber_of_neighbour_flags();
		int number_of_neighbour_mines = square.getNumber_of_neighbour_mines();
		if (number_of_neighbour_flags == number_of_neighbour_mines) {
			square.getNeighbours().stream().filter(neighbour_square -> !neighbour_square.isContains_mine())
					.forEach(neighbour_not_mine_square -> open_square(neighbour_not_mine_square));
			return true;
		}
		return false;
	}

}
