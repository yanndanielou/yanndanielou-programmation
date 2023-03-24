package moving_objects.weapon;

import java.awt.Rectangle;

import builders.genericobjects.GenericObjectDataModel;
import moving_objects.GameObject;

public abstract class Weapon extends GameObject {

	public Weapon(Rectangle surrounding_rectangle_absolute_on_complete_board) {
		super(surrounding_rectangle_absolute_on_complete_board);
	}

}
