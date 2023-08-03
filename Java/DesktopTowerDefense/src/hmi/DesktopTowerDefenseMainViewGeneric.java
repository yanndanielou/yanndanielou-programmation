package hmi;

import belligerents.Attacker;
import belligerents.Tower;
import game.Game;

public interface DesktopTowerDefenseMainViewGeneric {

	public void register_to_game(Game game);

	public void register_to_tower(Tower tower);
	public void register_to_attacker(Attacker attacker);

}
