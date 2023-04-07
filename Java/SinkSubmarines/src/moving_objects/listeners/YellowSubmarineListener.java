package moving_objects.listeners;

import game.Game;
import moving_objects.boats.SimpleSubMarine;
import moving_objects.boats.SubMarine;
import moving_objects.boats.YellowSubMarine;

public interface YellowSubmarineListener {
	void on_yellow_submarine_destruction(SubMarine subMarine);

	void on_simple_submarine_moved(SimpleSubMarine simpleSubMarine);

	void on_yellow_submarine_moved(YellowSubMarine yellowSubMarine);

}
