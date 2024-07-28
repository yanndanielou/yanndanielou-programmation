package authentification.loginandpassword;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class PasswordHistory {
	Map<Instant, Password> previousPasswords = new HashMap<Instant, Password>();

	
	public PasswordHistory(int length) {
	}

}
