package belligerents;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import belligerents.weapon.Weapon;
import builders.weapons.BombDataModel;
import game.Game;
import geometry.IntegerRectangle;

public abstract class Belligerent extends GameObject {
	private static final Logger LOGGER = LogManager.getLogger(Belligerent.class);

	final int maximumFireFrequencyInMilliseconds;

	protected Instant lastAllyBombDroppedTime = null;

	private int maxNumberOfLivingBombs;

	protected boolean forbidToFireByCheatcode = false;
	protected boolean forbidToMoveByCheatcode = false;

	protected BombDataModel weaponDataModel = null;

	protected ArrayList<Weapon> livingBombs = new ArrayList<Weapon>();

	public Belligerent(IntegerRectangle surroundingRectangleAbsoluteOnCompleteBoard, BombDataModel weaponDataModel,
			int maximumFireFrequencyInMilliseconds, Game game, int evolutionLevel) {
		super(surroundingRectangleAbsoluteOnCompleteBoard, game, evolutionLevel);
		this.maximumFireFrequencyInMilliseconds = maximumFireFrequencyInMilliseconds;
		this.weaponDataModel = weaponDataModel;
	}

	public Belligerent(IntegerRectangle surroundingRectangleAbsoluteOnCompleteBoard, Game game, int evolutionLevel) {
		super(surroundingRectangleAbsoluteOnCompleteBoard, game, evolutionLevel);
		this.maximumFireFrequencyInMilliseconds = Integer.MAX_VALUE;
		this.weaponDataModel = null;
	}

	public int getMaximumFireFrequencyInMilliseconds() {
		return maximumFireFrequencyInMilliseconds;
	}

	public boolean isAllowedToFire() {
		boolean allowedToFire = !forbidToFireByCheatcode && !isBeingDestroyed()
				&& isMinimalTimeSinceLastFireFulfilled() && !hasReachedMaximumNumberOfLivingBombs();
		return allowedToFire;
	}

	public boolean isAllowedToMove() {
		boolean allowedToFire = !forbidToMoveByCheatcode && !isBeingDestroyed();
		return allowedToFire;
	}

	public void forbidToFire() {
		forbidToFireByCheatcode = true;
	}

	public void forbidToMove() {
		forbidToMoveByCheatcode = true;
	}

	public int getRemainingNumberOfLivingBombsAllowed() {
		return maxNumberOfLivingBombs - livingBombs.size();
	}

	public boolean hasReachedMaximumNumberOfLivingBombs() {
		return getRemainingNumberOfLivingBombsAllowed() <= 0;
	}

	public boolean isMinimalTimeSinceLastFireFulfilled() {
		boolean minimumDelayBetweenTwoAllyBombsDroppedFulfilled = false;

		if (lastAllyBombDroppedTime != null) {
			Instant rightNow = ZonedDateTime.now().toInstant();
			long millisecondsSinceLastAllyBombDropped = rightNow.toEpochMilli()
					- lastAllyBombDroppedTime.toEpochMilli();

			if (millisecondsSinceLastAllyBombDropped > maximumFireFrequencyInMilliseconds) {
				minimumDelayBetweenTwoAllyBombsDroppedFulfilled = true;
			} else {
				LOGGER.debug("Cannot fire bomb because last one was " + millisecondsSinceLastAllyBombDropped
						+ " milliseconds ago");
			}
		} else {
			minimumDelayBetweenTwoAllyBombsDroppedFulfilled = true;
		}

		return minimumDelayBetweenTwoAllyBombsDroppedFulfilled;
	}

	public Instant getLastAllyBombDroppedTime() {
		return lastAllyBombDroppedTime;
	}

	public void onFire() {
		lastAllyBombDroppedTime = ZonedDateTime.now().toInstant();
	}

	public int getMaxNumberOfLivingBombs() {
		return maxNumberOfLivingBombs;
	}

	public void setMaxNumberOfLivingBombs(int maxNumberOfLivingBombs) {
		this.maxNumberOfLivingBombs = maxNumberOfLivingBombs;
	}

	public void addLivingBomb(Weapon w) {
		livingBombs.add(w);
	}

	public ArrayList<Weapon> getLivingBombs() {
		return livingBombs;
	}

	public boolean removeLivingBomb(Weapon weapon) {
		boolean removed = livingBombs.remove(weapon);
		if (!removed) {
			LOGGER.info("Could not remove living bomb:" + removed);
		}
		return removed;
	}

}
