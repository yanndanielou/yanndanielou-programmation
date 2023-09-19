package challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class ChallengeTests {

	@Nested
	public class Kaprekar {

		@Test
		public void test3524() {
			assertEquals(3, Challenge.kaprekar(3524));
		}

		@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
		@Test
		public void test1() {
			assertEquals(5, Challenge.kaprekar(1112));
		}

		@Test
		public void test2() {
			assertEquals(7, Challenge.kaprekar(456));
		}

		@Test
		public void test3() {
			assertEquals(3, Challenge.kaprekar(76));
		}
		@Test
		public void test4() {
			assertEquals(5, Challenge.kaprekar(100));
		}

		@Test
		public void test5() {
			assertEquals(4, Challenge.kaprekar(101));
		}
	}

	@Nested
	public class Solutions {

		@Test
		public void test1() {
			assertEquals(2, Challenge.solutions(1, 0, -1));
		}

		@Test
		public void test2() {
			assertEquals(1, Challenge.solutions(1, 0, 0));
		}

		@Test
		public void test3() {
			assertEquals(0, Challenge.solutions(1, 0, 1));
		}

		@Test
		public void test4() {
			assertEquals(0, Challenge.solutions(200, 420, 800));
		}

		@Test
		public void test5() {
			assertEquals(2, Challenge.solutions(200, 420, -800));
		}

		@Test
		public void test6() {
			assertEquals(2, Challenge.solutions(1000, 1000, 0));
		}

		@Test
		public void test7() {
			assertEquals(1, Challenge.solutions(10000, 400, 4));
		}
	}

}
