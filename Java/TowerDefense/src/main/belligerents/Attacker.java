package main.belligerents;

import java.awt.Point;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.belligerents.listeners.AttackerListener;
import main.belligerents.weapon.Weapon;
import main.builders.belligerents.AttackerDataModel;
import main.common.timer.TimeManagerListener;
import main.core.GameManager;
import main.game.Game;
import main.geometry2d.integergeometry.IntegerPrecisionRectangle;

public abstract class Attacker extends Belligerent implements TimeManagerListener {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Attacker.class);

	protected int scorePrizeMoneyOnDestruction = 0;
	protected AttackerDataModel attackerDataModel;
	protected int remainingHealth;

	protected ArrayList<AttackerListener> listeners = new ArrayList<>();
	protected Point escapeDestination;

	protected boolean escapeDestinationReached = false;

	protected Attacker(AttackerDataModel attackerDataModel, Game game, int x, int y, Point escapeDestination,
			int evolutionLevel) {

		super(new IntegerPrecisionRectangle(x, y, attackerDataModel.getWidth(), attackerDataModel.getHeight()), game,
				evolutionLevel);

		this.attackerDataModel = attackerDataModel;

		setMaxNumberOfLivingBombs(0);

		game.getTimeManager().addListener(this);

		GameManager.getInstance().getDesktopTowerDefenseMainView().registerToAttacker(this);
		addListener(game);
		addListener(game.getGameBoard());

		this.escapeDestination = escapeDestination;
		LOGGER.info("Create attacker " + this.getClass().getCanonicalName() + " " + attackerDataModel.getDescription()
				+ " at x:" + x + ", y:" + y);
	}

	@Override
	public boolean move(float xMovement, float yMovement) {
		boolean moved = super.move(xMovement, yMovement);
		if (surroundingRectangleAbsoluteOnCompleteBoard.getIntegerPrecisionRectangle().contains(escapeDestination)) {
			escaped();
		}
		return moved;
	}

	@Override
	public boolean isAllowedToMove() {
		return super.isAllowedToMove() && !escapeDestinationReached;
	}

	protected void escaped() {
		LOGGER.info(this + " has just escaped!");
		escapeDestinationReached = true;
		listeners.forEach(listener -> listener.onAttackerEscape(this));
	}

	public void addListener(AttackerListener attackerListener) {
		listeners.add(attackerListener);
		attackerListener.onListenToAttacker(this);
	}

	@Override
	public void notifyMovement() {
		listeners.forEach(listener -> listener.onAttackerMoved(this));
	}

	@Override
	public void impactNow(Weapon weapon) {
	}

	@Override
	public void notifyEndOfDestructionAndClean() {
	}

	public Point getEscapeDestination() {
		return escapeDestination;
	}

	public AttackerDataModel getAttackerDataModel() {
		return attackerDataModel;
	}

	@Override
	public float getSpeed() {
		return attackerDataModel.getLevels().get(evolutionLevel).getSpeed();
	}

}
