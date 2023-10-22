package towerdefense.core;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.exceptions.BadLogicException;
import common.timer.TimeManagerListener;
import game.gameboard.NeighbourGameBoardPointDirection;
import geometry2d.vectors.Vector2D;
import towerdefense.belligerents.Attacker;
import towerdefense.game.Game;

public class AttackerMovementOrchestor implements TimeManagerListener {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(AttackerMovementOrchestor.class);

	private Game game;

	public AttackerMovementOrchestor(Game game) {
		this.game = game;
		game.getTimeManager().addListener(this);
	}

	private void moveAttackers() {
		for (Attacker attacker : new ArrayList<>(game.getAttackers())) {
			if (attacker.isAllowedToMove()) {
				Vector2D nextMovement = MovingObjectPathFinder.getInstance().getNextMovement(attacker);
				LOGGER.debug(attacker + " will move " + nextMovement);
				attacker.move(nextMovement);

			}
		}
	}

	@Deprecated
	private void moveAttackersBasedOnGrid() {
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
	public void on20msTick() {
		moveAttackers();
	}

}
