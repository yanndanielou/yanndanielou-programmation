package moving_objects;

import java.awt.Rectangle;

import builders.genericobjects.GenericObjectDataModel;

public abstract class Weapon extends GameObject {

	public Weapon(GenericObjectDataModel genericObjectDataModel) {
		super(new Rectangle(0, 0, genericObjectDataModel.getWidth(), genericObjectDataModel.getHeight()));
	}

}
