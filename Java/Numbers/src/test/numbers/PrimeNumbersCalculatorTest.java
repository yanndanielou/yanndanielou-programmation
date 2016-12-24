package test.numbers;

import static main.matcher.BasicMatchers.*;
import static main.numbers.InfiniteNaturalNumber.*;
import static main.numbers.InfiniteNaturalNumber.THREE;
import static main.numbers.InfiniteNaturalNumber.TWO;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import main.junit.Scenario;
import main.numbers.InfiniteNaturalNumber;
import main.numbers.PrimeNumbersCalculator;
import main.util.CollectionUtils;

import static main.matcher.BasicMatchers.is;

@RunWith(HierarchicalContextRunner.class)
public class PrimeNumbersCalculatorTest {

	public class FindPrimeNumbersUpTo extends Scenario {

		private InfiniteNaturalNumber maxNumberToTest;
		private List<InfiniteNaturalNumber> expectedPrimeNumbers;

		@After
		public void after() {
			List<InfiniteNaturalNumber> primeNumbersFound = PrimeNumbersCalculator
					.findPrimeNumbersUpTo(maxNumberToTest);

			if (expectedPrimeNumbers.isEmpty()) {
				assertThat(primeNumbersFound, is(empty()));
			} else {
				assertThat(primeNumbersFound, containsExactlyAll(expectedPrimeNumbers));
			}
		}

		@Test
		public void ZERO_returnsEmpty() {
			maxNumberToTest = ZERO;
			expectedPrimeNumbers = CollectionUtils.emptyList();
		}

		@Test
		public void One_returnsEmpty() {
			maxNumberToTest = ONE;
			expectedPrimeNumbers = CollectionUtils.emptyList();
		}

		@Test
		public void Two_returns_2() {
			maxNumberToTest = TWO;
			expectedPrimeNumbers = CollectionUtils.asList(TWO);
		}

		@Test
		public void Three_returns_2_3() {
			maxNumberToTest = THREE;
			expectedPrimeNumbers = CollectionUtils.asList(TWO, THREE);
		}

		@Test
		public void Four_returns_2_3() {
			maxNumberToTest = FOUR;
			expectedPrimeNumbers = CollectionUtils.asList(TWO, THREE);
		}

		@Test
		public void Eleven_returns_2_3_5_7_11() {
			maxNumberToTest = ELEVEN;
			expectedPrimeNumbers = CollectionUtils.asList(TWO, THREE, FIVE, SEVEN, ELEVEN);
		}

		@Test
		public void Twelve_returns_2_3_5_7_11() {
			maxNumberToTest = TWELVE;
			expectedPrimeNumbers = CollectionUtils.asList(TWO, THREE, FIVE, SEVEN, ELEVEN);
		}
	}

}
