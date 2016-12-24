
package test.numbers;

import static main.matcher.BasicMatchers.containsExactlyAll;
import static main.matcher.BasicMatchers.is;
import static main.matcher.BasicMatchers.not;
import static main.numbers.InfiniteNaturalNumber.ELEVEN;
import static main.numbers.InfiniteNaturalNumber.FIFTY;
import static main.numbers.InfiniteNaturalNumber.FIVE;
import static main.numbers.InfiniteNaturalNumber.HEIGHT;
import static main.numbers.InfiniteNaturalNumber.HUNDRED;
import static main.numbers.InfiniteNaturalNumber.HUNDRED_ONE;
import static main.numbers.InfiniteNaturalNumber.NIGHTY;
import static main.numbers.InfiniteNaturalNumber.NIGHTY_ONE;
import static main.numbers.InfiniteNaturalNumber.NINE;
import static main.numbers.InfiniteNaturalNumber.ONE;
import static main.numbers.InfiniteNaturalNumber.SEVEN;
import static main.numbers.InfiniteNaturalNumber.SIX;
import static main.numbers.InfiniteNaturalNumber.TEN;
import static main.numbers.InfiniteNaturalNumber.THREE;
import static main.numbers.InfiniteNaturalNumber.TWELVE;
import static main.numbers.InfiniteNaturalNumber.TWENTY;
import static main.numbers.InfiniteNaturalNumber.TWENTY_ONE;
import static main.numbers.InfiniteNaturalNumber.TWO;
import static main.numbers.InfiniteNaturalNumber.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import main.junit.PerfTestScenario;
import main.numbers.InfiniteNaturalNumber;
import main.util.CollectionUtils;
import main.util.FormatterUtils;

@RunWith(HierarchicalContextRunner.class)
public class InfiniteNaturalNumberTest {

	@Ignore
	public class Constructors {

	}

	public class ArithmeticalOperations {

		private InfiniteNaturalNumber expectedResult;

		public class Powers {

			public class Powers10 {
				@Test
				public void Five_times10Power0__is_5() {
					InfiniteNaturalNumber result = FIVE.times10Power(0);
					assertThat(result, is(FIVE));
				}

				@Test
				public void Five_times10Power1__is_50() {
					InfiniteNaturalNumber result = FIVE.times10Power(1);
					assertThat(result, is(FIFTY));
				}

				@Test
				public void One_times10Power1__is_10() {
					InfiniteNaturalNumber result = ONE.times10Power(1);
					assertThat(result, is(TEN));
				}

				@Test
				public void One_times10Power2__is_10() {
					InfiniteNaturalNumber result = ONE.times10Power(2);
					assertThat(result, is(HUNDRED));
				}

				@Test
				public void Two_times10Power1__is_20() {
					InfiniteNaturalNumber result = TWO.times10Power(1);
					assertThat(result, is(TWENTY));
				}
			}

		}

		public class Elementary {

			public class Addition {

				@Test
				public void onePlusOneEqualsTwo() {
					InfiniteNaturalNumber additionResult = ONE.plus(ONE);
					assertThat(additionResult, is(TWO));
				}

				@Test
				public void onePlusTwoEqualsThree() {
					InfiniteNaturalNumber additionResult = ONE.plus(TWO);
					assertThat(additionResult, is(THREE));
				}

				@Test
				public void onePlusTenEqualsEleven() {
					InfiniteNaturalNumber additionResult = ONE.plus(TEN);
					assertThat(additionResult, is(ELEVEN));
				}

				@Test
				public void onePlusTwentyEqualsTwentyOne() {
					InfiniteNaturalNumber additionResult = ONE.plus(TWENTY);
					assertThat(additionResult, is(TWENTY_ONE));
				}

				@Test
				public void withCarry_90Plus10Equals100() {
					InfiniteNaturalNumber additionResult = NIGHTY.plus(TEN);
					assertThat(additionResult, is(HUNDRED));
				}

				@Test
				public void withCarry_9Plus1Equals10() {
					InfiniteNaturalNumber additionResult = NINE.plus(ONE);
					assertThat(additionResult, is(TEN));
				}

