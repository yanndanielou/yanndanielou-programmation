package main.belligerents;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.belligerents.weapon.SimpleTowerBomb;
import main.belligerents.weapon.Weapon;
import main.common.hmi.utils.HMIUtils;
import main.game.Game;
import main.gameboard.GameBoardPoint;
import main.geometry2d.gameboard.IntegerDimensionsRectangleShapeWithFloatLocationPrecisionOnIntegerPrecisionGrid;
import main.geometry2d.integergeometry.IntegerPrecisionPoint;
import main.geometry2d.integergeometry.IntegerPrecisionRectangle;
import main.geometry2d.vectors.Vector2D;

public abstract class GameObject {
	private static final Logger LOGGER = LogManager.getLogger(GameObject.class);

	protected IntegerDimensionsRectangleShapeWithFloatLocationPrecisionOnIntegerPrecisionGrid surroundingRectangleAbsoluteOnCompleteBoard;

	protected Game game;

	protected boolean beingDestroyed = false;

	private BufferedImage simpleTowerNormalBufferedImage = null;
	private static final String SIMPLE_TOWER_NORMAL_IMAGE_PATH = "Images/Simple_tower_60x60.png";

	private BufferedImage simpleTowerBombBufferedImage = null;
	private static final String SIMPLE_TOWER_BOMB_IMAGE_PATH = "Images/simple_tower_bomb.png";

	private BufferedImage normalAttackerBufferedImage = null;
	private static final String NORMAL_ATTACKER_IMAGE_PATH = "Images/Attacker_normal_going_right.png";

	private BufferedImage flyingAttackerBufferedImage = null;
	private static final String FLYINIG_ATTACKER_GOING_RIGHT_IMAGE_PATH = "Images/Attacker_flying_going_right.png";

	protected int evolutionLevel;

	protected GameObject(IntegerPrecisionRectangle surroundingRectangleAbsoluteOnCompleteBoard, Game game,
			int evolutionLevel) {
		this.surroundingRectangleAbsoluteOnCompleteBoard = new IntegerDimensionsRectangleShapeWithFloatLocationPrecisionOnIntegerPrecisionGrid(
				surroundingRectangleAbsoluteOnCompleteBoard);
		this.game = game;
		this.evolutionLevel = evolutionLevel;

		initializeAndLoadImages();

		LOGGER.info("Created: " + this + " at " + surroundingRectangleAbsoluteOnCompleteBoard);
	}

	private void initializeTowers() {
		simpleTowerNormalBufferedImage = HMIUtils.getBufferedImageFromFilePath(SIMPLE_TOWER_NORMAL_IMAGE_PATH);
	}

	private void initializeAndLoadImages() {
		initializeTowers();
		initializeBombs();
		initializeAttackers();
	}

	private void initializeAttackers() {
		normalAttackerBufferedImage = HMIUtils.getBufferedImageFromFilePath(NORMAL_ATTACKER_IMAGE_PATH);
		flyingAttackerBufferedImage = HMIUtils.getBufferedImageFromFilePath(FLYINIG_ATTACKER_GOING_RIGHT_IMAGE_PATH);
	}

	private void initializeBombs() {
		simpleTowerBombBufferedImage = HMIUtils.getBufferedImageFromFilePath(SIMPLE_TOWER_BOMB_IMAGE_PATH);
	}

	public BufferedImage getSimpleTowerNormalImage(Tower simpleTower) {
		return simpleTowerNormalBufferedImage;
	}

	public BufferedImage getSimpleTowerBombImage(SimpleTowerBomb simpleTowerBomb) {
		BufferedImage image = null;

		image = simpleTowerBombBufferedImage;

		return image;
	}

	public boolean isBeingDestroyed() {
		return beingDestroyed;
	}

	public abstract void impactNow(Weapon weapon);

	public List<IntegerPrecisionPoint> getAllPoints() {
		return surroundingRectangleAbsoluteOnCompleteBoard.getIntegerPrecisionRectangle().getAllPoints();
	}

