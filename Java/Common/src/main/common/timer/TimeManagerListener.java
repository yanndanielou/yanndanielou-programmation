package main.common.timer;

public interface TimeManagerListener {

	public default void on10msTick() {
	}

	public default void on20msTick() {
	}

	public default void on50msTick() {
	}

	public default void on100msTick() {
	}

	public default void onSecondTick() {
	}

	public default void onPause() {
	}

}
