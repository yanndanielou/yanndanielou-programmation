package main.time;

public interface TimeManagerListener {

	default public void on10msTick() {
	}

	default public void on20msTick() {
	}

	default public void on50msTick() {
	}

	default public void on100msTick() {
	}

	default public void onSecondTick() {
	}

	default public void onPause() {
	}

}
