import java.io.*;
import java.util.Scanner;


class SousTitres{
	public static final char CHOIX_AJOUTER_TEMPS = 'a';
	public static final char CHOIX_RETIRER_TEMPS = 'r';

	public static void main (String [] args)throws Exception{

	Scanner sc = new Scanner(System.in);

	System.out.println("Quel fichier .srt souhaitez vous modifier?");
	String nomFichier = sc.nextLine() + ".srt";

	
	System.out.println("Souhaitez vous ajouter (tapez " + CHOIX_AJOUTER_TEMPS + ") ou retirer (tapez " + CHOIX_RETIRER_TEMPS + ") du temps??\n");
	char ChoixAjouterRetirer = sc.nextLine().charAt(0);


	System.out.println("Combien de temps voulez vous ajouter ou retirer? Tapez en chiffres le temps en milisecondes)\n");
	int nombreMiliSecondesModification = sc.nextInt();


	System.out.println("A partir de quand souhaitez vous modifier le temps des sous titres? Tapez en chiffre la minute de debut des modifications (0 pour des le debut)\n");
	int minuteDebutModifications = sc.nextInt();

	BufferedReader  reader = new BufferedReader(new FileReader("in.srt"));
	PrintWriter writer = new PrintWriter(new FileWriter("out.srt"));
	String ligne = reader.readLine();

		
		while(ligne != null){

			if((ligne.length() == 29)&&(ligne.charAt(2) == ':')&&(ligne.charAt(5) == ':')&&(ligne.charAt(8) == ',')){
				System.out.println(ligne + " devient ");
				ligne = modifierTemps(ligne, 0, ChoixAjouterRetirer, nombreMiliSecondesModification);
			
				ligne = modifierTemps(ligne, 17, ChoixAjouterRetirer, nombreMiliSecondesModification);
				System.out.println(ligne + "\n");
				
			}
			
						
			writer.println(ligne);
			ligne = reader.readLine();
		
		}
		
		
	reader.close();
	writer.close();
	}
	
	static String modifierTemps(String ligne, int offset, int ChoixAjouterRetirer, int nombreMiliSecondesModification){
		//try { Thread.sleep(100); } catch (InterruptedException e) {}
				int heures=(ligne.charAt(offset + 0)-'0')*10 + (ligne.charAt(offset + 1)-'0');
				int minutes=(ligne.charAt(offset + 3)-'0')*10 + (ligne.charAt(offset + 4)-'0');
				int secondes=(ligne.charAt(offset + 6)-'0')*10 + (ligne.charAt(offset + 7)-'0');
				int milliemes=(ligne.charAt(offset + 9)-'0')*100 + (ligne.charAt(offset + 10)-'0')*10 +(ligne.charAt(offset + 11)-'0');

				
				int temps = milliemes + 1000*secondes + 60000 * minutes + 3600000 * heures;
				
				if(ChoixAjouterRetirer==CHOIX_AJOUTER_TEMPS){
					temps += nombreMiliSecondesModification;
				}
				
				else if(ChoixAjouterRetirer==CHOIX_RETIRER_TEMPS){
					temps -= nombreMiliSecondesModification;
				}
				
				
				milliemes = temps %1000;
				secondes = (temps /1000);
				minutes = secondes /60;
				heures = minutes /60;
				
				secondes %= 60;
				minutes  %= 60;
				
				char []  ligneTableau = ligne.toCharArray();
				
				ligneTableau[offset + 0] =	(char) (heures/10 + '0');
				ligneTableau[offset + 1] =  (char)(heures%10 + '0');
				
				ligneTableau[offset + 3] =  (char)(minutes/10+ '0');
				ligneTableau[offset + 4] =  (char)(minutes%10 + '0');
				
				ligneTableau[offset + 6] =  (char)(secondes/10 + '0');
				ligneTableau[offset + 7] =  (char)(secondes%10 + '0');
				
			
				ligneTableau[offset + 9] =  (char)(milliemes/100 + '0');
				ligneTableau[offset + 10] =  (char)((milliemes/10)%10 + '0');
				ligneTableau[offset + 11] =  (char)(milliemes%10 + '0');
				
				
				
				String finale = new String(ligneTableau);
				
				return finale;
	}
}