package common.random;

import static matcher.BasicMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RandomStringGeneratorTest {

	@Nested
	public class BadUsages {
		@Test
		public void whenCountIsNegative() {
			assertThrows(IllegalArgumentException.class, () -> RandomStringGenerator.randomAlphabetic(-1));
		}
	}

	@Nested
	public class CorrectUsages {

		@ParameterizedTest
		@ValueSource(ints = { 1, 3, 5, 15, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000 })
		void correctStringSizeIsReturned(int numberOfCharacters) {
			assertThat(RandomStringGenerator.randomAlphabetic(numberOfCharacters).length(), is(numberOfCharacters));
			assertThat(RandomStringGenerator.randomAlphanumeric(numberOfCharacters).length(), is(numberOfCharacters));
		}
	}

}
