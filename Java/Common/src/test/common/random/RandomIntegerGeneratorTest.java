package test.common.random;

import static main.matcher.BasicMatchers.empty;
import static main.matcher.BasicMatchers.greaterThanOrEqualTo;
import static main.matcher.BasicMatchers.is;
import static main.matcher.BasicMatchers.lessThanOrEqualTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.common.random.RandomIntegerGenerator;

class RandomIntegerGeneratorTest {

	@Nested
	public class BadUsages {
		@Test
		public void whenMinIsGreaterThanMax() {
			int min = 2;
			int max = 1;
			assertThrows(IllegalArgumentException.class,
					() -> RandomIntegerGenerator.getRandomNumberUsingNextInt(min, max));
		}
	}

	@Nested
	public class CorrectUsages {

		int min = 1;
		int max = 100;

		@Test
		void test() {
			int randomNumberUsingNextInt = RandomIntegerGenerator.getRandomNumberUsingNextInt(min, max);
			assertThat(new ArrayList<>(), is(empty()));
			assertThat(randomNumberUsingNextInt, is(greaterThanOrEqualTo(min)));
			assertThat(randomNumberUsingNextInt, is(lessThanOrEqualTo(max)));

		}
	}

}
