package moving_objects.weapon;

import java.awt.Rectangle;

import game.Game;
import moving_objects.GameObject;
import moving_objects.boats.Belligerent;
import moving_objects.listeners.GameObjectListerner;

public abstract class Weapon extends GameObject {

	protected Belligerent parent_belligerent;

	public Weapon(Rectangle surrounding_rectangle_absolute_on_complete_board, int y_speed, Game game,
			Belligerent parent_belligerent) {
		super(surrounding_rectangle_absolute_on_complete_board, game);
		this.y_speed = y_speed;
		this.parent_belligerent = parent_belligerent;
		parent_belligerent.add_living_bomb(this);
	}

	public void end_of_destroy_and_clean() {
		parent_belligerent.remove_living_bomb(this);
		super.end_of_destroy_and_clean();

		movement_listeners
				.forEach((movement_listener) -> movement_listener.on_weapon_end_of_destruction_and_clean(this));
	}

	@Override
	public void impact_now() {
		movement_listeners.forEach((movement_listener) -> movement_listener.on_weapon_beginning_of_destruction(this));
	}

	@Override
	public void notify_end_of_destruction_and_clean() {
		for (GameObjectListerner objectListerner : movement_listeners) {
			objectListerner.on_weapon_end_of_destruction_and_clean(this);
		}
	}

}
