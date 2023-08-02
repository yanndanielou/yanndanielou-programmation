package belligerents.listeners;

import belligerents.boats.AllyBoat;

public interface AllyBoatListener {
	void on_ally_boat_moved(AllyBoat allyBoat);
	void on_ally_boat_end_of_destruction_and_clean(AllyBoat allyBoat);
	void on_ally_boat_beginning_of_destruction(AllyBoat allyBoat);
	void on_listen_to_ally_boat(AllyBoat allyBoat);

}
