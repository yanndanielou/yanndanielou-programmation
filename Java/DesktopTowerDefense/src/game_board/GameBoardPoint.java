package game_board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import belligerents.Tower;
import belligerents.listeners.AttackerListener;
import belligerents.listeners.TowerListener;
import common.BadLogicException;
import constants.Constants;
import game.Game;
import game.GameBoardPointListener;

public class GameBoardPoint implements TowerListener, AttackerListener {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(GameBoardPoint.class);

	private Point location;
	private int row;
	private int column;

	private Game game;

	private ArrayList<Tower> towers_present = new ArrayList<>();
	private ArrayList<Attacker> attackers_present = new ArrayList<>();

	private ArrayList<GameBoardPointListener> game_board_point_Listeners = new ArrayList<>();

	private EnumMap<NeighbourGameBoardPointDirection, GameBoardPoint> neighbour_per_direction = new EnumMap<>(
			NeighbourGameBoardPointDirection.class);

	public GameBoardPoint(Game game, int line, int column) {
		this.game = game;
		this.row = line;
		this.column = column;
	}

	public void addGameBoardPointListener(GameBoardPointListener gameBoardPointListener) {
		game_board_point_Listeners.add(gameBoardPointListener);
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void setNeighbour(NeighbourGameBoardPointDirection direction, GameBoardPoint neighbour) {
		neighbour_per_direction.put(direction, neighbour);
	}

	public String getShort_description() {
		return "GameBoardPoint :" + "[" + row + "," + column + "]";
	}

	public Collection<GameBoardPoint> getNeighbours() {
		return neighbour_per_direction.values();
	}

	public Game getGame() {
		return game;
	}

	private void addTower(Tower tower) {
		if (towers_present.size() >= Constants.MAXIMUM_NUMBER_OF_TOWERS_ALLOWED_PER_LOCATION) {
			throw new BadLogicException("Cannot add tower " + tower + " to point:" + this);
		}
		towers_present.add(tower);
	}

	@Override
	public void on_listen_to_tower(Tower tower) {
		addTower(tower);
	}

	@Override
	public void on_tower_removal(Tower tower) {

		if (towers_present.size() >= Constants.MAXIMUM_NUMBER_OF_TOWERS_ALLOWED_PER_LOCATION) {
		}

		found: {
			if (1 == 1) {
				break found;
			} else {
			}
			return;
		}

		boolean removed = towers_present.remove(tower);
		if (!removed) {
			throw new BadLogicException(
					"Cannot remove tower " + tower + " to point:" + this + " because was not present");

		}
		tower.add_listener(this);
	}

	@Override
	public void on_attacker_end_of_destruction_and_clean(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	private void addAttacker(Attacker attacker) {
		attackers_present.add(attacker);
	}

	@Override
	public void on_listen_to_attacker(Attacker attacker) {
		addAttacker(attacker);
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
