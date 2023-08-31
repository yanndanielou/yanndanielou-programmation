package main.common.timer;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.common.exceptions.BadLogicException;

public abstract class PausableOneShotDelayedTask {

	private static final Logger LOGGER = LogManager.getLogger(PausableOneShotDelayedTask.class);

	private final long initialDelayInMilliseconds;
	private long remainingDelayInMilliseconds;

	private TimerTask taskInstanciatedForCurrentTimer;
	private Timer timer;

	private String label;

	private boolean paused = false;
	private boolean cancelled = false;
	

	public PausableOneShotDelayedTask(long delay) {
		LOGGER.info("PausableOneShotDelayedTask created");
		this.initialDelayInMilliseconds = delay;
		this.remainingDelayInMilliseconds = initialDelayInMilliseconds;
		this.label = "";
		createTimer();
	}

	public PausableOneShotDelayedTask(String label, long delay) {
		LOGGER.info("PausableOneShotDelayedTask " + label + " created");
		this.initialDelayInMilliseconds = delay;
		this.remainingDelayInMilliseconds = initialDelayInMilliseconds;
		this.label = label;
		createTimer();
	}

	private boolean isCancelled() {
		return cancelled;
	}

	private void createTimer() {

		// running timer task as daemon thread
		timer = new Timer(true);
		taskInstanciatedForCurrentTimer = new TimerTask() {
			@Override
			public void run() {
				runTask();
			}
		};

		timer.schedule(taskInstanciatedForCurrentTimer, remainingDelayInMilliseconds);

	}

	public void cancel() {
		System.out.println("PausableOneShotDelayedTask: cancel");
		timer.cancel();
		timer.purge();
		timer = null;
		cancelled = true;
	}

	public void pause() {
		System.out.println("PausableOneShotDelayedTask: pause");

		if (isCancelled()) {
			throw new BadLogicException("Cannot pause delayed task if already cancelled");
		}
		if (paused) {
			throw new BadLogicException("Cannot pause delayed task if already paused");
		}

		paused = true;

		long scheduledExecutionTime = taskInstanciatedForCurrentTimer.scheduledExecutionTime();
		System.out.println("scheduledExecutionTime:" + scheduledExecutionTime);

		long currentTimeMillis = System.currentTimeMillis();
		System.out.println("currentTimeMillis:" + currentTimeMillis);

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

	/**
	 * The action to be performed by this timer task.
	 */
	public abstract void runTask();
}
