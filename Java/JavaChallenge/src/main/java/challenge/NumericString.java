package challenge;

public class NumericString {

	public static final String INVALID_OPERATION_RESULT = "Invalid Operation";
	
	public static String add(String a, String b) {
		int aAsInt;
		int bAsInt;
		try {
		aAsInt = Integer.parseInt(a);
		bAsInt = Integer.parseInt(b);
		}
		catch (NumberFormatException e) {
			return INVALID_OPERATION_RESULT;
		}
		int resultAsInt = aAsInt + bAsInt;
		return ""+resultAsInt;

	}

}
