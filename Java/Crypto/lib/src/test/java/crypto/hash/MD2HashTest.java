package crypto.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MD2HashTest {
	public static Stream<Arguments> provideMD2StringExamples() {

	// @formatter:off
	return Stream.of(
			Arguments.of("Wikipedia, l'encyclopedie libre et gratuite", "c9b3cbbeb539034aac22567fa975f98e"),
			Arguments.of("", "8350e5a3e24c153df2275c9f80692773"),
			Arguments.of("a", "32ec01ec4a6dac72c0ab96fb34c0b5d1")
			);
	// @formatter:on	
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
}
