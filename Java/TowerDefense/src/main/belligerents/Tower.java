package main.belligerents;

import java.awt.geom.IllegalPathStateException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.belligerents.listeners.TowerListener;
import main.belligerents.weapon.Weapon;
import main.builders.belligerents.TowerDataModel;
import main.builders.weapons.BombDataModel;
import main.core.GameManager;
import main.game.Game;
import main.geometry2d.integergeometry.IntegerRectangle;

public class Tower extends Belligerent /* implements GameObjectListerner */ {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Tower.class);

	protected ArrayList<TowerListener> listeners = new ArrayList<>();

	public Tower(TowerDataModel towerDataModel, BombDataModel weaponDataModel, Game game, int evolutionLevel, int x,
			int y) {
		super(new IntegerRectangle(x, y, towerDataModel.getWidth(), towerDataModel.getHeight()), game, evolutionLevel);

		GameManager.getInstance().getDesktopTowerDefenseMainView().registerToTower(this);
		addListener(game.getGameBoard());
		addListener(game);
		// listeners.forEach((listener) -> listener.onListenToTower(this));
	}

	@Override
	public void notifyMovement() {
		throw new IllegalPathStateException("Towers can't move");
	}

	@Override
	protected void rightBorderOfGameBoardReached() {
		stopMovement();
	}

	@Override
	protected void leftBorderOfGameBoardReached() {
		stopMovement();
	}

	@Override
	public void impactNow(Weapon weapon) {
	}

	@Override
	public void notifyEndOfDestructionAndClean() {
		// Auto-generated method stub

	}

	@Override
	protected BufferedImage getGraphicalRepresentationAsBufferedImage() {
		return getSimpleTowerNormalImage(this);
	}

	@Override
	protected void downBorderOfGameBoardReached() {
		// Auto-generated method stub

	}

	public void addListener(TowerListener towerListener) {
		listeners.add(towerListener);
		towerListener.onListenToTower(this);
	}
}
