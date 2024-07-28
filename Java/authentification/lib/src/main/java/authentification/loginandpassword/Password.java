package authentification.loginandpassword;

import java.time.Instant;

public class Password {

	private Instant creationInstant = Instant.now();
	public String clearText;
	
	public Password(String clearText) {
		this.clearText = clearText;
		//creationInstant = new Instant
	}
}
