package time;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import builders.scenariolevel.ScenarioLevelDataModel;
import builders.scenariolevel.ScenarioLevelWaveDataModel;
import game.Game;
import game.GameListener;
import game.GameStatusListener;

public class TimeManager extends TimerTask implements GameStatusListener {
	private static TimeManager instance = null;
	private static TimeManager previous_instance_before_pause = null;

	private static final Logger LOGGER = LogManager.getLogger(TimeManager.class);

	private ArrayList<TimeManagerListener> new_listeners_waiting_current_tick_to_register = new ArrayList<>();
	private ArrayList<TimeManagerListener> time_manager_listeners = new ArrayList<>();

	private Timer timer;
	private int number_of_10ms_tick = 0;

	private TimeManager() {
		LOGGER.info("TimerTask created");

	}

	public static TimeManager getInstance() {
		if (instance == null) {
			instance = new TimeManager();

			if (previous_instance_before_pause != null) {
				instance.new_listeners_waiting_current_tick_to_register = previous_instance_before_pause.time_manager_listeners;
				previous_instance_before_pause = null;
			}
		}
		return instance;
	}

	public void add_listener(TimeManagerListener listener) {
		// LOGGER.info("add_listener:" + listener);
		new_listeners_waiting_current_tick_to_register.add(listener);
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

		notify_10ms_tick();

		time_manager_listeners.addAll(new_listeners_waiting_current_tick_to_register);
		new_listeners_waiting_current_tick_to_register.clear();
	}

	private void notify_10ms_tick() {
		for (TimeManagerListener time_manager_listener : time_manager_listeners) {
			time_manager_listener.on_10ms_tick();
		}
	}

	private void tick_20ms() {
		notify_20ms_tick();
	}

	private void notify_20ms_tick() {
		for (TimeManagerListener time_manager_listener : time_manager_listeners) {
			time_manager_listener.on_20ms_tick();
		}
	}

	private void tick_50ms() {
		notify_50ms_tick();
	}

	private void notify_50ms_tick() {
		for (TimeManagerListener time_manager_listener : time_manager_listeners) {
			time_manager_listener.on_50ms_tick();
		}
	}

	private void tick_100ms() {
		// System.out.println("tick_100ms:" + new Date());
		LOGGER.debug("tick_100ms begin");

		notify_100ms_tick();

		LOGGER.debug("tick_100ms end");
	}

	private void notify_100ms_tick() {
		for (TimeManagerListener time_manager_listener : time_manager_listeners) {
			time_manager_listener.on_100ms_tick();
		}
	}

	private void tick_second() {
		// System.out.println("tick_second:" + new Date());
		LOGGER.trace("tick_second begin");

		notify_second_tick();

		LOGGER.debug("tick_second end");
	}

	private void notify_second_tick() {
		for (TimeManagerListener time_manager_listener : time_manager_listeners) {
			time_manager_listener.on_second_tick();
		}
	}

	private void notify_pause_or_stop() {
		for (TimeManagerListener time_manager_listener : time_manager_listeners) {
			time_manager_listener.on_pause();
		}
	}

	public void start() {
		// running timer task as daemon thread
		timer = new Timer(true);
		timer.scheduleAtFixedRate(this, 0, 10);
		LOGGER.info("TimerTask started");
	}

	public void stop() {
		previous_instance_before_pause = instance;
		notify_pause_or_stop();
		timer.cancel();
		timer.purge();
		instance = null;
		System.out.println("TimerTask cancelled");
	}

	@Override
	public void on_game_paused(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_game_resumed(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_listen_to_game_status(Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on_game_cancelled(Game game) {
		time_manager_listeners.remove(game);
	}

}
