package common.timer;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.exceptions.BadLogicException;

public abstract class PausableOneShotDelayedTask {

	private static final Logger LOGGER = LogManager.getLogger(PausableOneShotDelayedTask.class);

	protected final long initialDelayInMilliseconds;
	protected long remainingDelayInMilliseconds;

	protected TimerTask taskInstanciatedForCurrentTimer;
	protected Timer timer;

	protected String label;

	protected boolean paused = false;
	protected boolean cancelled = false;

	protected PausableOneShotDelayedTask(long delay) {
		LOGGER.info(() -> "PausableOneShotDelayedTask created");
		this.initialDelayInMilliseconds = delay;
		this.remainingDelayInMilliseconds = initialDelayInMilliseconds;
		this.label = "";
		createTimer();
	}

	protected PausableOneShotDelayedTask(String label, long delay) {
		LOGGER.info(() -> "PausableOneShotDelayedTask " + label + " created");
		this.initialDelayInMilliseconds = delay;
		this.remainingDelayInMilliseconds = initialDelayInMilliseconds;
		this.label = label;
		createTimer();
	}

	private boolean isCancelled() {
		return cancelled;
	}

	protected void createTimer() {

		// running timer task as daemon thread
		timer = new Timer(true);
		taskInstanciatedForCurrentTimer = new TimerTask() {
			@Override
			public void run() {
				PausableOneShotDelayedTask.this.run();
				afterTaskRun();
			}
		};

		timer.schedule(taskInstanciatedForCurrentTimer, remainingDelayInMilliseconds);

	}

	public void cancel() {
		LOGGER.info("PausableOneShotDelayedTask: cancel");
		timer.cancel();
		timer.purge();
		timer = null;
		cancelled = true;
	}

	public void pause() {
		LOGGER.info("PausableOneShotDelayedTask: pause");

		if (isCancelled()) {
			throw new BadLogicException("Cannot pause delayed task if already cancelled");
		}
		if (paused) {
			throw new BadLogicException("Cannot pause delayed task if already paused");
		}

		paused = true;

		long scheduledExecutionTime = taskInstanciatedForCurrentTimer.scheduledExecutionTime();
		LOGGER.debug("scheduledExecutionTime:" + scheduledExecutionTime);

		long currentTimeMillis = System.currentTimeMillis();
		LOGGER.debug("currentTimeMillis:" + currentTimeMillis);

		long millisecondsBeforeNextScheduledExecution = scheduledExecutionTime - currentTimeMillis;
		remainingDelayInMilliseconds = millisecondsBeforeNextScheduledExecution;

		System.out.println("remainingDelayInMilliseconds:" + remainingDelayInMilliseconds);

		timer.cancel();
		timer.purge();
		timer = null;
		taskInstanciatedForCurrentTimer = null;
	}

	public void resume() {

		if (isCancelled()) {
			throw new BadLogicException("Cannot pause delayed task if already cancelled");
		}

		paused = false;
		createTimer();
	}

	protected void afterTaskRun() {

	}

	/**
	 * The action to be performed by this timer task.
	 */
	public abstract void run();
}
