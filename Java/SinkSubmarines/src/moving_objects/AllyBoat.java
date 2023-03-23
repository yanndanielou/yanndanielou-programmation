package moving_objects;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.gameboard.GameBoardDataModel;
import builders.genericobjects.GenericObjectDataModel;
import hmi.SinkSubmarinesMainView;

public class AllyBoat extends Belligerent {
	private static final Logger LOGGER = LogManager.getLogger(AllyBoat.class);

	private ArrayList<AllyBoatListener> movement_listeners = new ArrayList<AllyBoatListener>();

	public AllyBoat(GenericObjectDataModel genericObjectDataModel, GameBoardDataModel gameBoardDataModel) {
		super(new Rectangle(gameBoardDataModel.getWidth() / 2 - genericObjectDataModel.getWidth() / 2, 0,
				genericObjectDataModel.getWidth(), genericObjectDataModel.getHeight()));
	}

	@Override
	public void notify_movement() {
		for (AllyBoatListener allyBoatListener : movement_listeners) {
			allyBoatListener.on_ally_boat_moved();
		}
	}

	public void add_movement_listener(AllyBoatListener allyBoatListener) {
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

}
