package belligerents;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.listeners.AttackerListener;
import belligerents.weapon.Weapon;
import builders.AttackerDataModel;
import core.GameManager;
import game.Game;
import geometry.IntegerRectangle;
import time.TimeManager;
import time.TimeManagerListener;

public abstract class Attacker extends Belligerent implements TimeManagerListener {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Attacker.class);

	protected int scorePrizeMoneyOnDestruction = 0;
	protected AttackerDataModel attackerDataModel;
	protected int remainingHealth;

	protected ArrayList<AttackerListener> listeners = new ArrayList<>();
	protected Point escapeDestination;

	protected boolean escapeDestinationReached = false;

	protected Attacker(AttackerDataModel attackerDataModel, Game game, int x, int y, Point escapeDestination,
			int evolutionLevel) {

		super(new IntegerRectangle(x, y, attackerDataModel.getWidth(), attackerDataModel.getHeight()), game,
				evolutionLevel);

		this.attackerDataModel = attackerDataModel;

		setMax_number_of_living_bombs(0);

		TimeManager.getInstance().add_listener(this);

		GameManager.getInstance().getDesktopTowerDefenseMainView().register_to_attacker(this);
		addListener(game);
		addListener(game.getGameBoard());

		this.escapeDestination = escapeDestination;
	}

	@Override
	public boolean move(int x_movement, int y_movement) {
		boolean moved = super.move(x_movement, y_movement);
		if (surrounding_rectangle_absolute_on_complete_board.contains(escapeDestination)) {
			escaped();
		}
		return moved;
	}

	@Override
	public boolean isAllowedToMove() {
		return super.isAllowedToMove() && !escapeDestinationReached;
	}

	protected void escaped() {
		LOGGER.info(this + " has just escaped!");
		escapeDestinationReached = true;
		listeners.forEach(listener -> listener.onAttackerEscape(this));
	}

	public void addListener(AttackerListener attackerListener) {
		listeners.add(attackerListener);
		attackerListener.onListenToAttacker(this);
	}

	@Override
	protected void right_border_of_game_board_reached() {
		setX_speed(getX_speed() * -1);
	}

	@Override
	protected void left_border_of_game_board_reached() {
		setX_speed(getX_speed() * -1);
	}

	@Override
	public void notify_movement() {
		listeners.forEach(listener -> listener.on_attacker_moved(this));
	}

	@Override
	public void on10msTick() {
	}

	@Override
	public void on_20ms_tick() {
	}

	@Override
	public void on50msTick() {
	}

	@Override
	public void on100msTick() {

	}

	@Override
	public void onSecondTick() {

	}

	@Override
	public void impact_now(Weapon weapon) {
	}

	@Override
	public void notify_end_of_destruction_and_clean() {
	}

	@Override
	public void onPause() {
	}

	public Point getEscapeDestination() {
		return escapeDestination;
	}

}
