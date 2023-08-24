package main.belligerents;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.belligerents.weapon.SimpleTowerBomb;
import main.belligerents.weapon.Weapon;
import main.game.Game;
import main.gameboard.GameBoardPoint;
import main.geometry.IntegerPoint;
import main.geometry.IntegerRectangle;

public abstract class GameObject {
	private static final Logger LOGGER = LogManager.getLogger(GameObject.class);

	protected IntegerRectangle surroundingRectangleAbsoluteOnCompleteBoard;
	protected Integer xSpeed;
	protected Integer ySpeed;
	protected Game game;

	protected boolean beingDestroyed = false;

	private BufferedImage simpleTowerNormalBufferedImage = null;
	private static final String SIMPLE_TOWER_NORMAL_IMAGE_PATH = "Images/Simple_tower_60x60.png";

	private BufferedImage simpleTowerBombBufferedImage = null;
	private static final String SIMPLE_TOWER_BOMB_IMAGE_PATH = "Images/simple_tower_bomb.png";

	private BufferedImage normalAttackerBufferedImage = null;
	private static final String NORMAL_ATTACKER_IMAGE_PATH = "Images/Attacker_normal_going_right.png";

	protected int evolutionLevel;

	protected GameObject(IntegerRectangle surroundingRectangleAbsoluteOnCompleteBoard, Game game, int evolutionLevel) {
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
		simpleTowerNormalBufferedImage = getBufferedImageFromFilePath(SIMPLE_TOWER_NORMAL_IMAGE_PATH);
	}

	private void initializeAndLoadImages() {
		initializeTowers();
		initializeBombs();
		initializeAttackers();
	}

	private void initializeAttackers() {
		normalAttackerBufferedImage = getBufferedImageFromFilePath(NORMAL_ATTACKER_IMAGE_PATH);
	}

	private void initializeBombs() {
		simpleTowerBombBufferedImage = getBufferedImageFromFilePath(SIMPLE_TOWER_BOMB_IMAGE_PATH);
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

	public List<GameBoardPoint> getAllCornersGameBoardPoints() {
		ArrayList<GameBoardPoint> allCornersGameBoardPoints = new ArrayList<>();
		allCornersGameBoardPoints
				.add(game.getGameBoard().getGameBoardPointByXAndY(getExtremeLeftPointX(), getHighestPointY()));
		allCornersGameBoardPoints
				.add(game.getGameBoard().getGameBoardPointByXAndY(getExtremeRightPointX(), getHighestPointY()));
		allCornersGameBoardPoints
				.add(game.getGameBoard().getGameBoardPointByXAndY(getExtremeLeftPointX(), getLowestPointY()));
		allCornersGameBoardPoints
				.add(game.getGameBoard().getGameBoardPointByXAndY(getExtremeRightPointX(), getLowestPointY()));
		return allCornersGameBoardPoints;
	}

	public int getExtremeLeftPointX() {
		return surroundingRectangleAbsoluteOnCompleteBoard.getX();
	}

	public int getExtremeRightPointX() {
		return surroundingRectangleAbsoluteOnCompleteBoard.getMaxX();
	}

	public int getLowestPointY() {
		return surroundingRectangleAbsoluteOnCompleteBoard.getMaxY();
	}

	public int getHighestPointY() {
		return surroundingRectangleAbsoluteOnCompleteBoard.getY();
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
