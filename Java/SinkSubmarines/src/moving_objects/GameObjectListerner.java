package moving_objects;

import moving_objects.boats.SubMarine;
import moving_objects.weapon.Weapon;

public interface GameObjectListerner {

	void on_ally_boat_moved();

	void on_simple_submarine_moved();

	void on_simple_ally_bomb_moved();

	void on_submarine_destruction(SubMarine subMarine);

	void on_weapon_destruction(Weapon weapon);

}
