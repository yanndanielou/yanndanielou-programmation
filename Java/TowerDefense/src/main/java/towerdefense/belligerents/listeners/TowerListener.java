package towerdefense.belligerents.listeners;

import towerdefense.belligerents.Tower;

public interface TowerListener {
	void onListenToTower(Tower tower);
	void onTowerRemoval(Tower tower);

	// void onTowerMoved(Tower tower); For now, towers don't move

}
