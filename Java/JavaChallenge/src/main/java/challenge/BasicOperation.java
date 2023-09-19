package challenge;

public class BasicOperation {

	public static int operation(String a, String b, String op) {
		int aAsInt = Integer.parseInt(a);
		int bAsInt = Integer.parseInt(b);

		if (op == "add") {
			return aAsInt + bAsInt;
		}
		if (op == "substract") {
			return aAsInt - bAsInt;

		}
		if (op == "multiply") {
			return aAsInt * bAsInt;

		}
		if (op == "divide") {
			if(bAsInt==0) {
				return Integer.MIN_VALUE;
			}
			return aAsInt / bAsInt;

		}
		return 0;
	}
}
