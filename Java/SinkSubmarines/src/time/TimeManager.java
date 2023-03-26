package time;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeManager extends TimerTask {
	private static TimeManager instance;

	private static final Logger LOGGER = LogManager.getLogger(TimeManager.class);

	private ArrayList<TimeManagerListener> time_manager_listeners = new ArrayList<>();

	private Timer timer;
	private int number_of_10ms_tick = 0;

	private TimeManager() {
		// running timer task as daemon thread
		timer = new Timer(true);
		timer.scheduleAtFixedRate(this, 0, 10);
		System.out.println("TimerTask started");
	}

	public static TimeManager getInstance() {
		if (instance == null) {
			instance = new TimeManager();
		}
		return instance;
	}

	public void add_listener(TimeManagerListener listener) {
		time_manager_listeners.add(listener);
	}

	@Override
	public void run() {
		tick_10ms();
	}

	private void tick_10ms() {
		number_of_10ms_tick++;
		if (number_of_10ms_tick % 10 == 0) {
			tick_100ms();
		}
		if (number_of_10ms_tick % 2 == 0) {
			tick_20ms();
		}
		if (number_of_10ms_tick % 5 == 0) {
			tick_50ms();
		}
		if (number_of_10ms_tick % 100 == 0) {
			tick_second();
		}

		for (TimeManagerListener time_manager_listener : time_manager_listeners) {
			time_manager_listener.on_10ms_tick();
		}
	}

	private void tick_50ms() {
		for (TimeManagerListener time_manager_listener : time_manager_listeners) {
			time_manager_listener.on_50ms_tick();
		}

		
	}

	private void tick_20ms() {
		for (TimeManagerListener time_manager_listener : time_manager_listeners) {
			time_manager_listener.on_20ms_tick();
		}

	}

	private void tick_100ms() {
		// System.out.println("tick_100ms:" + new Date());
		LOGGER.debug("tick_second");

		for (TimeManagerListener time_manager_listener : time_manager_listeners) {
			time_manager_listener.on_100ms_tick();
		}
	}

	private void tick_second() {
		// System.out.println("tick_second:" + new Date());
		LOGGER.trace("tick_second");

		for (TimeManagerListener time_manager_listener : time_manager_listeners) {
			time_manager_listener.on_second_tick();
		}
	}

	public void start() {

	}

	public void stop() {

		timer.cancel();
		System.out.println("TimerTask cancelled");
	}
}
