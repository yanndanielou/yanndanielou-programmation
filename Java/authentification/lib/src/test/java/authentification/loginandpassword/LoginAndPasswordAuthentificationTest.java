package authentification.loginandpassword;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import common.color.StringToColorConversion;

public class LoginAndPasswordAuthentificationTest {

	@Test
	void createAndSaveAndLoadPassword() {
		LoginAndPasswordAuthentification loginAndPasswordAuthentification = new LoginAndPasswordAuthentification("lib/src/test/resources/authentification/loginandpassword/createAndSaveAndLoadPassword_test.json", "Yann", "Afeakl1.");
		
	}
	/*
	@Nested
	public class CreateAndSaveAndLoadPassword {
		@Nested
		public class BadUsages {
			

			@Test
			void invalidColorName() {
			//	assertNull(StringToColorConversion.convertStringToColor("not existing color"));
			}
		}

		@Nested
		public class CorrectUsages {
			private static Stream<Arguments> provideColorsByName() {

				// @formatter:off
				return Stream.of(
						Arguments.of(Color.blue, "blue"),
						Arguments.of(Color.black, "black"),
						Arguments.of(Color.red, "red"),
						Arguments.of(Color.YELLOW, "YELLOW"),
						Arguments.of(Color.yellow, "yellow")
						);
				// @formatter:on	
			}

			@ParameterizedTest
			@MethodSource("provideColorsByName")
			void colorName(Color colorAsColor, String colorAsString) {
				assertEquals(colorAsColor, StringToColorConversion.convertStringToColor(colorAsString));
			}

			@Test
			void byRBG() {
				assertEquals(Color.gray, StringToColorConversion.convertStringToColor("" + Color.gray.getRGB()));
			}
		}
	}
*/
}
