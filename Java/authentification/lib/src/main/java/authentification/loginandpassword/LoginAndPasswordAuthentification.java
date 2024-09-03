package authentification.loginandpassword;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;

import javax.crypto.SecretKey;

import com.baeldung.aes.AESUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import common.gson.adapter.InstantAdapter;
import crypto.hash.Hash;
import crypto.hash.Hash.HashType;
import crypto.hash.HashHelpers;

public class LoginAndPasswordAuthentification {

	String login;
	HashedPassword currentPassword;
	PasswordHistory passwordHistory;
	private String outputFilePath;

	public LoginAndPasswordAuthentification(String outputFilePath, String login, String passwordInClear) {
		this.outputFilePath = outputFilePath;
		changeLogin(login);
		changePassword(null, passwordInClear);
		save(passwordInClear);
	}

	private boolean changeLogin(String newLogin) {
		this.login = newLogin;
		return true;
	}

	public boolean changePassword(String previousPasswordClearText, String newPasswordClearText) {
		Hash newPasswordHash = HashHelpers.computeHashStandardLibrary(newPasswordClearText, HashType.SHA3_256);
		currentPassword = new HashedPassword(newPasswordHash);

		return true;
	}

	public boolean save(String currentPasswordInClear) {

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
			String salt = "salt";
			SecretKey keyFromPassword = AESUtil.getKeyFromPassword(currentPasswordInClear, salt);
			// AESUtil.encryptPasswordBased(salt, keyFromPassword, null);
			return true;
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return false;
		}

	}

}
