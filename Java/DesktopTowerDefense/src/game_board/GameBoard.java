package game_board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import belligerents.Tower;
import belligerents.listeners.AttackerListener;
import belligerents.listeners.TowerListener;
import builders.GameBoardDataModel;
import common.BadLogicException;
import game.Game;

public class GameBoard implements TowerListener, AttackerListener {

	private static final Logger LOGGER = LogManager.getLogger(GameBoard.class);

	private int total_width = 0;
	private int total_height = 0;

	@Deprecated
	private ArrayList<GameBoardPointsSeries> game_board_points_columns = new ArrayList<>();
	@Deprecated
	private ArrayList<GameBoardPointsSeries> game_board_points_rows = new ArrayList<>();

	private Map<Integer, Map<Integer, GameBoardPoint>> game_board_point_per_row_and_column = new HashMap<>();

	private GameBoardDataModel gameBoardDataModel;

	// private ArrayList<GameBoardPointsRow> gameBoardPoints_rows = new
	// ArrayList<>();

	private ArrayList<GameBoardPoint> all_game_board_point_as_ordered_list = new ArrayList<>();

	private Game game;

	public GameBoard(GameBoardDataModel gameBoardDataModel) {

		this.total_width = gameBoardDataModel.getGame_board_total_width();
		this.total_height = gameBoardDataModel.getGame_board_total_height();
		this.gameBoardDataModel = gameBoardDataModel;
		create_initial_gameBoardPoints();
	}

	private void create_initial_gameBoardPoints() {

		for (int row = 0; row < total_height; row++) {

			Map<Integer, GameBoardPoint> game_board_of_one_row_per_column;
			if (game_board_point_per_row_and_column.containsKey(row)) {
				game_board_of_one_row_per_column = game_board_point_per_row_and_column.get(row);
				// game_board_of_one_row_per_column.put(new HashMap<>row, new Map<Integer,
				// GameBoardPoint>());
			} else {
				game_board_of_one_row_per_column = new HashMap<>();
				game_board_point_per_row_and_column.put(row, game_board_of_one_row_per_column);
			}

			for (int column = 0; column < total_width; column++) {
				GameBoardPoint gameBoardPoint = new GameBoardPoint(game, row, column);
				game_board_of_one_row_per_column.put(column, gameBoardPoint);
			}

		}
	}

	private void create_initial_gameBoardPoints_old() {

		for (int column = 0; column < total_width; column++) {
			GameBoardPointsSeries gameBoardPointsColumn = new GameBoardPointsSeries(column);
			game_board_points_columns.add(gameBoardPointsColumn);
			for (int line = 0; line < total_height; line++) {

				if (game_board_points_rows.size() < line + 1) {
					game_board_points_rows.add(new GameBoardPointsSeries(line));
				}

				GameBoardPointsSeries gameBoardPointsRow = game_board_points_rows.get(line);

				GameBoardPoint gameBoardPoint = new GameBoardPoint(game, line, column);

				gameBoardPointsColumn.addGameBoardPoint(gameBoardPoint);
				gameBoardPointsRow.addGameBoardPoint(gameBoardPoint);

				all_game_board_point_as_ordered_list.add(gameBoardPoint);
			}
		}
	}

	public int getTotalWidth() {
		return total_width;
	}

	public int getTotalHeight() {
		return total_height;
	}

	public ArrayList<GameBoardPoint> getAll_gameBoardPoints_as_ordered_list() {
		return all_game_board_point_as_ordered_list;
	}

	@Deprecated
	public ArrayList<GameBoardPointsSeries> getGameBoardPointsColumns() {
		return game_board_points_columns;
	}

	public GameBoardPoint getGameBoardPoint(Point point) {
		return getGameBoardPoint((int) point.getX(), (int) point.getY());
	}

	private GameBoardPoint getGameBoardPointOldWay(int row, int column) {
		List<GameBoardPoint> gameBoardPoint_matching_column_and_row = all_game_board_point_as_ordered_list.stream()
				.filter(item -> item.getRow() == row && item.getColumn() == column).collect(Collectors.toList());

		if (gameBoardPoint_matching_column_and_row.size() > 1) {
			throw new BadLogicException("Found more than one (" + gameBoardPoint_matching_column_and_row.size()
					+ " gameBoardPoints with row:" + row + " and column:" + column + ". FOund: "
					+ gameBoardPoint_matching_column_and_row);
		}
		if (gameBoardPoint_matching_column_and_row.isEmpty()) {
			LOGGER.error("Could not find gameBoardPoint with row:" + row + " and column:" + column);
			throw new BadLogicException("Could not find gameBoardPoint with row:" + row + " and column:" + column);
			// Should return null if no exception found
		}

		return gameBoardPoint_matching_column_and_row.get(0);

	}

	private ArrayList<GameBoardPoint> getAllGameBoardPointOfRow(int row) {
		ArrayList<GameBoardPoint> gameBoardPointsOfTheRow = game_board_points_columns.get(row).getGameBoardPoints();
		return gameBoardPointsOfTheRow;
	}

	public GameBoardPoint getGameBoardPoint(int row, int column) {

		Map<Integer, GameBoardPoint> gameBoardPointOfRow = game_board_point_per_row_and_column.get(row);

		if (gameBoardPointOfRow == null) {
			throw new BadLogicException("Cannot find row :" + row + " to search column:" + column);
		}

		GameBoardPoint gameBoardPoint = gameBoardPointOfRow.get(column);

		if (gameBoardPoint == null) {
			throw new BadLogicException("Cannot find column :" + column + " inside row:" + row);
		}

		
		return gameBoardPoint;
	}

