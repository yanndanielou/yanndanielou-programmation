package main.numbers;

import static main.numbers.InfiniteNaturalNumber.THREE;
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
		if (previousPrimeNumbers.isEmpty()) {
			return getFirstPrimeNumber();
		} else {

		}
		return null;
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

		InfiniteNaturalNumber potentialPrimeNumber = foundPrimeNumbers.get(foundPrimeNumbers.size() - 1);

		while ((potentialPrimeNumber = potentialPrimeNumber.plus(TWO)).isSmallerOrEqualsTo(maxNumberToTest)) {
			boolean isPotentialPrimeNumber = true;
			for (InfiniteNaturalNumber primeNumber : foundPrimeNumbers) {
				if (potentialPrimeNumber.isMultipleOf(primeNumber)) {
					isPotentialPrimeNumber = false;
					break;
				}
			}
			if (isPotentialPrimeNumber)
				foundPrimeNumbers.add(potentialPrimeNumber);
		}

		return foundPrimeNumbers;
	}
}
