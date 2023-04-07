package moving_objects.listeners;

import moving_objects.boats.SubMarine;

public interface SubmarineListener {

	void on_submarine_notify_end_of_destroy_and_clean(SubMarine subMarine);

}
