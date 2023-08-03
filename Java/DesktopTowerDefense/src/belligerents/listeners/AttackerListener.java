package belligerents.listeners;

import belligerents.Attacker;

public interface AttackerListener {

	void on_attacker_end_of_destruction_and_clean(Attacker attacker);
	void on_listen_to_attacker(Attacker attacker);
	void on_attacker_moved(Attacker attacker);
	void on_attacker_beginning_of_destruction(Attacker attacker);


}
