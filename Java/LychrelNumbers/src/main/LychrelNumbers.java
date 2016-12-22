package main;

import main.numbers.InfiniteNaturalNumber;

public class LychrelNumbers {

	public static boolean isLychrelNumber(InfiniteNaturalNumber initialNumber) {

		System.out.println("Test number: " + initialNumber.toString());

		InfiniteNaturalNumber currentIterationNumber = initialNumber;

		int numberOfIteration = 0;
		while (!currentIterationNumber.isPalindrome()) {
			System.out
					.println("     iteration: " + numberOfIteration + ". Number: " + currentIterationNumber.toString());
			currentIterationNumber = nextIterationValue(currentIterationNumber);
			numberOfIteration++;
		}

		System.out.println(initialNumber.toString() + " is not a Lychrel number: after " + numberOfIteration
				+ " iterations, " + currentIterationNumber.toString() + " is palindrome");
		
		return true;
	}

	private static InfiniteNaturalNumber nextIterationValue(InfiniteNaturalNumber notPalindromeNumber) {
		InfiniteNaturalNumber base10DigitsInversion = notPalindromeNumber.getBase10DigitsInversion();
		InfiniteNaturalNumber afterIteration = notPalindromeNumber.plus(base10DigitsInversion);
		return afterIteration;
	}

}
