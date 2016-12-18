
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
		public void withCarry_5Plus6Equals11() {
			InfiniteNaturalNumber additionResult = FIVE.plus(SIX);
			assertTrue(additionResult.equals(ELEVEN));
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
		public void tenMinusTwoIsHeight() {
			InfiniteNaturalNumber substractionResult = TEN.minus(TWO);
			assertTrue(substractionResult.equals(HEIGHT));
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
