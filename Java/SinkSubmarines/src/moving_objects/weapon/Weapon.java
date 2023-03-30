package moving_objects.weapon;

import java.awt.Rectangle;

import moving_objects.GameObject;
import moving_objects.GameObjectListerner;

public abstract class Weapon extends GameObject {

	public Weapon(Rectangle surrounding_rectangle_absolute_on_complete_board, int y_speed) {
		super(surrounding_rectangle_absolute_on_complete_board);
		this.y_speed = y_speed;
	}

	@Override
	public void notify_destruction() {
		for (GameObjectListerner objectListerner : movement_listeners) {
			objectListerner.on_weapon_destruction(this);
		}
	}

}
