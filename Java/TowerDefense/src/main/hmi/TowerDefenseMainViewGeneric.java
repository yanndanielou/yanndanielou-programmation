package main.hmi;

import main.belligerents.Attacker;
import main.belligerents.Tower;
import main.game.Game;
import main.game.Player;

public interface TowerDefenseMainViewGeneric {

	public void registerToGame(Game game);

	public void registerToPlayer(Player player);

	public void registerToTower(Tower tower);

	public void registerToAttacker(Attacker attacker);

}
