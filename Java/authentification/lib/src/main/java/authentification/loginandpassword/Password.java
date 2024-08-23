package authentification.loginandpassword;

import java.time.Instant;

public abstract class Password {

	private Instant creationInstant = Instant.now();

	public Instant getCreationInstant() {
		return creationInstant;
	}
}
