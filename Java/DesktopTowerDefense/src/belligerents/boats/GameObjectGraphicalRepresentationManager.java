package belligerents.boats;

@Deprecated
public class GameObjectGraphicalRepresentationManager {
	private static GameObjectGraphicalRepresentationManager instance;
	//private static final Logger LOGGER = LogManager.getLogger(GameObjectsMovementOrchestor.class);
	

	public static GameObjectGraphicalRepresentationManager getInstance() {
		if (instance == null) {
			instance = new GameObjectGraphicalRepresentationManager();
		}
		return instance;
	}
}
