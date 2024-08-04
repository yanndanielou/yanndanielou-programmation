package crypto.hash;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.arrays.ArrayUtils;
import common.string.utils.StringUtils;

/***
 * Custom implementation of MD2 (Message Digest 2) Hash
 */
public class MD2HashCustomImplementation {
	private static final Logger LOGGER = LogManager.getLogger(MD2HashCustomImplementation.class);

	private static final int BLOCK_SIZE = 16;

	// S-table as defined in the RFC
	private static final int[] SAsBytesArray = {

			/*
			 * (byte) 0x29, (byte) 0x2E, (byte) 0x43, (byte) 0xB1, (byte) 0x5A, (byte) 0x1B,
			 * (byte) 0x66, (byte) 0x6B, (byte) 0x6E, (byte) 0xF5, (byte) 0x5E, (byte) 0x36,
			 * (byte) 0x3F, (byte) 0x28, (byte) 0x20, (byte) 0x8A, (byte) 0xDF, (byte) 0xE9,
			 * (byte) 0x61, (byte) 0xAC, (byte) 0xA3, (byte) 0x9E, (byte) 0x0C, (byte) 0xB4,
			 * (byte) 0xF7, (byte) 0x74, (byte) 0x82, (byte) 0xEA, (byte) 0x1C, (byte) 0x2A,
			 * (byte) 0xE5, (byte) 0x6F, (byte) 0x83, (byte) 0x21, (byte) 0x4C, (byte) 0x69,
			 * (byte) 0xFD, (byte) 0x8F, (byte) 0x8C, (byte) 0xE3, (byte) 0x7D, (byte) 0xA4,
			 * (byte) 0xB2, (byte) 0xF9, (byte) 0xDE, (byte) 0x1D, (byte) 0x0E, (byte) 0xE1,
			 * (byte) 0x24, (byte) 0x76, (byte) 0xA1, (byte) 0xD1, (byte) 0x45, (byte) 0xDF,
			 * (byte) 0xE6, (byte) 0x3B, (byte) 0x55, (byte) 0xE2, (byte) 0x94, (byte) 0x0B,
			 * (byte) 0xA0, (byte) 0xF3, (byte) 0x58, (byte) 0x9C, (byte) 0x61, (byte) 0x82,
			 * (byte) 0xCE, (byte) 0xFF, (byte) 0x63, (byte) 0xA2, (byte) 0x11, (byte) 0x8B,
			 * (byte) 0x0D, (byte) 0x89, (byte) 0xDC, (byte) 0x0F, (byte) 0x49, (byte) 0xFB,
			 * (byte) 0x7B, (byte) 0xFD, (byte) 0x54, (byte) 0xBA, (byte) 0xD4, (byte) 0x9F,
			 * (byte) 0xAB, (byte) 0xC2, (byte) 0x6D, (byte) 0xD7, (byte) 0x0E, (byte) 0xFC,
			 * (byte) 0xC8, (byte) 0x11, (byte) 0x72, (byte) 0xB9, (byte) 0x7E, (byte) 0xFF,
			 * (byte) 0x5D, (byte) 0x8E, (byte) 0x20, (byte) 0xD1, (byte) 0x9D, (byte) 0xF8,
			 * (byte) 0x60, (byte) 0x3E, (byte) 0x8D, (byte) 0xD7, (byte) 0xA1, (byte) 0x17,
			 * (byte) 0xB7, (byte) 0x9E, (byte) 0xAB, (byte) 0x9F, (byte) 0x11, (byte) 0xDC,
			 * (byte) 0x1A, (byte) 0x63, (byte) 0xEF, (byte) 0xF9, (byte) 0x1C, (byte) 0xC6,
			 * (byte) 0x92, (byte) 0x39, (byte) 0xCE, (byte) 0x47, (byte) 0x95, (byte) 0x5E,
			 * (byte) 0x49, (byte) 0x73, (byte) 0x30, (byte) 0x74, (byte) 0xD2, (byte) 0xA5,
			 * (byte) 0xF4, (byte) 0xF7, (byte) 0x80, (byte) 0xAE, (byte) 0x93, (byte) 0xB5,
			 * (byte) 0xE1, (byte) 0x87, (byte) 0xC4, (byte) 0x9B, (byte) 0xA6, (byte) 0x72,
			 * (byte) 0xB8, (byte) 0xDD, (byte) 0xC2, (byte) 0xF0, (byte) 0x8C, (byte) 0x14,
			 * (byte) 0xA4, (byte) 0x27, (byte) 0xF5, (byte) 0xCA, (byte) 0x91, (byte) 0x2A,
			 * (byte) 0x26, (byte) 0x1A, (byte) 0xC7, (byte) 0x90, (byte) 0x68, (byte) 0xCD,
			 * (byte) 0x74, (byte) 0x98, (byte) 0xBA, (byte) 0x7C, (byte) 0xB6, (byte) 0xAC,
			 * (byte) 0x89, (byte) 0x52, (byte) 0x8A, (byte) 0xD9, (byte) 0xB0, (byte) 0xB1,
			 * (byte) 0xA7, (byte) 0xE5, (byte) 0x52, (byte) 0x39, (byte) 0xD2, (byte) 0x5E,
			 * (byte) 0x34, (byte) 0xDF, (byte) 0xA2, (byte) 0x4B, (byte) 0x31, (byte) 0x91,
			 * (byte) 0xF6, (byte) 0x79, (byte) 0xA2, (byte) 0xD3, (byte) 0x53, (byte) 0x29,
			 * (byte) 0xE7, (byte) 0x84, (byte) 0x18, (byte) 0x16, (byte) 0x1A, (byte) 0x6D,
			 * (byte) 0xDB, (byte) 0xA3, (byte) 0xE3, (byte) 0xF2, (byte) 0x25, (byte) 0xCB,
			 * (byte) 0x12, (byte) 0x97, (byte) 0x1C, (byte) 0x84, (byte) 0x41, (byte) 0x6A,
			 * (byte) 0xD1, (byte) 0x56, (byte) 0xBC, (byte) 0xA3, (byte) 0x61, (byte) 0x43,
			 * (byte) 0xBB, (byte) 0xD5, (byte) 0xB3, (byte) 0xC4, (byte) 0x83, (byte) 0xD7,
			 * (byte) 0xB7, (byte) 0xAC, (byte) 0xE3, (byte) 0x64, (byte) 0xF2, (byte) 0xD3,
			 * (byte) 0xDA, (byte) 0x0B, (byte) 0x95, (byte) 0xA1, (byte) 0x31, (byte) 0xBD,
			 * (byte) 0x60, (byte) 0xBF, (byte) 0xD5, (byte) 0xC5, (byte) 0xB1, (byte) 0xB1,
			 * (byte) 0x0C, (byte) 0xF8, (byte) 0x87, (byte) 0xF5, (byte) 0xF2, (byte) 0x15,
			 * (byte) 0xFD, (byte) 0x79 };
			 */

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

	private static final int[] S = { 0x29, 0x2E, 0x43, 0xB1, 0x5A, 0x1B, 0x66, 0x6B, 0x6E, 0xF5, 0x5E, 0x36, 0x3F, 0x28,
			0x20, 0x8A, 0xDF, 0xE9, 0x61, 0xAC, 0xA3, 0x9E, 0x0C, 0xB4, 0xF7, 0x74, 0x82, 0xEA, 0x1C, 0x2A, 0xE5, 0x6F,
			0x83, 0x21, 0x4C, 0x69, 0xFD, 0x8F, 0x8C, 0xE3, 0x7D, 0xA4, 0xB2, 0xF9, 0xDE, 0x1D, 0x0E, 0xE1, 0x24, 0x76,
			0xA1, 0xD1, 0x45, 0xDF, 0xE6, 0x3B, 0x55, 0xE2, 0x94, 0x0B, 0xA0, 0xF3, 0x58, 0x9C, 0x61, 0x82, 0xCE, 0xFF,
			0x63, 0xA2, 0x11, 0x8B, 0x0D, 0x89, 0xDC, 0x0F, 0x49, 0xFB, 0x7B, 0xFD, 0x54, 0xBA, 0xD4, 0x9F, 0xAB, 0xC2,
			0x6D, 0xD7, 0x0E, 0xFC, 0xC8, 0x11, 0x72, 0xB9, 0x7E, 0xFF, 0x5D, 0x8E, 0x20, 0xD1, 0x9D, 0xF8, 0x60, 0x3E,
			0x8D, 0xD7, 0xA1, 0x17, 0xB7, 0x9E, 0xAB, 0x9F, 0x11, 0xDC, 0x1A, 0x63, 0xEF, 0xF9, 0x1C, 0xC6, 0x92, 0x39,
			0xCE, 0x47, 0x95, 0x5E, 0x49, 0x73, 0x30, 0x74, 0xD2, 0xA5, 0xF4, 0xF7, 0x80, 0xAE, 0x93, 0xB5, 0xE1, 0x87,
			0xC4, 0x9B, 0xA6, 0x72, 0xB8, 0xDD, 0xC2, 0xF0, 0x8C, 0x14, 0xA4, 0x27, 0xF5, 0xCA, 0x91, 0x2A, 0x26, 0x1A,
			0xC7, 0x90, 0x68, 0xCD, 0x74, 0x98, 0xBA, 0x7C, 0xB6, 0xAC, 0x89, 0x52, 0x8A, 0xD9, 0xB0, 0xB1, 0xA7, 0xE5,
			0x52, 0x39, 0xD2, 0x5E, 0x34, 0xDF, 0xA2, 0x4B, 0x31, 0x91, 0xF6, 0x79, 0xA2, 0xD3, 0x53, 0x29, 0xE7, 0x84,
			0x18, 0x16, 0x1A, 0x6D, 0xDB, 0xA3, 0xE3, 0xF2, 0x25, 0xCB, 0x12, 0x97, 0x1C, 0x84, 0x41, 0x6A, 0xD1, 0x56,
			0xBC, 0xA3, 0x61, 0x43, 0xBB, 0xD5, 0xB3, 0xC4, 0x83, 0xD7, 0xB7, 0xAC, 0xE3, 0x64, 0xF2, 0xD3, 0xDA, 0x0B,
			0x95, 0xA1, 0x31, 0xBD, 0x60, 0xBF, 0xD5, 0xC5, 0xB1, 0xB1, 0x0C, 0xF8, 0x87, 0xF5, 0xF2, 0x15, 0xFD,
			0x79 };

	public static int[] hashGPT(byte[] input) {
		int paddingLength = 16 - (input.length % 16);
		int[] paddedInput = Arrays.copyOf(ArrayUtils.byteArrayToIntArray(input), input.length + paddingLength);

		for (int i = input.length; i < paddedInput.length; i++) {
			paddedInput[i] = (int) paddingLength;
		}

		int[] checksum = new int[16];
		int[] buffer = new int[48];

		for (int i = 0; i < paddedInput.length / 16; i++) {
			System.arraycopy(paddedInput, i * 16, buffer, 0, 16);
			System.arraycopy(checksum, 0, buffer, 16, 16);

			for (int j = 0; j < 16; j++) {
				buffer[32 + j] = (int) (buffer[j] ^ buffer[16 + j]);
			}

			int t = 0;
			for (int j = 0; j < 18; j++) {
				for (int k = 0; k < 48; k++) {
					buffer[k] = (int) (buffer[k] ^ S[t & 0xFF]);
					t = buffer[k];
				}
				t = (int) (t + j);
			}

			System.arraycopy(buffer, 0, checksum, 0, 16);
		}

		int[] output = new int[16];
		System.arraycopy(checksum, 0, output, 0, 16);

		return output;
	}

	/**
	 * 
	 * TODO: debug comparing with Python
	 * https://nickthecrypt.medium.com/cryptography-hash-method-md2-message-digest-2-step-by-step-explanation-made-easy-with-python-10faa2e35e85
	 */
	private static int[] hashToBytesArray(byte[] inputAsByteArray) {

		System.out.println("Message length:" + inputAsByteArray.length);

		int paddingLength = BLOCK_SIZE - (inputAsByteArray.length % BLOCK_SIZE);
		int[] paddedInput = ArrayUtils
				.byteArrayToIntArray(Arrays.copyOf(inputAsByteArray, inputAsByteArray.length + paddingLength));

		for (int i = inputAsByteArray.length; i < paddedInput.length; i++) {
			paddedInput[i] = (byte) paddingLength;
		}

		System.out.println("paddingLength:" + paddingLength);

		System.out.println(tableToString(paddedInput, "paddedInput"));

		// byte[] checksum = new byte[16];
		int[] md_digest = new int[48];

		int blocks_number = paddedInput.length / 16;
		System.out.println("blocks_number:" + blocks_number);

		int[] checksum = new int[16];
		System.out.println("Checksum initialization:");
		System.out.println(tableToString(checksum, "checksum"));

		int l = 0;
		for (int block = 0; block < blocks_number; block++) {
			System.out.println("Block:" + block);
			for (int j = 0; j < BLOCK_SIZE; j++) {
				l = SAsBytesArray[(paddedInput[block * BLOCK_SIZE + j] ^ l)] ^ checksum[j];
				checksum[j] = l;
			}
		}

		System.out.println("Checksum calc 1:");
		System.out.println(tableToString(checksum, "checksum"));

		System.out.println("add the checksum to the processed message");
		paddedInput = IntStream.concat(Arrays.stream(paddedInput), Arrays.stream(checksum)).toArray();

		System.out.println("paddedInput:");
		System.out.println(tableToString(paddedInput, "paddedInput"));
		blocks_number += 1;

		for (int block = 0; block < blocks_number; block++) {
			System.out.println("Block:" + block);

			System.out.println("paddedInput length: " + paddedInput.length);
			System.out.println("md_digest length: " + md_digest.length);
			for (int j = 0; j < BLOCK_SIZE; j++) {
				if(block * BLOCK_SIZE + j >paddedInput.length) {
					int pause = 1;
				}
				
				md_digest[BLOCK_SIZE + j] = paddedInput[block * BLOCK_SIZE + j];
				md_digest[2 * BLOCK_SIZE + j] = (md_digest[BLOCK_SIZE + j] ^ md_digest[j]);

				// initial : md_digest[32 + j] = (byte) (md_digest[j] ^ md_digest[BLOCK_SIZE +
				// j]);
			}

			System.out.println(tableToString(md_digest, "md_digest"));

			int checktmp = 0;
			for (int j = 0; j < 18; j++) {
				for (int k = 0; k < 48; k++) {

					System.out.println("checktmp:" + checktmp);
					System.out.println("md_digest[k]:" + md_digest[k]);
					System.out.println("S[checktmp]:" + SAsBytesArray[checktmp & 0xFF]);

					int md_digest_int = md_digest[k] ^ SAsBytesArray[checktmp & 0xFF];
					// System.out.println("md_digest_int:" + md_digest_int);

					md_digest[k] = md_digest_int;

					System.out.println("Block:" + block + ", j:" + j + ", k:" + k + ", md_digest[" + k + "]" + " = "
							+ md_digest[k]);
					checktmp = md_digest[k];
				}
				checktmp = ((checktmp + j) % 256);
				System.out.println("j:" + j + ", checktmp:" + checktmp);

			}

			System.arraycopy(md_digest, 0, md_digest, 0, 16);
		}

		System.out.println(tableToString(paddedInput, "Checksum"));

		int[] output = new int[16];
		System.arraycopy(md_digest, 0, output, 0, 16);

		return output;
	}

	public static String computeMD2HashWithCustomImplementation(String input) {
		byte[] inputBytes = input.getBytes();
		int[] hashIntBytes = hashToBytesArray(inputBytes);
		//hashIntBytes = hashGPT(inputBytes);
		return StringUtils.transformIntArrayToString(hashIntBytes);
	}

	private static String tableToString(byte[] table, String name) {
		String ret = name;
		for (int i = 0; i < table.length; i++) {
			ret += ("[" + i + "] = " + table[i] + ", ");
		}
		return ret;
	}

	private static String tableToString(int[] table, String name) {
		String ret = name;
		for (int i = 0; i < table.length; i++) {
			ret += ("[" + i + "] = " + table[i] + ", ");
		}
		return ret;
	}

}
