package challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NumericStringTests {

	private String[][] strVector = new String[][] { { "91", "19" }, { "123456789", "987654322" }, { "9999999", "1" },
			{ "300", "3000" }, { "1000", "6200" }, { "-10", "-20" }, { "-100", "100" }, { "0", "6200" }, { "", "6" },
			{ "", null }, { null, "23" }, { "", "20" } };
	private String[] resVector = new String[] { "110", "1111111111", "10000000", "3300", "7200", "-30", "0", "6200",
			"Invalid Operation", "Invalid Operation", "Invalid Operation", "Invalid Operation" };

	@Test
	public void test01() {
		assertEquals(resVector[0], NumericString.add(strVector[0][0], strVector[0][1]));
	}

	@Test
	public void test02() {
		assertEquals(resVector[1], NumericString.add(strVector[1][0], strVector[1][1]));
	}

	@Test
	public void test03() {
		assertEquals(resVector[2], NumericString.add(strVector[2][0], strVector[2][1]));
	}

	@Test
	public void test04() {
		assertEquals(resVector[3], NumericString.add(strVector[3][0], strVector[3][1]));
	}

	@Test
	public void test05() {
		assertEquals(resVector[4], NumericString.add(strVector[4][0], strVector[4][1]));
	}

	@Test
	public void test06() {
		assertEquals(resVector[5], NumericString.add(strVector[5][0], strVector[5][1]));
	}

	@Test
	public void test07() {
		assertEquals(resVector[6], NumericString.add(strVector[6][0], strVector[6][1]));
	}

	@Test
	public void test08() {
		assertEquals(resVector[7], NumericString.add(strVector[7][0], strVector[7][1]));
	}

	@Test
	public void test09() {
		assertEquals(resVector[8], NumericString.add(strVector[8][0], strVector[8][1]));
	}

	@Test
	public void test10() {
		assertEquals(resVector[9], NumericString.add(strVector[9][0], strVector[9][1]));
	}

	@Test
	public void test11() {
		assertEquals(resVector[10], NumericString.add(strVector[10][0], strVector[10][1]));
	}

	@Test
	public void test12() {
		assertEquals(resVector[11], NumericString.add(strVector[11][0], strVector[11][1]));
	}
}
