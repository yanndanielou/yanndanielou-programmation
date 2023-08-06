package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import common.BadLogicException;
import game.Game;
import game_board.NeighbourGameBoardPointDirection;
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
			if (attacker.is_allowed_to_move()) {
				NeighbourGameBoardPointDirection nextMovementDirection = MovingObjectPathFinder.getInstance()
						.getNextMovementDirection(attacker);
				LOGGER.info(attacker + " will move " + nextMovementDirection);
				switch (nextMovementDirection) {
				case EAST:
					attacker.move(1, 0);
					break;
				case NORTH:
					attacker.move(0, -1);
					break;
				case NORTH_EAST:
					attacker.move(1, -1);
					break;
				case NORTH_WEST:
					attacker.move(-1, 1);
					break;
				case SOUTH:
					attacker.move(0, 1);
					break;
				case SOUTH_EAST:
					attacker.move(1, 1);
					break;
				case SOUTH_WEST:
					attacker.move(-1, 1);
					break;
				case WEST:
					attacker.move(-1, 0);
					break;
				default:
					throw new BadLogicException("");
				}

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
