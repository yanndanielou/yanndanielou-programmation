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
	}

	public void destroy() {
		parent_belligerent.remove_living_bomb(this);
		super.destroy();
	}

	@Override
	public void notify_destruction() {
		for (GameObjectListerner objectListerner : movement_listeners) {
			objectListerner.on_weapon_destruction(this);
		}
	}

}
