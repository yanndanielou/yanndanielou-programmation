package common.random;

import java.util.Random;

public class RandomIntegerGenerator {

	private static Random random = new Random();

	private RandomIntegerGenerator() {
	}

	public static int getRandomNumberUsingNextInt(int min, int max) {
		int randomInt = random.nextInt(max - min) + min;
		return randomInt;
	}
}
