package crypto.hash;

import java.util.Arrays;

import common.string.utils.StringUtils;

public class SHA256CustomImplementation {

	private static final int BLOCK_SIZE = 128;
	private static final int HASH_SIZE = 64;
	private static final long[] K = { 0x428a2f98d728ae22L, 0x7137449123ef65cdL, 0xb5c0fbcfec4d3b2fL,
			0xe9b5dba58189dbbcL, 0x3956c25bf348b538L, 0x59f111f1b605d019L, 0x923f82a4af194f9bL, 0xab1c5ed5da6d8118L,
			0xd807aa98a3030242L, 0x12835b0145706fbeL, 0x243185be4ee4b28cL, 0x550c7dc3d5ffb4e2L, 0x72be5d74f27b896fL,
			0x80deb1fe3b1696b1L, 0x9bdc06a725c71235L, 0xc19bf174cf692694L, 0xe49b69c19ef14ad2L, 0xefbe4786384f25e3L,
			0x0fc19dc68b8cd5b5L, 0x240ca1cc77ac9c65L, 0x2de92c6f592b0275L, 0x4a7484aa6ea6e483L, 0x5cb0a9dcbd41fbd4L,
			0x76f988da831153b5L, 0x983e5152ee66dfabL, 0xa831c66d2db43210L, 0xb00327c898fb213fL, 0xbf597fc7beef0ee4L,
			0xc6e00bf33da88fc2L, 0xd5a79147930aa725L, 0x06ca6351e003826fL, 0x142929670a0e6e70L, 0x27b70a8546d22ffcL,
			0x2e1b21385c26c926L, 0x4d2c6dfc5ac42aedL, 0x53380d139d95b3dfL, 0x650a73548baf63deL, 0x766a0abb3c77b2a8L,
			0x81c2c92e47edaee6L, 0x92722c851482353bL, 0xa2bfe8a14cf10364L, 0xa81a664bbc423001L, 0xc24b8b70d0f89791L,
			0xc76c51a30654be30L, 0xd192e819d6ef5218L, 0xd69906245565a910L, 0xf40e35855771202aL, 0x106aa07032bbd1b8L,
			0x19a4c116b8d2d0c8L, 0x1e376c085141ab53L, 0x2748774cdf8eeb99L, 0x34b0bcb5e19b48a8L, 0x391c0cb3c5c95a63L,
			0x4ed8aa4ae3418acbL, 0x5b9cca4f7763e373L, 0x682e6ff3d6b2b8a3L, 0x748f82ee5defb2fcL, 0x78a5636f43172f60L,
			0x84c87814a1f0ab72L, 0x8cc702081a6439ecL, 0x90befffa23631e28L, 0xa4506cebde82bde9L, 0xbef9a3f7b2c67915L,
			0xc67178f2e372532bL, 0xca273eceea26619cL, 0xd186b8c721c0c207L, 0xeada7dd6cde0eb1eL, 0xf57d4f7fee6ed178L,
			0x06f067aa72176fbaL, 0x0a637dc5a2c898a6L, 0x113f9804bef90daeL, 0x1b710b35131c471bL, 0x28db77f523047d84L,
			0x32caab7b40c72493L, 0x3c9ebe0a15c9bebcL, 0x431d67c49c100d4cL, 0x4cc5d4becb3e42b6L, 0x597f299cfc657e2aL,
			0x5fcb6fab3ad6faecL, 0x6c44198c4a475817L };

	private static final long[] H = { 0x6a09e667f3bcc908L, 0xbb67ae8584caa73bL, 0x3c6ef372fe94f82bL,
			0xa54ff53a5f1d36f1L, 0x510e527fade682d1L, 0x9b05688c2b3e6c1fL, 0x1f83d9abfb41bd6bL, 0x5be0cd19137e2179L };

	private byte[] hash(byte[] message) {
		byte[] padded = padMessage(message);
		for (int i = 0; i < padded.length; i += BLOCK_SIZE) {
			processBlock(Arrays.copyOfRange(padded, i, i + BLOCK_SIZE));
		}
		return toByteArray(H);
	}

	private byte[] padMessage(byte[] message) {
		int originalLength = message.length;
		long bitLength = originalLength * 8L;
		int paddingLength = (originalLength + 17) % BLOCK_SIZE;
		paddingLength = (BLOCK_SIZE - paddingLength) % BLOCK_SIZE;

		byte[] paddedMessage = new byte[originalLength + paddingLength + 17];
		System.arraycopy(message, 0, paddedMessage, 0, originalLength);
		paddedMessage[originalLength] = (byte) 0x80;
		for (int i = 8; i > 0; i--) {
			paddedMessage[paddedMessage.length - i] = (byte) (bitLength >>> ((8 - i) * 8));
		}
		return paddedMessage;
	}

	private static void processBlock(byte[] block) {
		long[] W = new long[80];
		for (int i = 0; i < 16; i++) {
			W[i] = ((block[i * 8] & 0xFFL) << 56) | ((block[i * 8 + 1] & 0xFFL) << 48)
					| ((block[i * 8 + 2] & 0xFFL) << 40) | ((block[i * 8 + 3] & 0xFFL) << 32)
					| ((block[i * 8 + 4] & 0xFFL) << 24) | ((block[i * 8 + 5] & 0xFFL) << 16)
					| ((block[i * 8 + 6] & 0xFFL) << 8) | ((block[i * 8 + 7] & 0xFFL));
		}

		for (int i = 16; i < 80; i++) {
			long s0 = Long.rotateRight(W[i - 15], 1) ^ Long.rotateRight(W[i - 15], 8) ^ (W[i - 15] >>> 7);
			long s1 = Long.rotateRight(W[i - 2], 19) ^ Long.rotateRight(W[i - 2], 61) ^ (W[i - 2] >>> 6);
			W[i] = W[i - 16] + s0 + W[i - 7] + s1;
		}

		long a = H[0], b = H[1], c = H[2], d = H[3], e = H[4], f = H[5], g = H[6], h = H[7];

		for (int i = 0; i < 80; i++) {
			long S1 = Long.rotateRight(e, 14) ^ Long.rotateRight(e, 18) ^ Long.rotateRight(e, 41);
			long ch = (e & f) ^ ((~e) & g);
			long temp1 = h + S1 + ch + K[i] + W[i];
			long S0 = Long.rotateRight(a, 28) ^ Long.rotateRight(a, 34) ^ Long.rotateRight(a, 39);
			long maj = (a & b) ^ (a & c) ^ (b & c);
			long temp2 = S0 + maj;

			h = g;
			g = f;
			f = e;
			e = d + temp1;
			d = c;
			c = b;
			b = a;
			a = temp1 + temp2;
		}

		H[0] += a;
		H[1] += b;
		H[2] += c;
		H[3] += d;
		H[4] += e;
		H[5] += f;
		H[6] += g;
		H[7] += h;
	}

	private static byte[] toByteArray(long[] array) {
		byte[] result = new byte[array.length * 8];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < 8; j++) {
				result[i * 8 + j] = (byte) (array[i] >>> (56 - j * 8));
			}
		}
		return result;
	}

	public static Hash computeSHA256Hash(String input) {

		SHA256CustomImplementation sha512 = new SHA256CustomImplementation();
		byte[] hash = sha512.hash(input.getBytes());
		for (byte b : hash) {
			System.out.printf("%02x", b);
		}
		String hashAsString = StringUtils.transformBytesArrayToString(hash);
		System.out.println(hashAsString);
		return new Hash(hashAsString, Hash.HashType.SHA2_256);
	}
}
