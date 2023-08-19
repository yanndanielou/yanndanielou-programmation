package main.time;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PausableTimer {

	private static final Logger LOGGER = LogManager.getLogger(PausableTimer.class);

	private Timer previousTimerBeforePause = null;
	private Timer timer;
	private TimerTask persistentToAllPausesAndResumesTaskToBeExecuted;

	private TimerTask taskInstanciatedForCurrentTimer;

	private boolean paused = false;

	private long period;

	private Long pauseBeginTimeMillis;
	private Long pauseEndTimeMillis;
	private Long pauseDurationInMilliseconds;

	private Long millisecondsBeforeNextScheduledExecution;

	public PausableTimer() {
		LOGGER.info("PausableTimer created");
	}

	public void start(TimerTask timerTask, long period) {

		this.period = period;
		this.persistentToAllPausesAndResumesTaskToBeExecuted = timerTask;

		// running timer task as daemon thread
		timer = new Timer(true);

		taskInstanciatedForCurrentTimer = new TimerTask() {
			@Override
			public void run() {
				persistentToAllPausesAndResumesTaskToBeExecuted.run();
			}
		};

		timer.scheduleAtFixedRate(timerTask, 0, period);

		LOGGER.info("PausableTimer started");
	}

	public void cancel() {
		System.out.println("PausableTimer: cancel");
		timer.cancel();
		timer.purge();
	}

	public void pause() {
		long scheduledExecutionTime = persistentToAllPausesAndResumesTaskToBeExecuted.scheduledExecutionTime();
		long nextScheduledExecutionTime = scheduledExecutionTime + period;
		pauseBeginTimeMillis = System.currentTimeMillis();
		System.out.println("PausableTimer: pause. scheduledExecutionTime:" + scheduledExecutionTime
				+ ", pauseBeginTimeMillis:" + pauseBeginTimeMillis);
		millisecondsBeforeNextScheduledExecution = nextScheduledExecutionTime - pauseBeginTimeMillis;
		timer.cancel();
		timer.purge();
		paused = true;
	}

	public void resume() {
		if (!paused) {
			LOGGER.error("PausableTimer cannot be resumed because wasn't paused");
		}
		if (millisecondsBeforeNextScheduledExecution != null) {
			LOGGER.error("PausableTimer cannot be resumed because has no millisecondsBeforeNextScheduledExecution");
		}

		pauseEndTimeMillis = System.currentTimeMillis();
		pauseDurationInMilliseconds = pauseEndTimeMillis - pauseBeginTimeMillis;
		LOGGER.info("Pause has lasted:" + pauseDurationInMilliseconds + " milliseconds");

		timer = new Timer(true);
		timer.scheduleAtFixedRate(persistentToAllPausesAndResumesTaskToBeExecuted,
				millisecondsBeforeNextScheduledExecution, period);

		paused = true;
	}

}
