package moving_objects.weapon;

import java.awt.Rectangle;

import builders.genericobjects.GenericObjectDataModel;
import moving_objects.GameObject;

public abstract class Weapon extends GameObject {

	public Weapon(GenericObjectDataModel genericObjectDataModel) {
		super(new Rectangle(0, 0, genericObjectDataModel.getWidth(), genericObjectDataModel.getHeight()));
	}

}
