package main.belligerents.weapon;

import geometry2d.integergeometry.IntegerPrecisionRectangle;
import main.belligerents.Belligerent;
import main.belligerents.GameObject;
import main.builders.weapons.BombDataModel;
import main.game.Game;

public abstract class Weapon extends GameObject {

	protected Belligerent launcher;
	protected Belligerent target;
	protected BombDataModel genericObjectDataModel;

	public Weapon(BombDataModel genericObjectDataModel, int x, int y, Game game, Belligerent parentBelligerent,
			Belligerent targetBelligerent, int evolutionLevel) {
		super(new IntegerPrecisionRectangle(x, y, genericObjectDataModel.getWidth(), genericObjectDataModel.getHeight()), game,
				evolutionLevel);
		this.launcher = parentBelligerent;
		this.target = targetBelligerent;
		this.genericObjectDataModel = genericObjectDataModel;
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

	@Override
	public float getSpeed() {
		return genericObjectDataModel.getLevels().get(evolutionLevel).getSpeed();
	}
}
