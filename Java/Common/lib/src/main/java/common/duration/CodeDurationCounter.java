package common.duration;

import java.time.Duration;

public class CodeDurationCounter {

	long counterStartTimeInNanoSeconds = System.nanoTime();

	public Duration getDuration() {
		long counterStopTimeInNanoSeconds = System.nanoTime();
		long durationInNanoSeconds = counterStopTimeInNanoSeconds - counterStartTimeInNanoSeconds;
		return Duration.ofNanos(durationInNanoSeconds);
	}

}
