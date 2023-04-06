package moving_objects.listeners;

import moving_objects.boats.AllyBoat;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.SubMarine;
import moving_objects.boats.YellowSubMarine;
import moving_objects.weapon.FloatingSubmarineBomb;
import moving_objects.weapon.SimpleAllyBomb;
import moving_objects.weapon.SimpleSubmarineBomb;
import moving_objects.weapon.Weapon;

public interface GameObjectListerner extends AllyBoatListener, SimpleSubmarineListener, SubmarineListener, WeaponListener, YellowSubmarineListener {

}
