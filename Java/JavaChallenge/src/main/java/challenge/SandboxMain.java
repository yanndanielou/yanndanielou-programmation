package challenge;

import java.util.ArrayList;
import java.util.Arrays;

public class SandboxMain {

	public SandboxMain() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/*
	 * squarePatch(5) ➞ [ [5, 5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5,
	 * 5, 5, 5], [5, 5, 5, 5, 5] ]
	 * 
	 * squarePatch(1) ➞ [ [1] ]
	 * 
	 * squarePatch(0) ➞ []
	 */
	public static int[][] squarePatch(int n) {

		int[][] squareCreated = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				squareCreated[i][j] = n;
			}
		}

		return squareCreated;
	}

	/*
	 * Create a function that counts the integer's number of digits.
	 */
	public static int count(int n) {
		for (int i = 1;; i++) {
			if (Math.pow(10, i) > Math.abs(n)) {
				return i;
			}
		}
	}

	public static boolean isEven(int number) {
		return number % 2 == 0;
	}

	public static int warOfNumbers(int[] numbers) {
		System.out.println("numbers:" + Arrays.toString(numbers));
		int sumOfEvenNumbers = 0;
		int sumOfOddNumbers = 0;
		for (int number : numbers) {
			if (isEven(number)) {
				sumOfEvenNumbers += number;
			} else {
				sumOfOddNumbers += number;
			}
		}

		System.out.println("sumOfEvenNumbers:" + sumOfEvenNumbers);
		System.out.println("sumOfOddNumbers:" + sumOfOddNumbers);

		int warResult = Math.abs(sumOfEvenNumbers - sumOfOddNumbers);
		System.out.println("warResult:" + warResult);

		return warResult;
	}

	public static int[] arrayOfMultiples(int num, int length) {
		ArrayList<Integer> resultAsList = new ArrayList<>();

		for (int i = 1; i <= length; i++) {
			resultAsList.add(num * i);
		}

		return resultAsList.stream().mapToInt(Integer::intValue).toArray();
	}

	public static int letterCounter(char[][] arr, char c) {

		int counter = 0;

		for (char[] arrayI : arr) {
			for (char charIJ : arrayI) {
				if (charIJ == c) {
					counter++;
				}
			}
		}
		return counter;

	}

	public static Integer basicCalculator(int a, String o, int b) {
		if (o == "+")
			return a + b;
		if (o == "-")
			return a - b;
		if (o == "/" && b != 0)
			return a / b;
		if (o == "/" && b == 0)
			return null;
		if (o == "*")
			return a * b;
		return null;
	}

	public class NumericString {
	
	}

}
