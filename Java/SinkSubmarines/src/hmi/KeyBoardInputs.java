package hmi;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameManager;
import moving_objects.boats.AllyBoat;

public class KeyBoardInputs implements KeyListener {
	private static final Logger LOGGER = LogManager.getLogger(KeyBoardInputs.class);

	private SinkSubmarinesMainView sinkSubmarinesMainView = null;

	public KeyBoardInputs(SinkSubmarinesMainView sinkSubmarinesMainView) {
		this.sinkSubmarinesMainView = sinkSubmarinesMainView;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		LOGGER.info("keyTyped:" + KeyEvent.getKeyText(e.getKeyCode()) + " event:" + e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		LOGGER.info("keyPressed:" + KeyEvent.getKeyText(e.getKeyCode()) + " event:" + e);

		AllyBoat ally_boat = GameManager.getInstance().getGame().getAlly_boat();
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			ally_boat.increase_left_speed();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			ally_boat.increase_right_speed();
		} else if (e.getKeyCode() == KeyEvent.VK_1) {
			GameManager.getInstance().dropSimpleAllyBoatAtLeftOfAllyBoat();
		} else if (e.getKeyCode() == KeyEvent.VK_3) {
			GameManager.getInstance().dropSimpleAllyBoatAtRightOfAllyBoat();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		LOGGER.info("keyReleased:" + KeyEvent.getKeyText(e.getKeyCode()) + " event:" + e);
	}

}
