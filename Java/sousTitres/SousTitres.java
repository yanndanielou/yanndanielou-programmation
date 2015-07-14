import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class SousTitres {

	static final int OFFSET_TIMESTAMP_START_DISPLAY = 0;
	static final int OFFSET_TIMESTAMP_END_DISPLAY = 17;

	public void run() {

		UserInputs userInputs = new UserInputs();
		UserInputsUI userInputsUI = new UserInputsUI();

		userInputsUI.fillUserInputs(userInputs);

		File inputSubtitlesFile = userInputs.getInputSubtitlesFile();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(inputSubtitlesFile.getAbsolutePath()));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(
					new FileWriter(inputSubtitlesFile.getParentFile().getAbsolutePath() + "\\out.srt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ligne = null;
		try {
			ligne = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (ligne != null) {

			if (isTimestampLine(ligne)) {
				System.out.println(ligne + " devient ");
				ligne = modifyTimestamp(ligne, userInputs);
				System.out.println(ligne + "\n");

			}

			writer.println(ligne);
			try {
				ligne = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.close();
	}

	private String modifyTimestamp(String ligne, UserInputs userInputs) {
		ligne = modifyTimestamp(ligne, OFFSET_TIMESTAMP_START_DISPLAY, userInputs);
		ligne = modifyTimestamp(ligne, OFFSET_TIMESTAMP_END_DISPLAY, userInputs);
		return ligne;
	}

	private String modifyTimestamp(String ligne, int offsetTimestampStartDisplay, UserInputs userInputs) {
		Action action = userInputs.getAction();

		if (action == Action.ADD_TIME || action == Action.REMOVE_TIME) {
			return modifyTimestampWithAddOrRemoveMilliseconds(ligne, offsetTimestampStartDisplay, userInputs);
		} else if (action == Action.BALANCE_TIME) {
			return modifyTimestampWithBalanceTime(ligne, offsetTimestampStartDisplay, userInputs);
		}

		return null;
	}

	private boolean isTimestampLine(String line) {
		return (line.length() == 29) && (line.charAt(2) == ':') && (line.charAt(5) == ':') && (line.charAt(8) == ',');
	}

	private int getTimeInMilliSeconds(String ligne, int offset) {
		int hours = (ligne.charAt(offset + 0) - '0') * 10 + (ligne.charAt(offset + 1) - '0');
		int minutes = (ligne.charAt(offset + 3) - '0') * 10 + (ligne.charAt(offset + 4) - '0');
		int seconds = (ligne.charAt(offset + 6) - '0') * 10 + (ligne.charAt(offset + 7) - '0');
		int milliseconds = (ligne.charAt(offset + 9) - '0') * 100 + (ligne.charAt(offset + 10) - '0') * 10
				+ (ligne.charAt(offset + 11) - '0');

		return milliseconds + 1000 * seconds + 60000 * minutes + 3600000 * hours;
	}

	private String buildAndReplaceTimeStampInLine(String line, int newTimeStampInMillisecond, int offset) {

		int milliseconds = newTimeStampInMillisecond % 1000;
		int seconds = (newTimeStampInMillisecond / 1000);
		int minutes = seconds / 60;
		int hours = minutes / 60;

		seconds %= 60;
		minutes %= 60;

		char[] ligneTableau = line.toCharArray();

		ligneTableau[offset + 0] = (char) (hours / 10 + '0');
		ligneTableau[offset + 1] = (char) (hours % 10 + '0');

		ligneTableau[offset + 3] = (char) (minutes / 10 + '0');
		ligneTableau[offset + 4] = (char) (minutes % 10 + '0');

		ligneTableau[offset + 6] = (char) (seconds / 10 + '0');
		ligneTableau[offset + 7] = (char) (seconds % 10 + '0');

		ligneTableau[offset + 9] = (char) (milliseconds / 100 + '0');
		ligneTableau[offset + 10] = (char) ((milliseconds / 10) % 10 + '0');
		ligneTableau[offset + 11] = (char) (milliseconds % 10 + '0');

		return new String(ligneTableau);
	}

	private String modifyTimestampWithBalanceTime(String line, int offsetTimestampStartDisplay, UserInputs userInputs) {
		int originalTimestamp = getTimeInMilliSeconds(line, offsetTimestampStartDisplay);

		int newTimestamp = (int) (originalTimestamp * userInputs.getBalanceRatio());
		return buildAndReplaceTimeStampInLine(line, newTimestamp, offsetTimestampStartDisplay);
	}

	private String modifyTimestampWithAddOrRemoveMilliseconds(String line, int offset, UserInputs userInputs) {

		int nombreMilisecondsModification = userInputs.getNombreMilisecondsModification();
		int minuteDebutModifications = userInputs.getMinuteDebutModifications();
		Action action = userInputs.getAction();

		int originalTimestamp = getTimeInMilliSeconds(line, offset);
		int originalTimestampInMinutes = originalTimestamp / 1000 / 60;
		int newTimestamp = originalTimestamp;

		if (minuteDebutModifications == 0 || minuteDebutModifications >= originalTimestampInMinutes) {
			if (action == Action.ADD_TIME) {
				newTimestamp += nombreMilisecondsModification;
			}

			else if (action == Action.REMOVE_TIME) {
				newTimestamp -= nombreMilisecondsModification;
			}
		}

		return buildAndReplaceTimeStampInLine(line, newTimestamp, offset);
	}
}