				@Test
				public void withCarry_5Plus6Equals12() {
					InfiniteNaturalNumber additionResult = FIVE.plus(SEVEN);
					assertThat(additionResult, is(TWELVE));
				}

				@Test
				public void afterAdditionInitialNumbersAreUnchanged() {
					InfiniteNaturalNumber unused_additionResult = ONE.plus(TEN);

					assertThat(ONE.getNumberOfDigits(), is(1));
					assertThat(TEN.getNumberOfDigits(), is(2));

					// to avoid warning
					assertThat(unused_additionResult, is(ELEVEN));
				}
			}

			public class Substraction {

				@Test
				public void oneMinusOneIsZero() {
					InfiniteNaturalNumber substractionResult = ONE.minus(ONE);
					assertThat(substractionResult, is(ZERO));
				}

				@Test
				public void hundredMinusNineIs91() {
					InfiniteNaturalNumber substractionResult = HUNDRED.minus(NINE);
					assertThat(substractionResult, is(NIGHTY_ONE));
				}

				@Test
				public void tenMinusTwoIsHeight() {
					InfiniteNaturalNumber substractionResult = TEN.minus(TWO);
					assertThat(substractionResult, is(HEIGHT));
				}

				@Test
				public void TwentyOneMinusTenIsEleven() {
					InfiniteNaturalNumber substractionResult = TWENTY_ONE.minus(TEN);
					assertThat(substractionResult, is(ELEVEN));
				}

			}

			public class Multiplication {

				private InfiniteNaturalNumber factor1;
				private InfiniteNaturalNumber factor2;

				protected void computeAndCheck() {
					InfiniteNaturalNumber result = factor1.times(factor2);
					assertThat(result, is(expectedResult));
				}

				public class TestResult {
					@After
					public void after() {
						computeAndCheck();
					}

					@Test
					public void tenTimesZeroIsZero() {
						factor1 = TEN;
						factor2 = ZERO;
						expectedResult = ZERO;
					}

					@Test
					public void tenTimesOneIsTen() {
						factor1 = TEN;
						factor2 = ONE;
						expectedResult = TEN;
					}

					@Test
					public void tenTimesTwoIsTwenty() {
						factor1 = TEN;
						factor2 = TWO;
						expectedResult = TWENTY;
					}

					@Test
					public void tenTimesTenIsHundred() {
						factor1 = TEN;
						factor2 = TEN;
						expectedResult = HUNDRED;
					}
				}

				public class PerfTests extends PerfTestScenario {

					@After
					public void after() {
						computeAndCheck();
						System.out.println("Multiplication of " + factor1 + " and " + factor2 + " calculated in "
								+ FormatterUtils.GetDurationAsString(getTestDuration()));
					}

					@Test
					public void test_10_000_times_2() {
						factor1 = new InfiniteNaturalNumber("10_000");
						factor2 = new InfiniteNaturalNumber("2");

						expectedResult = new InfiniteNaturalNumber("20_000");
					}

					@Test
					public void test_2_times_10_000() {
						factor1 = new InfiniteNaturalNumber("2");
						factor2 = new InfiniteNaturalNumber("10_000");

						expectedResult = new InfiniteNaturalNumber("20_000");
					}

					@Test
					public void test_10_000_times_10_000() {
						factor1 = new InfiniteNaturalNumber("10_000");
						factor2 = new InfiniteNaturalNumber("10_000");

						expectedResult = new InfiniteNaturalNumber("100_000_000");
					}

					@Test
					public void test_1_000_000_times_1_000_000() {
						factor1 = new InfiniteNaturalNumber("1_000_000");
						factor2 = new InfiniteNaturalNumber("1_000_000");

						expectedResult = new InfiniteNaturalNumber("1_000_000_000_000");
					}
				}
			}

			public class Division {

				@Test
				public void tenDividedByTenIsOne() {
					InfiniteNaturalNumber divisionResult = TEN.dividedBy(TEN);
					assertThat(divisionResult, is(ONE));
				}

				@Test
				public void tenDividedByElevenIsZero() {
					InfiniteNaturalNumber divisionResult = TEN.dividedBy(ELEVEN);
					assertThat(divisionResult, is(ZERO));
				}

