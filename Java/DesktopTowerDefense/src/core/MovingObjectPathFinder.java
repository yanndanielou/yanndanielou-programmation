package core;

import java.awt.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.Attacker;
import game_board.GameBoard;
import game_board.GameBoardPoint;
import game_board.NeighbourGameBoardPointDirection;
import geometry.IntegerPoint;

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

	private NeighbourGameBoardPointDirection getNextMovementDirectionForSmallerPathInStraightLine(Attacker attacker) {
		IntegerPoint upperLeftAttackerPoint = new IntegerPoint(attacker.get_extreme_left_point_x(), attacker.getHighestPointY());
		GameBoard gameBoard = attacker.getGame().getGameBoard();
		Point destination = attacker.getEscape_destination();
		GameBoardPoint upperLeftAttackerGameBoardPoint = gameBoard.getGameBoardPoint(upperLeftAttackerPoint);

		NeighbourGameBoardPointDirection nearestDirection = null;
		double minimum_distance = Double.MAX_VALUE;
		for (NeighbourGameBoardPointDirection candidateDirection : NeighbourGameBoardPointDirection.values()) {

			GameBoardPoint neighbourGameBoardPoint = gameBoard
					.getNeighbourGameBoardPoint(upperLeftAttackerGameBoardPoint, candidateDirection);

			if (neighbourGameBoardPoint != null) {
				double distanceUsingCandidate = destination.distance(neighbourGameBoardPoint);
				LOGGER.debug("Distance from:" + upperLeftAttackerPoint + " and " + destination + " in direction:"
						+ candidateDirection + " via:" + neighbourGameBoardPoint + " : " + distanceUsingCandidate);
				if (distanceUsingCandidate < minimum_distance) {
					nearestDirection = candidateDirection;
					minimum_distance = distanceUsingCandidate;
				}
			}
		}

		return nearestDirection;
	}

	public NeighbourGameBoardPointDirection getNextMovementDirection(Attacker attacker) {
		NeighbourGameBoardPointDirection nextMovementDirectionForSmallerPathInStraightLine = getNextMovementDirectionForSmallerPathInStraightLine(
				attacker);
		return nextMovementDirectionForSmallerPathInStraightLine;
	}

}
