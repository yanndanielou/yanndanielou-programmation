package main;

import main.numbers.InfiniteNaturalNumber;

public class LychrelNumbersApplication {
	public static void main(String[] args) {
		for (InfiniteNaturalNumber number = InfiniteNaturalNumber.ZERO; number.isStrictlySmallerThan(
				InfiniteNaturalNumber.TWO_HUNDREDS); number = number.plus(InfiniteNaturalNumber.ONE)) {
			LychrelNumbers.isLychrelNumber(number);
		}
	}
}
