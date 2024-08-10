package crypto.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.hash.Hashing;

import common.string.utils.StringUtils;

/***
 * Custom implementation of MD2 (Message Digest 2) Hash
 */
public class HashHelpers {
	private static final Logger LOGGER = LogManager.getLogger(HashHelpers.class);

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

	public static String computeSHA512WithStandardLibrary(String input) {
		return Hashing.sha512().hashString(input, StandardCharsets.UTF_8).toString();
	}

	public static String computeSHA256WithStandardLibrary(String input) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA3-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		final byte[] hashbytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
		String sha3Hex = StringUtils.transformBytesArrayToString(hashbytes);
		return sha3Hex;
	}

}
