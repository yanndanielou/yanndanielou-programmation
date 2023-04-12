package moving_objects.listeners;

import moving_objects.boats.YellowSubMarine;

public interface YellowSubmarineListener {
	void on_yellow_submarine_end_of_destruction_and_clean(YellowSubMarine yellowSubMarine);

	void on_yellow_submarine_moved(YellowSubMarine yellowSubMarine);

}
