package main.common.timer;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.common.exceptions.BadLogicException;

public abstract class PausablePeriodicDelayedTask extends PausableOneShotDelayedTask {

	protected PausablePeriodicDelayedTask(long delay) {
		super(delay);
	}

	protected PausablePeriodicDelayedTask(String label, long delay) {
		super(label, delay);
	}
	
	@Override
	protected void afterTaskRun() {
		createTimer();
	}

}
