package common.authentification.passwords.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.authentification.passwords.PasswordsConstants;

public class PasswordValidator {

	private static final Logger LOGGER = LogManager.getLogger(PasswordValidator.class);
	private static final boolean SPECIAL_CHAR_NEEDED = true;
	private static final int MIN_LENGTH_INT = 8;
	private static final String MIN_LENGTH_AS_STR = "" + MIN_LENGTH_INT;
	private static final String MAX_LENGTH = "";

	/**
	 * https://www.baeldung.com/java-regex-password-validation
	 */
	private static boolean regularExpressionBasedPasswordValidation(String password) {

		// String regExpn =
		String regExpn = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[" + PasswordsConstants.SPECIAL_CHARACTERS_STRING
				+ "])(?=\\S+$).{" + MIN_LENGTH_AS_STR + "," + MAX_LENGTH + "}$";
		// Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
		Pattern pattern = Pattern.compile(regExpn);
		Matcher matcher = pattern.matcher(password);
		boolean matches = matcher.matches();
		LOGGER.info(() -> password + " matches regex based validation " + regExpn + ": " + matches);
		return matches;

	}

	/**
	 * https://www.baeldung.com/java-regex-password-validation
	 */
	private static boolean dynamicPasswordValidation(String password) {

		if (password != null) {

			String ONE_DIGIT_REGEX_PART = "(?=.*[0-9])";
			String LOWER_CASE_REGEX_PART = "(?=.*[a-z])";
			String UPPER_CASE_REGEX_PART = "(?=.*[A-Z])";
			String SPECIAL_CHAR = SPECIAL_CHAR_NEEDED ? "(?=.*[" + PasswordsConstants.SPECIAL_CHARACTERS_STRING + "])"
					: "";

			String NO_SPACE_REGEX_PART = "(?=\\S+$)";

			String MIN_MAX_CHAR = ".{" + MIN_LENGTH_AS_STR + "," + MAX_LENGTH + "}";
			String PATTERN = ONE_DIGIT_REGEX_PART + LOWER_CASE_REGEX_PART + UPPER_CASE_REGEX_PART + SPECIAL_CHAR
					+ NO_SPACE_REGEX_PART + MIN_MAX_CHAR;

			boolean matches = password.matches(PATTERN);
			LOGGER.info(() -> password + " matches dynamic validation " + PATTERN + ": " + matches);
			return matches;
		}
		return false;
	}

	public static boolean isPasswordComplex(String password) {

		// public static final String SPECIAL_CHARACTERS_STRING = "!@#$%^&*()_+";

		return password != null
				&& (regularExpressionBasedPasswordValidation(password) && dynamicPasswordValidation(password));
	}
}
