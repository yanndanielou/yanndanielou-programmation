package belligerents;

import java.awt.geom.IllegalPathStateException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.listeners.TowerListener;
import belligerents.weapon.Weapon;
import builders.BombDataModel;
import builders.TowerDataModel;
import core.GameManager;
import game.Game;
import geometry.IntegerRectangle;

public class Tower extends Belligerent /* implements GameObjectListerner */ {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Tower.class);

	protected ArrayList<TowerListener> listeners = new ArrayList<>();

	public Tower(TowerDataModel towerDataModel, BombDataModel weaponDataModel, Game game, int x, int y,
			int evolutionLevel) {
		super(new IntegerRectangle(x, y, towerDataModel.getWidth(), towerDataModel.getHeight()), game, evolutionLevel);

		GameManager.getInstance().getDesktopTowerDefenseMainView().register_to_tower(this);
		add_listener(game.getGameBoard());
		add_listener(game);
		// listeners.forEach((listener) -> listener.on_listen_to_tower(this));
	}

	@Override
	public void notify_movement() {
		throw new IllegalPathStateException("Towers can't move");
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
	protected BufferedImage get_graphical_representation_as_buffered_image() {
		return getSimpleTowerNormalImage(this);
	}

	@Override
	protected void down_border_of_game_board_reached() {
		// TODO Auto-generated method stub

	}

	public void add_listener(TowerListener tower_listener) {
		listeners.add(tower_listener);
		tower_listener.on_listen_to_tower(this);
	}
}
