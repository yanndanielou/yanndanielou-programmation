package main.belligerents.weapon;

import java.awt.image.BufferedImage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.belligerents.Belligerent;
import main.belligerents.listeners.GameObjectListerner;
import main.builders.weapons.BombDataModel;
import main.game.Game;
import main.geometry2d.integergeometry.IntegerRectangle;

public class SimpleTowerBomb extends Weapon {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(SimpleTowerBomb.class);

	public SimpleTowerBomb(BombDataModel genericObjectDataModel, int x, int y, int xSpeed, Game game,
			Belligerent parentBelligerent, Belligerent targetBelligerent, int evolutionLevel) {
		super(new IntegerRectangle(x, y, genericObjectDataModel.getWidth(), genericObjectDataModel.getHeight()), game,
				parentBelligerent, targetBelligerent, evolutionLevel);
		setXSpeed(xSpeed);
	}

	@Override
	protected void rightBorderOfGameBoardReached() {
		stopHorizontalMovement();
	}

	@Override
	protected void leftBorderOfGameBoardReached() {
		stopHorizontalMovement();
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
