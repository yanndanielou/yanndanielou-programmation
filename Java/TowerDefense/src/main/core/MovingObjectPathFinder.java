package main.core;

import java.awt.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.gameboard.NeighbourGameBoardPointDirection;
import geometry2d.integergeometry.IntegerPrecisionPoint;
import geometry2d.vectors.Vector2D;
import main.belligerents.Attacker;
import main.gameboard.GameBoard;
import main.gameboard.GameBoardPoint;

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
		IntegerPrecisionPoint upperLeftAttackerPoint = new IntegerPrecisionPoint(
				attacker.getExtremeLeftPointXWithIntegerPrecision(), attacker.getHighestPointY());
		GameBoard gameBoard = attacker.getGame().getGameBoard();
		Point destination = attacker.getEscapeDestination();
		GameBoardPoint upperLeftAttackerGameBoardPoint = (GameBoardPoint) gameBoard.getGameBoardPoint(upperLeftAttackerPoint);

		NeighbourGameBoardPointDirection nearestDirection = null;
		double minimumDistance = Double.MAX_VALUE;
		for (NeighbourGameBoardPointDirection candidateDirection : NeighbourGameBoardPointDirection.values()) {

			GameBoardPoint neighbourGameBoardPoint = (GameBoardPoint) gameBoard
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
		else {
			throw new org.apache.commons.lang3.NotImplementedException();
		}
	}

}
