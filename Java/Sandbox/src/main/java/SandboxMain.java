import java.lang.Runtime.Version;

public class SandboxMain {

	public SandboxMain() {
		// TODO Auto-generated constructor stub
	}

	private static void JavaVersion() {
		Version version = Runtime.version();
		System.out.println(version);
	}

	public static void main(String[] args) {

		int asInt = 252;
		byte aAsByte = (byte) asInt;
		aAsByte = (byte) (asInt & 0xFF);
		aAsByte = (byte) (asInt % 256);
		aAsByte = (byte) (asInt);
		aAsByte = (byte) (aAsByte & 0xFF);



		System.out.println(aAsByte + ": " + aAsByte);
	}

}
