package main.belligerents;

import java.awt.Point;
import java.awt.image.BufferedImage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.belligerents.weapon.Weapon;
import main.builders.belligerents.AttackerDataModel;
import main.game.Game;

public class NormalAttacker extends Attacker {
	private static final Logger LOGGER = LogManager.getLogger(NormalAttacker.class);

	public NormalAttacker(AttackerDataModel attackerDataModel, Game game, int x, int y, Point escapeDestination, int evolutionLevel) {

		super(attackerDataModel, game, x, y, escapeDestination, evolutionLevel);
		LOGGER.info("Create NormalAttacker at x:" + x + ", y:" + y);
	}

	@Override
	protected void rightBorderOfGameBoardReached() {
		setXSpeed(getXSpeed() * -1);
	}

	@Override
	protected void leftBorderOfGameBoardReached() {
	}

	@Override
	public void impactNow(Weapon weapon) {
		super.impactNow(weapon);
	}

	@Override
	protected BufferedImage getGraphicalRepresentationAsBufferedImage() {
		return getNormalAttackerBufferedImage(this);
	}

	@Override
	protected void downBorderOfGameBoardReached() {
		// Auto-generated method stub

	}

}
