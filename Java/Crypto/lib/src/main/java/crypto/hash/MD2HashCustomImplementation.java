package crypto.hash;

import java.util.Arrays;

import common.string.utils.StringUtils;

/***
 * Custom implementation of MD2 (Message Digest 2) Hash
 */
public class MD2HashCustomImplementation {
	// S-table as defined in the RFC
	private static final byte[] SAsBytesArray = { (byte) 0x29, (byte) 0x2E, (byte) 0x43, (byte) 0xB1, (byte) 0x5A,
			(byte) 0x1B, (byte) 0x66, (byte) 0x6B, (byte) 0x6E, (byte) 0xF5, (byte) 0x5E, (byte) 0x36, (byte) 0x3F,
			(byte) 0x28, (byte) 0x20, (byte) 0x8A, (byte) 0xDF, (byte) 0xE9, (byte) 0x61, (byte) 0xAC, (byte) 0xA3,
			(byte) 0x9E, (byte) 0x0C, (byte) 0xB4, (byte) 0xF7, (byte) 0x74, (byte) 0x82, (byte) 0xEA, (byte) 0x1C,
			(byte) 0x2A, (byte) 0xE5, (byte) 0x6F, (byte) 0x83, (byte) 0x21, (byte) 0x4C, (byte) 0x69, (byte) 0xFD,
			(byte) 0x8F, (byte) 0x8C, (byte) 0xE3, (byte) 0x7D, (byte) 0xA4, (byte) 0xB2, (byte) 0xF9, (byte) 0xDE,
			(byte) 0x1D, (byte) 0x0E, (byte) 0xE1, (byte) 0x24, (byte) 0x76, (byte) 0xA1, (byte) 0xD1, (byte) 0x45,
			(byte) 0xDF, (byte) 0xE6, (byte) 0x3B, (byte) 0x55, (byte) 0xE2, (byte) 0x94, (byte) 0x0B, (byte) 0xA0,
			(byte) 0xF3, (byte) 0x58, (byte) 0x9C, (byte) 0x61, (byte) 0x82, (byte) 0xCE, (byte) 0xFF, (byte) 0x63,
			(byte) 0xA2, (byte) 0x11, (byte) 0x8B, (byte) 0x0D, (byte) 0x89, (byte) 0xDC, (byte) 0x0F, (byte) 0x49,
			(byte) 0xFB, (byte) 0x7B, (byte) 0xFD, (byte) 0x54, (byte) 0xBA, (byte) 0xD4, (byte) 0x9F, (byte) 0xAB,
			(byte) 0xC2, (byte) 0x6D, (byte) 0xD7, (byte) 0x0E, (byte) 0xFC, (byte) 0xC8, (byte) 0x11, (byte) 0x72,
			(byte) 0xB9, (byte) 0x7E, (byte) 0xFF, (byte) 0x5D, (byte) 0x8E, (byte) 0x20, (byte) 0xD1, (byte) 0x9D,
			(byte) 0xF8, (byte) 0x60, (byte) 0x3E, (byte) 0x8D, (byte) 0xD7, (byte) 0xA1, (byte) 0x17, (byte) 0xB7,
			(byte) 0x9E, (byte) 0xAB, (byte) 0x9F, (byte) 0x11, (byte) 0xDC, (byte) 0x1A, (byte) 0x63, (byte) 0xEF,
			(byte) 0xF9, (byte) 0x1C, (byte) 0xC6, (byte) 0x92, (byte) 0x39, (byte) 0xCE, (byte) 0x47, (byte) 0x95,
			(byte) 0x5E, (byte) 0x49, (byte) 0x73, (byte) 0x30, (byte) 0x74, (byte) 0xD2, (byte) 0xA5, (byte) 0xF4,
			(byte) 0xF7, (byte) 0x80, (byte) 0xAE, (byte) 0x93, (byte) 0xB5, (byte) 0xE1, (byte) 0x87, (byte) 0xC4,
			(byte) 0x9B, (byte) 0xA6, (byte) 0x72, (byte) 0xB8, (byte) 0xDD, (byte) 0xC2, (byte) 0xF0, (byte) 0x8C,
			(byte) 0x14, (byte) 0xA4, (byte) 0x27, (byte) 0xF5, (byte) 0xCA, (byte) 0x91, (byte) 0x2A, (byte) 0x26,
			(byte) 0x1A, (byte) 0xC7, (byte) 0x90, (byte) 0x68, (byte) 0xCD, (byte) 0x74, (byte) 0x98, (byte) 0xBA,
			(byte) 0x7C, (byte) 0xB6, (byte) 0xAC, (byte) 0x89, (byte) 0x52, (byte) 0x8A, (byte) 0xD9, (byte) 0xB0,
			(byte) 0xB1, (byte) 0xA7, (byte) 0xE5, (byte) 0x52, (byte) 0x39, (byte) 0xD2, (byte) 0x5E, (byte) 0x34,
			(byte) 0xDF, (byte) 0xA2, (byte) 0x4B, (byte) 0x31, (byte) 0x91, (byte) 0xF6, (byte) 0x79, (byte) 0xA2,
			(byte) 0xD3, (byte) 0x53, (byte) 0x29, (byte) 0xE7, (byte) 0x84, (byte) 0x18, (byte) 0x16, (byte) 0x1A,
			(byte) 0x6D, (byte) 0xDB, (byte) 0xA3, (byte) 0xE3, (byte) 0xF2, (byte) 0x25, (byte) 0xCB, (byte) 0x12,
			(byte) 0x97, (byte) 0x1C, (byte) 0x84, (byte) 0x41, (byte) 0x6A, (byte) 0xD1, (byte) 0x56, (byte) 0xBC,
			(byte) 0xA3, (byte) 0x61, (byte) 0x43, (byte) 0xBB, (byte) 0xD5, (byte) 0xB3, (byte) 0xC4, (byte) 0x83,
			(byte) 0xD7, (byte) 0xB7, (byte) 0xAC, (byte) 0xE3, (byte) 0x64, (byte) 0xF2, (byte) 0xD3, (byte) 0xDA,
			(byte) 0x0B, (byte) 0x95, (byte) 0xA1, (byte) 0x31, (byte) 0xBD, (byte) 0x60, (byte) 0xBF, (byte) 0xD5,
			(byte) 0xC5, (byte) 0xB1, (byte) 0xB1, (byte) 0x0C, (byte) 0xF8, (byte) 0x87, (byte) 0xF5, (byte) 0xF2,
			(byte) 0x15, (byte) 0xFD, (byte) 0x79 };

