package test;

import static main.matcher.BasicMatchers.containsExactly;
import static main.matcher.BasicMatchers.empty;
import static main.matcher.BasicMatchers.is;
import static main.matcher.BasicMatchers.notNullValue;
import static main.matcher.BasicMatchers.nullValue;
import static main.numbers.InfiniteNaturalNumber.ELEVEN;
import static main.numbers.InfiniteNaturalNumber.FOUR;
import static main.numbers.InfiniteNaturalNumber.ONE;
import static main.numbers.InfiniteNaturalNumber.TEN;
import static main.numbers.InfiniteNaturalNumber.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import main.MultiplicationPersistenceCalculator;
import main.junit.PerfTestScenario;
import main.numbers.InfiniteNaturalNumber;
import main.util.FormatterUtils;

@RunWith(HierarchicalContextRunner.class)
public class MultiplicationPersistenceCalculatorTest {

	private MultiplicationPersistenceCalculator multiplicationPersistenceCalculator = new MultiplicationPersistenceCalculator();

	public class FindNumberHavingMultiplicative {

		private InfiniteNaturalNumber multiplicative;
		private InfiniteNaturalNumber numberHavingMultiplicativeFound;

		protected void compute() {
			numberHavingMultiplicativeFound = multiplicationPersistenceCalculator
					.findNumberHavingMultiplicative(multiplicative);
		}

		public class AtLeastOneNumberHasThisMultiplicative {

			private int expectedPersistenceOfNumberFound;

			protected void check() {
				assertThat(numberHavingMultiplicativeFound, is(notNullValue()));
				int multiplicationPersistence = multiplicationPersistenceCalculator
						.getMultiplicationPersistence(numberHavingMultiplicativeFound);
				assertThat(multiplicationPersistence, is(expectedPersistenceOfNumberFound));
			}

			@After
			public void after() {
				compute();
				check();
			}

			@Test
			public void of_25_has_persistence_3() {
				multiplicative = new InfiniteNaturalNumber("25");
				expectedPersistenceOfNumberFound = 3;
			}

		}

		public class NoNumberHasThisMultiplicative {

			protected void check() {
				assertThat(numberHavingMultiplicativeFound, is(nullValue()));
			}

			public class TestResult {

				@After
				public void after() {
					compute();
					check();
				}

				@Test
				public void _37() {
					multiplicative = new InfiniteNaturalNumber("37");
				}

				@Test
				public void _39() {
					multiplicative = new InfiniteNaturalNumber("39");
				}
			}

			public class PerfTest extends PerfTestScenario {

				@After
				public void after() {
					compute();
					check();
					System.out.println("NoNumberHasThisMultiplicative for " + multiplicative + " was calculated in "
							+ FormatterUtils.GetDurationAsString(getTestDuration()));

				}

				@Ignore
				@Test
				public void _277777788888899() {
					multiplicative = new InfiniteNaturalNumber("277777788888899");
				}

			}
		}
	}

	public class MultiplicativeSuite {

		@Test
		public void MultiplicativeSuite_of_0_is_empty() {
			List<InfiniteNaturalNumber> multiplicativeSuite = multiplicationPersistenceCalculator
					.getMultiplicativeSuite(ZERO);
			assertThat(multiplicativeSuite, is(empty()));
		}

		@Test
		public void MultiplicativeSuite_of_4_is_empty() {
			List<InfiniteNaturalNumber> multiplicativeSuite = multiplicationPersistenceCalculator
					.getMultiplicativeSuite(FOUR);
			assertThat(multiplicativeSuite, is(empty()));
		}

		@Test
		public void MultiplicativeSuite_of_10_is_0() {
			List<InfiniteNaturalNumber> multiplicativeSuite = multiplicationPersistenceCalculator
					.getMultiplicativeSuite(TEN);
			assertThat(multiplicativeSuite, containsExactly(ZERO));
		}

		@Test
		public void MultiplicativeSuite_of_11_is_1() {
			List<InfiniteNaturalNumber> multiplicativeSuite = multiplicationPersistenceCalculator
					.getMultiplicativeSuite(ELEVEN);
			assertThat(multiplicativeSuite, containsExactly(ONE));
		}

		@Test
		public void MultiplicativeSuite_of_25_is_10_0() {
			List<InfiniteNaturalNumber> multiplicativeSuite = multiplicationPersistenceCalculator
					.getMultiplicativeSuite(new InfiniteNaturalNumber("25"));
			assertThat(multiplicativeSuite, containsExactly(TEN, ZERO));
		}