				@Test
				public void hunderdDividedByOneIsHundred() {
					InfiniteNaturalNumber divisionResult = HUNDRED.dividedBy(ONE);
					assertThat(divisionResult, is(HUNDRED));
				}

				@Test
				public void hunderdDividedByTenIsTen() {
					InfiniteNaturalNumber divisionResult = HUNDRED.dividedBy(TEN);
					assertThat(divisionResult, is(TEN));
				}

				@Test
				public void hunderdDividedByNineIsEleven() {
					InfiniteNaturalNumber divisionResult = HUNDRED.dividedBy(NINE);
					assertThat(divisionResult, is(ELEVEN));
				}

				@Test
				public void hunderdDividedByElevenIsNine() {
					InfiniteNaturalNumber divisionResult = HUNDRED.dividedBy(ELEVEN);
					assertThat(divisionResult, is(NINE));
				}
			}

		}

		public class RestOfDivision {

			private InfiniteNaturalNumber number;
			private InfiniteNaturalNumber divisor;

			@After
			public void after() {
				InfiniteNaturalNumber result = number.restOfDivisionBy(divisor);
				assertThat(result, is(expectedResult));
			}

			@Test
			public void _of_10_by_3_is_1() {
				number = TEN;
				divisor = THREE;
				expectedResult = ONE;
			}

			@Test
			public void _of_10_by_10_is_0() {
				number = TEN;
				divisor = TEN;
				expectedResult = ZERO;
			}

			@Test
			public void _of_10_by_11_is_10() {
				number = TEN;
				divisor = ELEVEN;
				expectedResult = TEN;
			}

			@Test
			public void _of_100_by_11_is_1() {
				number = HUNDRED;
				divisor = ELEVEN;
				expectedResult = ONE;
			}
		}

	}

	public class Base10DigitsInversion {

		@Test
		public void getBase10DigitsInversion_of_0_is_0() {
			assertThat(ZERO, is(ZERO.getBase10DigitsInversion()));
		}

		@Test
		public void getBase10DigitsInversion_of_10_is_1() {
			assertThat(ONE, is(TEN.getBase10DigitsInversion()));
		}

		@Test
		public void getBase10DigitsInversion_of_11_is_11() {
			assertThat(ELEVEN, is(ELEVEN.getBase10DigitsInversion()));
		}

		@Test
		public void getBase10DigitsInversion_of_100_is_1() {
			assertThat(ONE, is(HUNDRED.getBase10DigitsInversion()));
		}

		@Test
		public void getBase10DigitsInversion_of_12_is_21() {
			assertThat(TWENTY_ONE, is(TWELVE.getBase10DigitsInversion()));
		}

		@Test
		public void getBase10DigitsInversion_of_102_is_201() {
			assertThat(new InfiniteNaturalNumber("201"),
					is(new InfiniteNaturalNumber("102").getBase10DigitsInversion()));
		}

		@Test
		public void getBase10DigitsInversion_twice_of_number_returns_same_number() {
			assertThat(NIGHTY_ONE, is(NIGHTY_ONE.getBase10DigitsInversion().getBase10DigitsInversion()));
		}
	}

	public class DigitsOrderedInGrowingOrder {
		@Test
		public void areDigitsOrderedInGrowingOrder_10_false() {
			assertThat(TEN.areDigitsOrderedInGrowingOrder(), is(false));
		}

		@Test
		public void areDigitsOrderedInGrowingOrder_11_true() {
			assertThat(ELEVEN.areDigitsOrderedInGrowingOrder(), is(true));
		}

		@Test
		public void areDigitsOrderedInGrowingOrder_12_true() {
			assertThat(TWELVE.areDigitsOrderedInGrowingOrder(), is(true));
		}

		@Test
		public void areDigitsOrderedInGrowingOrder_8_true() {
			assertThat(HEIGHT.areDigitsOrderedInGrowingOrder(), is(true));
		}

		@Test
		public void areDigitsOrderedInGrowingOrder_100_False() {
			assertThat(HUNDRED.areDigitsOrderedInGrowingOrder(), is(false));
		}

