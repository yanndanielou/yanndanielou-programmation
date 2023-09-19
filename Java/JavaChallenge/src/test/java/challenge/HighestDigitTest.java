package challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HighestDigitTest {

	@Test
	public void test01() {
		assertEquals(6, HighestDigit.highestDigit(4666));
	}

	@Test
	public void test02() {
		assertEquals(5, HighestDigit.highestDigit(544));
	}

	@Test
	public void test03() {
		assertEquals(5, HighestDigit.highestDigit(51));
	}

	@Test
	public void test04() {
		assertEquals(0, HighestDigit.highestDigit(0));
	}

	@Test
	public void test05() {
		assertEquals(9, HighestDigit.highestDigit(7495037));
	}

	@Test
	public void test06() {
		assertEquals(2, HighestDigit.highestDigit(222222));
	}

}
