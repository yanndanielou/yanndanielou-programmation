package moving_objects.weapon;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import builders.genericobjects.GenericObjectDataModel;
import game.Game;
import moving_objects.boats.Belligerent;
import moving_objects.listeners.GameObjectListerner;

public class FloatingSubmarineBomb extends Weapon {

	public FloatingSubmarineBomb(GenericObjectDataModel genericObjectDataModel, int x, int y, int y_speed, Game game,
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
		game_object_listener.on_listen_to_floating_submarine_bomb(this);
	}

	@Override
	public void notify_movement() {
		for (GameObjectListerner objectlistener : movement_listeners) {
			objectlistener.on_floating_submarine_bomb_moved(this);
		}
	}

	@Override
	protected void ocean_bed_reached() {

	}

	@Override
	protected void top_of_object_reaches_surface() {
		stop_movement();
	}

	@Override
	public void impact_now() {
		this.current_destruction_timer_in_milliseconds = 1_000;
		super.impact_now();
	}

	@Override
	public BufferedImage get_graphical_representation_as_buffered_image() {
		return getFloatingSubmarineBombImage(this);
	}

	@Override
	protected void rocks_reached() {
		// TODO Auto-generated method stub
		
	}

}
