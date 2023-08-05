package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import game.Game;
import time.TimeManager;
import time.TimeManagerListener;

public class AttackerMovementOrchestor implements TimeManagerListener {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(AttackerMovementOrchestor.class);

	private Game game;

	public AttackerMovementOrchestor(Game game) {
		this.game = game;
		TimeManager.getInstance().add_listener(this);
	}

	private void move_attackers() {
		for (Attacker attacker : game.getAttackers()) {
			if(attacker.is_allowed_to_move()) {
				
			}
		}
	}

	@Override
	public void on_10ms_tick() {
		move_attackers();
	}

	@Override
	public void on_50ms_tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_100ms_tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_second_tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_pause() {
		// TODO Auto-generated method stub

	}

}
