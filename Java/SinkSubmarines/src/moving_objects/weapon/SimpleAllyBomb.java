package moving_objects.weapon;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import builders.genericobjects.GenericObjectDataModel;
import builders.genericobjects.GenericObjectsDataModel;
import moving_objects.GameObject;
import moving_objects.GameObjectListerner;

public class SimpleAllyBomb extends Weapon {

	public SimpleAllyBomb(GenericObjectDataModel genericObjectDataModel, int x, int y) {
		super(new Rectangle(x, y, genericObjectDataModel.getWidth(), genericObjectDataModel.getHeight()));
		setY_speed(1);
	}

	@Override
	protected void right_border_of_game_board_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void left_border_of_game_board_reached() {
	}

	@Override
	public void notify_movement() {
		for (GameObjectListerner objectlistener : movement_listeners) {
			objectlistener.on_simple_ally_bomb_moved();
		}
	}

	@Override
	protected void ocean_bed_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void water_surface_reached() {
		// TODO Auto-generated method stub

	}

}
