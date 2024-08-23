package authentification.loginandpassword.complexpassword;

import java.util.List;

import authentification.loginandpassword.ClearTextPassword;

/**
 * Cela offre une sécurité supplémentaire en empêchant les utilisateurs de
 * spécifier des mots de passe qui sont trop longs et doivent être enregistrés
 * quelque part car ils ne peuvent pas être facilement mémoris
 */
public class MaximumPasswordLengthRule implements ClearTextCurrentPasswordComplexityRule {

	private int maximumPasswordLength;

	public MaximumPasswordLengthRule(int maximumPasswordLength) {
		this.maximumPasswordLength = maximumPasswordLength;
	}

	@Override
	public PasswordComplexityError isNewPasswordAllowed(String newPasswordInClearText, String login) {
		return newPasswordInClearText.length() > maximumPasswordLength ? PasswordComplexityError.MAXIMUM_PASSWORD_LENGTH
				: null;
	}
}
