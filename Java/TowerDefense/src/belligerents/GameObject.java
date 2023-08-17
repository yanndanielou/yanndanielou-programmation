package belligerents;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.weapon.SimpleTowerBomb;
import belligerents.weapon.Weapon;
import game.Game;
import gameboard.GameBoard;
import geometry.IntegerPoint;
import geometry.IntegerRectangle;

public abstract class GameObject {
	private static final Logger LOGGER = LogManager.getLogger(GameObject.class);

	protected IntegerRectangle surroundingRectangleAbsoluteOnCompleteBoard;
	protected Integer xSpeed;
	protected Integer ySpeed;
	protected Game game;

	protected boolean beingDestroyed = false;

	private BufferedImage simpleTowerNormalBufferedImage = null;
	private final String simpleTowerNormalImagePath = "Images/Simple_tower_60x60.png";

	private BufferedImage simpleTowerBombBufferedImage = null;
	private final String simpleTowerBombImagePath = "Images/simple_tower_bomb.png";

	private BufferedImage normalAttackerBufferedImage = null;
	private final String normalAttackerImagePath = "Images/Attacker_normal_going_right.png";

	protected int evolutionLevel;

	public GameObject(IntegerRectangle surroundingRectangleAbsoluteOnCompleteBoard, Game game,
			int evolutionLevel) {
		this.surroundingRectangleAbsoluteOnCompleteBoard = surroundingRectangleAbsoluteOnCompleteBoard;
		this.game = game;
		this.evolutionLevel = evolutionLevel;

		initializeAndLoadImages();

		LOGGER.info("Created: " + this + " at " + surroundingRectangleAbsoluteOnCompleteBoard);
	}

	private BufferedImage getBufferedImageFromFilePath(String imagePath) {
		File imageFile = new File(imagePath);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(imageFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return bufferedImage;
	}

	private void initializeTowers() {
		simpleTowerNormalBufferedImage = getBufferedImageFromFilePath(simpleTowerNormalImagePath);
	}

	private void initializeAndLoadImages() {
		initializeTowers();
		initializeBombs();
		initializeAttackers();
	}

	private void initializeAttackers() {
		normalAttackerBufferedImage = getBufferedImageFromFilePath(normalAttackerImagePath);
	}

	private void initializeBombs() {
		simpleTowerBombBufferedImage = getBufferedImageFromFilePath(simpleTowerBombImagePath);
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

	public IntegerRectangle getSurroundingRectangleAbsoluteOnCompleteBoard() {
		return surroundingRectangleAbsoluteOnCompleteBoard;
	}

	public List<IntegerPoint> getAllPoints() {
		return surroundingRectangleAbsoluteOnCompleteBoard.getAllPoints();
	}

	public void setSurroundingRectangleAbsoluteOnCompleteBoard(
			IntegerRectangle surroundingRectangleAbsoluteOnCompleteBoard) {
		this.surroundingRectangleAbsoluteOnCompleteBoard = surroundingRectangleAbsoluteOnCompleteBoard;
	}

	public int getXSpeed() {
		return xSpeed;
	}

	public boolean isInMovement() {
		return xSpeed != 0 || ySpeed != 0;
	}

	public void setXSpeed(int xSpeed) {
		LOGGER.debug(this + " set x speed:" + xSpeed);
		this.xSpeed = xSpeed;
	}

	public int getYSpeed() {
		return ySpeed;
	}

	public void setYSpeed(int ySpeed) {
		LOGGER.info(this + " set y speed:" + ySpeed);
		this.ySpeed = ySpeed;
	}

	public int getExtremeLeftPointX() {
		return (int) surroundingRectangleAbsoluteOnCompleteBoard.getX();
	}

	public int getExtremeRightPointX() {
		return (int) surroundingRectangleAbsoluteOnCompleteBoard.getMaxX();
	}

	public int getLowestPointY() {
		return (int) surroundingRectangleAbsoluteOnCompleteBoard.getMaxY();
	}

	public int getHighestPointY() {
		return (int) surroundingRectangleAbsoluteOnCompleteBoard.getY();
	}

	public boolean move(int xMovement, int yMovement) {
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

	protected abstract void rightBorderOfGameBoardReached();

	protected abstract void leftBorderOfGameBoardReached();

	protected abstract void downBorderOfGameBoardReached();

	public abstract void notifyMovement();

	public abstract void notifyEndOfDestructionAndClean();

	public void endOfDestroyAndClean() {
		notifyEndOfDestructionAndClean();
	}

	public void stopHorizontalMovement() {
		xSpeed = 0;
	}

	public void stopVerticalMovement() {
		ySpeed = 0;
	}

	public void stopMovement() {
		stopHorizontalMovement();
		stopVerticalMovement();
	}

	public Game getGame() {
		return game;
	}

	public BufferedImage getNormalAttackerBufferedImage(NormalAttacker normalAttacker) {
		return normalAttackerBufferedImage;
	}

}
