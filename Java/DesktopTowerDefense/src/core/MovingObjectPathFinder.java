package core;

import java.awt.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import game_board.NeighbourGameBoardPointDirection;

public class MovingObjectPathFinder {
	private static MovingObjectPathFinder instance;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MovingObjectPathFinder.class);

	private MovingObjectPathFinder() {

	}

	public static MovingObjectPathFinder getInstance() {
		if (instance == null) {
			instance = new MovingObjectPathFinder();
		}
		return instance;
	}

	
	public NeighbourGameBoardPointDirection getNextMovementDirection(Attacker attacker) {
		Point destination = attacker.getEscape_destination();
		return null;
	}

}
