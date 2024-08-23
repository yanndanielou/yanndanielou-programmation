package authentification.loginandpassword.complexpassword;

public class MinimumPasswordLengthRule implements ClearTextCurrentPasswordComplexityRule {

	private int minimumPasswordLength;

	public MinimumPasswordLengthRule(int minimumPasswordLength) {
		this.minimumPasswordLength = minimumPasswordLength;
	}

	@Override
	public PasswordComplexityError isNewPasswordAllowed(String newPasswordInClearText, String login) {
		if (newPasswordInClearText.length() >= minimumPasswordLength) {
			return null;
		} else {
			return PasswordComplexityError.MINIMUM_PASSWORD_LENGTH;
		}
	}
}
