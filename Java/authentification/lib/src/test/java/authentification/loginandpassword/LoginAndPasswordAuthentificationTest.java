package authentification.loginandpassword;

import org.junit.jupiter.api.Test;

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
