package main.time;

import java.util.ArrayList;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PausableTimeManager extends TimerTask {

	private static final Logger LOGGER = LogManager.getLogger(PausableTimeManager.class);

	private ArrayList<TimeManagerListener> newListenersWaitingCurrentTickToRegister = new ArrayList<>();
	private ArrayList<TimeManagerListener> timeManagerListeners = new ArrayList<>();

	private PausableTimer timer;
	private int numberOf10msTick = 0;

	private boolean aTickIsUnderProcessing = false;

	public PausableTimeManager() {
		LOGGER.info("PausableTimeManager created");

	}

	public void addListener(TimeManagerListener listener) {
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
		if (numberOf10msTick % 1000 == 0) {
			tick10Seconds();
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
		LOGGER.info("PausableTimeManager: tickSecond");
		notifySecondTick();
	}

	private void tick10Seconds() {
		LOGGER.info("PausableTimeManager: tick10Seconds");
		notifySecondTick();
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
		timer = new PausableTimer();
		timer.start(this, 0, 10);
		LOGGER.info("PausableTimeManager started");
	}

	public void pause() {
		System.out.println("PausableTimeManager: pause");
		timer.pause();
		notifyPauseOrStop();
	}

	public void resume() {
		System.out.println("PausableTimeManager: resume");
		timer.resume();
	}

	public void stop() {
		System.out.println("PausableTimeManager:stop");
		timer.cancel();
		notifyPauseOrStop();
	}
}
