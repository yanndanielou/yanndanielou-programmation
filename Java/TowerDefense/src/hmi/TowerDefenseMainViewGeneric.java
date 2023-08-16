package hmi;

import belligerents.Attacker;
import belligerents.Tower;
import game.Game;
import game.Player;

public interface TowerDefenseMainViewGeneric {

	public void registerToGame(Game game);

	public void registerToPlayer(Player player);

	public void register_to_tower(Tower tower);

	public void register_to_attacker(Attacker attacker);

}
