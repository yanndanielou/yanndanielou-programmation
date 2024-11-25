package secondregard.application;

import java.util.ArrayList;
import java.util.List;

import authentification.passwords.generation.PasswordGenerator;

public class GenererMotDePasseSecondRegard {

	public static void main(String[] args) {
		final int NUMBER_OF_PASSWORDS_TO_GENERATE = 10;

		List<String> passwordsGenerated = new ArrayList<String>();
		for (int i = 0; i < NUMBER_OF_PASSWORDS_TO_GENERATE; i++) {
			passwordsGenerated.add(PasswordGenerator.generateComplexPassword(8));
		}

		passwordsGenerated.forEach(e -> System.out.println(e));
	}

}
