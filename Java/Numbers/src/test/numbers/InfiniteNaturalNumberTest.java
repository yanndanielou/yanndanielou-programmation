
package test.numbers;

import static main.numbers.InfiniteNaturalNumber.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import main.numbers.InfiniteNaturalNumber;

@RunWith(HierarchicalContextRunner.class)
public class InfiniteNaturalNumberTest {

	@Ignore
	public class Constructors {

	}

	public class ArithmeticalOperations {

		public class Powers {

			public class Powers10 {
				@Test
				public void Five_times10Power0__is_1() {
					InfiniteNaturalNumber result = FIVE.times10Power(0);
					assertTrue(result.equals(FIVE));
				}

				@Test
				public void Five_times10Power1__is_50() {
					InfiniteNaturalNumber result = FIVE.times10Power(1);
					assertTrue(result.equals(FIFTY));
				}

				@Test
				public void One_times10Power1__is_10() {
					InfiniteNaturalNumber result = ONE.times10Power(1);
					assertTrue(result.equals(TEN));
				}

				@Test
				public void One_times10Power2__is_10() {
					InfiniteNaturalNumber result = ONE.times10Power(2);
					assertTrue(result.equals(HUNDRED));
				}

				@Test
				public void Two_times10Power1__is_20() {
					InfiniteNaturalNumber result = TWO.times10Power(1);
					assertTrue(result.equals(TWENTY));
				}
			}

		}

		public class Elementary {

			public class Addition {

				@Test
				public void onePlusOneEqualsTwo() {
					InfiniteNaturalNumber additionResult = ONE.plus(ONE);
					assertTrue(additionResult.equals(TWO));
				}

				@Test
				public void onePlusTwoEqualsThree() {
					InfiniteNaturalNumber additionResult = ONE.plus(TWO);
					assertTrue(additionResult.equals(THREE));
				}

				@Test
				public void onePlusTenEqualsEleven() {
					InfiniteNaturalNumber additionResult = ONE.plus(TEN);
					assertTrue(additionResult.equals(ELEVEN));
				}

				@Test
				public void onePlusTwentyEqualsTwentyOne() {
					InfiniteNaturalNumber additionResult = ONE.plus(TWENTY);
					assertTrue(additionResult.equals(TWENTY_ONE));
				}

				@Test
				public void withCarry_90Plus10Equals100() {
					InfiniteNaturalNumber additionResult = NIGHTY.plus(TEN);
					assertTrue(additionResult.equals(HUNDRED));
				}

				@Test
				public void withCarry_9Plus1Equals10() {
					InfiniteNaturalNumber additionResult = NINE.plus(ONE);
					assertTrue(additionResult.equals(TEN));
				}

				@Test
				public void withCarry_5Plus6Equals12() {
					InfiniteNaturalNumber additionResult = FIVE.plus(SEVEN);
					assertTrue(additionResult.equals(TWELVE));
				}

				@Test
				public void afterAdditionInitialNumbersAreUnchanged() {
					InfiniteNaturalNumber unused_additionResult = ONE.plus(TEN);

					assertTrue(ONE.getNumberOfDigits() == 1);
					assertTrue(TEN.getNumberOfDigits() == 2);

					// to avoid warning
					assertTrue(unused_additionResult.equals(ELEVEN));
				}
			}

			public class Substraction {

				@Test
				public void oneMinusOneIsZero() {
					InfiniteNaturalNumber substractionResult = ONE.minus(ONE);
					assertTrue(substractionResult.equals(ZERO));
				}

				@Test
				public void hundredMinusNineIs91() {
					InfiniteNaturalNumber substractionResult = HUNDRED.minus(NINE);
					assertTrue(substractionResult.equals(NIGHTY_ONE));
				}

				@Test
				public void tenMinusTwoIsHeight() {
					InfiniteNaturalNumber substractionResult = TEN.minus(TWO);
					assertTrue(substractionResult.equals(HEIGHT));
				}

				@Test
				public void TwentyOneMinusTenIsEleven() {
					InfiniteNaturalNumber substractionResult = TWENTY_ONE.minus(TEN);
					assertTrue(substractionResult.equals(ELEVEN));
				}

			}

			public class Multiplication {
				@Test
				public void tenTimesZeroIsZero() {
					InfiniteNaturalNumber multiplicationResult = TEN.times(ZERO);
					assertTrue(multiplicationResult.equals(ZERO));
				}

				@Test
				public void tenTimesOneIsTen() {
					InfiniteNaturalNumber multiplicationResult = TEN.times(ONE);
					assertTrue(multiplicationResult.equals(TEN));
				}

