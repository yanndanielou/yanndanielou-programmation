package belligerents.listeners;

import belligerents.boats.SubMarine;

public interface SubmarineListener {

	void on_submarine_end_of_destruction_and_clean(SubMarine subMarine);
	void on_listen_to_submarine(SubMarine subMarine);
	void on_submarine_beginning_of_destruction(SubMarine subMarine);


}
