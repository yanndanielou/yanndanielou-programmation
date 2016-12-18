package main.numbers;

import java.util.ArrayList;
import java.util.List;

public class InfiniteNaturalNumber {

	private List<Byte> digits = new ArrayList<Byte>();

	public InfiniteNaturalNumber() {

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

	public int numberOfDigit() {
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
