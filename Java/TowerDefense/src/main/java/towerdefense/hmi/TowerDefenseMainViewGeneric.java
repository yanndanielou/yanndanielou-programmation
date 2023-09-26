package towerdefense.hmi;

import towerdefense.belligerents.Attacker;
import towerdefense.belligerents.Tower;
import towerdefense.game.Game;
import towerdefense.game.Player;

public interface TowerDefenseMainViewGeneric {

	public void registerToGame(Game game);

	public void registerToPlayer(Player player);

	public void registerToTower(Tower tower);

	public void registerToAttacker(Attacker attacker);

}
