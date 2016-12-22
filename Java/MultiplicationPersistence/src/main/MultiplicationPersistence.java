package main;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import main.numbers.InfiniteNaturalNumber;

public class MultiplicationPersistence {

	private LocalTime startTime = LocalTime.now();
	private int maxMultiplicationPersistenceFound = 0;
	private boolean activateDebugLogs = true;
	private List<InfiniteNaturalNumber> numbersWithPersistenceEqualToPreviousHighestPersistenceFound = new ArrayList<>();
	private List<InfiniteNaturalNumber> numbersWithPersistenceEqualToCurrentHighestPersistenceFound = new ArrayList<>();

	public void findNumbersWithBiggestMultiplicativePersistence() {

		LocalTime previousLoggedTime = LocalTime.now();
		int numberOfDigitsOfLastLoggedNumber = 0;

		for (InfiniteNaturalNumber i = InfiniteNaturalNumber.ZERO;; i = i.plus(InfiniteNaturalNumber.ONE)) {
			boolean logged = false;

			if (i.containsDigit(0)) {
				// persistence is zero!
				continue;
			}
			if (i.containsDigit(1)) {
				continue;
			}
			if (!i.areDigitsOrderedInGrowingOrder()) {
				// ignored: we have already tested that combinaison
				continue;
			}

			List<InfiniteNaturalNumber> multiplicativeSuite = getMultiplicativeSuite(i);
			if (multiplicativeSuite == null) {
				continue;
			}
			int multiplicationPersistence = multiplicativeSuite.size();

			if (multiplicationPersistence > maxMultiplicationPersistenceFound) {

				System.out.println(timeAndDurationForLogs(previousLoggedTime) + ". New persistence found! " + i
						+ " has multiplicative persistence of:" + multiplicationPersistence
						+ " and multiplicative suite:" + multiplicativeSuite);
				maxMultiplicationPersistenceFound = multiplicationPersistence;
				logged = true;

				numbersWithPersistenceEqualToPreviousHighestPersistenceFound.clear();
				numbersWithPersistenceEqualToPreviousHighestPersistenceFound
						.addAll(numbersWithPersistenceEqualToCurrentHighestPersistenceFound);
				numbersWithPersistenceEqualToCurrentHighestPersistenceFound.clear();

				System.out.println("Numbers found with persistence: " + (maxMultiplicationPersistenceFound - 1) + " :"
						+ numbersWithPersistenceEqualToPreviousHighestPersistenceFound);

			} else if (activateDebugLogs && (i.getNumberOfDigits() > numberOfDigitsOfLastLoggedNumber + 2)) {
				System.out.println(timeAndDurationForLogs(previousLoggedTime) + " still alive.., testing " + i);
				logged = true;
			}

			if (multiplicationPersistence == maxMultiplicationPersistenceFound) {
				numbersWithPersistenceEqualToCurrentHighestPersistenceFound.add(i);
			}

			if (logged) {
				previousLoggedTime = LocalTime.now();
				numberOfDigitsOfLastLoggedNumber = i.getNumberOfDigits();
			}
		}

	}

	public List<InfiniteNaturalNumber> getMultiplicativeSuite(InfiniteNaturalNumber number) {
		InfiniteNaturalNumber base10DigitsMultiplication = number;

		// System.out.println("Calculating multiplication persistence of:" +
		// number + "..");

		List<InfiniteNaturalNumber> multiplicativeSuite = new ArrayList<>();

		if (number.containsDigit(5) && number.containsOneOfDigits(2, 4, 6, 8)) {
			// will contain zero in the next element of the suite
			multiplicativeSuite.add(base10DigitsMultiplication.getBase10DigitsMultiplication());
			return multiplicativeSuite;
		}

		while (base10DigitsMultiplication.getNumberOfDigits() > 1) {

			base10DigitsMultiplication = base10DigitsMultiplication.getBase10DigitsMultiplication();

			if (base10DigitsMultiplication.getNumberOfDigits() > 2
					&& !numbersWithPersistenceEqualToPreviousHighestPersistenceFound
							.contains(base10DigitsMultiplication)) {
				// return null;
			}

			// System.out.println(" multiplicative element:" + persistence + "
			// of:" + number + " is:" + base10DigitsMultiplication);
			multiplicativeSuite.add(base10DigitsMultiplication);
		}

		// System.out.println("Multiplication persistence of:" + number + " is:"
		// + persistence + ". Multiplicative suite is:" + multiplicativeSuite);
		return multiplicativeSuite;

	}

	public int getMultiplicationPersistence(InfiniteNaturalNumber number) {
		List<InfiniteNaturalNumber> multiplicativeSuite = getMultiplicativeSuite(number);
		int multiplicativePersistence = multiplicativeSuite.size();
		System.out.println("Multiplication persistence of:" + number + " is:" + multiplicativePersistence
				+ ". Multiplicative suite is:" + multiplicativeSuite);
		return multiplicativePersistence;
	}

	private String timeAndDurationForLogs(LocalTime previousLoggedTime) {
		LocalTime now = LocalTime.now();
		Duration durationSinceLastLog = Duration.between(previousLoggedTime, now);
		Duration durationSinceStart = Duration.between(startTime, now);
		return now + " (" + getDurationAsString(durationSinceStart) + " since beginning and "
				+ getDurationAsString(durationSinceLastLog) + " since last log)";
	}

	private static String getDurationAsString(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.toMinutes();
		long millis = duration.toMillis();
		long seconds = millis / 1000;

		if (hours > 0) {
			return hours + " hours " + minutes % 60 + " min";
		}

		if (minutes > 0) {
			return minutes + " min " + seconds % 60 + " s";
		}

		if (seconds > 0) {
			return seconds + " s " + millis % 1000 + " ms";
		}

		return millis + " ms";
	}

}
