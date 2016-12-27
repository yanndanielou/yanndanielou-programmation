package main.numbers;

import java.util.List;

import main.util.CollectionUtils;

public class JIntegerNaturalNumber extends NaturalNumber {

	private long value;

	public JIntegerNaturalNumber(JIntegerNaturalNumber toClone) {
		this.value = toClone.value;
	}

	public JIntegerNaturalNumber(List<Byte> digits) {
		this.value = 0;
		for (int i = 0; i < digits.size(); i++) {
			byte digit = digits.get(i);
			this.value = this.value * 10 + digit;
		}
	}

	public JIntegerNaturalNumber(long value) {
		this.value = value;
	}

	@Override
	public NaturalNumber clone() {
		return new JIntegerNaturalNumber(this);
	}

	@Override
	public NaturalNumber plus(NaturalNumber rhs) {
		if (rhs instanceof JIntegerNaturalNumber) {
			JIntegerNaturalNumber rhsAsJIntegerNaturalNumber = (JIntegerNaturalNumber) rhs;
			return new JIntegerNaturalNumber(value + rhsAsJIntegerNaturalNumber.value);
		} else {
			throw new IllegalStateException("not supported yet");
		}
	}

	@Override
	public NaturalNumber minus(NaturalNumber rhs) {
		if (isStrictlySmallerThan(rhs)) {
			throw new IllegalStateException(getClass().getName() + " cannot be negative");
		}

		if (rhs instanceof JIntegerNaturalNumber) {
			JIntegerNaturalNumber rhsAsJIntegerNaturalNumber = (JIntegerNaturalNumber) rhs;
			return new JIntegerNaturalNumber(value - rhsAsJIntegerNaturalNumber.value);
		} else {
			throw new IllegalStateException("not supported yet");
		}
	}

	@Override
	public NaturalNumber times(NaturalNumber factor2) {
		if (factor2 instanceof JIntegerNaturalNumber) {
			JIntegerNaturalNumber factor2AsJIntegerNaturalNumber = (JIntegerNaturalNumber) factor2;
			return new JIntegerNaturalNumber(value * factor2AsJIntegerNaturalNumber.value);
		} else {
			throw new IllegalStateException("not supported yet");
		}
	}

	@Override
	public NaturalNumber dividedBy(NaturalNumber divisor) {
		if (divisor instanceof JIntegerNaturalNumber) {
			JIntegerNaturalNumber divisorAsJIntegerNaturalNumber = (JIntegerNaturalNumber) divisor;
			return new JIntegerNaturalNumber(value / divisorAsJIntegerNaturalNumber.value);
		} else {
			throw new IllegalStateException("not supported yet");
		}
	}

	@Override
	public boolean isMultipleOf(NaturalNumber divisor) {
		if (this.isStrictlySmallerThan(divisor)) {
			return false;
		}
		if (divisor instanceof JIntegerNaturalNumber) {
			JIntegerNaturalNumber divisorAsJIntegerNaturalNumber = (JIntegerNaturalNumber) divisor;
			return value % divisorAsJIntegerNaturalNumber.value == 0;
		} else {
			throw new IllegalStateException("not supported yet");
		}
	}

	@Override
	public NaturalNumber restOfDivisionBy(NaturalNumber divisor) {
		if (divisor instanceof JIntegerNaturalNumber) {
			JIntegerNaturalNumber divisorAsJIntegerNaturalNumber = (JIntegerNaturalNumber) divisor;
			return new JIntegerNaturalNumber(value % divisorAsJIntegerNaturalNumber.value);
		} else {
			throw new IllegalStateException("not supported yet");
		}
	}

	@Override
	public NaturalNumber getBase10DigitsMultiplication() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSmallerOrEqualsTo(NaturalNumber rhs) {
		if (rhs instanceof JIntegerNaturalNumber) {
			JIntegerNaturalNumber rhsAsJIntegerNaturalNumber = (JIntegerNaturalNumber) rhs;
			return value <= rhsAsJIntegerNaturalNumber.value;
		} else {
			throw new IllegalStateException("not supported yet");
		}
	}

	@Override
	public boolean isStrictlySmallerThan(NaturalNumber rhs) {
		if (rhs instanceof JIntegerNaturalNumber) {
			JIntegerNaturalNumber rhsAsJIntegerNaturalNumber = (JIntegerNaturalNumber) rhs;
			return value < rhsAsJIntegerNaturalNumber.value;
		} else {
			throw new IllegalStateException("not supported yet");
		}
	}

	@Override
	public boolean isGreaterOrEqualsTo(NaturalNumber rhs) {
		if (rhs instanceof JIntegerNaturalNumber) {
			JIntegerNaturalNumber rhsAsJIntegerNaturalNumber = (JIntegerNaturalNumber) rhs;
			return value >= rhsAsJIntegerNaturalNumber.value;
		} else {
			throw new IllegalStateException("not supported yet");
		}
	}

	@Override
	public boolean isStrictlyGreaterThan(NaturalNumber rhs) {
		if (rhs instanceof JIntegerNaturalNumber) {
			JIntegerNaturalNumber rhsAsJIntegerNaturalNumber = (JIntegerNaturalNumber) rhs;
			return value > rhsAsJIntegerNaturalNumber.value;
		} else {
			throw new IllegalStateException("not supported yet");
		}
	}

	@Override
	public boolean hasOnlyOneDigitPrimeDivisors() {

		JIntegerNaturalNumber remainingNumber = new JIntegerNaturalNumber(this);

		List<JIntegerNaturalNumber> oneDigitPrimeNumbers = CollectionUtils.asList(new JIntegerNaturalNumber(2),
				new JIntegerNaturalNumber(3), new JIntegerNaturalNumber(5), new JIntegerNaturalNumber(7));

		for (JIntegerNaturalNumber oneDigitPrimeNumber : oneDigitPrimeNumbers) {

			while (remainingNumber.isMultipleOf(oneDigitPrimeNumber)) {
				remainingNumber = (JIntegerNaturalNumber) remainingNumber.dividedBy(oneDigitPrimeNumber);
			}
			if (remainingNumber.value == 1) {
				return true;
			}
		}
		return false;
	}

	public List<JIntegerNaturalNumber> getAllPrimeDivisors() {

		List<JIntegerNaturalNumber> primeDivisors = CollectionUtils.emptyList();

		JIntegerNaturalNumber remainingNumber = new JIntegerNaturalNumber(this);

		List<JIntegerNaturalNumber> oneDigitPrimeNumbers = CollectionUtils.asList(new JIntegerNaturalNumber(2),
				new JIntegerNaturalNumber(3), new JIntegerNaturalNumber(5), new JIntegerNaturalNumber(7));

		for (JIntegerNaturalNumber oneDigitPrimeNumber : oneDigitPrimeNumbers) {

			while (remainingNumber.isMultipleOf(oneDigitPrimeNumber)) {
				remainingNumber = (JIntegerNaturalNumber) remainingNumber.dividedBy(oneDigitPrimeNumber);
				primeDivisors.add(oneDigitPrimeNumber);
			}
			if (remainingNumber.value == 1) {
				return primeDivisors;
			}
		}
		throw new IllegalStateException("only prime divisors up to "
				+ oneDigitPrimeNumbers.get(oneDigitPrimeNumbers.size() - 1) + " are supported");
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public long getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (value ^ (value >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		JIntegerNaturalNumber other = (JIntegerNaturalNumber) obj;
		if (value != other.value)
			return false;
		return true;
	}

}
