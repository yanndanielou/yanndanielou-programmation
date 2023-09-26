package towerdefense.belligerents;

import java.awt.Point;
import java.awt.image.BufferedImage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import towerdefense.builders.belligerents.AttackerDataModel;
import towerdefense.game.Game;

public class NormalAttacker extends Attacker {
	private static final Logger LOGGER = LogManager.getLogger(NormalAttacker.class);

	public NormalAttacker(AttackerDataModel attackerDataModel, Game game, int x, int y, Point escapeDestination,
			int evolutionLevel) {

		super(attackerDataModel, game, x, y, escapeDestination, evolutionLevel);
		LOGGER.info("Create NormalAttacker at x:" + x + ", y:" + y);
	}

	@Override
	protected BufferedImage getGraphicalRepresentationAsBufferedImage() {
		return getNormalAttackerBufferedImage(this);
	}

}
