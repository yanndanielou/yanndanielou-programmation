package crypto.aes;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.baeldung.aes.AESUtil;

public class AESUtilTest {

	@Nested
	public class AES {
		// https://www.baeldung.com/java-aes-encryption-decryption
		@Test
		void encodeAndDecodeStringWithGeneratedKey() throws NoSuchAlgorithmException, IllegalBlockSizeException,
				InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

			String input = "baeldung";
			SecretKey key = AESUtil.generateKey(128);
			IvParameterSpec ivParameterSpec = AESUtil.generateIv();
			String algorithm = "AES/CBC/PKCS5Padding";
			String cipherText = AESUtil.encrypt(algorithm, input, key, ivParameterSpec);
			String decryptedPlainText = AESUtil.decrypt(algorithm, cipherText, key, ivParameterSpec);
			Assertions.assertEquals(input, decryptedPlainText);
		}
		
		// https://www.baeldung.com/java-aes-encryption-decryption
		@Test
		void encodeAndDecodeStringWithPassword() throws NoSuchAlgorithmException, IllegalBlockSizeException,
				InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

			String input = "baeldung";
			
			
			SecretKey key = AESUtil.generateKey(128);
			IvParameterSpec ivParameterSpec = AESUtil.generateIv();
			String algorithm = "AES/CBC/PKCS5Padding";
			String cipherText = AESUtil.encrypt(algorithm, input, key, ivParameterSpec);
			String decryptedPlainText = AESUtil.decrypt(algorithm, cipherText, key, ivParameterSpec);
			Assertions.assertEquals(input, decryptedPlainText);
		}
	}

}