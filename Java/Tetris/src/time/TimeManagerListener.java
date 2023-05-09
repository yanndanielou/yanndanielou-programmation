package time;

public interface TimeManagerListener {

	public void on_10ms_tick();

	default public void on_20ms_tick() {
	}

	public void on_50ms_tick();

	public void on_100ms_tick();

	public void on_second_tick();

	public void on_pause();

}