				@Test
				public void tenTimesTwoIsTwenty() {
					InfiniteNaturalNumber multiplicationResult = TEN.times(TWO);
					assertTrue(multiplicationResult.equals(TWENTY));
				}

				@Test
				public void tenTimesTenIsHundred() {
					InfiniteNaturalNumber multiplicationResult = TEN.times(TEN);
					assertTrue(multiplicationResult.equals(HUNDRED));
				}
			}

			public class Division {

				@Test
				public void tenDividedByTenIsOne() {
					InfiniteNaturalNumber divisionResult = TEN.dividedBy(TEN);
					assertTrue(divisionResult.equals(ONE));
				}

				@Test
				public void tenDividedByElevenIsZero() {
					InfiniteNaturalNumber divisionResult = TEN.dividedBy(ELEVEN);
					assertTrue(divisionResult.equals(ZERO));
				}

				@Test
				public void hunderdDividedByOneIsHundred() {
					InfiniteNaturalNumber divisionResult = HUNDRED.dividedBy(ONE);
					assertTrue(divisionResult.equals(HUNDRED));
				}

				@Test
				public void hunderdDividedByTenIsTen() {
					InfiniteNaturalNumber divisionResult = HUNDRED.dividedBy(TEN);
					assertTrue(divisionResult.equals(TEN));
				}

				@Test
				public void hunderdDividedByNineIsEleven() {
					InfiniteNaturalNumber divisionResult = HUNDRED.dividedBy(NINE);
					assertTrue(divisionResult.equals(ELEVEN));
				}

				@Test
				public void hunderdDividedByElevenIsNine() {
					InfiniteNaturalNumber divisionResult = HUNDRED.dividedBy(ELEVEN);
					assertTrue(divisionResult.equals(NINE));
				}
			}

		}
	}

	public class Base10DigitsInversion {

		@Test
		public void getBase10DigitsInversion_of_0_is_0() {
			assertTrue(ZERO.equals(ZERO.getBase10DigitsInversion()));
		}

		@Test
		public void getBase10DigitsInversion_of_10_is_1() {
			assertTrue(ONE.equals(TEN.getBase10DigitsInversion()));
		}

		@Test
		public void getBase10DigitsInversion_of_11_is_11() {
			assertTrue(ELEVEN.equals(ELEVEN.getBase10DigitsInversion()));
		}

		@Test
		public void getBase10DigitsInversion_of_100_is_1() {
			assertTrue(ONE.equals(HUNDRED.getBase10DigitsInversion()));
		}

		@Test
		public void getBase10DigitsInversion_of_12_is_21() {
			assertTrue(TWENTY_ONE.equals(TWELVE.getBase10DigitsInversion()));
		}

		@Test
		public void getBase10DigitsInversion_of_102_is_201() {
			assertTrue(new InfiniteNaturalNumber("201")
					.equals(new InfiniteNaturalNumber("102").getBase10DigitsInversion()));
		}

		@Test
		public void getBase10DigitsInversion_twice_of_number_returns_same_number() {
			assertTrue(NIGHTY_ONE.equals(NIGHTY_ONE.getBase10DigitsInversion().getBase10DigitsInversion()));
		}
	}

	public class DigitsOrderedInGrowingOrder {
		@Test
		public void areDigitsOrderedInGrowingOrder_10_false() {
			assertFalse(TEN.areDigitsOrderedInGrowingOrder());
		}

		@Test
		public void areDigitsOrderedInGrowingOrder_11_true() {
			assertTrue(ELEVEN.areDigitsOrderedInGrowingOrder());
		}

		@Test
		public void areDigitsOrderedInGrowingOrder_12_true() {
			assertTrue(TWELVE.areDigitsOrderedInGrowingOrder());
		}

		@Test
		public void areDigitsOrderedInGrowingOrder_8_true() {
			assertTrue(HEIGHT.areDigitsOrderedInGrowingOrder());
		}

		@Test
		public void areDigitsOrderedInGrowingOrder_100_False() {
			assertFalse(HUNDRED.areDigitsOrderedInGrowingOrder());
		}

		@Test
		public void areDigitsOrderedInGrowingOrder_101_False() {
			assertFalse(HUNDRED_ONE.areDigitsOrderedInGrowingOrder());
		}

		@Test
		public void areDigitsOrderedInGrowingOrder_123_False() {
			assertTrue(new InfiniteNaturalNumber("123").areDigitsOrderedInGrowingOrder());
		}
	}

	public class ContainsDigit {
		public class AsInt {
			@Test
			public void Ten_containsDitig_1() {
				assertTrue(TEN.containsDigit(1));
			}
		}

		public class AsByte {
			@Test
			public void Ten_containsDitig_1() {
				byte one = (byte) 1;
				assertTrue(TEN.containsDigit(one));
			}
		}
	}

