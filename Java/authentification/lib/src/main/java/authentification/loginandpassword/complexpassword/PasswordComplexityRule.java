package authentification.loginandpassword.complexpassword;

import java.util.List;

import authentification.loginandpassword.Password;

public interface PasswordComplexityRule {

	public enum PasswordComplexityError {
		MINIMUM_PASSWORD_LENGTH, MAXIMUM_PASSWORD_LENGTH, UNKNOWN_REASON;
	}

	public PasswordComplexityError isNewPasswordAllowed(String newPasswordInClear, String login,
			List<Password> previousPasswordsFromOldestToRecent);

}
