package common.timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PausablePeriodicDelayedTask extends PausableDelayedTask {
	private static final Logger LOGGER = LogManager.getLogger(PausablePeriodicDelayedTask.class);

	protected PausablePeriodicDelayedTask(long delay) {
		super(delay);
	}

	protected PausablePeriodicDelayedTask(String label, long delay) {
		super(label, delay);
	}
	
	@Override
	protected void afterTaskRun() {
		LOGGER.info("Task " + label + " recreate timer after execution"); 
		createTimer();
	}

}