	public class Base10DigitsMultiplication {

		@Test
		public void Base10DigitsMultiplication_of_0_is_0() {
			assertTrue(ZERO.equals(ZERO.getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_1_is_1() {
			assertTrue(ONE.equals(ONE.getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_10_is_0() {
			assertTrue(ZERO.equals(TEN.getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_11_is_1() {
			assertTrue(ONE.equals(ELEVEN.getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_111_is_1() {
			assertTrue(ONE.equals(new InfiniteNaturalNumber("111").getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_321_is_6() {
			assertTrue(SIX.equals(new InfiniteNaturalNumber("321").getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_412_is_8() {
			assertTrue(HEIGHT.equals(new InfiniteNaturalNumber("412").getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_124_is_8() {
			assertTrue(HEIGHT.equals(new InfiniteNaturalNumber("124").getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_421_is_8() {
			assertTrue(HEIGHT.equals(new InfiniteNaturalNumber("421").getBase10DigitsMultiplication()));
		}

		@Test
		public void Base10DigitsMultiplication_of_12_is_2() {
			assertTrue(ZERO.equals(ZERO.getBase10DigitsMultiplication()));
		}
	}

	public class Palindrome {

		@Test
		public void ZeroIsPalindrome() {
			assertTrue(ZERO.isPalindrome());
		}

		@Test
		public void OneIsPalindrome() {
			assertTrue(ONE.isPalindrome());
		}

		@Test
		public void ElevenIsPalindrome() {
			assertTrue(ELEVEN.isPalindrome());
		}

		@Test
		public void TwelveIsNotPalindrome() {
			assertFalse(TWELVE.isPalindrome());
		}

		@Test
		public void HundredIsNotPalindrome() {
			assertFalse(HUNDRED.isPalindrome());
		}

		@Test
		public void HundredOneIsPalindrome() {
			assertTrue(HUNDRED_ONE.isPalindrome());
		}
	}

	public class Comparison {
		public class Equal {

			@Test
			public void sameNumberAreEquals() {
				InfiniteNaturalNumber first_ten = new InfiniteNaturalNumber("10");
				InfiniteNaturalNumber second_ten = new InfiniteNaturalNumber("10");
				assertTrue(first_ten.equals(second_ten));
			}

			@Test
			public void tenAndTwentyAreNotEqual() {
				InfiniteNaturalNumber ten = new InfiniteNaturalNumber("10");
				InfiniteNaturalNumber twenty = new InfiniteNaturalNumber("20");
				assertFalse(ten.equals(twenty));
			}

			@Test
			public void oneAndTenAreNotEqual() {
				InfiniteNaturalNumber ten = new InfiniteNaturalNumber("10");
				InfiniteNaturalNumber one = new InfiniteNaturalNumber("1");
				assertFalse(ten.equals(one));
			}

		}

		public class GreaterOrEquals {

			@Test
			public void oneIsNotGreaterOrEquansToNine() {
				assertFalse(ONE.isGreaterOrEqualsTo(NINE));
			}

		}

		public class StrictlyGreater {

			@Test
			public void twoIsStricltlyGreaterThanOne() {
				InfiniteNaturalNumber one = new InfiniteNaturalNumber("1");
				InfiniteNaturalNumber two = new InfiniteNaturalNumber("2");
				assertTrue(two.isStrictlyGreaterThan(one));
			}

			@Test
			public void tenIsStricltlyGreaterThanOne() {
				InfiniteNaturalNumber one = new InfiniteNaturalNumber("1");
				InfiniteNaturalNumber ten = new InfiniteNaturalNumber("10");
				assertTrue(ten.isStrictlyGreaterThan(one));
			}

			@Test
			public void twentyTwoIsStricltlyGreaterThanEleven() {
				InfiniteNaturalNumber twentyOne = new InfiniteNaturalNumber("21");
				InfiniteNaturalNumber eleven = new InfiniteNaturalNumber("11");
				assertTrue(twentyOne.isStrictlyGreaterThan(eleven));
			}

			@Test
			public void tenIsNotStricltlyGreaterThanTen_sameInstance() {
				InfiniteNaturalNumber ten = new InfiniteNaturalNumber("10");
				assertFalse(ten.isStrictlyGreaterThan(ten));
			}

			@Test
			public void tenIsNotStricltlyGreaterThanTen_differentInstances() {
				InfiniteNaturalNumber ten_instance1 = new InfiniteNaturalNumber("10");
				InfiniteNaturalNumber ten_instance2 = new InfiniteNaturalNumber("10");
				assertFalse(ten_instance1.isStrictlyGreaterThan(ten_instance2));
			}
		}
	}
}
