package main.numbers;

import static main.numbers.InfiniteNaturalNumber.*;
import static main.numbers.InfiniteNaturalNumber.TWO;

import java.util.ArrayList;
import java.util.List;

import main.util.CollectionUtils;

public class PrimeNumbersCalculator {

	public static final List<InfiniteNaturalNumber> admittedPrimeNumbers = CollectionUtils.asList(TWO, THREE);

	public static InfiniteNaturalNumber getFirstPrimeNumber() {
		return admittedPrimeNumbers.get(0);
	}

	public static InfiniteNaturalNumber getNextPrimeNumber(List<InfiniteNaturalNumber> previousPrimeNumbers) {
		return getNextPrimeNumber(previousPrimeNumbers, null);
	}

	private static InfiniteNaturalNumber getNextPrimeNumber(List<InfiniteNaturalNumber> previousPrimeNumbers,
			InfiniteNaturalNumber maxNumberToTest) {
		if (previousPrimeNumbers.isEmpty()) {
			return getFirstPrimeNumber();
		}
		if (previousPrimeNumbers.size() < admittedPrimeNumbers.size()) {
			return admittedPrimeNumbers.get(previousPrimeNumbers.size());
		} else {
			InfiniteNaturalNumber potentialPrimeNumber = previousPrimeNumbers.get(previousPrimeNumbers.size() - 1);
			while (true) {
				potentialPrimeNumber = potentialPrimeNumber.plus(TWO);

				if (maxNumberToTest != null && potentialPrimeNumber.isStrictlyGreaterThan(maxNumberToTest)) {
					return null;
				}

				boolean isPotentialPrimeNumber = true;
				for (InfiniteNaturalNumber primeNumber : previousPrimeNumbers) {
					if (potentialPrimeNumber.isMultipleOf(primeNumber)) {
						isPotentialPrimeNumber = false;
						break;
					}
				}
				if (isPotentialPrimeNumber)
					return potentialPrimeNumber;
			}
		}
	}

	public static List<InfiniteNaturalNumber> findPrimeNumbersUpTo(InfiniteNaturalNumber maxNumberToTest) {
		if (maxNumberToTest.isStrictlySmallerThan(admittedPrimeNumbers.get(0))) {
			return new ArrayList<InfiniteNaturalNumber>();
		}
		if (admittedPrimeNumbers.contains(maxNumberToTest)) {
			int index = admittedPrimeNumbers.indexOf(maxNumberToTest);
			List<InfiniteNaturalNumber> subList = admittedPrimeNumbers.subList(0, index + 1);
			return subList;
		}

		List<InfiniteNaturalNumber> foundPrimeNumbers = new ArrayList<>();
		foundPrimeNumbers.addAll(admittedPrimeNumbers);

		InfiniteNaturalNumber potentialPrimeNumber;

		while ((potentialPrimeNumber = foundPrimeNumbers.get(foundPrimeNumbers.size() - 1))
				.isSmallerOrEqualsTo(maxNumberToTest)) {

			InfiniteNaturalNumber nextPrimeNumber = getNextPrimeNumber(foundPrimeNumbers, maxNumberToTest);
			if (nextPrimeNumber == null) {
				break;
			}
			foundPrimeNumbers.add(nextPrimeNumber);

		}

		return foundPrimeNumbers;
	}
}
