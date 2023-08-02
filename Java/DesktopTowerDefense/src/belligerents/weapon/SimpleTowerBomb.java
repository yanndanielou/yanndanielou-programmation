package belligerents.weapon;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.genericobjects.AllySimpleBombDataModel;
import game.Game;
import belligerents.boats.Belligerent;
import belligerents.listeners.GameObjectListerner;

public class SimpleTowerBomb extends Weapon {
	private static final Logger LOGGER = LogManager.getLogger(SimpleTowerBomb.class);

	public SimpleTowerBomb(AllySimpleBombDataModel genericObjectDataModel, int x, int y, int x_speed, Game game,
			Belligerent parent_belligerent) {
		super(new Rectangle(x, y, genericObjectDataModel.getWidth(), genericObjectDataModel.getHeight()),
				genericObjectDataModel.getY_speed(), game, parent_belligerent);
		setX_speed(x_speed);
	}

	@Override
	protected void right_border_of_game_board_reached() {
		stop_horizontal_movement();
	}

	@Override
	protected void left_border_of_game_board_reached() {
		stop_horizontal_movement();
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
	public void notify_end_of_destruction_and_clean() {
		super.notify_end_of_destruction_and_clean();
		for (GameObjectListerner objectlistener : movement_listeners) {
			objectlistener.on_simple_ally_bomb_end_of_destruction_and_clean(this);
		}
	}

	@Override
	protected void ocean_bed_reached() {
		LOGGER.info("ocean_bed_reached, will delete" + this);

		this.current_destruction_timer_in_milliseconds = 1_000;
	}

	@Override
	protected void rocks_reached() {

		LOGGER.info("rocks_reached, will delete" + this);

		this.current_destruction_timer_in_milliseconds = 1_000;
	}

	@Override
	protected void top_of_object_reaches_surface() {
	}

	@Override
	public void impact_now() {
		LOGGER.info("Impact now " + this);
		this.current_destruction_timer_in_milliseconds = 500;
		for (GameObjectListerner objectlistener : movement_listeners) {
			objectlistener.on_simple_ally_bomb_beginning_of_destruction(this);
		}
		super.impact_now();
	}

	@Override
	public BufferedImage get_graphical_representation_as_buffered_image() {
		return getSimpleTowerBombImage(this);
	}

	@Override
	protected void down_border_of_game_board_reached() {
		// TODO Auto-generated method stub
		
	}

}
