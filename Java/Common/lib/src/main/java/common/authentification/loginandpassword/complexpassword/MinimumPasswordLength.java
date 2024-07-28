package common.authentification.loginandpassword.complexpassword;

import java.util.List;

import common.authentification.loginandpassword.Password;

public class MinimumPasswordLength implements PasswordComplexityRule {

	private int minimumPasswordLength;

	public MinimumPasswordLength(int minimumPasswordLength) {
		this.minimumPasswordLength = minimumPasswordLength;
	}

	@Override
	public PasswordComplexityError isNewPasswordAllowed(String newPasswordInClear, String login, List<Password> previousPasswords) {
		if (newPasswordInClear.length() >= minimumPasswordLength) {
			return null;
		} else {
			return PasswordComplexityError.MAXIMUM_PASSWORD_LENGTH;
		}
	}
}