	public List<GameBoardPoint> getAllCornersGameBoardPoints() {
		ArrayList<GameBoardPoint> allCornersGameBoardPoints = new ArrayList<>();
		allCornersGameBoardPoints.add(getTopLeftCorner());
		allCornersGameBoardPoints.add(getTopRightCorner());
		allCornersGameBoardPoints.add(getBottomLeftCorner());
		allCornersGameBoardPoints.add(getBottomRightCorner());
		return allCornersGameBoardPoints;
	}

	public GameBoardPoint getBottomLeftCorner() {
		return game.getGameBoard().getGameBoardPointByXAndY(getExtremeLeftPointXWithIntegerPrecision(),
				getLowestPointY());
	}

	public GameBoardPoint getBottomRightCorner() {
		return game.getGameBoard().getGameBoardPointByXAndY(getExtremeRightPointX(), getLowestPointY());
	}

	public GameBoardPoint getTopLeftCorner() {
		return game.getGameBoard().getGameBoardPointByXAndY(getExtremeLeftPointXWithIntegerPrecision(),
				getHighestPointY());
	}

	public GameBoardPoint getTopRightCorner() {
		return game.getGameBoard().getGameBoardPointByXAndY(getExtremeRightPointX(), getHighestPointY());
	}

	public int getExtremeLeftPointXWithIntegerPrecision() {
		return surroundingRectangleAbsoluteOnCompleteBoard.getIntegerPrecisionRectangle().getX();
	}

	public int getExtremeRightPointX() {
		return surroundingRectangleAbsoluteOnCompleteBoard.getIntegerPrecisionRectangle().getMaxX();
	}

	public int getLowestPointY() {
		return surroundingRectangleAbsoluteOnCompleteBoard.getIntegerPrecisionRectangle().getMaxY();
	}

	public int getHighestPointY() {
		return surroundingRectangleAbsoluteOnCompleteBoard.getIntegerPrecisionRectangle().getY();
	}

	public int getWidth() {
		return surroundingRectangleAbsoluteOnCompleteBoard.getWidth();
	}

	public int getHeight() {
		return surroundingRectangleAbsoluteOnCompleteBoard.getHeight();
	}

	public boolean move(Vector2D nextMovement) {
		return move(Math.round(nextMovement.getX()), Math.round(nextMovement.getY()));
	}

	public boolean move(float xMovement, float yMovement) {
		boolean hasMoved = false;
		surroundingRectangleAbsoluteOnCompleteBoard.translate(xMovement, yMovement);
		notifyMovement();

		return hasMoved;
	}

	protected abstract BufferedImage getGraphicalRepresentationAsBufferedImage();

	// FIXME: remove BufferedImage and directly create icon from path
	public ImageIcon getGraphicalRepresentationAsIcon() {
		BufferedImage graphicalRepresentationAsBufferedImage = getGraphicalRepresentationAsBufferedImage();
		ImageIcon imageIcon = new ImageIcon(graphicalRepresentationAsBufferedImage);
		return imageIcon;
	}

	protected void rightBorderOfGameBoardReached() {
	}

	protected void leftBorderOfGameBoardReached() {
	}

	protected void downBorderOfGameBoardReached() {
	}

	public abstract void notifyMovement();

	public abstract void notifyEndOfDestructionAndClean();

	public void endOfDestroyAndClean() {
		notifyEndOfDestructionAndClean();
	}

	public Game getGame() {
		return game;
	}

	public BufferedImage getNormalAttackerBufferedImage(Attacker normalAttacker) {
		return normalAttackerBufferedImage;
	}

	public BufferedImage getFlyingAttackerBufferedImage(Attacker normalAttacker) {
		return flyingAttackerBufferedImage;
	}

	public int getEvolutionLevel() {
		return evolutionLevel;
	}

	public abstract float getSpeed();
}
