package moving_objects;

import java.awt.Rectangle;
import java.util.ArrayList;

import builders.genericobjects.GenericObjectDataModel;

public class AllyBoat extends Belligerent {
	private ArrayList<AllyBoatListener> movement_listeners = new ArrayList<AllyBoatListener>();

	public AllyBoat(GenericObjectDataModel genericObjectDataModel) {
		super(new Rectangle(0, 0, genericObjectDataModel.getWidth(), genericObjectDataModel.getHeight()));
	}

	@Override
	public void notify_movement() {
		for (AllyBoatListener allyBoatListener : movement_listeners) {
			allyBoatListener.on_ally_boat_moved();
		}
	}

	public void add_movement_listener(AllyBoatListener allyBoatListener) {
		movement_listeners.add(allyBoatListener);
	}

}
