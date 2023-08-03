package belligerents.listeners;

import belligerents.weapon.SimpleTowerBomb;
import belligerents.weapon.Weapon;

public interface WeaponListener {

	void on_simple_tower_bomb_moved(SimpleTowerBomb simpleAllyBomb);

	void on_listen_to_simple_tower_bomb(SimpleTowerBomb simpleAllyBomb);

	void on_simple_tower_bomb_end_of_destruction_and_clean(SimpleTowerBomb simpleAllyBomb);

	void on_simple_tower_bomb_beginning_of_destruction(SimpleTowerBomb simpleAllyBomb);

	void on_weapon_end_of_destruction_and_clean(Weapon weapon);

	void on_weapon_beginning_of_destruction(Weapon weapon);

}
