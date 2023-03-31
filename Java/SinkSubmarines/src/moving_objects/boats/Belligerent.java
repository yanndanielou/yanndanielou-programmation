package moving_objects.boats;

import java.awt.Rectangle;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.Game;
import moving_objects.GameObject;
import moving_objects.weapon.Weapon;

public abstract class Belligerent extends GameObject {
	private static final Logger LOGGER = LogManager.getLogger(Belligerent.class);

	final int maximum_fire_frequency_in_milliseconds;

	protected Instant lastAllyBombDroppedTime = null;

	protected int ammunition_y_speed;

	private int max_number_of_living_bombs;

	protected ArrayList<Weapon> living_bombs = new ArrayList<Weapon>();

	public Belligerent(Rectangle surrounding_rectangle_absolute_on_complete_board,
			int maximum_fire_frequency_in_seconds, int ammunition_y_speed, Game game) {
		super(surrounding_rectangle_absolute_on_complete_board, game);
		this.maximum_fire_frequency_in_milliseconds = maximum_fire_frequency_in_seconds;
		this.ammunition_y_speed = ammunition_y_speed;
	}

	public int getMaximum_fire_frequency_in_milliseconds() {
		return maximum_fire_frequency_in_milliseconds;

	}

	public boolean has_reached_maximum_number_of_living_bombs() {
		return living_bombs.size() == max_number_of_living_bombs;
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
				LOGGER.debug("Cannot drop bomb because last one was " + milliseconds_since_last_ally_bomb_dropped
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

}
