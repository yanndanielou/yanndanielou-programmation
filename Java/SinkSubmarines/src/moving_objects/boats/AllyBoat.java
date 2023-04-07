package moving_objects.boats;

import java.awt.Rectangle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.gameboard.GameBoardDataModel;
import builders.genericobjects.AllySimpleBombDataModel;
import builders.genericobjects.GenericObjectDataModel;
import constants.Constants;
import game.Game;
import moving_objects.listeners.GameObjectListerner;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;
import moving_objects.weapon.Weapon;

public class AllyBoat extends Belligerent implements GameObjectListerner {
	private static final Logger LOGGER = LogManager.getLogger(AllyBoat.class);

	public AllyBoat(GenericObjectDataModel genericObjectDataModel, GameBoardDataModel gameBoardDataModel,
			AllySimpleBombDataModel allySimpleBombDataModel, Game game) {
		super(new Rectangle(gameBoardDataModel.getWidth() / 2 - genericObjectDataModel.getWidth() / 2,
				-genericObjectDataModel.getHeight(), genericObjectDataModel.getWidth(),
				genericObjectDataModel.getHeight()),
				Constants.MINIMUM_DELAY_BETWEEN_TWO_ALLY_BOMB_DROPPED_IN_MILLISECONDS,
				allySimpleBombDataModel.getY_speed(), game);
	}

	@Override
	public void notify_movement() {
		for (GameObjectListerner allyBoatListener : movement_listeners) {
			allyBoatListener.on_ally_boat_moved(this);
		}
	}

	public void add_movement_listener(GameObjectListerner allyBoatListener) {
		movement_listeners.add(allyBoatListener);
	}

	public void increase_left_speed() {
		if (x_speed > -Constants.MAXIMUM_ALLY_BOAT_HORIZONTAL_SPEED) {
			x_speed--;
			LOGGER.info("Ally boat, increase left speed to:" + x_speed);
		}
	}

	public void increase_right_speed() {
		if (x_speed < Constants.MAXIMUM_ALLY_BOAT_HORIZONTAL_SPEED) {
			x_speed++;
			LOGGER.info("Ally boat, increase right speed to:" + x_speed);
		}
	}

	@Override
	protected void right_border_of_game_board_reached() {
		stop_movement();
	}

	@Override
	protected void left_border_of_game_board_reached() {
		stop_movement();
	}

	@Override
	protected void ocean_bed_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void water_surface_reached() {
		// TODO Auto-generated method stub

	}

	@Override
	public void impact_now() {
		this.current_destruction_timer_in_milliseconds = 10_000;
	}

	@Override
	public void notify_end_of_destruction_and_clean() {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_ally_boat_moved(AllyBoat allyBoat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_submarine_moved(SimpleSubMarine simpleSubMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_submarine_notify_end_of_destroy_and_clean(SubMarine subMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_ally_bomb_moved(SimpleAllyBomb simpleAllyBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_weapon_destruction(Weapon weapon) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_submarine_bomb_moved(SimpleSubmarineBomb simpleSubmarineBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_floating_bomb_moved(FloatingSubmarineBomb floatingSubmarineBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_listen_to_simple_ally_bomb(SimpleAllyBomb simpleAllyBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_yellow_submarine_end_of_destroy_and_clean(SubMarine subMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_yellow_submarine_moved(YellowSubMarine yellowSubMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_ally_bomb_end_of_destruction_and_clean(SimpleAllyBomb simpleAllyBomb) {
		living_bombs.remove(simpleAllyBomb);
	}

	@Override
	public void on_simple_ally_bomb_begin_of_destruction(SimpleAllyBomb simpleAllyBomb) {
		// TODO Auto-generated method stub
		
	}

}
