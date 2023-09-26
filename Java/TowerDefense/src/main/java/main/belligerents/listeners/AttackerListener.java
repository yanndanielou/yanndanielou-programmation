package main.belligerents.listeners;

import main.belligerents.Attacker;

public interface AttackerListener {

	void onAttackerEndOfDestructionAndClean(Attacker attacker);

	void onListenToAttacker(Attacker attacker);

	void onAttackerMoved(Attacker attacker);

	void onAttackerBeginningOfDestruction(Attacker attacker);

	void onAttackerEscape(Attacker attacker);

}
