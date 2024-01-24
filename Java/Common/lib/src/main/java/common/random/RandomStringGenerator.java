package common.random;

import org.apache.commons.lang3.RandomStringUtils;

/***
 * Source: https://www.baeldung.com/java-random-string
 */
public class RandomStringGenerator {

	private RandomStringGenerator() {
	}

	public static String randomAlphabetic(int numberOfCharacters) {
		return RandomStringUtils.randomAlphabetic(numberOfCharacters);
	}

	public static String randomAlphanumeric(int numberOfCharacters) {
		return RandomStringUtils.randomAlphanumeric(numberOfCharacters);
	}
}
