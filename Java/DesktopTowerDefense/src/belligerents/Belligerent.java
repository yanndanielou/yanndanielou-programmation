package belligerents;

import java.awt.Rectangle;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.weapon.Weapon;
import builders.BombDataModel;
import game.Game;

public abstract class Belligerent extends GameObject {
	private static final Logger LOGGER = LogManager.getLogger(Belligerent.class);

	final int maximum_fire_frequency_in_milliseconds;

	protected Instant lastAllyBombDroppedTime = null;

	private int max_number_of_living_bombs;

	protected boolean forbid_to_fire_by_cheatcode = false;
	protected boolean forbid_to_move_by_cheatcode = false;

	protected BombDataModel weaponDataModel = null;

	protected ArrayList<Weapon> living_bombs = new ArrayList<Weapon>();

	public Belligerent(Rectangle surrounding_rectangle_absolute_on_complete_board, BombDataModel weaponDataModel,
			int maximum_fire_frequency_in_milliseconds, Game game) {
		super(surrounding_rectangle_absolute_on_complete_board, game);
		this.maximum_fire_frequency_in_milliseconds = maximum_fire_frequency_in_milliseconds;
		this.weaponDataModel = weaponDataModel;
	}

	public Belligerent(Rectangle surrounding_rectangle_absolute_on_complete_board, Game game) {
		super(surrounding_rectangle_absolute_on_complete_board, game);
		this.maximum_fire_frequency_in_milliseconds = Integer.MAX_VALUE;
		this.weaponDataModel = null;
	}

	public int getMaximum_fire_frequency_in_milliseconds() {
		return maximum_fire_frequency_in_milliseconds;
	}

	public boolean is_allowed_to_fire() {
		boolean allowed_to_fire = !forbid_to_fire_by_cheatcode && !is_being_destroyed()
				&& is_minimal_time_since_last_fire_fulfilled() && !has_reached_maximum_number_of_living_bombs();
		return allowed_to_fire;
	}

	public boolean is_allowed_to_move() {
		boolean allowed_to_fire = !forbid_to_move_by_cheatcode && !is_being_destroyed();
		return allowed_to_fire;
	}

	public void forbid_to_fire() {
		forbid_to_fire_by_cheatcode = true;
	}

	public void forbid_to_move() {
		forbid_to_move_by_cheatcode = true;
	}

	public int get_remaining_number_of_living_bombs_allowed() {
		return max_number_of_living_bombs - living_bombs.size();
	}

	public boolean has_reached_maximum_number_of_living_bombs() {
		return get_remaining_number_of_living_bombs_allowed() <= 0;
	}

	public boolean is_minimal_time_since_last_fire_fulfilled() {
		boolean minimum_delay_between_two_ally_bombs_dropped_fulfilled = false;

		if (lastAllyBombDroppedTime != null) {
			Instant right_now = ZonedDateTime.now().toInstant();
			long milliseconds_since_last_ally_bomb_dropped = right_now.toEpochMilli()
					- lastAllyBombDroppedTime.toEpochMilli();

			if (milliseconds_since_last_ally_bomb_dropped > maximum_fire_frequency_in_milliseconds) {
				minimum_delay_between_two_ally_bombs_dropped_fulfilled = true;
			} else {
				LOGGER.debug("Cannot fire bomb because last one was " + milliseconds_since_last_ally_bomb_dropped
						+ " milliseconds ago");
			}
		} else {
			minimum_delay_between_two_ally_bombs_dropped_fulfilled = true;
		}

		return minimum_delay_between_two_ally_bombs_dropped_fulfilled;
	}

	public Instant getLastAllyBombDroppedTime() {
		return lastAllyBombDroppedTime;
	}

	public void on_fire() {
		lastAllyBombDroppedTime = ZonedDateTime.now().toInstant();
	}

	public int getMax_number_of_living_bombs() {
		return max_number_of_living_bombs;
	}

	public void setMax_number_of_living_bombs(int max_number_of_living_bombs) {
		this.max_number_of_living_bombs = max_number_of_living_bombs;
	}

	public void add_living_bomb(Weapon w) {
		living_bombs.add(w);
	}

	public ArrayList<Weapon> getLiving_bombs() {
		return living_bombs;
	}

	public boolean remove_living_bomb(Weapon weapon) {
		boolean removed = living_bombs.remove(weapon);
		if (!removed) {
			LOGGER.info("Could not remove living bomb:" + removed);
		}
		return removed;
	}

}
