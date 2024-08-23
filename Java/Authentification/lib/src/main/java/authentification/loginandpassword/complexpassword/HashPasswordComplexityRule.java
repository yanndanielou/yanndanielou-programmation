package authentification.loginandpassword.complexpassword;

import java.util.List;

import authentification.loginandpassword.HashedPassword;
import crypto.hash.Hash;

public interface HashPasswordComplexityRule extends PasswordComplexityRule {

	public PasswordComplexityError isNewPasswordAllowed(Hash newPasswordHash, String login,
			List<HashedPassword> previousPasswordsHashsFromOldestToRecent);

}
