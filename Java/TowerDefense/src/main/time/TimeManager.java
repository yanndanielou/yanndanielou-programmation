package main.time;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.game.Game;
import main.game.GameStatusListener;

public class TimeManager extends TimerTask implements GameStatusListener {
	private static TimeManager instance = null;
	private static TimeManager previousInstanceBeforePause = null;

	private static final Logger LOGGER = LogManager.getLogger(TimeManager.class);

	private ArrayList<TimeManagerListener> newListenersWaitingCurrentTickToRegister = new ArrayList<>();
	private ArrayList<TimeManagerListener> timeManagerListeners = new ArrayList<>();

	private Timer timer;
	private int numberOf10msTick = 0;

	private boolean aTickIsUnderProcessing = false;

	private TimeManager() {
		LOGGER.info("TimerTask created");

	}

	public static TimeManager getInstance() {
		if (instance == null) {
			instance = new TimeManager();

			if (previousInstanceBeforePause != null) {
				instance.newListenersWaitingCurrentTickToRegister = previousInstanceBeforePause.timeManagerListeners;
				previousInstanceBeforePause = null;
			}
		}
		return instance;
	}

	public void addListener(TimeManagerListener listener) {
		// LOGGER.info("addListener:" + listener);
		newListenersWaitingCurrentTickToRegister.add(listener);
	}

	@Override
	public void run() {
		if (aTickIsUnderProcessing) {
			LOGGER.fatal("A tick is already under processing, discard current tick");
		}
		aTickIsUnderProcessing = true;
		tick10ms();
		aTickIsUnderProcessing = false;
	}

	private void tick10ms() {
		numberOf10msTick++;
		if (numberOf10msTick % 10 == 0) {
			tick100ms();
		}
		if (numberOf10msTick % 2 == 0) {
			tick20ms();
		}
		if (numberOf10msTick % 5 == 0) {
			tick50ms();
		}
		if (numberOf10msTick % 100 == 0) {
			tickSecond();
		}

		notify10msTick();

		timeManagerListeners.addAll(newListenersWaitingCurrentTickToRegister);
		newListenersWaitingCurrentTickToRegister.clear();
	}

	private void notify10msTick() {
		for (TimeManagerListener timeManagerListener : timeManagerListeners) {
			timeManagerListener.on10msTick();
		}
	}

	private void tick20ms() {
		notify20msTick();
	}

	private void notify20msTick() {
		for (TimeManagerListener timeManagerListener : timeManagerListeners) {
			timeManagerListener.on20msTick();
		}
	}

	private void tick50ms() {
		notify50msTick();
	}

	private void notify50msTick() {
		for (TimeManagerListener timeManagerListener : timeManagerListeners) {
			timeManagerListener.on50msTick();
		}
	}

	private void tick100ms() {
		// System.out.println("tick100ms:" + new Date());
		LOGGER.debug("tick100ms begin");

		notify100msTick();

		LOGGER.debug("tick100ms end");
	}

	private void notify100msTick() {
		for (TimeManagerListener timeManagerListener : timeManagerListeners) {
			timeManagerListener.on100msTick();
		}
	}

	private void tickSecond() {
		// System.out.println("tickSecond:" + new Date());
		LOGGER.trace("tickSecond begin");

		notifySecondTick();

		LOGGER.debug("tickSecond end");
	}

	private void notifySecondTick() {
		for (TimeManagerListener timeManagerListener : timeManagerListeners) {
			timeManagerListener.onSecondTick();
		}
	}

	private void notifyPauseOrStop() {
		for (TimeManagerListener timeManagerListener : timeManagerListeners) {
			timeManagerListener.onPause();
		}
	}

	public void start() {
		// running timer task as daemon thread
		timer = new Timer(true);
		timer.scheduleAtFixedRate(this, 0, 10);
		LOGGER.info("TimerTask started");
	}

	public void stop() {
		previousInstanceBeforePause = instance;
		notifyPauseOrStop();
		timer.cancel();
		timer.purge();
		instance = null;
		System.out.println("TimerTask cancelled");
	}

	@Override
	public void onListenToGameStatus(Game game) {
		// Auto-generated method stub

	}

	@Override
	public void onGameCancelled(Game game) {
		stop();
	}

	@Override
	public void onGameLost(Game game) {
		stop();
	}

	@Override
	public void onGameWon(Game game) {
		stop();
	}

	@Override
	public void onGameStarted(Game game) {
		start();
	}

}
