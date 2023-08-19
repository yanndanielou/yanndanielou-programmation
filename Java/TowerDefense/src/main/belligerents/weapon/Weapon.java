package main.belligerents.weapon;

import main.belligerents.Belligerent;
import main.belligerents.GameObject;
import main.game.Game;
import main.geometry.IntegerRectangle;

public abstract class Weapon extends GameObject {

	protected Belligerent launcher;
	protected Belligerent target;

	public Weapon(IntegerRectangle surroundingRectangleAbsoluteOnCompleteBoard, Game game, Belligerent parentBelligerent,
			Belligerent targetBelligerent, int evolutionLevel) {
		super(surroundingRectangleAbsoluteOnCompleteBoard, game, evolutionLevel);
		this.launcher = parentBelligerent;
		this.target = targetBelligerent;
		parentBelligerent.addLivingBomb(this);
	}

	public void endOfDestroyAndClean() {
		launcher.removeLivingBomb(this);
		// super.endOfDestroyAndClean();

		// movementListeners
		// .forEach((movementListener) ->
		// movementListener.onWeaponEndOfDestructionAndClean(this));
	}

	@Override
	public void impactNow(Weapon weapon) {
	}

	@Override
	public void notifyEndOfDestructionAndClean() {
		// for (GameObjectListerner objectListerner : movementListeners) {
		// objectListerner.onWeaponEndOfDestructionAndClean(this);
		// }
	}

}
