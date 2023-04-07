package moving_objects.weapon;

import java.awt.Rectangle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.genericobjects.AllySimpleBombDataModel;
import game.Game;
import moving_objects.boats.Belligerent;
import moving_objects.listeners.GameObjectListerner;

public class SimpleAllyBomb extends Weapon {
	private static final Logger LOGGER = LogManager.getLogger(SimpleAllyBomb.class);

	public SimpleAllyBomb(AllySimpleBombDataModel genericObjectDataModel, int x, int y, int x_speed, Game game,
			Belligerent parent_belligerent) {
		super(new Rectangle(x, y, genericObjectDataModel.getWidth(), genericObjectDataModel.getHeight()),
				genericObjectDataModel.getY_speed(), game, parent_belligerent);
		setX_speed(x_speed);
	}

	@Override
	protected void right_border_of_game_board_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void left_border_of_game_board_reached() {
	}

	@Override
	public void add_movement_listener(GameObjectListerner allyBoatListener) {
		super.add_movement_listener(allyBoatListener);
		allyBoatListener.on_listen_to_simple_ally_bomb(this);
	}

	@Override
	public boolean proceed_horizontal_movement() {
		boolean ret = super.proceed_horizontal_movement();
		if (x_speed < 0) {
			x_speed = Math.min(0, x_speed + 1);
		} else if (x_speed > 0) {
			x_speed = Math.max(0, x_speed - 1);
		}
		return ret;
	}

	@Override
	public void notify_movement() {
		for (GameObjectListerner objectlistener : movement_listeners) {
			objectlistener.on_simple_ally_bomb_moved(this);
		}
	}

	@Override
	public void notify_destruction() {
		super.notify_destruction();
		for (GameObjectListerner objectlistener : movement_listeners) {
			objectlistener.on_simple_ally_bomb_destruction(this);
		}
	}

	@Override
	protected void ocean_bed_reached() {
		LOGGER.info("ocean_bed_reached, will delete" + this);

		this.current_destruction_timer_in_milliseconds = 2_000;
	}

	@Override
	protected void water_surface_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	public void impact_now() {
		LOGGER.info("Impact now " + this);
		this.current_destruction_timer_in_milliseconds = 500;
	}

}
