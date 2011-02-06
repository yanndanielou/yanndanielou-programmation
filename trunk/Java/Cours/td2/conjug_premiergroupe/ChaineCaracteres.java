public class ChaineCaracteres{

	public static void main (String [] args){

		String une_chaine = 		"le java à Polytech, c'est Top";
		String un_message = 		"le java à Polytech, c'est Top";
		String un = 				"le java à Polytech, ";
		String message_coupe = 		"c'est Top";
		String message_colle = 		"le java à Polytech, "+ "c'est Top";
		String autosatisfaction =	un + message_coupe;
		String propagande = 		new String("le java à Polytech, c'est Top");
		String leitmotiv = 			new String ("le java à Polytech, c'est Top");


		System.out.println("comparaison de une_chaine et un_message: " + (une_chaine == un_message));

		System.out.println("comparaison de une_chaine et message_colle: " + (une_chaine == message_colle));

		System.out.println("comparaison de une_chaine et autosatisfaction: " + (une_chaine == autosatisfaction));

		System.out.println("comparaison de une_chaine et propagande: " + (une_chaine == propagande));

		System.out.println("comparaison de une_chaine et leitmotiv: " + (propagande == leitmotiv));
	}
}

