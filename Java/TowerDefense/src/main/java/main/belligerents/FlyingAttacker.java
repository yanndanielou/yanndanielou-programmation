package main.belligerents;

import java.awt.Point;
import java.awt.image.BufferedImage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.builders.belligerents.AttackerDataModel;
import main.game.Game;

public class FlyingAttacker extends Attacker {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(FlyingAttacker.class);

	public FlyingAttacker(AttackerDataModel attackerDataModel, Game game, int x, int y, Point escapeDestination,
			int evolutionLevel) {

		super(attackerDataModel, game, x, y, escapeDestination, evolutionLevel);
	}

	@Override
	protected BufferedImage getGraphicalRepresentationAsBufferedImage() {
		return getFlyingAttackerBufferedImage(this);
	}

}
