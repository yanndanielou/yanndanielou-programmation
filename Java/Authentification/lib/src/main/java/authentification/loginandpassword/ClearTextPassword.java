package authentification.loginandpassword;

@Deprecated
public class ClearTextPassword extends Password {

	public String clearTextPassword;
	
	public ClearTextPassword(String clearText) {
		this.clearTextPassword = clearText;
	}
}
