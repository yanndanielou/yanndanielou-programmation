package common.timer;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.exceptions.BadLogicException;

public abstract class PausableOneShotDelayedTask extends PausableDelayedTask {

	protected PausableOneShotDelayedTask(long delay) {
		super(delay);
	}

	protected PausableOneShotDelayedTask(String label, long delay) {
		super(label, delay);
	}

}
