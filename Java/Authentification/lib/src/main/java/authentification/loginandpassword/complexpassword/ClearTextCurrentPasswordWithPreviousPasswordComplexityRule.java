package authentification.loginandpassword.complexpassword;

public interface ClearTextCurrentPasswordWithPreviousPasswordComplexityRule extends PasswordComplexityRule {

	public PasswordComplexityError isNewPasswordAllowed(String newPasswordInClearText, String login,
			String previousPasswordsInClearText);

}