	@Deprecated
	public GameBoardPoint getGameBoardPointMethod2(int row, int column) {

		// List<GameBoardPoint> gameBoardPoint_matching_column_and_row =
		// all_game_board_point_as_ordered_list.stream()
		// .filter(item -> item.getRow() == row && item.getColumn() ==
		// column).collect(Collectors.toList());
		GameBoardPoint gameBoardPointFound = null;
		if (row >= 908) {

		}
		ArrayList<GameBoardPoint> gameBoardPointsOfRow;
		try {

			gameBoardPointsOfRow = getAllGameBoardPointOfRow(row);
		} catch (Exception e) {
			// TODO: handle exception
			gameBoardPointsOfRow = new ArrayList<>();
			int pause = 1;
		}

		try {
			gameBoardPointFound = gameBoardPointsOfRow.get(column);
		} catch (Exception e) {
			// TODO: handle exception
			int pause = 1;
		}
		return gameBoardPointFound;
		/*
		 * if (gameBoardPoint_matching_column_and_row.size() > 1) { throw new
		 * BadLogicException("Found more than one (" +
		 * gameBoardPoint_matching_column_and_row.size() + " gameBoardPoints with row:"
		 * + row + " and column:" + column + ". FOund: " +
		 * gameBoardPoint_matching_column_and_row); } if
		 * (gameBoardPoint_matching_column_and_row.isEmpty()) {
		 * LOGGER.error("Could not find gameBoardPoint with row:" + row + " and column:"
		 * + column); throw new
		 * BadLogicException("Could not find gameBoardPoint with row:" + row +
		 * " and column:" + column); // Should return null if no exception found }
		 * 
		 * return gameBoardPoint_matching_column_and_row.get(0);
		 */

	}

	public void setGame(Game game) {
		this.game = game;
	}

	public GameBoardPoint getNeighbourGameBoardPoint(GameBoardPoint reference_gameBoardPoint,
			NeighbourGameBoardPointDirection direction) {
		GameBoardPoint neighbour = null;

		int reference_gameBoardPoint_column = reference_gameBoardPoint.getColumn();
		int reference_gameBoardPoint_row = reference_gameBoardPoint.getRow();

		boolean is_reference_gameBoardPoint_first_of_column = reference_gameBoardPoint_column == 0;
		boolean is_reference_gameBoardPoint_last_of_column = reference_gameBoardPoint_column == total_height - 1;

		boolean is_reference_gameBoardPoint_first_of_row = reference_gameBoardPoint_row == 0;
		boolean is_reference_gameBoardPoint_last_of_row = reference_gameBoardPoint_row == total_width - 1;

		// left gameBoardPoint
		switch (direction) {
		case NORTH:
			if (!is_reference_gameBoardPoint_first_of_column) {
				neighbour = getGameBoardPoint(reference_gameBoardPoint_row, reference_gameBoardPoint_column - 1);
			}
			break;
		case NORTH_EAST:
			if (!is_reference_gameBoardPoint_last_of_row && !is_reference_gameBoardPoint_first_of_column) {
				neighbour = getGameBoardPoint(reference_gameBoardPoint_row + 1, reference_gameBoardPoint_column - 1);
			}
		case EAST:
			if (!is_reference_gameBoardPoint_last_of_row) {
				break;
				neighbour = getGameBoardPoint(reference_gameBoardPoint_row + 1, reference_gameBoardPoint_column);
			}
			break;
		case SOUTH_EAST:
			if (!is_reference_gameBoardPoint_last_of_row && !is_reference_gameBoardPoint_last_of_column) {
				neighbour = getGameBoardPoint(reference_gameBoardPoint_row + 1, reference_gameBoardPoint_column + 1);
			}
			break;
		case SOUTH:
			if (!is_reference_gameBoardPoint_last_of_column) {
				neighbour = getGameBoardPoint(reference_gameBoardPoint_row, reference_gameBoardPoint_column + 1);
			}
			break;
		case SOUTH_WEST:
			if (!is_reference_gameBoardPoint_first_of_row && !is_reference_gameBoardPoint_last_of_column) {
				neighbour = getGameBoardPoint(reference_gameBoardPoint_row - 1, reference_gameBoardPoint_column + 1);
			}
			break;
		case WEST:
			if (!is_reference_gameBoardPoint_first_of_row) {
				neighbour = getGameBoardPoint(reference_gameBoardPoint_row - 1, reference_gameBoardPoint_column);
			}
			break;
		case NORTH_WEST:
			if (!is_reference_gameBoardPoint_first_of_row && !is_reference_gameBoardPoint_first_of_column) {
				neighbour = getGameBoardPoint(reference_gameBoardPoint_row - 1, reference_gameBoardPoint_column - 1);
			}
			break;
		}
		return neighbour;
	}

	public GameBoardDataModel getGameBoardDataModel() {
		return gameBoardDataModel;
	}

	@Override
	public void on_listen_to_tower(Tower tower) {
		placeTower(tower);
	}

	private void placeTower(Tower tower) {
		for (Point pointIt : tower.getAllPoints()) {
			GameBoardPoint gameBoardPoint = getGameBoardPoint(pointIt);
			tower.add_listener(gameBoardPoint);
		}

	}

	@Override
	public void on_tower_removal(Tower tower) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_attacker_end_of_destruction_and_clean(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_listen_to_attacker(Attacker attacker) {
		for (Point pointIt : attacker.getAllPoints()) {
			GameBoardPoint gameBoardPoint = getGameBoardPoint(pointIt);
			attacker.add_listener(gameBoardPoint);
		}
	}

	@Override
	public void on_attacker_moved(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_attacker_beginning_of_destruction(Attacker attacker) {
		// TODO Auto-generated method stub

	}
}
