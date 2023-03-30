package moving_objects;

public interface GameObjectListerner {

	void on_ally_boat_moved();

	void on_simple_submarine_moved();

	void on_simple_ally_bomb_moved();

	void on_destruction(GameObject gameObject);


}
