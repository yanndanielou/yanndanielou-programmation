package crypto.hash;

import java.util.Objects;

public class Hash {

	public enum HashType {
		MD2, SHA2_256, SHA3_256, SHA_512;
	}
	
	private String hashUTF8;
	private HashType hashType;

	public Hash(String hashUTF8, HashType hashType) {
		this.hashUTF8 = hashUTF8;
		this.hashType = hashType;
	}
	
	public String getHashUTF8() {
		return hashUTF8;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hash other = (Hash) obj;
		return hashType == other.hashType && Objects.equals(hashUTF8, other.hashUTF8);
	}

}
