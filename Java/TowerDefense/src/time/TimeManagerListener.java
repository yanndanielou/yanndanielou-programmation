package time;

public interface TimeManagerListener {

	public void on10msTick();

	default public void on_20ms_tick() {
	}

	public void on50msTick();

	public void on100msTick();

	public void onSecondTick();

	public void onPause();

}
