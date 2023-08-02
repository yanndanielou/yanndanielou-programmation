package belligerents.listeners;

import belligerents.boats.Attacker;

public interface SubmarineListener {

	void on_submarine_end_of_destruction_and_clean(Attacker subMarine);
	void on_listen_to_submarine(Attacker subMarine);
	void on_submarine_beginning_of_destruction(Attacker subMarine);


}
