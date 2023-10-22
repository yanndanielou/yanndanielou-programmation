package junit;

import java.time.Duration;
import java.time.LocalTime;

public class PerfTestScenario {

	LocalTime startTime = LocalTime.now();
	Duration limitDuration = null;

	protected Duration getTestDuration() {
		LocalTime now = LocalTime.now();
		Duration duration = Duration.between(startTime, now);
		return duration;
	}

	public PerfTestScenario() {
	}
}
