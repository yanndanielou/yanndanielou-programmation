package authentification.passwords.common_passwords;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import authentification.passwords.PasswordsConstants;
import crypto.hash.Hash;
import crypto.hash.Hash.HashType;
import crypto.hash.HashHelpers;

public class CommonPasswordsManager {

	private static final Logger LOGGER = LogManager.getLogger(CommonPasswordsManager.class);

	private List<String> commonPasswords = new ArrayList<>();
	private List<Hash> commonPasswordsSHA256 = new ArrayList<>();

	public CommonPasswordsManager() {
		this(PasswordsConstants.SECLIST_COMMON_CREDENTIALS_MOST_COMMIN_PASSWORDS_TOP_1000000_FILE_PATH);
	}

	public CommonPasswordsManager(String filePath) {
		load(filePath);
	}

	public boolean isKnownPassword(String password) {
		return true;
	}

	public boolean isKnownPasswordAmongTopx(String password, int top) {
		return true;
	}

	private void load(String filePath) {

		Scanner scanner;
		try {
			File source = new File(filePath);
			scanner = new Scanner(source);
			// It holds true till there is single element
			// left in the object with usage of hasNext()
			// method
			while (scanner.hasNextLine()) {
				// Printing the content of file
				String line = scanner.nextLine();
				commonPasswords.add(line);
			}
			LOGGER.info(commonPasswords.size() + " common passwords found:");
			scanner.close();
		} catch (FileNotFoundException e) {
			LOGGER.fatal("Could not parse passwords file:" + filePath);
			e.printStackTrace();
		}
		computeCommonPasswordsHashs();
	}

	private void computeCommonPasswordsHashs() {
		LOGGER.info("Compute common passwords hashs");

		commonPasswords.forEach(e -> {
			commonPasswordsSHA256.add(HashHelpers.computeHashStandardLibrary(e, HashType.SHA2_256));
		});
		LOGGER.info(commonPasswordsSHA256.size() + " common passwords hashs computed");
	}
}
