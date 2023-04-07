package moving_objects.listeners;

import moving_objects.boats.YellowSubMarine;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;
import moving_objects.weapon.Weapon;

public interface WeaponListener {

	void on_simple_ally_bomb_moved(SimpleAllyBomb simpleAllyBomb);

	void on_weapon_destruction(Weapon weapon);

	void on_simple_submarine_bomb_moved(SimpleSubmarineBomb simpleSubmarineBomb);

	void on_floating_bomb_moved(FloatingSubmarineBomb floatingSubmarineBomb);

}
