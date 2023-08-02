package belligerents.weapon;

import java.awt.Rectangle;

import game.Game;
import belligerents.GameObject;
import belligerents.boats.Belligerent;
import belligerents.listeners.GameObjectListerner;

public abstract class Weapon extends GameObject {

	protected Belligerent launcher;
	protected Belligerent target;

	public Weapon(Rectangle surrounding_rectangle_absolute_on_complete_board, int y_speed, Game game,
			Belligerent parent_belligerent, Belligerent target_belligerent) {
		super(surrounding_rectangle_absolute_on_complete_board, game);
		this.y_speed = y_speed;
		this.launcher = parent_belligerent;
		this.target = target_belligerent;
		parent_belligerent.add_living_bomb(this);
	}

	public void end_of_destroy_and_clean() {
		launcher.remove_living_bomb(this);
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