		@Test
		public void areDigitsOrderedInGrowingOrder_101_False() {
			assertThat(HUNDRED_ONE.areDigitsOrderedInGrowingOrder(), is(false));
		}

		@Test
		public void areDigitsOrderedInGrowingOrder_123_True() {
			assertThat(new InfiniteNaturalNumber("123").areDigitsOrderedInGrowingOrder(), is(true));
		}
	}

	public class DivisorsFinder {

		private InfiniteNaturalNumber number;

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
						number = SIX;
						expectedResult = true;
					}

					@Test
					public void for_10_returns_true() {
						number = TEN;
						expectedResult = true;
					}

					@Test
					public void for_20_returns_true() {
						number = TWENTY;
						expectedResult = true;
					}

					@Test
					public void for_22_returns_false() {
						number = new InfiniteNaturalNumber("22");
						expectedResult = false;
					}

					@Test
					public void for_16_returns_true() {
						number = TWENTY;
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
							number = new InfiniteNaturalNumber("2048");
							expectedResult = true;
						}

						@Test
						public void for_2053_returns_false() {
							number = new InfiniteNaturalNumber("2053");
							expectedResult = false;
						}
					}
				}
			}

			public class AllPrimeDivisors {
				private List<InfiniteNaturalNumber> expectedDivisors;
				private List<InfiniteNaturalNumber> foundDivisors;

				protected void computeAndCheck() {
					foundDivisors = number.getAllPrimeDivisors();
					assertThat(foundDivisors, containsExactlyAll(expectedDivisors));
				}

				public class TestResult {

					@After
					public void after() {
						computeAndCheck();
					}

					@Test
					public void of_6_are_2_3() {
						number = SIX;
						expectedDivisors = CollectionUtils.asList(TWO, THREE);
					}

					@Test
					public void of_10_are_2_5() {
						number = TEN;
						expectedDivisors = CollectionUtils.asList(TWO, FIVE);
					}

					@Test
					public void of_20_are_2_2_5() {
						number = TWENTY;
						expectedDivisors = CollectionUtils.asList(TWO, TWO, FIVE);
					}
				}

				public class PerfTests extends PerfTestScenario {

					@After
					public void after() {
						computeAndCheck();
						System.out.println("Prime divisors of " + number + " found in "
								+ FormatterUtils.GetDurationAsString(getTestDuration()));
					}

					@Test
					public void of_10_000_are_2_2_2_2_5_5_5_5() {
						number = new InfiniteNaturalNumber("10_000");
						expectedDivisors = CollectionUtils.asList(TWO, TWO, TWO, TWO, FIVE, FIVE, FIVE, FIVE);
					}

					@Test
					public void of_2003_hasNoDivisor() {
						number = new InfiniteNaturalNumber("2003");
						expectedDivisors = CollectionUtils.asList(number);
					}

					@Ignore
					@Test
					public void of_11003_hasNoDivisor() {
						number = new InfiniteNaturalNumber("11003");
						expectedDivisors = CollectionUtils.asList(number);
					}

					@Ignore
					@Test
					public void of_24001_hasNoDivisor() {
						number = new InfiniteNaturalNumber("24001");
						expectedDivisors = CollectionUtils.asList(number);
					}

					@Ignore
					@Test
					public void of_49999_hasNoDivisor() {
						number = new InfiniteNaturalNumber("49999");
						expectedDivisors = CollectionUtils.asList(number);
					}

				}
			}
		}
	}

	public class ContainsDigit {
		public class AsInt {
			@Test
			public void Ten_containsDitig_1() {
				assertThat(TEN.containsDigit(1), is(true));
			}
		}

		public class AsByte {
			@Test
			public void Ten_containsDitig_1() {
				byte one = (byte) 1;
				assertThat(TEN.containsDigit(one), is(true));
			}
		}
	}

	public class Base10DigitsMultiplication {

		@Test
		public void Base10DigitsMultiplication_of_0_is_0() {
			assertThat(ZERO, is(ZERO.getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_1_is_1() {
			assertThat(ONE, is(ONE.getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_10_is_0() {
			assertThat(ZERO, is(TEN.getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_11_is_1() {
			assertThat(ONE, is(ELEVEN.getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_111_is_1() {
			assertThat(ONE, is(new InfiniteNaturalNumber("111").getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_321_is_6() {
			assertThat(SIX, is(new InfiniteNaturalNumber("321").getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_412_is_8() {
			assertThat(HEIGHT, is(new InfiniteNaturalNumber("412").getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_124_is_8() {
			assertThat(HEIGHT, is(new InfiniteNaturalNumber("124").getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_421_is_8() {
			assertThat(HEIGHT, is(new InfiniteNaturalNumber("421").getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_12_is_2() {
			assertThat(ZERO, is(ZERO.getBase10DigitsMultiplication()));
		}
	}

	public class Palindrome {

		@Test
		public void ZeroIsPalindrome() {
			assertThat(ZERO.isPalindrome(), is(true));
		}

		@Test
		public void OneIsPalindrome() {
			assertThat(ONE.isPalindrome(), is(true));
		}

		@Test
		public void ElevenIsPalindrome() {
			assertThat(ELEVEN.isPalindrome(), is(true));
		}

		@Test
		public void TwelveIsNotPalindrome() {
			assertThat(TWELVE.isPalindrome(), is(false));
		}

		@Test
		public void HundredIsNotPalindrome() {
			assertThat(HUNDRED.isPalindrome(), is(false));
		}

		@Test
		public void HundredOneIsPalindrome() {
			assertThat(HUNDRED_ONE.isPalindrome(), is(true));
		}
	}

	public class Comparison {
		public class Equal {

			@Test
			public void sameNumberAreEquals() {
				InfiniteNaturalNumber first_ten = new InfiniteNaturalNumber("10");
				InfiniteNaturalNumber second_ten = new InfiniteNaturalNumber("10");
				assertThat(first_ten, is(second_ten));
			}

			@Test
			public void tenAndTwentyAreNotEqual() {
				InfiniteNaturalNumber ten = new InfiniteNaturalNumber("10");
				InfiniteNaturalNumber twenty = new InfiniteNaturalNumber("20");
				assertThat(ten, is(not(twenty)));
			}

			@Test
			public void oneAndTenAreNotEqual() {
				InfiniteNaturalNumber ten = new InfiniteNaturalNumber("10");
				InfiniteNaturalNumber one = new InfiniteNaturalNumber("1");
				assertThat(ten, is(not(one)));
			}

		}

		public class GreaterOrEquals {

			@Test
			public void oneIsNotGreaterOrEquansToNine() {
				assertThat(ONE.isGreaterOrEqualsTo(NINE), is(false));
			}

		}

		public class StrictlyGreater {

			@Test
			public void twoIsStricltlyGreaterThanOne() {
				InfiniteNaturalNumber one = new InfiniteNaturalNumber("1");
				InfiniteNaturalNumber two = new InfiniteNaturalNumber("2");
				assertThat(two.isStrictlyGreaterThan(one), is(true));
			}

			@Test
			public void tenIsStricltlyGreaterThanOne() {
				InfiniteNaturalNumber one = new InfiniteNaturalNumber("1");
				InfiniteNaturalNumber ten = new InfiniteNaturalNumber("10");
				assertThat(ten.isStrictlyGreaterThan(one), is(true));
			}

			@Test
			public void twentyTwoIsStricltlyGreaterThanEleven() {
				InfiniteNaturalNumber twentyOne = new InfiniteNaturalNumber("21");
				InfiniteNaturalNumber eleven = new InfiniteNaturalNumber("11");
				assertThat(twentyOne.isStrictlyGreaterThan(eleven), is(true));
			}

			@Test
			public void tenIsNotStricltlyGreaterThanTen_sameInstance() {
				InfiniteNaturalNumber ten = new InfiniteNaturalNumber("10");
				assertThat(ten.isStrictlyGreaterThan(ten), is(false));
			}

			@Test
			public void tenIsNotStricltlyGreaterThanTen_differentInstances() {
				InfiniteNaturalNumber ten_instance1 = new InfiniteNaturalNumber("10");
				InfiniteNaturalNumber ten_instance2 = new InfiniteNaturalNumber("10");
				assertThat(ten_instance1.isStrictlyGreaterThan(ten_instance2), is(false));
			}
		}
	}

}
