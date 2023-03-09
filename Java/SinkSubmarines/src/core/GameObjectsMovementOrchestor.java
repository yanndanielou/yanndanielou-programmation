package core;

import java.util.ArrayList;

import moving_objects.GameObject;
import time.TimeManager;
import time.TimeManagerListener;

public class GameObjectsMovementOrchestor implements TimeManagerListener {
	private static GameObjectsMovementOrchestor instance;
	
	private ArrayList<GameObject> game_objects = new ArrayList<>();

	private GameObjectsMovementOrchestor() {
		TimeManager.getInstance().add_listener(this);
	}

	public static GameObjectsMovementOrchestor getInstance() {
		if (instance == null) {
			instance = new GameObjectsMovementOrchestor();
		}
		return instance;
	}


	@Override
	public void on_10ms_tick() {
		
		for(GameObject gameObject : game_objects) {
			
		}
	}

	@Override
	public void on_100ms_tick() {
	}

	@Override
	public void on_second_tick() {
	}

}
