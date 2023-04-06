package moving_objects;

import moving_objects.boats.AllyBoat;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.SubMarine;
import moving_objects.boats.YellowSubMarine;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;
import moving_objects.weapon.Weapon;

public interface GameObjectListerner {

	void on_ally_boat_moved(AllyBoat allyBoat);

	void on_simple_submarine_moved(SimpleSubMarine simpleSubMarine);

	void on_simple_ally_bomb_moved(SimpleAllyBomb simpleAllyBomb);
	
	void on_yellow_submarine_moved(YellowSubMarine yellowSubMarine);

	void on_submarine_destruction(SubMarine subMarine);

	void on_weapon_destruction(Weapon weapon);

	void on_simple_submarine_bomb_moved(SimpleSubmarineBomb simpleSubmarineBomb);

	void on_floating_bomb_moved(FloatingSubmarineBomb floatingSubmarineBomb);

	void on_yellow_submarine_destruction(SubMarine subMarine);

}
