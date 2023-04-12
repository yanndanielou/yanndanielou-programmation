package moving_objects.weapon;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import builders.genericobjects.GenericObjectDataModel;
import game.Game;
import moving_objects.boats.Belligerent;
import moving_objects.listeners.GameObjectListerner;

public class SimpleSubmarineBomb extends Weapon {

	public SimpleSubmarineBomb(GenericObjectDataModel genericObjectDataModel, int x, int y, int y_speed, Game game,
			Belligerent parent_belligerent) {
		super(new Rectangle(x, y, genericObjectDataModel.getWidth(), genericObjectDataModel.getHeight()), y_speed, game,
				parent_belligerent);
	}

	@Override
	protected void right_border_of_game_board_reached() {
	}

	@Override
	protected void left_border_of_game_board_reached() {
	}

	@Override
	public void add_movement_listener(GameObjectListerner game_object_listener) {
		super.add_movement_listener(game_object_listener);
		game_object_listener.on_listen_to_simple_submarine_bomb(this);
	}

	@Override
	public void notify_movement() {
		for (GameObjectListerner objectlistener : movement_listeners) {
			objectlistener.on_simple_submarine_bomb_moved(this);
		}
	}

	@Override
	protected void ocean_bed_reached() {

	}

	@Override
	protected void water_surface_reached() {
		this.current_destruction_timer_in_milliseconds = 100;
		stop_movement();
	}

	@Override
	public void impact_now() {
		this.current_destruction_timer_in_milliseconds = 2_000;
	}

	@Override
	public BufferedImage get_graphical_representation_as_buffered_image() {
		return getSimpleSubmarineBombImage(this);
	}

}
