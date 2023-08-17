package core;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import common.BadLogicException;
import game.Game;
import gameboard.NeighbourGameBoardPointDirection;
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

	private void moveAttackers() {
		for (Attacker attacker : new ArrayList<>(game.getAttackers())) {
			if (attacker.isAllowedToMove()) {
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
	public void on10msTick() {
		moveAttackers();
	}

	@Override
	public void on50msTick() {

	}

	@Override
	public void on100msTick() {

	}

	@Override
	public void onSecondTick() {

	}

	@Override
	public void onPause() {

	}

}
