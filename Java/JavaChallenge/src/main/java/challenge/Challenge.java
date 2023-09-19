package challenge;

import java.util.List;

import main.common.numbers.utils.NumberUtils;

public class Challenge {

	public static int solutions(int a, int b, int c) {
		int discriminant = b * b - 4 * a * c;
		if (discriminant > 0)
			return 2;
		if (discriminant == 0)
			return 1;
		return 0;
	}

	
	public static int kaprekar(int inputNum) {
		if (inputNum > Math.pow(10, 4)) {
			return -1;
		}

		int iteration = 0;
		for (; inputNum != 6174 && inputNum != 0; iteration++) {

			while (NumberUtils.getNumberOfDigits(inputNum) < 4) {
				inputNum *= 10;
			}

			List<Integer> digitsFromIntegerInAscendingOrderAsIntList = NumberUtils
					.getDigitsFromIntegerInAscendingOrderAsIntList(inputNum);
			List<Integer> digitsFromIntegerInDescendingOrderAsIntList = NumberUtils
					.getDigitsFromIntegerInDescendingOrderAsIntList(inputNum);

			int ascendingOrderInt = NumberUtils
					.computePositiveIntFromListOfDigits(digitsFromIntegerInAscendingOrderAsIntList);
			int descendingOrderInt = NumberUtils
					.computePositiveIntFromListOfDigits(digitsFromIntegerInDescendingOrderAsIntList);

			System.out.println("kaprekar iteration " + iteration + " with current number:" + inputNum
					+ ". ascendingOrderInt:" + ascendingOrderInt + " descendingOrder:" + descendingOrderInt);

			inputNum = Math.abs(ascendingOrderInt - descendingOrderInt);
			System.out.println("kaprekar iteration " + iteration + " result:" + inputNum);

		}
		return iteration;
	}
}
