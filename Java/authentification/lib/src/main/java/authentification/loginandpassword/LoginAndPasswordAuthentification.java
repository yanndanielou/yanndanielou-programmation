package authentification.loginandpassword;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import common.gson.adapter.InstantAdapter;

public class LoginAndPasswordAuthentification {

	String login;
	Password currentPassword;
	PasswordHistory passwordHistory;

	public LoginAndPasswordAuthentification(String login, String passwordInClear) {
		changeLogin(login);
		currentPassword = new Password(passwordInClear);
	}

	private boolean changeLogin(String newLogin) {
		this.login = newLogin;
		return true;
	}

	public void save(String outputFilePath) {

		/*
		 * FileOutputStream fileOutputStream; try { fileOutputStream = new
		 * FileOutputStream(outputFilePath);
		 * 
		 * ObjectOutputStream objectOutputStream;
		 * 
		 * objectOutputStream = new ObjectOutputStream(fileOutputStream);
		 * 
		 * objectOutputStream.writeObject(this); objectOutputStream.flush();
		 * objectOutputStream.close(); } catch (FileNotFoundException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */
		try (Writer writer = new FileWriter(outputFilePath)) {
			// Gson gson = new
			// GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
			Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Instant.class, new InstantAdapter())
					.create();
			String jsonContent = gson.toJson(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
