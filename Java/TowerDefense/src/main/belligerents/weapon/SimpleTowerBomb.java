package main.belligerents.weapon;

import java.awt.image.BufferedImage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.belligerents.Belligerent;
import main.belligerents.listeners.GameObjectListerner;
import main.builders.weapons.BombDataModel;
import main.game.Game;

public class SimpleTowerBomb extends Weapon {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(SimpleTowerBomb.class);

	public SimpleTowerBomb(BombDataModel genericObjectDataModel, int x, int y, Game game, Belligerent parentBelligerent,
			Belligerent targetBelligerent, int evolutionLevel) {
		super(genericObjectDataModel, x, y, game, parentBelligerent, targetBelligerent, evolutionLevel);
	}

	@Override
	protected void rightBorderOfGameBoardReached() {
	}

	@Override
	protected void leftBorderOfGameBoardReached() {
	}

	@Deprecated
	public void addMovementListener(GameObjectListerner allyBoatListener) {
		// super.addMovementListener(allyBoatListener);
		allyBoatListener.onListenToSimpleTowerBomb(this);
	}

	@Override
	public void notifyMovement() {
		// for (GameObjectListerner objectlistener : movementListeners) {
		// objectlistener.onSimpleTowerBombMoved(this);
		// }
	}

	@Override
	public void notifyEndOfDestructionAndClean() {
		super.notifyEndOfDestructionAndClean();
		// for (GameObjectListerner objectlistener : movementListeners) {
		// objectlistener.onSimpleTowerBombEndOfDestructionAndClean(this);
		// }
	}

	@Override
	protected BufferedImage getGraphicalRepresentationAsBufferedImage() {
		return getSimpleTowerBombImage(this);
	}

	@Override
	protected void downBorderOfGameBoardReached() {
		// Auto-generated method stub

	}

	@Override
	public void impactNow(Weapon weapon) {
		// Auto-generated method stub

	}

}
