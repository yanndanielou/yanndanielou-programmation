package towerdefense.belligerents.weapon;

import geometry2d.integergeometry.IntegerPrecisionRectangle;
import towerdefense.belligerents.Belligerent;
import towerdefense.belligerents.GameObject;
import towerdefense.builders.weapons.BombDataModel;
import towerdefense.game.Game;

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
