
public class MaxReccursiveApplication {

	public static void main(String[] args) {
		//-Xss4m
		reccursiveFunction(0);
	}

	private static void reccursiveFunction(int reccursiveNumber) {
		System.out.println("reccursiveFunction:" + reccursiveNumber);
		try {
			reccursiveFunction(reccursiveNumber + 1);
		} catch (StackOverflowError stackOverflowError) {
			System.out.println(
					"StackOverflowError:" + stackOverflowError + " raised at reccursive number:" + reccursiveNumber);
		}
	}

}
