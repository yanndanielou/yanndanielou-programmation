package common.timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.exceptions.BadLogicException;

public abstract class PausableDelayedTask {

	private static final Logger LOGGER = LogManager.getLogger(PausableDelayedTask.class);

	protected final long initialDelayInMilliseconds;
	protected long remainingDelayInMilliseconds;

	protected TimerTask taskInstanciatedForCurrentTimer;
	protected Timer timer;

	protected String label;

	protected boolean paused = false;
	protected boolean cancelled = false;

	protected PausableDelayedTask(long delay) {
		this.initialDelayInMilliseconds = delay;
		this.remainingDelayInMilliseconds = initialDelayInMilliseconds;
		this.label = UUID.randomUUID().toString().split("-")[0];
		LOGGER.info(() -> this.getClass().getName() + " " + label + " created with delay:" + delay);
		createTimer();
	}

	protected PausableDelayedTask(String label, long delay) {
		LOGGER.info(() -> this.getClass().getName() + " " + label + " created with delay:" + delay);
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
				PausableDelayedTask.this.run();
				afterTaskRun();
			}
		};

		LOGGER.info(this.getClass().getName() + " " + label + " schedule timer taskInstanciatedForCurrentTimer:"
				+ taskInstanciatedForCurrentTimer + ", remainingDelayInMilliseconds:" + remainingDelayInMilliseconds);
		timer.schedule(taskInstanciatedForCurrentTimer, remainingDelayInMilliseconds);
		this.remainingDelayInMilliseconds = initialDelayInMilliseconds;


	}

	public void cancel() {
		LOGGER.info(this.getClass().getName() + " " + label + " : cancel");
		timer.cancel();
		timer.purge();
		timer = null;
		cancelled = true;
	}

	public void pause() {
		LOGGER.info(this.getClass().getName() + " " + label + " : pause");

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

		LOGGER.info(this.getClass().getName() + " " + label + " : remainingDelayInMilliseconds: "
				+ remainingDelayInMilliseconds);

		System.out.println("remainingDelayInMilliseconds:" + remainingDelayInMilliseconds);

		timer.cancel();
		timer.purge();
		timer = null;
		taskInstanciatedForCurrentTimer = null;
	}

	public void resume() {
		LOGGER.info(this.getClass().getName() + " " + label + " : resume");

		if (isCancelled()) {
			throw new BadLogicException("Cannot pause delayed task if already cancelled");
		}
		
		if(!paused) {
			throw new BadLogicException("Cannot resume delayed task if it is not paused");
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
