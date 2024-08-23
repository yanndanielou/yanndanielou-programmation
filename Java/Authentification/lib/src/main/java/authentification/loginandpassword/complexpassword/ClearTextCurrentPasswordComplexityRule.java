package authentification.loginandpassword.complexpassword;

public interface ClearTextCurrentPasswordComplexityRule extends PasswordComplexityRule {

	public PasswordComplexityError isNewPasswordAllowed(String newPasswordInClearText, String login);

}
