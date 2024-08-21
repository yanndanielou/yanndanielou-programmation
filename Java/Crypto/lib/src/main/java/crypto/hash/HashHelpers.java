package crypto.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.hash.Hashing;

import common.string.utils.StringUtils;
import crypto.hash.Hash.HashType;

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

	private static Hash computeMD2HashWithStandardLibrary(String input) {
		byte[] inputAsBytes = input.getBytes();
		byte[] hashBytes = MD2HashToBytesArray(inputAsBytes);
		return new Hash(StringUtils.transformBytesArrayToString(hashBytes), HashType.MD2);
	}

	private static Hash computeSHA512WithStandardLibrary(String input) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		final byte[] hashbytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
		String sha512Hex = StringUtils.transformBytesArrayToString(hashbytes);
		String withGoogle = Hashing.sha512().hashString(input, StandardCharsets.UTF_8).toString();
		boolean areEquals = sha512Hex.equals(withGoogle);
		if (!areEquals) {
			throw new RuntimeException("Wrong computation of SHA 512 for " + input);
		}
		return new Hash(withGoogle, HashType.SHA_512);
	}

	private static Hash computeSHA256WithStandardLibrary(String input) {
		String hash = Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
		return new Hash(hash, HashType.SHA2_256);
	}

	public static Hash computeHashStandardLibrary(String input, Hash.HashType hashType) {
		switch (hashType) {
		case MD2:
			return computeMD2HashWithStandardLibrary(input);
		case SHA2_256:
			return computeSHA256WithStandardLibrary(input);
		case SHA3_256:
			return computeSHA3_256WithStandardLibrary(input);
		case SHA_512:
			return computeSHA512WithStandardLibrary(input);
		default:
			break;
		}
		return null;
	}

	private static Hash computeSHA3_256WithStandardLibrary(String input) {

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA3-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		final byte[] hashbytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
		String sha3Hex = StringUtils.transformBytesArrayToString(hashbytes);
		return new Hash(sha3Hex, HashType.SHA3_256);
	}

}
