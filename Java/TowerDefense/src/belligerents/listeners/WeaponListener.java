package belligerents.listeners;

import belligerents.weapon.SimpleTowerBomb;
import belligerents.weapon.Weapon;

public interface WeaponListener {

	void onSimpleTowerBombMoved(SimpleTowerBomb simpleAllyBomb);

	void onListenToSimpleTowerBomb(SimpleTowerBomb simpleAllyBomb);

	void onSimpleTowerBombEndOfDestructionAndClean(SimpleTowerBomb simpleAllyBomb);

	void onSimpleTowerBombBeginningOfDestruction(SimpleTowerBomb simpleAllyBomb);

	void onWeaponEndOfDestructionAndClean(Weapon weapon);

	void onWeaponBeginningOfDestruction(Weapon weapon);

}
