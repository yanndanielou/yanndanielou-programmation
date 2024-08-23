package authentification.loginandpassword;

public class ClearTextPassword extends Password {

	public String clearTextPassword;
	
	public ClearTextPassword(String clearText) {
		this.clearTextPassword = clearText;
	}
}
