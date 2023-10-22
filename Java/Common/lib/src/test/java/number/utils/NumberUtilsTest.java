package number.utils;

import static matcher.BasicMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import common.numbers.utils.NumberUtils;
import util.CollectionUtils;

public class NumberUtilsTest {

	@Nested
	public class ComputePositiveIntFromListOfDigits {
		@Test
		public void test0() {
			assertThat(NumberUtils.computePositiveIntFromListOfDigits(CollectionUtils.asList(0)), is(0));
		}

		@Test
		public void test1() {
			assertThat(NumberUtils.computePositiveIntFromListOfDigits(CollectionUtils.asList(1)), is(1));
		}

		@Test
		public void test12() {
			assertThat(NumberUtils.computePositiveIntFromListOfDigits(CollectionUtils.asList(1, 2)), is(12));
		}

		@Test
		public void test123() {
			assertThat(NumberUtils.computePositiveIntFromListOfDigits(CollectionUtils.asList(1, 2, 3)), is(123));
		}

		@Test
		public void test01() {
			assertThat(NumberUtils.computePositiveIntFromListOfDigits(CollectionUtils.asList(0, 1)), is(1));
		}

	}


	@Nested
	public class GetDigitsFromIntegerAsIntList {
		
		@Test
		public void test1() {
			assertThat(NumberUtils.getDigitsFromIntegerAsIntList(1),
					matcher.BasicMatchers.containsExactly(1));
		}

		@Test
		public void test12() {
			assertThat(NumberUtils.getDigitsFromIntegerAsIntList(12),
					matcher.BasicMatchers.containsExactly(1,2));
		}


		@Test
		public void test123() {
			assertThat(NumberUtils.getDigitsFromIntegerAsIntList(123),
					matcher.BasicMatchers.containsExactly(1,2,3));
		}

		@Test
		public void test987() {
			assertThat(NumberUtils.getDigitsFromIntegerAsIntList(987),
					matcher.BasicMatchers.containsExactly(9,8,7));
		}

	}
	
	@Nested
	public class GetDigitsFromIntegerInAscendingOrderAsIntList {
		
		@Test
		public void test1() {
			assertThat(NumberUtils.getDigitsFromIntegerInAscendingOrderAsIntList(1),
					matcher.BasicMatchers.containsExactly(1));
		}

		@Test
		public void test12() {
			assertThat(NumberUtils.getDigitsFromIntegerInAscendingOrderAsIntList(12),
					matcher.BasicMatchers.containsExactly(1, 2));
		}

		@Test
		public void test21() {
			assertThat(NumberUtils.getDigitsFromIntegerInAscendingOrderAsIntList(21),
					matcher.BasicMatchers.containsExactly(1, 2));
		}

		@Test
		public void test3777() {
			assertThat(NumberUtils.getDigitsFromIntegerInAscendingOrderAsIntList(3777),
					matcher.BasicMatchers.containsExactly(3, 7, 7, 7));
		}

		@Test
		public void test0() {
			assertThat(NumberUtils.getDigitsFromIntegerInAscendingOrderAsIntList(0),
					matcher.BasicMatchers.containsExactly(0));
		}

	}

	@Nested
	public class GetDigitsFromIntegerInDescendingOrderAsIntList {
		@Test
		public void test3777() {
			assertThat(NumberUtils.getDigitsFromIntegerInDescendingOrderAsIntList(3777),
					matcher.BasicMatchers.containsExactly(7, 7, 7, 3));
		}

		@Test
		public void test0() {
			assertThat(NumberUtils.getDigitsFromIntegerInDescendingOrderAsIntList(0),
					matcher.BasicMatchers.containsExactly(0));
		}

	}

	@Nested
	public class GetHighestDigitOfInteger {

		@Test
		public void test3777() {
			assertEquals(7, NumberUtils.getHighestDigitOfInteger(3777));
		}

		@Test
		public void test433() {
			assertEquals(4, NumberUtils.getHighestDigitOfInteger(433));
		}

		@Test
		public void test42() {
			assertEquals(4, NumberUtils.getHighestDigitOfInteger(42));
		}

		@Test
		public void test0() {
			assertEquals(0, NumberUtils.getHighestDigitOfInteger(0));
		}

		@Test
		public void test333333() {
			assertEquals(3, NumberUtils.getHighestDigitOfInteger(333333));
		}
	}

}