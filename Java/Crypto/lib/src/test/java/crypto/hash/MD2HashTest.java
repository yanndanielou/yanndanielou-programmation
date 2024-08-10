package crypto.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import org.junit.Ignore;
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
		assertEquals(expectedMD2Hash, HashHelpers.computeMD2HashWithStandardLibrary(input));
	}


	//@ParameterizedTest
	//@MethodSource("provideMD2CollisionExamples")
	@Ignore
	void custom_implemation_collisions(String input1, String input2) {
		assertNotEquals(input1, input2);
		assertEquals(MD2HashCustomImplementation.computeMD2HashWithCustomImplementation(input1),
				MD2HashCustomImplementation.computeMD2HashWithCustomImplementation(input2));
	}

	//@ParameterizedTest
	//@MethodSource("provideMD2CollisionExamples")
	@Ignore
	void standard_implemation_collisions(String input1, String input2) {
		assertNotEquals(input1, input2);
		assertEquals(HashHelpers.computeMD2HashWithStandardLibrary(input1),
				HashHelpers.computeMD2HashWithStandardLibrary(input2));
	}

}
