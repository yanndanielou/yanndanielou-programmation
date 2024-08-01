package crypto.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import common.string.utils.StringUtils;

/***
 * Custom implementation of MD2 (Message Digest 2) Hash
 */
public class MD2HashHelpers {

	private static byte[] MD2HashToBytesArray(byte[] input) {
		try {
			// Créer une instance de MessageDigest avec MD2
			MessageDigest md = MessageDigest.getInstance("MD2");

			// Mettre à jour le MessageDigest avec les bytes du message d'entrée
			md.update(input);

			// Effectuer le hachage
			byte[] digest = md.digest();
			return digest;
		} catch (NoSuchAlgorithmException e) {
			// Cette exception est levée si MD2 n'est pas disponible
			throw new RuntimeException("MD2 algorithm not available", e);
		}
	}

	public static String computeMD2HashWithStandardLibrary(String input) {
		byte[] inputAsBytes = input.getBytes();
		byte[] hashBytes = MD2HashToBytesArray(inputAsBytes);
		return StringUtils.transformBytesArrayToString(hashBytes);
	}

}
