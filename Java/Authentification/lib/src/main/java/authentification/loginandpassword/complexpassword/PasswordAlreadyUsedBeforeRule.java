package authentification.loginandpassword.complexpassword;

import java.util.List;

import authentification.loginandpassword.HashedPassword;
import crypto.hash.Hash;

/**
 * QPWDRQDDIF
 */
public class PasswordAlreadyUsedBeforeRule implements HashPasswordComplexityRule {

	@Override
	public PasswordComplexityError isNewPasswordAllowed(Hash newPasswordHash, String login,
			List<HashedPassword> previousPasswordsHashsFromOldestToRecent) {
		// TODO Auto-generated method stub
		return null;
	}
}
