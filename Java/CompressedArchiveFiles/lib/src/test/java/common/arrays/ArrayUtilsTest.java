package common.arrays;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class ArrayUtilsTest {

	@Test
	void nullArgument() {
		assertNull(ArrayUtils.byteArrayToIntArray(null));
	}

	@Test
	void validTable() {
		int[] intArray = { 1, 2, 3 };
		byte[] byteArray = { 1, 2, 3 };
		assertArrayEquals(intArray, ArrayUtils.byteArrayToIntArray(byteArray));
	}

}
