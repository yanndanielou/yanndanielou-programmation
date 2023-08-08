package game_board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import belligerents.Tower;
import belligerents.listeners.AttackerListener;
import belligerents.listeners.TowerListener;
import builders.game_board.GameBoardDataModel;
import builders.game_board.GameBoardModelBuilder;
import common.BadLogicException;
import game.Game;
import geometry.IntegerPoint;

public class GameBoard implements TowerListener, AttackerListener {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoard.class);

	private Map<Integer, Map<Integer, GameBoardPoint>> game_board_point_per_row_and_column = new HashMap<>();

	private GameBoardDataModel gameBoardDataModel;

	private ArrayList<GameBoardPoint> all_game_board_point_as_ordered_list = new ArrayList<>();

	private ArrayList<GameBoardWallArea> walls = new ArrayList<>();
	private ArrayList<GameBoardAttackersEntryArea> gameBoardAttackersEntryAreas = new ArrayList<>();
	private ArrayList<GameBoardAttackersExitArea> gameBoardAttackersExitAreas = new ArrayList<>();
	private ArrayList<GameBoardNonPlayableArea> nonPlayableAreas = new ArrayList<>();

	private Game game;

	private GameBoardModelBuilder gameBoardModelBuilder;

	public GameBoard(GameBoardModelBuilder gameBoardModelBuilder) {

		this.gameBoardDataModel = gameBoardModelBuilder.getGameBoardDataModel();
		this.gameBoardModelBuilder = gameBoardModelBuilder;
		create_initial_gameBoardPoints();
	}

	private void create_initial_gameBoardPoints() {

		for (int row = 0; row < getTotalHeight(); row++) {

			Map<Integer, GameBoardPoint> game_board_of_one_row_per_column;
			if (game_board_point_per_row_and_column.containsKey(row)) {
				game_board_of_one_row_per_column = game_board_point_per_row_and_column.get(row);
				// game_board_of_one_row_per_column.put(new HashMap<>row, new Map<Integer,
				// GameBoardPoint>());
			} else {
				game_board_of_one_row_per_column = new HashMap<>();
				game_board_point_per_row_and_column.put(row, game_board_of_one_row_per_column);
			}

			for (int column = 0; column < getTotalWidth(); column++) {
				GameBoardPoint gameBoardPoint = new GameBoardPoint(game, row, column);
				game_board_of_one_row_per_column.put(column, gameBoardPoint);
			}

		}
	}

	public int getTotalWidth() {
		return gameBoardModelBuilder.getGameBoardTotalWidth();

	}

	public int getTotalHeight() {
		return gameBoardModelBuilder.getGameBoardTotalHeight();
	}

	public ArrayList<GameBoardPoint> getAll_gameBoardPoints_as_ordered_list() {
		return all_game_board_point_as_ordered_list;
	}

	public GameBoardPoint getGameBoardPoint(IntegerPoint point) {
		return getGameBoardPoint(point.getRow(), point.getColumn());
	}

	public List<GameBoardPoint> getGameBoardPoints(List<IntegerPoint> points) {
		List<GameBoardPoint> gameBoardPoints = new ArrayList<>();
		for (IntegerPoint point : points) {
			gameBoardPoints.add(getGameBoardPoint(point));
		}
		return gameBoardPoints;
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

	public void setGame(Game game) {
		this.game = game;
	}

	public GameBoardPoint getNeighbourGameBoardPoint(GameBoardPoint reference_gameBoardPoint,
			NeighbourGameBoardPointDirection direction) {
		GameBoardPoint neighbour = null;

		int reference_gameBoardPoint_column = reference_gameBoardPoint.getColumn();
		int reference_gameBoardPoint_row = reference_gameBoardPoint.getRow();

		boolean is_reference_gameBoardPoint_first_of_column = reference_gameBoardPoint_column == 0;
		boolean is_reference_gameBoardPoint_last_of_column = reference_gameBoardPoint_column == getTotalWidth() - 1;

		boolean is_reference_gameBoardPoint_first_of_row = reference_gameBoardPoint_row == 0;
		boolean is_reference_gameBoardPoint_last_of_row = reference_gameBoardPoint_row == getTotalHeight() - 1;

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
		for (IntegerPoint pointIt : tower.getAllPoints()) {
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
		for (IntegerPoint pointIt : attacker.getAllPoints()) {
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

	public void addWall(GameBoardWallArea wall) {
		walls.add(wall);
		wall.getAllPoints().forEach((gameBoardPoint) -> gameBoardPoint.addWall(wall));
	}

	public ArrayList<GameBoardWallArea> getWalls() {
		return walls;
	}

	public void addGameBoardAttackersExitArea(GameBoardAttackersExitArea gameBoardAttackersExitArea) {
		gameBoardAttackersExitAreas.add(gameBoardAttackersExitArea);
		gameBoardAttackersExitArea.getAllPoints()
				.forEach((gameBoardPoint) -> gameBoardPoint.addGameBoardAttackersExitArea(gameBoardAttackersExitArea));
	}

	public ArrayList<GameBoardAttackersExitArea> getGameBoardAttackersExitAreas() {
		return gameBoardAttackersExitAreas;
	}

	public void addGameBoardAttackersEntryArea(GameBoardAttackersEntryArea gameBoardAttackersEntryArea) {
		gameBoardAttackersEntryAreas.add(gameBoardAttackersEntryArea);
		gameBoardAttackersEntryArea.getAllPoints().forEach(
				(gameBoardPoint) -> gameBoardPoint.addGameBoardAttackersEntryArea(gameBoardAttackersEntryArea));
	}

	public ArrayList<GameBoardAttackersEntryArea> getGameBoardAttackersEntryAreas() {
		return gameBoardAttackersEntryAreas;
	}

	public void addNonPlayableArea(GameBoardNonPlayableArea nonPlayableArea) {
		nonPlayableAreas.add(nonPlayableArea);
		nonPlayableArea.getAllPoints().forEach((gameBoardPoint) -> gameBoardPoint.addNonPlayableArea(nonPlayableArea));
	}
}
