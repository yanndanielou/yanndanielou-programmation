package main.junit;

import java.time.Duration;
import java.time.LocalTime;

public class PerfTestScenario {

	LocalTime startTime = LocalTime.now();

	protected Duration getTestDuration() {
		LocalTime now = LocalTime.now();
		Duration duration = Duration.between(startTime, now);
		return duration;
	}

	public PerfTestScenario() {
	}
}
