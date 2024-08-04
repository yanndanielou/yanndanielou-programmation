package common.string.utils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {

	public static String getISO8601CurrentLocalTime() {
		String formattedTime = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
		return formattedTime;
	}

	public static String transformIntArrayToString(int[] bytesArray) {

		StringBuilder hexString = new StringBuilder();
		for (int b : bytesArray) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static String transformBytesArrayToString(byte[] bytesArray) {

		StringBuilder hexString = new StringBuilder();
		for (byte b : bytesArray) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

}
