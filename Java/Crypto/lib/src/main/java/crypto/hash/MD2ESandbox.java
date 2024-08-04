package crypto.hash;

public class MD2ESandbox {

	public static void main(String[] args) {
		String computeMD2HashWithCustomImplementation = MD2HashCustomImplementation.computeMD2HashWithCustomImplementation("");
		System.out.println(computeMD2HashWithCustomImplementation);
		System.out.println("Hash is good:" + "8350e5a3e24c153df2275c9f80692773".equals(computeMD2HashWithCustomImplementation));
	}

}
