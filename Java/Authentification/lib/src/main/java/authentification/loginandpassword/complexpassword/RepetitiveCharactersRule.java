package authentification.loginandpassword.complexpassword;

public class RepetitiveCharactersRule implements ClearTextCurrentPasswordComplexityRule {

	private QPWDLMTREP_LEVEL level;

	/**
	 * https://www.ibm.com/docs/fr/i/7.5?topic=passwords-restriction-repeated-characters-qpwdlmtrep
	 */
	public enum QPWDLMTREP_LEVEL {
		/** The same characters can be used more than once in a password. */
		QPWDLMTREP_0,
		/**
		 * The same character cannot be used more than once in a password.
		 */
		QPWDLMTREP_1,
		/**
		 * The same character cannot be used consecutively in a password.
		 */
		QPWDLMTREP_2;
	}

	public RepetitiveCharactersRule(QPWDLMTREP_LEVEL level) {
		this.level = level;
	}

	@Override
	public PasswordComplexityError isNewPasswordAllowed(String newPasswordInClearText, String login) {
		// TODO Auto-generated method stub
		return null;
	}
}
