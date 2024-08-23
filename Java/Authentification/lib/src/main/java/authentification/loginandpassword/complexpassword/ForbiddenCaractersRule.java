package authentification.loginandpassword.complexpassword;

import java.util.List;

/**
 * QPWDLMTCHR provides additional security by preventing users from using
 * specific characters, such as vowels, in a password. Restricting vowels
 * prevents users from forming actual words for their passwords.
 */
public class ForbiddenCaractersRule implements ClearTextCurrentPasswordComplexityRule {

	List<Character> forbiddenCharacters;
	
	@Override
	public PasswordComplexityError isNewPasswordAllowed(String newPasswordInClearText, String login) {
		return PasswordComplexityError.PASSWORD_CONTAINS_RESTRICTED_CARACTERS;
	}
}
