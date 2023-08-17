package time;

public interface TimeManagerListener {

	public void on10msTick();

	default public void on20msTick() {
	}

	public void on50msTick();

	public void on100msTick();

	public void onSecondTick();

	public void onPause();

}
