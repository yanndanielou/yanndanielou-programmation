package common.timer;

public abstract class PausableOneShotDelayedTask extends PausableDelayedTask {

	protected PausableOneShotDelayedTask(long delay) {
		super(delay);
	}

	protected PausableOneShotDelayedTask(String label, long delay) {
		super(label, delay);
	}

}
