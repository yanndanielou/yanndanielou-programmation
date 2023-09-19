package challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SandboxMainTest {

	@Nested
	public class CalculatorTest {
		@Test
		public void test01() {
			assertEquals(6, SandboxMain.basicCalculator(2, "+", 4));
		}

		@Test
		public void test02() {
			assertEquals(6, SandboxMain.basicCalculator(12, "-", 6));
		}

		@Test
		public void test03() {
			assertEquals(9, SandboxMain.basicCalculator(18, "/", 2));
		}

		@Test
		public void test04() {
			assertEquals(24, SandboxMain.basicCalculator(6, "*", 4));
		}

		@Test
		public void test05() {
			assertEquals(null, SandboxMain.basicCalculator(2, "/", 0));
		}

		@Test
		public void test06() {
			assertEquals(null, SandboxMain.basicCalculator(2, "x", 4));
		}

		@Test
		public void test07() {
			assertEquals(null, SandboxMain.basicCalculator(2, "o", 4));
		}

		@Test
		public void test08() {
			assertEquals(10, SandboxMain.basicCalculator(12, "-", 2));
		}

		@Test
		public void test09() {
			assertEquals(34, SandboxMain.basicCalculator(17, "*", 2));
		}

		@Test
		public void test10() {
			assertEquals(4, SandboxMain.basicCalculator(17, "-", 13));
		}
	}

	@Nested
	public class warOfNumbers {
		@Test
		public void testExampleFor2() {
			assertEquals(2, SandboxMain.warOfNumbers(new int[] { 2, 8, 7, 5 }));
		}

		@Test
		public void testExampleFor27() {
			assertEquals(27, SandboxMain.warOfNumbers(new int[] { 12, 90, 75 }));
		}

		@Test
		public void test1() {
			assertEquals(168, SandboxMain.warOfNumbers(new int[] { 5, 9, 45, 6, 2, 7, 34, 8, 6, 90, 5, 243 }));
		}

		@Test
		public void test2() {
			assertEquals(793, SandboxMain.warOfNumbers(new int[] { 654, 7, 23, 3, 78, 4, 56, 34 }));
		}

		@Test
		public void test3() {
			assertEquals(5, SandboxMain.warOfNumbers(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }));
		}

		@Test
		public void test4() {
			assertEquals(228, SandboxMain
					.warOfNumbers(new int[] { 97, 83, 209, 141, 134, 298, 110, 207, 229, 275, 115, 64, 244, 278 }));
		}

		@Test
		public void test5() {
			assertEquals(83, SandboxMain.warOfNumbers(new int[] { 332, 92, 35, 315, 109, 168, 320, 230, 63, 323, 16,
					204, 105, 17, 226, 157, 245, 44, 223, 136, 93 }));
		}

		@Test
		public void test6() {
			assertEquals(846, SandboxMain.warOfNumbers(new int[] { 322, 89, 36, 310, 297, 157, 251, 55, 264, 244, 200,
					304, 25, 308, 311, 269, 303, 257, 6, 311, 307, 310, 50, 46, 54, 237, 59, 105, 267 }));
		}

		@Test
		public void test7() {
			assertEquals(0, SandboxMain.warOfNumbers(new int[] { 50, 100, 149, 1, 200, 199, 3, 2 }));
		}
	}

}
