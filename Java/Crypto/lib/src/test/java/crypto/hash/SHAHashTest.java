package crypto.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.awt.Color;
import java.util.stream.Stream;

import org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi.SHA3_256;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import crypto.hash.Hash.HashType;

public class SHAHashTest {

	protected static String formatHash(String rawHash) {

		String formattedHash = rawHash.toLowerCase().replaceAll("\\s+", "");
		return formattedHash;
	}

	public static Stream<Arguments> provideSHA512StringExamples() {
		return Stream.of(
				// Arguments.of("Wikipedia, l'encyclopedie libre et gratuite",
				// "c9b3cbbeb539034aac22567fa975f98e"),
				Arguments.of("",
						"cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e"),
				Arguments.of("abc",
						"9b71d224bd62f3785d96d46ad3ea3d73319bfbc230b0a8231e7b88de4cecb1b925a7df5f1e78d41a0e1c5ba8fa7d6b3976b97e8d6f6e450730ad35c653a86c89"),
				Arguments.of("hello",
						"9b71d224bd62f3785d96d46ad3ea3d73319bfbc230b0a8231e7b88de4cecb1b925a7df5f1e78d41a0e1c5ba8fa7d6b3976b97e8d6f6e450730ad35c653a86c89"),
				Arguments.of("world",
						"11853df40f4b2b919d3815f64792e58d08663767a494bcbb38c0b2389d9140bbb170281b4a847be7757bde12c9cd0054ce3652d0ad3a1a0c92babb69798246ee"),
				Arguments.of("The quick brown fox jumps over the lazy dog",
						"07E547D9 586F6A73 F73FBAC0 435ED769 51218FB7 D0C8D788 A309D785 436BBB64   2E93A252 A954F239 12547D1E 8A3B5ED6 E1BFD709 7821233F A0538F3D B854FEE6"),
				Arguments.of(
						"abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmnoijklmnopjklmnopqklmnopqrl\r\n"
								+ "mnopqrsmnopqrstnopqrstu",
						"8E959B75 DAE313DA 8CF4F728 14FC143F\r\n"
								+ "8F7779C6 EB9F7FA1 7299AEAD B6889018 501D289E 4900F7E4\r\n"
								+ "331B99DE C4B5433A C7D329EE B6DD2654 5E96E55B 874BE909"));
	}

	public static Stream<Arguments> provideSHA256StringExamples() {
		return Stream.of(
				// Arguments.of("Wikipedia, l'encyclopedie libre et gratuite",
				// "c9b3cbbeb539034aac22567fa975f98e"),
				Arguments.of("", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"),
				Arguments.of("sha256 this string", "1af1dfa857bf1d8814fe1af8983c18080019922e557f15a8a0d3db739d77aacb"),
				Arguments.of("Hello World", "a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146e"),
				Arguments.of("hello", "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"));
	}

	public static Stream<Arguments> provideSHA3_256StringExamples() {
		return Stream.of(
				// Arguments.of("Wikipedia, l'encyclopedie libre et gratuite",
				// "c9b3cbbeb539034aac22567fa975f98e"),
				Arguments.of("", "a7ffc6f8bf1ed76651c14756a061d662f580ff4de43b49fa82d80a4b80f8434a"),
				Arguments.of("Hello World", "e167f68d6563d75bb25f3aa49c29ef612d41352dc00606de7cbd630bb2665f51"));
	}

	@Nested
	class CustomImplementation {

		// @ParameterizedTest
		// @MethodSource("com.domain.ColorTest#provideColors")

		@ParameterizedTest
		@MethodSource("crypto.hash.SHAHashTest#provideSHA512StringExamples")
		void sha512(String input, String expectedMD2Hash) {
			assertEquals(formatHash(expectedMD2Hash), SHA512CustomImplementation.computeSHA512Hash(input).getHashUTF8());
		}
	}

	@Nested
	class StandardImplementation {

		// @ParameterizedTest
		// @MethodSource("com.domain.ColorTest#provideColors")

		@ParameterizedTest
		@MethodSource("crypto.hash.SHAHashTest#provideSHA512StringExamples")
		void encodeAndDecodeSHA512StringWithGeneratedKey(String input, String expectedMD2Hash) {
			assertEquals(formatHash(expectedMD2Hash), HashHelpers.computeHashStandardLibrary(input, HashType.SHA_512).getHashUTF8());
		}
		// @ParameterizedTest
		// @MethodSource("com.domain.ColorTest#provideColors")

		@ParameterizedTest
		@MethodSource("crypto.hash.SHAHashTest#provideSHA256StringExamples")
		void encodeAndDecodeSHA256StringWithGeneratedKey(String input, String expectedHash) {
			assertEquals(formatHash(expectedHash), HashHelpers.computeHashStandardLibrary(input, HashType.SHA2_256).getHashUTF8());
		}

		@ParameterizedTest
		@MethodSource("crypto.hash.SHAHashTest#provideSHA3_256StringExamples")
		void encodeAndDecodeSHA3_256StringWithGeneratedKey(String input, String expectedHash) {
			assertEquals(formatHash(expectedHash),
					HashHelpers.computeHashStandardLibrary(input, HashType.SHA3_256).getHashUTF8());
		}
	}
}
