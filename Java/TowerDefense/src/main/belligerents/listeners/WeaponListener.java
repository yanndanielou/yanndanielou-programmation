package main.belligerents.listeners;

import main.belligerents.weapon.SimpleTowerBomb;
import main.belligerents.weapon.Weapon;

public interface WeaponListener {

	void onSimpleTowerBombMoved(SimpleTowerBomb simpleAllyBomb);

	void onListenToSimpleTowerBomb(SimpleTowerBomb simpleAllyBomb);

	void onSimpleTowerBombEndOfDestructionAndClean(SimpleTowerBomb simpleAllyBomb);

	void onSimpleTowerBombBeginningOfDestruction(SimpleTowerBomb simpleAllyBomb);

	void onWeaponEndOfDestructionAndClean(Weapon weapon);

	void onWeaponBeginningOfDestruction(Weapon weapon);

}
