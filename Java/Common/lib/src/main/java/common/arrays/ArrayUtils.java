package common.arrays;

public class ArrayUtils {
	public static int[] byteArrayToIntArray(byte[] byteArray) {
		if (byteArray == null) {
			return null;
		}
		// Crée un nouveau tableau de int de la même taille que le tableau de byte
		int[] intArray = new int[byteArray.length];

		// Convertit chaque élément du tableau de byte en int
		for (int i = 0; i < byteArray.length; i++) {
			// Utilise & 0xFF pour obtenir la représentation positive des bytes négatifs
			intArray[i] = byteArray[i] & 0xFF;
		}

		return intArray;
	}
}
