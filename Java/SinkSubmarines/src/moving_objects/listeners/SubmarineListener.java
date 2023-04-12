package moving_objects.listeners;

import moving_objects.boats.SubMarine;

public interface SubmarineListener {

	void on_submarine_end_of_destruction_and_clean(SubMarine subMarine);
	void on_listen_to_submarine(SubMarine subMarine);

}
