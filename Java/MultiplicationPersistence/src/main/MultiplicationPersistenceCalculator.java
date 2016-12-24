package main;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import main.numbers.InfiniteNaturalNumber;
import main.util.CollectionUtils;
import main.util.FormatterUtils;

public class MultiplicationPersistenceCalculator {

	private LocalTime startTime = LocalTime.now();
	private int maxMultiplicationPersistenceFound = 0;
	private boolean activateDebugLogs = true;

	public InfiniteNaturalNumber findNumberHavingMultiplicative(InfiniteNaturalNumber multiplicative) {

		// multiplicative is the result of n multiplication digits [2..9] (could
		// contain 1 but we ignore it)

		boolean hasOnlyOneDigitPrimeDivisors = multiplicative.hasOnlyOneDigitPrimeDivisors();
		if (hasOnlyOneDigitPrimeDivisors) {
			// We just have to concatenate all its divisors
			List<InfiniteNaturalNumber> allPrimeDivisors = multiplicative.getAllPrimeDivisors();
			List<Byte> numbersAsListOfBytes = CollectionUtils.emptyList();
			for (InfiniteNaturalNumber allPrimeDivisor : allPrimeDivisors) {
				byte divisorAsDigit = allPrimeDivisor.getUnitDigit();
				numbersAsListOfBytes.add(divisorAsDigit);
			}
			return new InfiniteNaturalNumber(numbersAsListOfBytes);
		}
		return null;
	}

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

			} else if (activateDebugLogs && (i.getNumberOfDigits() > numberOfDigitsOfLastLoggedNumber + 2)) {
				System.out.println(timeAndDurationForLogs(previousLoggedTime) + " still alive.., testing " + i);
				logged = true;
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
			multiplicativeSuite.add(InfiniteNaturalNumber.ZERO);
			return multiplicativeSuite;
		}

		while (base10DigitsMultiplication.getNumberOfDigits() > 1) {

			if (base10DigitsMultiplication.containsDigit(0)) {
				// persistence is zero!
				multiplicativeSuite.add(InfiniteNaturalNumber.ZERO);
				break;
			}
			if (base10DigitsMultiplication.containsDigit(5)
					&& base10DigitsMultiplication.containsOneOfDigits(2, 4, 6, 8)) {
				// will contain zero in the next element of the suite
				multiplicativeSuite.add(base10DigitsMultiplication.getBase10DigitsMultiplication());
				multiplicativeSuite.add(InfiniteNaturalNumber.ZERO);
				break;
			}

			base10DigitsMultiplication = base10DigitsMultiplication.getBase10DigitsMultiplication();

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
		return now + " (" + FormatterUtils.GetDurationAsString(durationSinceStart) + " since beginning and "
				+ FormatterUtils.GetDurationAsString(durationSinceLastLog) + " since last log)";
	}

}
