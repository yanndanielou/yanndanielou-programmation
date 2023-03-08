package time;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeManager extends TimerTask {
	private static final Logger LOGGER = LogManager.getLogger(TimeManager.class);

	private Timer timer;
	private int number_of_10ms_tick = 0;

	@Override
	public void run() {
		tick_10ms();
	}

	private void tick_10ms() {
		number_of_10ms_tick++;
		if (number_of_10ms_tick % 10 == 0) {
			tick_100ms();
		}
		if (number_of_10ms_tick % 100 == 0) {
			tick_second();
		}
	}

	private void tick_100ms() {
		// System.out.println("tick_100ms:" + new Date());
		LOGGER.debug("tick_second");
	}

	private void tick_second() {
		// System.out.println("tick_second:" + new Date());
		LOGGER.info("tick_second");

	}

	public void start() {

	}

	public void stop() {

		timer.cancel();
		System.out.println("TimerTask cancelled");
	}

	public TimeManager() {
		// running timer task as daemon thread
		timer = new Timer(true);
		timer.scheduleAtFixedRate(this, 0, 10);
		System.out.println("TimerTask started");
	}
}
