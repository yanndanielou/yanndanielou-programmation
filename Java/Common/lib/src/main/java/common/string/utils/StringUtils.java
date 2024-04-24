package common.string.utils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {


	public static String getISO8601CurrentLocalTime() {
		String formattedTime= ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT);
	return formattedTime;
	}
}
