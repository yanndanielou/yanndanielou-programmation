package main.numbers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InfiniteNaturalNumber implements Cloneable {

	public static final InfiniteNaturalNumber ZERO = new InfiniteNaturalNumber("0");

	public static final InfiniteNaturalNumber ONE = new InfiniteNaturalNumber("1");
	public static final InfiniteNaturalNumber TWO = new InfiniteNaturalNumber("2");
	public static final InfiniteNaturalNumber THREE = new InfiniteNaturalNumber("3");
	public static final InfiniteNaturalNumber FOUR = new InfiniteNaturalNumber("4");
	public static final InfiniteNaturalNumber FIVE = new InfiniteNaturalNumber("5");
	public static final InfiniteNaturalNumber SIX = new InfiniteNaturalNumber("6");
	public static final InfiniteNaturalNumber SEVEN = new InfiniteNaturalNumber("7");
	public static final InfiniteNaturalNumber HEIGHT = new InfiniteNaturalNumber("8");
	public static final InfiniteNaturalNumber NINE = new InfiniteNaturalNumber("9");

	public static final InfiniteNaturalNumber TEN = new InfiniteNaturalNumber("10");
	public static final InfiniteNaturalNumber ELEVEN = new InfiniteNaturalNumber("11");
	public static final InfiniteNaturalNumber TWELVE = new InfiniteNaturalNumber("12");

	public static final InfiniteNaturalNumber TWENTY = new InfiniteNaturalNumber("20");
	public static final InfiniteNaturalNumber TWENTY_ONE = new InfiniteNaturalNumber("21");

	public static final InfiniteNaturalNumber THIRTY = new InfiniteNaturalNumber("30");

	public static final InfiniteNaturalNumber FORTY = new InfiniteNaturalNumber("40");

	public static final InfiniteNaturalNumber FIFTY = new InfiniteNaturalNumber("50");

	public static final InfiniteNaturalNumber NIGHTY = new InfiniteNaturalNumber("90");
	public static final InfiniteNaturalNumber NIGHTY_ONE = new InfiniteNaturalNumber("91");
	public static final InfiniteNaturalNumber NIGHTY_NINE = new InfiniteNaturalNumber("99");

	public static final InfiniteNaturalNumber HUNDRED = new InfiniteNaturalNumber("100");
	public static final InfiniteNaturalNumber HUNDRED_ONE = new InfiniteNaturalNumber("101");

	public static final InfiniteNaturalNumber TWO_HUNDREDS = new InfiniteNaturalNumber("200");

	// ordered from the bigger digit to the unit
	private List<Byte> digits = new ArrayList<Byte>();

	// List of unique digits in base 10 (can contain up to 10 items)
	private Set<Byte> uniquedigits = new HashSet<Byte>();

	private InfiniteNaturalNumber() {
	}

	public InfiniteNaturalNumber(InfiniteNaturalNumber toClone) {
		ArrayList<Byte> arrayList = (ArrayList<Byte>) (toClone.digits);
		Object cloneAsObject = arrayList.clone();
		List<Byte> clone = (List<Byte>) cloneAsObject;
		digits = clone;
	}

	public InfiniteNaturalNumber(byte numberBetween0And9) {
		throwExceptionIfNotValidDigit(numberBetween0And9);
		addDigitAtUnit(numberBetween0And9);
	}

	public InfiniteNaturalNumber(String asString) {
		for (int i = 0; i < asString.length(); i++) {
			char digitAsChar = asString.charAt(i);
			if (isDigitIgnored(digitAsChar)) {
				continue;
			}
			if (isDigitAllowed(digitAsChar)) {
				addDigitAtUnit(digitAsByte(digitAsChar));
			} else {
				throw new IllegalStateException(
						"Cannot create number:" + asString + " because digit:" + digitAsChar + " is not allowed");
			}
		}
	}

	//

	public InfiniteNaturalNumber asAtLeastAsManyDigitsThan(InfiniteNaturalNumber rhs) {
		if (getNumberOfDigits() >= rhs.getNumberOfDigits()) {
			return this;
		} else {
			InfiniteNaturalNumber clone = new InfiniteNaturalNumber(this);
			while (clone.getNumberOfDigits() < rhs.getNumberOfDigits()) {
				clone.addDigitAtHighestPosition((byte) 0);
			}
			return clone;
		}
	}

	// Operations

	public InfiniteNaturalNumber plus(InfiniteNaturalNumber rhs) {
		InfiniteNaturalNumber cloneLhs = this.asAtLeastAsManyDigitsThan(rhs);
		InfiniteNaturalNumber cloneRhs = rhs.asAtLeastAsManyDigitsThan(this);

		InfiniteNaturalNumber ret = new InfiniteNaturalNumber();

		boolean carry = false;

		// lhs and rhs have same digit numbers
		for (int i = cloneLhs.getNumberOfDigits() - 1; i >= 0; i--) {
			byte sumDigits = (byte) (cloneLhs.digits.get(i) + cloneRhs.digits.get(i));
			if (carry) {
				sumDigits++;
			}

			if (sumDigits >= 10) {
				sumDigits -= 10;
				carry = true;
			} else
				carry = false;

			ret.addDigitAtHighestPosition(sumDigits);
		}

		if (carry)
			ret.addDigitAtHighestPosition((byte) 1);

		return ret;
	}

	public InfiniteNaturalNumber minus(InfiniteNaturalNumber rhs) {

		/*
		 * if (equals(rhs)) { return ZERO; }
		 */
		if (this.isStrictlySmallerThan(rhs)) {
			throw new IllegalStateException(getClass().getName() + " cannot be negative");
		}

		InfiniteNaturalNumber cloneLhs = this.asAtLeastAsManyDigitsThan(rhs);
		InfiniteNaturalNumber cloneRhs = rhs.asAtLeastAsManyDigitsThan(this);

		InfiniteNaturalNumber ret = new InfiniteNaturalNumber();
		boolean carry = false;

		for (int i = cloneLhs.getNumberOfDigits() - 1; i >= 0; i--) {

			byte lhsDigit = cloneLhs.digits.get(i);
			byte rhsDigit = cloneRhs.digits.get(i);

			byte difference = (byte) (lhsDigit - rhsDigit);

			if (carry)
				difference--;

			if (difference < 0) {
				carry = true;
				difference += 10;
			} else {
				carry = false;
			}

			ret.addDigitAtHighestPosition(difference);
		}

		ret.removeUselessZeroDigits();

		return ret;
	}

	public InfiniteNaturalNumber times(byte factor2) {
		return times(new InfiniteNaturalNumber(factor2));
	}

	public InfiniteNaturalNumber times(InfiniteNaturalNumber factor2) {
		InfiniteNaturalNumber res = ZERO;

		InfiniteNaturalNumber factorWithLessDigits = factor2.getNumberOfDigits() < this.getNumberOfDigits() ? factor2
				: this;
		InfiniteNaturalNumber factorWithMostDigits = factor2 == factorWithLessDigits ? this : factor2;

		for (InfiniteNaturalNumber i = ZERO; i.isStrictlySmallerThan(factorWithLessDigits); i = i.plus(ONE)) {
			res = res.plus(factorWithMostDigits);
		}

		return res;
	}

	public InfiniteNaturalNumber times10Power(int exponent) {
		InfiniteNaturalNumber ret = new InfiniteNaturalNumber(this);

		for (int i = 0; i < exponent; i++)
			ret.addDigitAtUnit((byte) 0);

		return ret;
	}

	public InfiniteNaturalNumber dividedBy(InfiniteNaturalNumber divisor) {

		// handle division by zero
		if (divisor.equals(ZERO)) {
			throw new IllegalStateException("Division by zero");
		}

		InfiniteNaturalNumber quotient = ZERO;

		InfiniteNaturalNumber dividend = this;

		while (dividend.isGreaterOrEqualsTo(divisor)) {
			dividend = dividend.minus(divisor);
			quotient = quotient.plus(ONE);
		}

		return quotient;
	}

	public boolean isMultipleOf(InfiniteNaturalNumber divisor) {
		return restOfDivisionBy(divisor).equals(ZERO);
	}

	public InfiniteNaturalNumber restOfDivisionBy(InfiniteNaturalNumber divisor) {
		InfiniteNaturalNumber quotient = this.dividedBy(divisor);
		InfiniteNaturalNumber rest = this.minus(quotient.times(divisor));
		return rest;
	}

	// base 10 operations

	public InfiniteNaturalNumber getBase10DigitsMultiplication() {

		InfiniteNaturalNumber numberToTest = this;

		InfiniteNaturalNumber base10DigitsMultiplication = ONE;

		if (numberToTest.containsDigit((byte) 0)) {
			return ZERO;
		}

		for (int i = 0; i < numberToTest.getNumberOfDigits(); i++) {
			byte digit = numberToTest.digits.get(i);
			if (digit == 1) {
				continue;
			}
			base10DigitsMultiplication = base10DigitsMultiplication.times(digit);
		}

		return base10DigitsMultiplication;
	}

	public InfiniteNaturalNumber getBase10DigitsInversion() {
		InfiniteNaturalNumber base10DigitsInverted = new InfiniteNaturalNumber(this);
		Collections.reverse(base10DigitsInverted.digits);
		base10DigitsInverted.removeUselessZeroDigits();
		return base10DigitsInverted;
	}

	public boolean isPalindrome() {
		for (int i = 0; i < getNumberOfDigits() / 2; i++) {
			if (digits.get(i) != digits.get(getNumberOfDigits() - 1 - i)) {
				return false;
			}
		}
		return true;
	}

	// Comparisons

	public boolean isSmallerOrEqualsTo(InfiniteNaturalNumber rhs) {
		return equals(rhs) || isStrictlySmallerThan(rhs);
	}

	public boolean isStrictlySmallerThan(InfiniteNaturalNumber rhs) {
		if (equals(rhs))
			return false;

		if (getNumberOfDigits() < rhs.getNumberOfDigits())
			return true;

		if (getNumberOfDigits() > rhs.getNumberOfDigits())
			return false;

		return !isStrictlyGreaterThan(rhs);
	}

	public boolean isGreaterOrEqualsTo(InfiniteNaturalNumber rhs) {
		return equals(rhs) || isStrictlyGreaterThan(rhs);
	}

	public boolean isStrictlyGreaterThan(InfiniteNaturalNumber rhs) {
		if (equals(rhs))
			return false;

		if (getNumberOfDigits() > rhs.getNumberOfDigits())
			return true;

		if (getNumberOfDigits() < rhs.getNumberOfDigits())
			return false;

		if (getNumberOfDigits() == rhs.getNumberOfDigits()) {
			for (int i = 0; i < getNumberOfDigits(); i++) {
				Byte digit_lhs = digits.get(i);
				Byte digit_rhs = rhs.digits.get(i);
				if (digit_lhs > digit_rhs) {
					return true;
				}
				if (digit_lhs < digit_rhs) {
					return false;
				}
			}

		}

		// error
		return false;
	}

	/*
	 * 
	 * 
	 */
	/*
	 * private List<InfiniteNaturalNumber> getAllPrimeDivisors() {
	 * 
	 * }
	 */
	private void removeUselessZeroDigits() {
		while (digits.size() > 1 && digits.get(0) == 0) {
			removeDigitAtHighestPosition();
		}
	}

	private byte digitAsByte(char digit) {
		for (int i = 0; i < 10; i++) {
			char iAsChar = (char) ('0' + i);
			if (digit == iAsChar) {
				byte ret = (byte) i;
				return ret;
			}
		}
		throw new IllegalStateException("Digit:" + digit + " is not valid");
	}

	private boolean isDigitIgnored(char digit) {
		return digit == '_';
	}

	private boolean isDigitAllowed(char digit) {
		for (int i = 0; i < 10; i++) {
			char iAsChar = (char) ('0' + i);
			if (digit == iAsChar) {
				return true;
			}
		}
		return false;
	}

	public int getNumberOfDigits() {
		return digits.size();
	}

	public boolean containsDigit(byte digit) {
		throwExceptionIfNotValidDigit(digit);
		return uniquedigits.contains(digit);
	}

	public boolean containsDigit(int digit) {
		return containsDigit((byte) digit);
	}

	public boolean areDigitsOrderedInGrowingOrder() {
		for (int i = 0; i < getNumberOfDigits() - 1; i++) {
			if (digits.get(i) > digits.get(i + 1)) {
				return false;
			}
		}
		return true;
	}

	public boolean containsOneOfDigits(int... digits) {
		for (int digit : digits) {
			if (containsDigit(digit)) {
				return true;
			}
		}
		return false;
	}

	public void addDigitAtUnit(byte digit) {
		digits.add(digit);
		uniquedigits.add(digit);
	}

	public void removeDigitAtHighestPosition() {
		Byte digitToRemove = digits.get(0);
		digits.remove(0);
		if (!digits.contains(digitToRemove)) {
			uniquedigits.remove(digitToRemove);
		}
	}

	public void addDigitAtHighestPosition(byte digit) {
		digits.add(0, digit);
		uniquedigits.add(digit);
	}

	private void throwExceptionIfNotValidDigit(byte digit) {
		if (digit < 0 || digit > 9) {
			throw new IllegalStateException("Digit" + digit + " must be between 0 and 9");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		} else if (obj instanceof InfiniteNaturalNumber) {
			InfiniteNaturalNumber right = (InfiniteNaturalNumber) obj;
			return digits.equals(right.digits);
		}
		return false;
	}

	@Override
	public String toString() {
		String toString = "";
		for (int i = 0; i < getNumberOfDigits(); i++) {
			Byte byte1 = digits.get(i);
			char c = (char) ('0' + byte1);
			toString += c;
		}
		return toString;
	}

}
