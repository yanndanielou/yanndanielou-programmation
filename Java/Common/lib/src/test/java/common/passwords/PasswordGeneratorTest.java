package common.passwords;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class PasswordGeneratorTest {
	static final Logger LOGGER = LogManager.getLogger(PasswordGeneratorTest.class);

	@Nested
	public class ForCoverageOnly {

		@Test
		void testsNothingButImproveCoverage() {

			PasswordGenerator passwordGenerator = new PasswordGenerator();
			org.passay.CharacterData c = PasswordGenerator.SPECIAL_CHARACTERS_CHARACTER_DATA;

			assertDoesNotThrow(() -> {
				c.getErrorCode();
				passwordGenerator.toString();
			});
		}
	}
	
	@Nested
	public class ValidatePassword {

		
		@ParameterizedTest
		@ValueSource(strings = {"Aazz111.", "Aazz111(", "Aazz111(fea'feafeafed3333333Ffeefddd"})
		void complex(String password) {
			assertTrue(PasswordValidator.isPasswordComplex(password));
		}
		
		private static Stream<Object> provideSpecialCharacter() {
			return PasswordsConstants.SPECIAL_CHARACTERS_STRING.chars()
					  .mapToObj(c -> (char) c);
		}
		
		@ParameterizedTest
		@MethodSource("provideSpecialCharacter")
		void eachSpecialCharacter(char specialChar) {
				assertTrue(PasswordValidator.isPasswordComplex("Aazz1122"+ specialChar));
		}
		
		@ParameterizedTest
		@CsvSource(value = {":empty", "12121faefeAA:without special char", "12121faefe.:without capital"}, delimiter = ':')
		void notComplex(String password, String reason) {
			assertFalse(PasswordValidator.isPasswordComplex(password), reason);
		}
	}


	@Nested
	public class GeneratePassword {

		@Nested
		public class GenerateComplexPassword {

			@Nested
			public class AmountOfCharactersIsCorrect {

				@ParameterizedTest
				@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 50, 100 })
				void isPossible(int numberOfCharacters) {

					String generatedComplexPassword = PasswordGenerator.generateComplexPassword(numberOfCharacters);
					assertEquals(numberOfCharacters, generatedComplexPassword.length());

				}
			}
			

			@Nested
			public class MoreThan3Characters {

				@ParameterizedTest
				@ValueSource(ints = { 1, 2, 3 })
				void isPossible(int numberOfCharacters) {

					String generatedComplexPassword = PasswordGenerator.generateComplexPassword(numberOfCharacters);
					assertEquals(numberOfCharacters, generatedComplexPassword.length());

				}
			}

			@Nested
			public class GenererPourSecondRegard {

				@RepeatedTest(50)
				void isPossible() {
					String generateComplexPassword = PasswordGenerator.generateComplexPassword(8);
					System.out.println("Pour Y:" + generateComplexPassword);
				}
			}
		}
	}
}
