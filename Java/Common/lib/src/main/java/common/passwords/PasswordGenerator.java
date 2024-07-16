package common.passwords;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;

public class PasswordGenerator implements PasswordsConstants{

	private static final Logger LOGGER = LogManager.getLogger(PasswordGenerator.class);


	public static final CharacterData SPECIAL_CHARACTERS_CHARACTER_DATA = new CharacterData() {
		public String getErrorCode() {
			return "Error";
		}

		public String getCharacters() {
			return SPECIAL_CHARACTERS_STRING;
		}
	};

	public static String generateComplexPassword(int length) {
		int remainingLength = length;

		int lowerCasesCount = length / 4;
		remainingLength -= lowerCasesCount;

		int upperCasesCount = length / 3;
		remainingLength -= upperCasesCount;

		int digitsCount = remainingLength / 2;
		remainingLength -= digitsCount;

		int specialCharactersCount = remainingLength;

		LOGGER.info(() -> "To generate complex password with length:" + length + ". Rules will be lowerCasesCount:"
				+ lowerCasesCount + ", upperCasesCount:" + upperCasesCount + ", digitsCount:" + digitsCount
				+ ", specialCharactersCount:" + specialCharactersCount);

		return generatePassayPassword(length, lowerCasesCount, upperCasesCount, digitsCount, specialCharactersCount);
	}

	public static String generatePassayPassword(int length, int lowerCasesCount, int upperCasesCount, int digitsCount,
			int specialCharactersCount) {

		List<CharacterRule> characterRules = new ArrayList();

		if (lowerCasesCount > 0) {
			CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
			CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
			lowerCaseRule.setNumberOfCharacters(lowerCasesCount);
			characterRules.add(lowerCaseRule);
		}

		if (upperCasesCount > 0) {
			CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
			CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
			upperCaseRule.setNumberOfCharacters(upperCasesCount);
			characterRules.add(upperCaseRule);
		}

		if (digitsCount > 0) {
			CharacterData digitChars = EnglishCharacterData.Digit;
			CharacterRule digitRule = new CharacterRule(digitChars);
			digitRule.setNumberOfCharacters(digitsCount);
			characterRules.add(digitRule);
		}

		if (specialCharactersCount > 0) {
			CharacterRule splCharRule = new CharacterRule(SPECIAL_CHARACTERS_CHARACTER_DATA);
			splCharRule.setNumberOfCharacters(specialCharactersCount);
			characterRules.add(splCharRule);
		}

		org.passay.PasswordGenerator passayPasswordGenerator = new org.passay.PasswordGenerator();
		String generatedPassword = passayPasswordGenerator.generatePassword(length, characterRules);
		LOGGER.info(() -> "Password generated with constraints. length:" + length + ", lowerCasesCount:"
				+ lowerCasesCount + ", upperCasesCount:" + upperCasesCount + ", digitsCount:" + digitsCount
				+ ", specialCharactersCount:" + specialCharactersCount + ". GeneratedPassword:" + generatedPassword);
		return generatedPassword;
	}
	
}
