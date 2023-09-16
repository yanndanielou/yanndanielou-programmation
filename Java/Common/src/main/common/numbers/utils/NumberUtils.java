package main.common.numbers.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@SuppressWarnings("java:S1118")
public class NumberUtils {

	public static boolean isEven(int number) {
		return number % 2 == 0;
	}

	public static IntStream getDigitsFromIntegerAsIntStream(int n) {
		String numberAsString = String.valueOf(n);
		return numberAsString.chars();
	}

	public static List<Integer> getDigitsFromIntegerAsIntList(int n) {
		String numberAsString = String.valueOf(n);
		int[] numberAsListOfInt = numberAsString.chars().toArray();
		return transformListOfIntWithCharacterValueToListOfInteger(numberAsListOfInt);
	}

	private static List<Integer> transformListOfIntWithCharacterValueToListOfInteger(
			int[] listOfIntWithCharacterValue) {
		List<Integer> list = new ArrayList<Integer>(Arrays.stream(listOfIntWithCharacterValue).boxed().toList());
		list = list.stream().map(p -> Character.getNumericValue(p)).collect(Collectors.toList());
		return list;
	}

	public static List<Integer> getDigitsFromIntegerInAscendingOrderAsIntList(int n) {
		String numberAsString = String.valueOf(n);
		int[] numberAsListOfInt = numberAsString.chars().sorted().toArray();
		return transformListOfIntWithCharacterValueToListOfInteger(numberAsListOfInt);
	}

	public static List<Integer> getDigitsFromIntegerInDescendingOrderAsIntList(int n) {
		List<Integer> digitsFromIntegerAsIntList = getDigitsFromIntegerInAscendingOrderAsIntList(n);
		Collections.reverse(digitsFromIntegerAsIntList);

		return digitsFromIntegerAsIntList;
	}

	public static int getHighestDigitOfInteger(int n) {

		IntStream chars = getDigitsFromIntegerAsIntStream(n);
		OptionalInt maxDigit = chars.max();
		return Character.getNumericValue(maxDigit.getAsInt());
	}

	public static int computePositiveIntFromListOfDigits(List<Integer> digits) {
		int result = 0;

		for (int i = 0; i < digits.size(); i++) {
			Integer digit = digits.get(i);
			int power = digits.size() - 1 - i;
			double increment = digit * Math.pow(10, power);
			result += increment;
		}

		return result;

	}

	public static int getNumberOfDigits(int n) {
		for (int i = 1;; i++) {
			if (Math.pow(10, i) > Math.abs(n)) {
				return i;
			}
		}
	}

}
