package main.numbers;

import java.util.ArrayList;
import java.util.List;

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

	public static final InfiniteNaturalNumber TWENTY = new InfiniteNaturalNumber("20");
	public static final InfiniteNaturalNumber TWENTY_ONE = new InfiniteNaturalNumber("21");

	// ordered from the bigger digit to the unit
	private List<Byte> digits = new ArrayList<Byte>();

	private InfiniteNaturalNumber() {
		// this("0");
	}

	public InfiniteNaturalNumber(InfiniteNaturalNumber toClone) {
		ArrayList<Byte> arrayList = (ArrayList<Byte>) (toClone.digits);
		Object cloneAsObject = arrayList.clone();
		List<Byte> clone = (List<Byte>) cloneAsObject;
		digits = clone;
	}

	public InfiniteNaturalNumber(String asString) {
		for (int i = 0; i < asString.length(); i++) {
			char digitAsChar = asString.charAt(i);
			if (isDigitAllowed(digitAsChar)) {
				digits.add(digitAsByte(digitAsChar));
			} else {
				digits.clear();
				return;
			}
		}
	}

	//

	public InfiniteNaturalNumber asAtLeastAsManuDigitsThan(InfiniteNaturalNumber rhs) {
		if (getNumberOfDigits() >= rhs.getNumberOfDigits()) {
			return this;
		} else {
			InfiniteNaturalNumber clone = new InfiniteNaturalNumber(this);
			while (clone.getNumberOfDigits() < rhs.getNumberOfDigits()) {
				clone.digits.add(0, (byte) 0);
			}
			return clone;
		}
	}

	// Operations

	public InfiniteNaturalNumber plus(InfiniteNaturalNumber rhs) {
		InfiniteNaturalNumber cloneLhs = this.asAtLeastAsManuDigitsThan(rhs);
		InfiniteNaturalNumber cloneRhs = rhs.asAtLeastAsManuDigitsThan(this);

		InfiniteNaturalNumber ret = new InfiniteNaturalNumber();

		boolean carry = false;

		// lhs and rhs have same digit numbers
		for (int i = 0; i < cloneLhs.getNumberOfDigits(); i++) {
			byte sumDigits = (byte) (cloneLhs.digits.get(i) + cloneRhs.digits.get(i));
			if (carry) {
				sumDigits++;
			}

			if (sumDigits >= 10) {
				sumDigits -= 10;
				carry = true;
			} else
				carry = false;

			ret.digits.add(sumDigits);
		}

		if (carry)
			ret.digits.add((byte) 1);

		return ret;
	}

	public InfiniteNaturalNumber minus(InfiniteNaturalNumber rhs) {

		/*
		 * if (equals(rhs)) { return ZERO; }
		 */
		if (this.isStrictlySmallerThan(rhs)) {
			throw new IllegalStateException(getClass().getName() + " cannot be negative");
		}

		InfiniteNaturalNumber cloneLhs = this.asAtLeastAsManuDigitsThan(rhs);
		InfiniteNaturalNumber cloneRhs = rhs.asAtLeastAsManuDigitsThan(this);

		InfiniteNaturalNumber ret = new InfiniteNaturalNumber();
		boolean carry = false;

		for (int i = cloneLhs.getNumberOfDigits() - 1; i >= 0; i--) {

			byte lhsDigit = cloneLhs.digits.get(i);
			byte rhsDigit = cloneRhs.digits.get(i);

			byte difference = (byte) (lhsDigit - rhsDigit);

			if (carry)
				difference--;

			if (rhsDigit > lhsDigit) {
				carry = true;
				difference += 10;
			} else {
				carry = false;
			}

			ret.digits.add(0, difference);
		}

		if (carry)
			ret.digits.add((byte) 1);

		ret.removeUselessZeroDigits();
		
		return ret;
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
					return true;
				}
			}

		}

		// error
		return false;
	}

	private void removeUselessZeroDigits() {

		while (digits.size() > 1 && digits.get(0) == 0) {
			digits.remove(0);
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
		return -1;
	}

	private boolean isDigitAllowed(char digit) {
		for (int i = 0; i < 10; i++) {
			char iAsChar = (char) ('0' + i);
			if (digit == iAsChar)
				return true;
		}
		return false;
	}

	public int getNumberOfDigits() {
		return digits.size();
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

}
