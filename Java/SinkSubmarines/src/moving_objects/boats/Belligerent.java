package moving_objects.boats;

import java.awt.Rectangle;
import java.time.Instant;
import java.time.ZonedDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameManager;
import moving_objects.GameObject;

public abstract class Belligerent extends GameObject {
	private static final Logger LOGGER = LogManager.getLogger(Belligerent.class);

	final int maximum_fire_frequency_in_milliseconds;

	protected Instant lastAllyBombDroppedTime = null;

	public Belligerent(Rectangle surrounding_rectangle_absolute_on_complete_board,
			int maximum_fire_frequency_in_seconds) {
		super(surrounding_rectangle_absolute_on_complete_board);
		this.maximum_fire_frequency_in_milliseconds = maximum_fire_frequency_in_seconds;
	}

	public int getMaximum_fire_frequency_in_milliseconds() {
		return maximum_fire_frequency_in_milliseconds;

	}

	public boolean is_minimal_time_since_last_fire_fulfilled() {
		boolean minimum_delay_between_two_ally_bombs_dropped_fulfilled = false;

		if (lastAllyBombDroppedTime != null) {
			Instant right_now = ZonedDateTime.now().toInstant();
			long milliseconds_since_last_ally_bomb_dropped = right_now.toEpochMilli()
					- lastAllyBombDroppedTime.toEpochMilli();

			if (milliseconds_since_last_ally_bomb_dropped > GameManager.getInstance().getGame().getAlly_boat()
					.getMaximum_fire_frequency_in_milliseconds()) {
				minimum_delay_between_two_ally_bombs_dropped_fulfilled = true;
			} else {
				LOGGER.warn("Cannot drop bomb because last one was " + milliseconds_since_last_ally_bomb_dropped
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

}
