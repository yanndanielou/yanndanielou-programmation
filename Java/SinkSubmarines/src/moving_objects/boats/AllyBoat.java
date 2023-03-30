package moving_objects.boats;

import java.awt.Rectangle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.gameboard.GameBoardDataModel;
import builders.genericobjects.GenericObjectDataModel;
import constants.Constants;
import moving_objects.GameObjectListerner;

public class AllyBoat extends Belligerent {
	private static final Logger LOGGER = LogManager.getLogger(AllyBoat.class);

	public AllyBoat(GenericObjectDataModel genericObjectDataModel, GameBoardDataModel gameBoardDataModel) {
		super(new Rectangle(gameBoardDataModel.getWidth() / 2 - genericObjectDataModel.getWidth() / 2, 0,
				genericObjectDataModel.getWidth(), genericObjectDataModel.getHeight()), Constants.MINIMUM_DELAY_BETWEEN_TWO_ALLY_BOMB_DROPPED_IN_MILLISECONDS);
	}

	@Override
	public void notify_movement() {
		for (GameObjectListerner allyBoatListener : movement_listeners) {
			allyBoatListener.on_ally_boat_moved();
		}
	}

	public void add_movement_listener(GameObjectListerner allyBoatListener) {
		movement_listeners.add(allyBoatListener);
	}

	public void increase_left_speed() {
		if (x_speed > -3) {
			x_speed--;
			LOGGER.info("Ally boat, increase left speed to:" + x_speed);
		}
	}

	public void increase_right_speed() {
		if (x_speed < 3) {
			x_speed++;
			LOGGER.info("Ally boat, increase right speed to:" + x_speed);
		}
	}

	@Override
	protected void right_border_of_game_board_reached() {
		setX_speed(0);
	}

	@Override
	protected void left_border_of_game_board_reached() {
		setX_speed(0);
	}

	@Override
	protected void ocean_bed_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void water_surface_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	public void impact_now() {
		this.current_destruction_timer_in_seconds = 10;
	}

}
