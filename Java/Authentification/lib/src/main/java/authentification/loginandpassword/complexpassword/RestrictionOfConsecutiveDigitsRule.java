package authentification.loginandpassword.complexpassword;

/**
 * QPWDLMTAJC. Numeric characters are not allowed next to each other in
 * passwords.
 * 
 */
public class RestrictionOfConsecutiveDigitsRule implements ClearTextCurrentPasswordComplexityRule {

	@Override
	public PasswordComplexityError isNewPasswordAllowed(String newPasswordInClearText, String login) {
		// TODO Auto-generated method stub
		return null;
	}

}
