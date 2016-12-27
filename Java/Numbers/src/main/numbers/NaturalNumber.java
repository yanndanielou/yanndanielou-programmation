package main.numbers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.util.CollectionUtils;

public abstract class NaturalNumber implements Cloneable {
	/*
	 * public static final NaturalNumber ZERO = new NaturalNumber("0");
	 * 
	 * public static final NaturalNumber ONE = new NaturalNumber("1"); public
	 * static final NaturalNumber TWO = new NaturalNumber("2"); public static
	 * final NaturalNumber THREE = new NaturalNumber("3"); public static final
	 * NaturalNumber FOUR = new NaturalNumber("4"); public static final
	 * NaturalNumber FIVE = new NaturalNumber("5"); public static final
	 * NaturalNumber SIX = new NaturalNumber("6"); public static final
	 * NaturalNumber SEVEN = new NaturalNumber("7"); public static final
	 * NaturalNumber HEIGHT = new NaturalNumber("8"); public static final
	 * NaturalNumber NINE = new NaturalNumber("9");
	 * 
	 * public static final NaturalNumber TEN = new NaturalNumber("10"); public
	 * static final NaturalNumber ELEVEN = new NaturalNumber("11"); public
	 * static final NaturalNumber TWELVE = new NaturalNumber("12");
	 * 
	 * public static final NaturalNumber TWENTY = new NaturalNumber("20");
	 * public static final NaturalNumber TWENTY_ONE = new NaturalNumber("21");
	 * 
	 * public static final NaturalNumber THIRTY = new NaturalNumber("30");
	 * 
	 * public static final NaturalNumber FORTY = new NaturalNumber("40");
	 * 
	 * public static final NaturalNumber FIFTY = new NaturalNumber("50");
	 * 
	 * public static final NaturalNumber NIGHTY = new NaturalNumber("90");
	 * public static final NaturalNumber NIGHTY_ONE = new NaturalNumber("91");
	 * public static final NaturalNumber NIGHTY_NINE = new NaturalNumber("99");
	 * 
	 * public static final NaturalNumber HUNDRED = new NaturalNumber("100");
	 * public static final NaturalNumber HUNDRED_ONE = new NaturalNumber("101");
	 * 
	 * public static final NaturalNumber TWO_HUNDREDS = new
	 * NaturalNumber("200");
	 */
	public abstract NaturalNumber clone();

	// Operations

	public abstract NaturalNumber plus(NaturalNumber rhs);

	public abstract NaturalNumber minus(NaturalNumber rhs);

	public abstract NaturalNumber times(NaturalNumber factor2);

	public abstract NaturalNumber dividedBy(NaturalNumber divisor);

	/*
	 * public abstract NaturalNumber times10Power(int exponent);
	 */
	/*
	 * public abstract boolean isEven();
	 * 
	 * public abstract boolean isOdd();
	 */
	public abstract boolean isMultipleOf(NaturalNumber divisor);

	public abstract NaturalNumber restOfDivisionBy(NaturalNumber divisor);

	// base 10 operations
	// public abstract NaturalNumber getBase10DigitsAddition();

	public abstract NaturalNumber getBase10DigitsMultiplication();

	// Comparisons

	public abstract boolean isSmallerOrEqualsTo(NaturalNumber rhs);

	public abstract boolean isStrictlySmallerThan(NaturalNumber rhs);

	public abstract boolean isGreaterOrEqualsTo(NaturalNumber rhs);

	public abstract boolean isStrictlyGreaterThan(NaturalNumber rhs);

	public abstract boolean hasOnlyOneDigitPrimeDivisors();

	public static List<Byte> asListOfDigits(String numberAsString) {
		List<Byte> digits = CollectionUtils.emptyList();

		for (int i = 0; i < numberAsString.length(); i++) {
			char digitAsChar = numberAsString.charAt(i);
			if (isDigitIgnored(digitAsChar)) {
				continue;
			}
			if (isDigitAllowed(digitAsChar)) {
				digits.add(digitAsByte(digitAsChar));
			} else {
				throw new IllegalStateException(
						"Cannot create number:" + numberAsString + " because digit:" + digitAsChar + " is not allowed");
			}
		}
		return digits;
	}

	private static byte digitAsByte(char digit) {
		for (int i = 0; i < 10; i++) {
			char iAsChar = (char) ('0' + i);
			if (digit == iAsChar) {
				byte ret = (byte) i;
				return ret;
			}
		}
		throw new IllegalStateException("Digit:" + digit + " is not valid");
	}

	private static boolean isDigitIgnored(char digit) {
		return digit == '_';
	}

	private static boolean isDigitAllowed(char digit) {
		for (int i = 0; i < 10; i++) {
			char iAsChar = (char) ('0' + i);
			if (digit == iAsChar) {
				return true;
			}
		}
		return false;
	}

}
