package crypto.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.awt.Color;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SHAHashTest {
	public static Stream<Arguments> provideSHA512StringExamples() {
		return Stream.of(
				// Arguments.of("Wikipedia, l'encyclopedie libre et gratuite",
				// "c9b3cbbeb539034aac22567fa975f98e"),
				Arguments.of("",
						"cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e"),
				Arguments.of("hello",
						"9b71d224bd62f3785d96d46ad3ea3d73319bfbc230b0a8231e7b88de4cecb1b925a7df5f1e78d41a0e1c5ba8fa7d6b3976b97e8d6f6e450730ad35c653a86c89"),
				Arguments.of("world",
						"11853df40f4b2b919d3815f64792e58d08663767a494bcbb38c0b2389d9140bbb170281b4a847be7757bde12c9cd0054ce3652d0ad3a1a0c92babb69798246ee"),
				Arguments.of(
						"abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmnoijklmnopjklmnopqklmnopqrl\r\n"
								+ "mnopqrsmnopqrstnopqrstu",
						"8E959B75 DAE313DA 8CF4F728 14FC143F\r\n"
								+ "8F7779C6 EB9F7FA1 7299AEAD B6889018 501D289E 4900F7E4\r\n"
								+ "331B99DE C4B5433A C7D329EE B6DD2654 5E96E55B 874BE909"));
	}

	@Nested
	class CustomImplementation {

		// @ParameterizedTest
		// @MethodSource("com.domain.ColorTest#provideColors")

		@ParameterizedTest
		@MethodSource("crypto.hash.SHAHashTest#provideSHA512StringExamples")
		void encodeAndDecodeStringWithGeneratedKey(String input, String expectedMD2Hash) {
			assertEquals(expectedMD2Hash, SHA512CustomImplementation.computeSHA512Hash(input));
		}
	}

	@Nested
	class StandardImplementation {

		// @ParameterizedTest
		// @MethodSource("com.domain.ColorTest#provideColors")

		@ParameterizedTest
		@MethodSource("crypto.hash.SHAHashTest#provideSHA512StringExamples")
		void encodeAndDecodeStringWithGeneratedKey(String input, String expectedMD2Hash) {
			assertEquals(expectedMD2Hash, HashHelpers.computeSHA512WithStandardLibrary(input));
		}
	}
}
