package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameObjectsMovementOrchestor {
	private static GameObjectsMovementOrchestor instance;
	private static final Logger LOGGER = LogManager.getLogger(GameObjectsMovementOrchestor.class);

	private GameObjectsMovementOrchestor() {
	}

	public static GameObjectsMovementOrchestor getInstance() {
		if (instance == null) {
			instance = new GameObjectsMovementOrchestor();
		}
		return instance;
	}
}
