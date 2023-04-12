package moving_objects.listeners;

import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;
import moving_objects.weapon.Weapon;

public interface WeaponListener {

	void on_simple_ally_bomb_moved(SimpleAllyBomb simpleAllyBomb);

	void on_listen_to_simple_ally_bomb(SimpleAllyBomb simpleAllyBomb);

	void on_simple_ally_bomb_end_of_destruction_and_clean(SimpleAllyBomb simpleAllyBomb);

	void on_simple_ally_bomb_beginning_of_destruction(SimpleAllyBomb simpleAllyBomb);

	void on_weapon_end_of_destruction_and_clean(Weapon weapon);

	void on_weapon_beginning_of_destruction(Weapon weapon);

	void on_listen_to_simple_submarine_bomb(SimpleSubmarineBomb simpleSubmarineBomb);

	void on_simple_submarine_bomb_moved(SimpleSubmarineBomb simpleSubmarineBomb);

	void on_simple_submarine_bomb_end_of_destruction_and_clean(SimpleSubmarineBomb simpleSubmarineBomb);

	void on_simple_submarine_bomb_beginning_of_destruction(SimpleSubmarineBomb simpleSubmarineBomb);

	void on_listen_to_floating_submarine_bomb(FloatingSubmarineBomb floatingSubmarineBomb);

	void on_floating_submarine_bomb_moved(FloatingSubmarineBomb floatingSubmarineBomb);

}
