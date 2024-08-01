package crypto.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MD2HashTest {
	public static Stream<Arguments> provideMD2StringExamples() {
		return Stream.of(
				Arguments.of("Wikipedia, l'encyclopedie libre et gratuite", "c9b3cbbeb539034aac22567fa975f98e"),
				Arguments.of("", "8350e5a3e24c153df2275c9f80692773"),
				Arguments.of("a", "32ec01ec4a6dac72c0ab96fb34c0b5d1"));
	}

	public static Stream<Arguments> provideMD2CollisionExamples() {
		return Stream.of(Arguments.of("83850e5a3e24c153df2275c9f8069277", "8350e5a3e24c153df2275c9f8069277"),
				Arguments.of("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij",
						"abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghik"),
				Arguments.of("1234567890123456789012345678901234567890123456789012345678901234",
						"1234567890123456789012345678901234567890123456789012345678901235"));
	}

	@ParameterizedTest
	@MethodSource("provideMD2StringExamples")
	void custom_implemation_encodeAndDecodeStringWithGeneratedKey(String input, String expectedMD2Hash) {
		assertEquals(expectedMD2Hash, MD2HashCustomImplementation.computeMD2HashWithCustomImplementation(input));
	}

	@ParameterizedTest
	@MethodSource("provideMD2StringExamples")
	void standard_implemation_encodeAndDecodeStringWithGeneratedKey(String input, String expectedMD2Hash) {
		assertEquals(expectedMD2Hash, MD2HashHelpers.computeMD2HashWithStandardLibrary(input));
	}

	@ParameterizedTest
	@MethodSource("provideMD2CollisionExamples")
	void custom_implemation_collisions(String input1, String input2) {
		assertNotEquals(input1, input2);
		assertEquals(MD2HashCustomImplementation.computeMD2HashWithCustomImplementation(input1),
				MD2HashCustomImplementation.computeMD2HashWithCustomImplementation(input2));
	}

	@ParameterizedTest
	@MethodSource("provideMD2CollisionExamples")
	void standard_implemation_collisions(String input1, String input2) {
		assertNotEquals(input1, input2);
		assertEquals(MD2HashHelpers.computeMD2HashWithStandardLibrary(input1),
				MD2HashHelpers.computeMD2HashWithStandardLibrary(input2));
	}

	public static String toHexString(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static byte[] hashWithMD2(byte[] input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD2");
			return md.digest(input);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD2 algorithm not available", e);
		}
	}

	@Test
	void testCollisions() {
		String input1 = "1234567890123456789012345678901234567890123456789012345678901234";
		String input2 = "1234567890123456789012345678901234567890123456789012345678901235";

		byte[] hash1 = hashWithMD2(input1.getBytes());
		byte[] hash2 = hashWithMD2(input2.getBytes());

		System.out.println("Hash MD2 of input1: " + toHexString(hash1));
		System.out.println("Hash MD2 of input2: " + toHexString(hash2));
		System.out.println("Hashes are equal: " + Arrays.equals(hash1, hash2));

		String input3 = "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij";
		String input4 = "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghik";

		byte[] hash3 = hashWithMD2(input3.getBytes());
		byte[] hash4 = hashWithMD2(input4.getBytes());

		System.out.println("Hash MD2 of input3: " + toHexString(hash3));
		System.out.println("Hash MD2 of input4: " + toHexString(hash4));
		System.out.println("Hashes are equal: " + Arrays.equals(hash3, hash4));
	}
}
