package authentification.loginandpassword;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class PasswordHistory {
	Map<Instant, HashedPassword> previousPasswords = new HashMap<Instant, HashedPassword>();

	
	public PasswordHistory(int length) {
	}

}
