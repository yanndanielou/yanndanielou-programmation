package common;

import java.util.Random;

public class RandomIntegerGenerator {

	private static Random random = new Random();

	private RandomIntegerGenerator() {
	}

	public static int getRandomNumberUsingNextInt(int min, int max) {
		int random_int = random.nextInt(max - min) + min;
		return random_int;
	}
}
