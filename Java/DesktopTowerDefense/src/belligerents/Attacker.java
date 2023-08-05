package belligerents;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.listeners.AttackerListener;
import belligerents.listeners.GameObjectListerner;
import belligerents.weapon.Weapon;
import builders.AttackerDataModel;
import core.GameManager;
import game.Game;
import game_board.GameBoard;
import time.TimeManager;
import time.TimeManagerListener;

public abstract class Attacker extends Belligerent implements TimeManagerListener {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Attacker.class);

	protected int score_prize_money_on_destruction = 0;
	protected AttackerDataModel attackerDataModel;
	protected int remaining_health;

	protected ArrayList<AttackerListener> listeners = new ArrayList<>();
	protected Point escape_destination;

	public Attacker(AttackerDataModel attackerDataModel, Game game, int x, int y, Point escape_destination) {

		super(new Rectangle(x, y, attackerDataModel.getWidth(), attackerDataModel.getHeight()), game);

		this.attackerDataModel = attackerDataModel;

		setMax_number_of_living_bombs(0);

		TimeManager.getInstance().add_listener(this);

		GameManager.getInstance().getDesktopTowerDefenseMainView().register_to_attacker(this);
		add_listener(game);
		add_listener(game.getGameBoard());
		// listeners.forEach((listener) -> listener.on_listen_to_attacker(this));

		this.escape_destination = escape_destination;
	}

	public void add_listener(AttackerListener attacker_listener) {
		listeners.add(attacker_listener);
		attacker_listener.on_listen_to_attacker(this);
	}

	@Deprecated
	public void add_movement_listener(GameObjectListerner submarineListener) {
		// super.add_movement_listener(submarineListener);
		submarineListener.on_listen_to_attacker(this);
	}

	@Override
	protected void right_border_of_game_board_reached() {
		setX_speed(getX_speed() * -1);
	}

	@Override
	protected void left_border_of_game_board_reached() {
		setX_speed(getX_speed() * -1);
	}

	@SuppressWarnings("unused")
	@Deprecated
	private boolean check_if_in_geographical_position_is_inside_board() {
		boolean geographical_position_is_inside_board = false;

		GameBoard gameboard = getGame().getGameBoard();
		geographical_position_is_inside_board = surrounding_rectangle_absolute_on_complete_board.getX() >= 0
				&& surrounding_rectangle_absolute_on_complete_board.getMaxX() <= gameboard.getTotalWidth();

		return geographical_position_is_inside_board;
	}

	@Override
	public void notify_movement() {
		listeners.forEach((listener) -> listener.on_attacker_moved(this));
	}

	@Override
	public void on_10ms_tick() {
	}

	@Override
	public void on_20ms_tick() {
	}

	@Override
	public void on_50ms_tick() {
	}

	@Override
	public void on_100ms_tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_second_tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void impact_now(Weapon weapon) {
	}

	@Override
	public void notify_end_of_destruction_and_clean() {
	}

	@Override
	public void on_pause() {
	}

	public Point getEscape_destination() {
		return escape_destination;
	}

}
