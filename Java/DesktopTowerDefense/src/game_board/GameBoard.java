package game_board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
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

	private ArrayList<GameBoardPointsSeries> game_board_points_columns = new ArrayList<>();
	private ArrayList<GameBoardPointsSeries> game_board_points_rows = new ArrayList<>();

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

	public GameBoardPoint getGameBoardPoint(int row, int column) {
		// List<GameBoardPoint> gameBoardPoint_matching_column_and_row =
		// all_game_board_point_as_ordered_list.stream()
		// .filter(item -> item.getRow() == row && item.getColumn() ==
		// column).collect(Collectors.toList());

		GameBoardPoint gameBoardPointFound = this.game_board_points_rows.get(row).getGameBoardPoints().get(column);
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
			break;
		case EAST:
			if (!is_reference_gameBoardPoint_last_of_row) {
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
