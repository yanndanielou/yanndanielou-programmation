package time;

public interface TimeManagerListener {
	
	public void on_10ms_tick();
	public void on_100ms_tick();
	public void on_second_tick();

}
