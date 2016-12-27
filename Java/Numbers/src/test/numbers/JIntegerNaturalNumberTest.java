
package test.numbers;

import static main.matcher.BasicMatchers.containsExactlyAll;
import static main.matcher.BasicMatchers.is;
import static main.matcher.BasicMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import main.junit.PerfTestScenario;
import main.numbers.JIntegerNaturalNumber;
import main.util.CollectionUtils;
import main.util.FormatterUtils;

@RunWith(HierarchicalContextRunner.class)
public class JIntegerNaturalNumberTest {

	public class Constructors {

		@Test
		public void intAndListOfBytesConstructorsReturnSameNumber() {
			JIntegerNaturalNumber first = new JIntegerNaturalNumber(123);
			JIntegerNaturalNumber second = new JIntegerNaturalNumber(
					CollectionUtils.asList((byte) 1, (byte) 2, (byte) 3));
			assertThat(first, is(second));
		}
	}

	public class Comparison {

		public class Equal {
			@Test
			public void sameNumberAreEquals() {
				JIntegerNaturalNumber first_ten = new JIntegerNaturalNumber(10);
				JIntegerNaturalNumber second_ten = new JIntegerNaturalNumber(10);
				assertThat(first_ten, is(second_ten));
			}

			@Test
			public void tenAndTwentyAreNotEqual() {
				JIntegerNaturalNumber ten = new JIntegerNaturalNumber(10);
				JIntegerNaturalNumber twenty = new JIntegerNaturalNumber(20);
				assertThat(ten, is(not(twenty)));
			}

			@Test
			public void oneAndTenAreNotEqual() {
				JIntegerNaturalNumber ten = new JIntegerNaturalNumber(10);
				JIntegerNaturalNumber one = new JIntegerNaturalNumber(1);
				assertThat(ten, is(not(one)));
			}
		}
	}

	public class DivisorsFinder {

		private JIntegerNaturalNumber number;

		public class PrimeDivisors {

			public class HasOnlyOneDigitPrimeDivisors {
				private boolean expectedResult;

				protected void computeAndCheck() {
					boolean actualResult = number.hasOnlyOneDigitPrimeDivisors();
					assertThat(actualResult, is(expectedResult));
				}

				public class TestResult {
					@After
					public void after() {
						computeAndCheck();
					}

					@Test
					public void for_9_returns_true() {
						number = new JIntegerNaturalNumber(9);
						expectedResult = true;
					}

					@Test
					public void for_10_returns_true() {
						number = new JIntegerNaturalNumber(10);
						expectedResult = true;
					}

					@Test
					public void for_20_returns_true() {
						number = new JIntegerNaturalNumber(20);
						expectedResult = true;
					}

					@Test
					public void for_22_returns_false() {
						number = new JIntegerNaturalNumber(22);
						expectedResult = false;
					}

					@Test
					public void for_16_returns_true() {
						number = new JIntegerNaturalNumber(16);
						expectedResult = true;
					}

					public class PerfTests extends PerfTestScenario {

						@After
						public void after() {
							computeAndCheck();
							System.out.println("HasOnlyOneDigitPrimeDivisors divisors of " + number + " ran in "
									+ FormatterUtils.GetDurationAsString(getTestDuration()));
						}

						@Test
						public void for_2048_returns_true() {
							number = new JIntegerNaturalNumber(2048);
							expectedResult = true;
						}

						@Test
						public void for_2053_returns_false() {
							number = new JIntegerNaturalNumber(2053);
							expectedResult = false;
						}

						@Test
						public void for_49999_returns_false() {
							number = new JIntegerNaturalNumber(49999);
							expectedResult = false;
						}

						@Test
						public void for_2power20_returns_true() {
							number = new JIntegerNaturalNumber(1048576);
							expectedResult = true;
						}

						@Test
						public void for_2power24_returns_true() {
							number = new JIntegerNaturalNumber(33554432);
							expectedResult = true;
						}
					}
				}
			}

			public class AllPrimeDivisors {
				private List<JIntegerNaturalNumber> expectedDivisors;
				private List<JIntegerNaturalNumber> foundDivisors;

				protected void computeAndCheck() {
					foundDivisors = number.getAllPrimeDivisors();
					assertThat(foundDivisors, containsExactlyAll(expectedDivisors));
				}

				public class TestResult {

					@After
					public void after() {
						computeAndCheck();
					}

					@Ignore
					@Test
					public void of_0_is_empty() {
						number = new JIntegerNaturalNumber(0);
						expectedDivisors = CollectionUtils.emptyList();
					}

					@Test
					public void of_6_are_2_3() {
						number = new JIntegerNaturalNumber(6);
						expectedDivisors = CollectionUtils.asList(new JIntegerNaturalNumber(2),
								new JIntegerNaturalNumber(3));
					}

					@Test
					public void of_10_are_2_5() {
						number = new JIntegerNaturalNumber(10);
						expectedDivisors = CollectionUtils.asList(new JIntegerNaturalNumber(2),
								new JIntegerNaturalNumber(5));
					}

					@Test
					public void of_20_are_2_2_5() {
						number = new JIntegerNaturalNumber(20);
						expectedDivisors = CollectionUtils.asList(new JIntegerNaturalNumber(2),
								new JIntegerNaturalNumber(2), new JIntegerNaturalNumber(5));
					}
				}

				public class PerfTests extends PerfTestScenario {

					@After
					public void after() {
						computeAndCheck();
						System.out.println("Prime divisors of " + number + " found in "
								+ FormatterUtils.GetDurationAsString(getTestDuration()));
					}

					@Ignore
					@Test
					public void of_10_000_are_2_2_2_2_5_5_5_5() {
						number = new JIntegerNaturalNumber(10_000);
						expectedDivisors = CollectionUtils.asList(new JIntegerNaturalNumber(2),
								new JIntegerNaturalNumber(2), new JIntegerNaturalNumber(2),
								new JIntegerNaturalNumber(2), new JIntegerNaturalNumber(5),
								new JIntegerNaturalNumber(5), new JIntegerNaturalNumber(5),
								new JIntegerNaturalNumber(5));
					}

					@Ignore
					@Test
					public void of_2003_hasNoDivisor() {
						number = new JIntegerNaturalNumber(2003);
						expectedDivisors = CollectionUtils.asList(number);
					}

					@Ignore
					@Test
					public void of_11003_hasNoDivisor() {
						number = new JIntegerNaturalNumber(11003);
						expectedDivisors = CollectionUtils.asList(number);
					}

					@Ignore
					@Test
					public void of_24001_hasNoDivisor() {
						number = new JIntegerNaturalNumber(24001);
						expectedDivisors = CollectionUtils.asList(number);
					}

					@Ignore
					@Test
					public void of_49999_hasNoDivisor() {
						number = new JIntegerNaturalNumber(49999);
						expectedDivisors = CollectionUtils.asList(number);
					}

				}
			}

		}
	}
}
