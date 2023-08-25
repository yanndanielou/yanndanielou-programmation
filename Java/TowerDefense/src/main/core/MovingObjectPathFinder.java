package main.core;

import java.awt.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.belligerents.Attacker;
import main.gameboard.GameBoard;
import main.gameboard.GameBoardPoint;
import main.gameboard.NeighbourGameBoardPointDirection;
import main.geometry2d.integergeometry.IntegerPoint;
import main.geometry2d.vectors.Vector2D;

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

	private boolean isMovementAllowed(GameBoard gameBoard, Attacker attacker, int dx, int dy) {

		for (GameBoardPoint attackerCornerGameBoardPoint : attacker.getAllCornersGameBoardPoints()) {

		}

		return true;
	}

	private NeighbourGameBoardPointDirection getNextMovementDirectionForSmallerPathInStraightLine(Attacker attacker) {
		IntegerPoint upperLeftAttackerPoint = new IntegerPoint(attacker.getExtremeLeftPointX(),
				attacker.getHighestPointY());
		GameBoard gameBoard = attacker.getGame().getGameBoard();
		Point destination = attacker.getEscapeDestination();
		GameBoardPoint upperLeftAttackerGameBoardPoint = gameBoard.getGameBoardPoint(upperLeftAttackerPoint);

		NeighbourGameBoardPointDirection nearestDirection = null;
		double minimumDistance = Double.MAX_VALUE;
		for (NeighbourGameBoardPointDirection candidateDirection : NeighbourGameBoardPointDirection.values()) {

			GameBoardPoint neighbourGameBoardPoint = gameBoard
					.getNeighbourGameBoardPoint(upperLeftAttackerGameBoardPoint, candidateDirection);

			if (neighbourGameBoardPoint != null) {
				double distanceUsingCandidate = destination.distance(neighbourGameBoardPoint);
				LOGGER.info("Distance from:" + upperLeftAttackerPoint + " and " + destination + " in direction:"
						+ candidateDirection + " via:" + neighbourGameBoardPoint + " : " + distanceUsingCandidate);
				if (distanceUsingCandidate < minimumDistance) {
					nearestDirection = candidateDirection;
					minimumDistance = distanceUsingCandidate;
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

	public Vector2D getNextMovement(Attacker attacker) {
		Point attackerEscapeDestination = attacker.getEscapeDestination();
		GameBoardPoint attackerTopLeftCorner = attacker.getTopLeftCorner();
		if (attacker.getAttackerDataModel().isFlying()) {
			Vector2D fromAttackerTopLeftCornerToEscapeDestination = new Vector2D(attackerTopLeftCorner,
					attackerEscapeDestination);
			fromAttackerTopLeftCornerToEscapeDestination.resizeTo(attacker.getSpeed());
			return fromAttackerTopLeftCornerToEscapeDestination;
		}
		return null;
	}

}
