package crypto.aes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.cartesian.CartesianTest;
import org.junitpioneer.jupiter.cartesian.CartesianTest.Values;

public class CertificateBasedCryptoTest {

	@Nested
	public class EncodePayLoadWithPublicKey {
		// https://medium.com/javarevisited/cryptography-sign-payload-encrypt-a-plain-text-password-and-decrypt-it-61a2d8a09e73
		@Test
		void signatureIsValid() throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
				BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, SignatureException {
			String payload = "test-payload";
			// Generate key pair
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);

			KeyPair keyPair = keyGen.generateKeyPair();
			PrivateKey privateKey = keyPair.getPrivate();
			System.out.println("privateKey:" + privateKey);
			PublicKey publicKey = keyPair.getPublic();
			System.out.println("publicKey:" + publicKey);

			// Sign the payload
			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(privateKey);
			signature.update(payload.getBytes());
			byte[] signedPayload = signature.sign();

			// Verify the signature
			Signature verifier = Signature.getInstance("SHA256withRSA");
			verifier.initVerify(publicKey);
			verifier.update(payload.getBytes());
			assertTrue(verifier.verify(signedPayload));

		}
	}

//https://medium.com/javarevisited/cryptography-sign-payload-encrypt-a-plain-text-password-and-decrypt-it-61a2d8a09e73
	@Nested
	public class EncryptAndDecryptStringUsingRSAEncryption {

		@CartesianTest
		void signatureIsValid(
				@Values(strings = { "RSA/ECB/PKCS1Padding", "AES/CBC/NoPadding", "AES/CBC/PKCS5Padding",
						"AES/ECB/NoPadding", "AES/ECB/PKCS5Padding", "DES/CBC/NoPadding", "DES/CBC/PKCS5Padding",
						"DES/ECB/NoPadding", "DES/ECB/PKCS5Padding", "DESede/CBC/NoPadding", "DESede/CBC/PKCS5Padding",
						"DESede/ECB/NoPadding", "DESede/ECB/PKCS5Padding", "RSA/ECB/PKCS1Padding",
						"RSA/ECB/OAEPWithSHA-1AndMGF1Padding", "RSA/ECB/OAEPWithSHA-256AndMGF1Padding" }) String cipherTransformation)
				throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException,
				IOException, NoSuchProviderException, NoSuchPaddingException, UnrecoverableKeyException,
				InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

			// Security.addProvider(new BouncyCastleProvider());
			/*
			 * // Load keystore and truststore KeyStore keyStore =
			 * KeyStore.getInstance("JKS"); keyStore.load(new
			 * FileInputStream("mykeystore.jks"), "keystore_password".toCharArray());
			 * 
			 * KeyStore trustStore = KeyStore.getInstance("JKS"); trustStore.load(new
			 * FileInputStream("mytruststore.jks"), "truststore_password".toCharArray());
			 * 
			 * // Retrieve private and public keys from keystore PrivateKey privateKey =
			 * (PrivateKey) keyStore.getKey("mykey", "key_password".toCharArray());
			 * PublicKey publicKey = trustStore.getCertificate("mykey").getPublicKey();
			 */

			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);

			KeyPair keyPair = keyGen.generateKeyPair();

			PrivateKey privateKey = keyPair.getPrivate();
			System.out.println("privateKey:" + privateKey);
			PublicKey publicKey = keyPair.getPublic();
			System.out.println("publicKey:" + publicKey);

			// Encrypt data using public key

			System.out.println("cipherTransformation:" + cipherTransformation);
			// Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
			Cipher cipher = Cipher.getInstance(cipherTransformation);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			String originalStringToEncrypt = "Hello, world!";
			byte[] encryptedData = cipher.doFinal(originalStringToEncrypt.getBytes());
			String encryptedDataAsString = new String(encryptedData);
			assertNotEquals(originalStringToEncrypt, encryptedDataAsString);

			// Decrypt data using private key
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] decryptedDataAsByteArray = cipher.doFinal(encryptedData);
			String decryptedString = new String(decryptedDataAsByteArray);

			System.out.println("Original string: " + originalStringToEncrypt);
			System.out.println("Encrypted data as String: " + encryptedDataAsString);
			System.out.println("Decrypted string: " + decryptedString);
			assertEquals(decryptedString, originalStringToEncrypt);

		}
	}
}