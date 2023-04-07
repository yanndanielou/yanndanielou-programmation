package moving_objects.weapon;

import java.awt.Rectangle;

import builders.genericobjects.GenericObjectDataModel;
import game.Game;
import moving_objects.boats.Belligerent;
import moving_objects.listeners.GameObjectListerner;

public class FloatingSubmarineBomb extends Weapon {

	public FloatingSubmarineBomb(GenericObjectDataModel genericObjectDataModel, int x, int y, int y_speed, Game game, Belligerent parent_belligerent) {
		super(new Rectangle(x, y, genericObjectDataModel.getWidth(), genericObjectDataModel.getHeight()), y_speed, game, parent_belligerent);
	}

	@Override
	protected void right_border_of_game_board_reached() {
	}

	@Override
	protected void left_border_of_game_board_reached() {
	}

	@Override
	public void notify_movement() {
		for (GameObjectListerner objectlistener : movement_listeners) {
			objectlistener.on_floating_bomb_moved(this);
		}
	}

	@Override
	protected void ocean_bed_reached() {

	}

	@Override
	protected void water_surface_reached() {
		stop_movement();
	}

	@Override
	public void impact_now() {
		this.current_destruction_timer_in_milliseconds = 1_000;
	}

}
