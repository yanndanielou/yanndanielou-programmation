package authentification.loginandpassword;

import crypto.hash.Hash;

public class HashedPassword extends Password {

	public Hash hash;

	public HashedPassword(Hash hash) {
		this.hash = hash;
	}

}
