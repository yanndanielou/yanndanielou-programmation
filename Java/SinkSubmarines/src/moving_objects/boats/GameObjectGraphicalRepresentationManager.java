package moving_objects.boats;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameObjectsMovementOrchestor;

public class GameObjectGraphicalRepresentationManager {
	private static GameObjectGraphicalRepresentationManager instance;
	private static final Logger LOGGER = LogManager.getLogger(GameObjectsMovementOrchestor.class);

	private GameObjectGraphicalRepresentationManager() {
	}

	public static GameObjectGraphicalRepresentationManager getInstance() {
		if (instance == null) {
			instance = new GameObjectGraphicalRepresentationManager();
		}
		return instance;
	}

}
