package authentification.loginandpassword;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class PasswordHistory {
	Map<Instant, ClearTextPassword> previousPasswords = new HashMap<Instant, ClearTextPassword>();

	
	public PasswordHistory(int length) {
	}

}
