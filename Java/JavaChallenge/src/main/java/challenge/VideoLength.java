package challenge;

public class VideoLength {

	// The format is mm:ss
	public static int minutesToSeconds(String tm) {

		String[] tmSplit = tm.split(":");
		String mmAsString = tmSplit[0];
		String ssAsString = tmSplit[1];

		int mmAsInt;
		int ssAsInt;
		try {
			mmAsInt = Integer.parseInt(mmAsString);
			ssAsInt = Integer.parseInt(ssAsString);
		} catch (NumberFormatException e) {
			return -1;
		}
		
		if(ssAsInt>=60) {
			return -1;
		}

		int totalSeconds = ssAsInt + mmAsInt * 60;
		return totalSeconds;
	}

}
