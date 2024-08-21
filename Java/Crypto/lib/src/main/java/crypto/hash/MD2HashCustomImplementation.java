package crypto.hash;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.arrays.ArrayUtils;
import common.string.utils.StringUtils;
import crypto.hash.Hash.HashType;

/***
 * Custom implementation of MD2 (Message Digest 2) Hash
 */
public class MD2HashCustomImplementation {
	private static final Logger LOGGER = LogManager.getLogger(MD2HashCustomImplementation.class);

	private static final int BLOCK_SIZE = 16;

	// S-table as defined in the RFC
	private static final int[] STableRFC = {

			41, 46, 67, 201, 162, 216, 124, 1, 61, 54, 84, 161, 236, 240, 6, 19, 98, 167, 5, 243, 192, 199, 115, 140,
			152, 147, 43, 217, 188, 76, 130, 202, 30, 155, 87, 60, 253, 212, 224, 22, 103, 66, 111, 24, 138, 23, 229,
			18, 190, 78, 196, 214, 218, 158, 222, 73, 160, 251, 245, 142, 187, 47, 238, 122, 169, 104, 121, 145, 21,
			178, 7, 63, 148, 194, 16, 137, 11, 34, 95, 33, 128, 127, 93, 154, 90, 144, 50, 39, 53, 62, 204, 231, 191,
			247, 151, 3, 255, 25, 48, 179, 72, 165, 181, 209, 215, 94, 146, 42, 172, 86, 170, 198, 79, 184, 56, 210,
			150, 164, 125, 182, 118, 252, 107, 226, 156, 116, 4, 241, 69, 157, 112, 89, 100, 113, 135, 32, 134, 91, 207,
			101, 230, 45, 168, 2, 27, 96, 37, 173, 174, 176, 185, 246, 28, 70, 97, 105, 52, 64, 126, 15, 85, 71, 163,
			35, 221, 81, 175, 58, 195, 92, 249, 206, 186, 197, 234, 38, 44, 83, 13, 110, 133, 40, 132, 9, 211, 223, 205,
			244, 65, 129, 77, 82, 106, 220, 55, 200, 108, 193, 171, 250, 36, 225, 123, 8, 12, 189, 177, 74, 120, 136,
			149, 139, 227, 99, 232, 109, 233, 203, 213, 254, 59, 0, 29, 57, 242, 239, 183, 14, 102, 88, 208, 228, 166,
			119, 114, 248, 235, 117, 75, 10, 49, 68, 80, 180, 143, 237, 31, 26, 219, 153, 141, 51, 159, 17, 131, 20 };

	/**
	 * 
	 * TODO: debug comparing with Python
	 * https://nickthecrypt.medium.com/cryptography-hash-method-md2-message-digest-2-step-by-step-explanation-made-easy-with-python-10faa2e35e85
	 */
	private static int[] hashToBytesArray(byte[] inputAsByteArray) {

		LOGGER.info("Message length:" + inputAsByteArray.length);

		int paddingLength = BLOCK_SIZE - (inputAsByteArray.length % BLOCK_SIZE);
		int[] paddedInput = ArrayUtils
				.byteArrayToIntArray(Arrays.copyOf(inputAsByteArray, inputAsByteArray.length + paddingLength));

		for (int i = inputAsByteArray.length; i < paddedInput.length; i++) {
			paddedInput[i] = (byte) paddingLength;
		}

		LOGGER.info("paddingLength:" + paddingLength);
		LOGGER.info(tableToString(paddedInput, "paddedInput"));

		// byte[] checksum = new byte[16];
		int[] md_digest = new int[48];

		int blocks_number = paddedInput.length / 16;
		LOGGER.info("blocks_number:" + blocks_number);

		int[] checksum = new int[16];
		LOGGER.info("Checksum initialization:");
		LOGGER.info(tableToString(checksum, "checksum"));

		int l = 0;
		for (int block = 0; block < blocks_number; block++) {
			LOGGER.debug("Block:" + block);
			for (int j = 0; j < BLOCK_SIZE; j++) {
				l = STableRFC[(paddedInput[block * BLOCK_SIZE + j] ^ l)] ^ checksum[j];
				checksum[j] = l;
			}
		}

		LOGGER.info(tableToString(checksum, "checksum"));

		LOGGER.info("add the checksum to the processed message");
		paddedInput = IntStream.concat(Arrays.stream(paddedInput), Arrays.stream(checksum)).toArray();

		LOGGER.info(tableToString(paddedInput, "paddedInput"));
		blocks_number += 1;

		for (int block = 0; block < blocks_number; block++) {
			LOGGER.debug("Block:" + block);

			LOGGER.debug("paddedInput length: " + paddedInput.length);
			LOGGER.debug("md_digest length: " + md_digest.length);
			for (int j = 0; j < BLOCK_SIZE; j++) {
				md_digest[BLOCK_SIZE + j] = paddedInput[block * BLOCK_SIZE + j];
				md_digest[2 * BLOCK_SIZE + j] = (md_digest[BLOCK_SIZE + j] ^ md_digest[j]);
			}

			LOGGER.debug(tableToString(md_digest, "md_digest"));

			int checktmp = 0;
			for (int j = 0; j < 18; j++) {
				for (int k = 0; k < 48; k++) {

					LOGGER.debug("checktmp:" + checktmp);
					LOGGER.debug("md_digest[k]:" + md_digest[k]);
					LOGGER.debug("S[checktmp]:" + STableRFC[checktmp & 0xFF]);

					int md_digest_int = md_digest[k] ^ STableRFC[checktmp & 0xFF];
					// LOGGER.info("md_digest_int:" + md_digest_int);

					md_digest[k] = md_digest_int;

					LOGGER.debug("Block:" + block + ", j:" + j + ", k:" + k + ", md_digest[" + k + "]" + " = "
							+ md_digest[k]);
					checktmp = md_digest[k];
				}
				checktmp = ((checktmp + j) % 256);
				LOGGER.debug("j:" + j + ", checktmp:" + checktmp);

			}

			System.arraycopy(md_digest, 0, md_digest, 0, 16);
		}

		LOGGER.info(tableToString(paddedInput, "Checksum"));

		int[] output = new int[16];
		System.arraycopy(md_digest, 0, output, 0, 16);

		return output;
	}

	static Hash computeMD2HashWithCustomImplementation(String input) {
		byte[] inputBytes = input.getBytes();
		int[] hashIntBytes = hashToBytesArray(inputBytes);
		return new Hash(StringUtils.transformIntArrayToString(hashIntBytes), HashType.MD2);
	}

	private static String tableToString(int[] table, String name) {
		String ret = name;
		for (int i = 0; i < table.length; i++) {
			ret += ("[" + i + "] = " + table[i] + ", ");
		}
		return ret;
	}

}
