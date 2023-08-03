package belligerents;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.listeners.GameObjectListerner;
import belligerents.weapon.SimpleTowerBomb;
import belligerents.weapon.Weapon;
import builders.BombDataModel;
import builders.TowerDataModel;
import game.Game;

public class SimpleTower extends Belligerent implements GameObjectListerner {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(SimpleTower.class);

	public SimpleTower(TowerDataModel towerDataModel, BombDataModel weaponDataModel, Game game, int x, int y) {
		super(new Rectangle(x, y, towerDataModel.getWidth(), towerDataModel.getHeight()), game);
	}

	@Override
	public void notify_movement() {
	}

	@Override
	public void add_movement_listener(GameObjectListerner allyBoatListener) {
		movement_listeners.add(allyBoatListener);
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
	public void impact_now(Weapon weapon) {
	}

	@Override
	public void notify_end_of_destruction_and_clean() {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_normal_attacker_moved(NormalAttacker simpleSubMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_attacker_end_of_destruction_and_clean(Attacker subMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_tower_bomb_moved(SimpleTowerBomb simpleAllyBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_weapon_end_of_destruction_and_clean(Weapon weapon) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_listen_to_simple_tower_bomb(SimpleTowerBomb simpleAllyBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_simple_tower_bomb_end_of_destruction_and_clean(SimpleTowerBomb simpleAllyBomb) {
		living_bombs.remove(simpleAllyBomb);
	}

	@Override
	public void on_simple_tower_bomb_beginning_of_destruction(SimpleTowerBomb simpleAllyBomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public BufferedImage get_graphical_representation_as_buffered_image() {
		return getSimpleTowerNormalImage(this);
	}

	@Override
	public void on_listen_to_attacker(Attacker subMarine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_attacker_beginning_of_destruction(Attacker attacker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_weapon_beginning_of_destruction(Weapon weapon) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void down_border_of_game_board_reached() {
		// TODO Auto-generated method stub

	}

}