		@Test
		public void MultiplicativeSuite_of_39_is_27_14_4() {
			List<InfiniteNaturalNumber> multiplicativeSuite = multiplicationPersistenceCalculator
					.getMultiplicativeSuite(new InfiniteNaturalNumber("39"));
			assertThat(multiplicativeSuite,
					containsExactly(new InfiniteNaturalNumber("27"), new InfiniteNaturalNumber("14"), FOUR));
		}
	}

	public class MultiplicativePersistence extends PerfTestScenario {

		@After
		public void after() {
			System.out.println("MultiplicativePersistence was calculated in "
					+ FormatterUtils.GetDurationAsString(getTestDuration()));
		}

		@Test
		public void MultiplicativePersistence_of_0_is_0() {
			int multiplicationPersistence = multiplicationPersistenceCalculator.getMultiplicationPersistence(ZERO);
			assertThat(multiplicationPersistence, is(0));
		}

		@Test
		public void MultiplicativePersistence_of_4_is_0() {
			int multiplicationPersistence = multiplicationPersistenceCalculator.getMultiplicationPersistence(FOUR);
			assertThat(multiplicationPersistence, is(0));
		}

		@Test
		public void MultiplicativePersistence_of_10_is_1() {
			int multiplicationPersistence = multiplicationPersistenceCalculator.getMultiplicationPersistence(TEN);
			assertThat(multiplicationPersistence, is(1));
		}

		@Test
		public void MultiplicativePersistence_of_11_is_1() {
			int multiplicationPersistence = multiplicationPersistenceCalculator.getMultiplicationPersistence(ELEVEN);
			assertThat(multiplicationPersistence, is(1));
		}

		@Test
		public void MultiplicativePersistence_of_25_is_2() {
			int multiplicationPersistence = multiplicationPersistenceCalculator
					.getMultiplicationPersistence(new InfiniteNaturalNumber("25"));
			assertThat(multiplicationPersistence, is(2));
		}

		@Test
		public void MultiplicativePersistence_of_39_is_3() {
			int multiplicationPersistence = multiplicationPersistenceCalculator
					.getMultiplicationPersistence(new InfiniteNaturalNumber("39"));
			assertThat(multiplicationPersistence, is(3));
		}

		@Test
		public void MultiplicativePersistence_of_77_is_4() {
			int multiplicationPersistence = multiplicationPersistenceCalculator
					.getMultiplicationPersistence(new InfiniteNaturalNumber("77"));
			assertThat(multiplicationPersistence, is(4));
		}

		@Test
		public void MultiplicativePersistence_of_679_is_5() {
			int multiplicationPersistence = multiplicationPersistenceCalculator
					.getMultiplicationPersistence(new InfiniteNaturalNumber("679"));
			assertThat(multiplicationPersistence, is(5));
		}

		@Test
		public void MultiplicativePersistence_of_6788_is_6() {
			int multiplicationPersistence = multiplicationPersistenceCalculator
					.getMultiplicationPersistence(new InfiniteNaturalNumber("6788"));
			assertThat(multiplicationPersistence, is(6));
		}

		@Test
		public void MultiplicativePersistence_of_68889_is_7() {
			int multiplicationPersistence = multiplicationPersistenceCalculator
					.getMultiplicationPersistence(new InfiniteNaturalNumber("68889"));
			assertThat(multiplicationPersistence, is(7));
		}

		@Test
		public void MultiplicativePersistence_of_2677889_is_8() {
			int multiplicationPersistence = multiplicationPersistenceCalculator
					.getMultiplicationPersistence(new InfiniteNaturalNumber("2677889"));
			assertThat(multiplicationPersistence, is(8));
		}

		@Test
		public void MultiplicativePersistence_of_6778892_is_8() {
			int multiplicationPersistence = multiplicationPersistenceCalculator
					.getMultiplicationPersistence(new InfiniteNaturalNumber("6778892"));
			assertThat(multiplicationPersistence, is(8));
		}

		@Test
		public void MultiplicativePersistence_of_26888999_is_9() {
			int multiplicationPersistence = multiplicationPersistenceCalculator
					.getMultiplicationPersistence(new InfiniteNaturalNumber("26888999"));
			assertThat(multiplicationPersistence, is(9));
		}

		@Test
		public void MultiplicativePersistence_of_3778888999_is_10() {
			int multiplicationPersistence = multiplicationPersistenceCalculator
					.getMultiplicationPersistence(new InfiniteNaturalNumber("3778888999"));
			assertThat(multiplicationPersistence, is(10));
		}

		@Test
		public void MultiplicativePersistence_of_277777788888899_is_11() {
			int multiplicationPersistence = multiplicationPersistenceCalculator
					.getMultiplicationPersistence(new InfiniteNaturalNumber("277777788888899"));
			assertThat(multiplicationPersistence, is(11));
		}

	}

}