	/**
	 * 41, 46, 67, 201, 162, 216, 124, 1, 61, 54, 84, 161, 236, 240, 6, 19, 98, 167,
	 * 5, 243, 192, 199, 115, 140, 152, 147, 43, 217, 188, 76, 130, 202, 30, 155,
	 * 87, 60, 253, 212, 224, 22, 103, 66, 111, 24, 138, 23, 229, 18, 190, 78, 196,
	 * 214, 218, 158, 222, 73, 160, 251, 245, 142, 187, 47, 238, 122, 169, 104, 121,
	 * 145, 21, 178, 7, 63, 148, 194, 16, 137, 11, 34, 95, 33, 128, 127, 93, 154,
	 * 90, 144, 50, 39, 53, 62, 204, 231, 191, 247, 151, 3, 255, 25, 48, 179, 72,
	 * 165, 181, 209, 215, 94, 146, 42, 172, 86, 170, 198, 79, 184, 56, 210, 150,
	 * 164, 125, 182, 118, 252, 107, 226, 156, 116, 4, 241, 69, 157, 112, 89, 100,
	 * 113, 135, 32, 134, 91, 207, 101, 230, 45, 168, 2, 27, 96, 37, 173, 174, 176,
	 * 185, 246, 28, 70, 97, 105, 52, 64, 126, 15, 85, 71, 163, 35, 221, 81, 175,
	 * 58, 195, 92, 249, 206, 186, 197, 234, 38, 44, 83, 13, 110, 133, 40, 132, 9,
	 * 211, 223, 205, 244, 65, 129, 77, 82, 106, 220, 55, 200, 108, 193, 171, 250,
	 * 36, 225, 123, 8, 12, 189, 177, 74, 120, 136, 149, 139, 227, 99, 232, 109,
	 * 233, 203, 213, 254, 59, 0, 29, 57, 242, 239, 183, 14, 102, 88, 208, 228, 166,
	 * 119, 114, 248, 235, 117, 75, 10, 49, 68, 80, 180, 143, 237, 31, 26, 219, 153,
	 * 141, 51, 159, 17, 131, 20
	 */

	/**
	 * 
	 * TODO: debug comparing with Python
	 * https://nickthecrypt.medium.com/cryptography-hash-method-md2-message-digest-2-step-by-step-explanation-made-easy-with-python-10faa2e35e85
	 */
	private static byte[] hashToBytesArray(byte[] input) {
		int paddingLength = 16 - (input.length % 16);
		byte[] paddedInput = Arrays.copyOf(input, input.length + paddingLength);

		for (int i = input.length; i < paddedInput.length; i++) {
			paddedInput[i] = (byte) paddingLength;
		}

		byte[] checksum = new byte[16];
		byte[] buffer = new byte[48];

		for (int i = 0; i < paddedInput.length / 16; i++) {
			System.arraycopy(paddedInput, i * 16, buffer, 0, 16);
			System.arraycopy(checksum, 0, buffer, 16, 16);

			for (int j = 0; j < 16; j++) {
				buffer[32 + j] = (byte) (buffer[j] ^ buffer[16 + j]);
			}

			byte t = 0;
			for (int j = 0; j < 18; j++) {
				for (int k = 0; k < 48; k++) {
					buffer[k] = (byte) (buffer[k] ^ SAsBytesArray[t & 0xFF]);
					t = buffer[k];
				}
				t = (byte) (t + j);
			}

			System.arraycopy(buffer, 0, checksum, 0, 16);
		}

		byte[] output = new byte[16];
		System.arraycopy(checksum, 0, output, 0, 16);

		return output;
	}

	public static String computeMD2HashWithCustomImplementation(String input) {
		byte[] inputBytes = input.getBytes();
		byte[] hashBytes = hashToBytesArray(inputBytes);
		return StringUtils.transformBytesArrayToString(hashBytes);
	}

}
