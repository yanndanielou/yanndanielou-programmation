package belligerents.listeners;

import belligerents.Attacker;

public interface AttackerListener {

	void onAttackerEndOfDestructionAndClean(Attacker attacker);

	void onListenToAttacker(Attacker attacker);

	void on_attacker_moved(Attacker attacker);

	void onAttackerBeginningOfDestruction(Attacker attacker);

	void onAttackerEscape(Attacker attacker);

}
