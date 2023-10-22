package main.common.timer;

public abstract class PausablePeriodicDelayedTask extends PausableOneShotDelayedTask {

	protected PausablePeriodicDelayedTask(long delay) {
		super(delay);
	}

	protected PausablePeriodicDelayedTask(String label, long delay) {
		super(label, delay);
	}
	
	@Override
	protected void afterTaskRun() {
		createTimer();
	}

}
