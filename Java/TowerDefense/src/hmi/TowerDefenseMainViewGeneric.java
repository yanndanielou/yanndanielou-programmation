package hmi;

import belligerents.Attacker;
import belligerents.Tower;
import game.Game;
import game.Player;

public interface TowerDefenseMainViewGeneric {

	public void registerToGame(Game game);

	public void registerToPlayer(Player player);

	public void registerToTower(Tower tower);

	public void registerToAttacker(Attacker attacker);

}
