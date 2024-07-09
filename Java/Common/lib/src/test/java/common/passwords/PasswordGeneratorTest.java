package common.passwords;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PasswordGeneratorTest {
	static final Logger LOGGER = LogManager.getLogger(PasswordGeneratorTest.class);

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
