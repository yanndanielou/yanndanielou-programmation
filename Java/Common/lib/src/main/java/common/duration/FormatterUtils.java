package common.duration;

import java.time.Duration;

public class FormatterUtils {

	public static String GetDurationAsString(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.toMinutes();
		long millis = duration.toMillis();
		long seconds = millis / 1000;

		if (hours > 0) {
			return hours + "hours" + minutes % 60 + "min";
		}

		if (minutes > 0) {
			return minutes + "min " + seconds % 60 + "s";
		}

		if (seconds > 0) {
			return seconds + "s" + millis % 1000 + "ms";
		}

		return millis + "ms";
	}
}
