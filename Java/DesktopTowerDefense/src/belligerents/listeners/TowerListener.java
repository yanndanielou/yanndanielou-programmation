package belligerents.listeners;

import belligerents.Tower;

public interface TowerListener {
	void on_listen_to_tower(Tower tower);

	void on_tower_moved(Tower tower);

}
