
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
	public class Constructor {

	}

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
