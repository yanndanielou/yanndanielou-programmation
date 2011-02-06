//A partir de java.io
import java.io.RandomAccessFile; 
import java.io.FileWriter;
import java.io.File;

import java.util.Date;


class Log implements VariablesGlobales{

	private static int niveauTraces = INFO;
	private static String nomLogServeur1 = "serveur01.log";
	private static String nomLogServeur2= "serveur02.log";
	private static String nomFichierLog;


  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************

 /**
   * Constructeur déterminant le nom du fichier log à créer
   */
	public Log(){
		/*Le cahier des charges  spécifie que l'on doit avoir deux fichiers Log et de les utiliser alternativement afin d'avoir un historique des
		  deux dernières éxécutions du jeu de Poker.

			On va donc utiliser pour fichier log le plus vieux des deux afin de garder celui de la dernière éxécution et de celle actuelle
			Tout d'abord, on regarde si les deux fichiers existent. Si l'un d'entre eux n'éxiste pas, ce sera celui là
			Ensuite, si les deux existent, on prendra celui qui a été modifié il y a le plus de temps*/

			try {
			String tracesAAjouter = "Determination du fichier Log à utiliser\n";

			File fichierLog1 = new File(this.nomLogServeur1);
			File fichierLog2 = new File(this.nomLogServeur2);

			//Si le fichier fichierLog1 n'existe pas, alors c'est celui-ci qui devient le fichier log
			if(fichierLog1.exists() == false){
				tracesAAjouter+= "Le fichier " + nomLogServeur1 + " n'existe pas, il est donc le prochain fichier LOG\n";
				this.nomFichierLog = this.nomLogServeur1;
			}

			//Si le fichier fichierLog2 n'existe pas, alors c'est celui-ci qui devient le fichier log
			else if(fichierLog2.exists() == false){
				tracesAAjouter+= "Le fichier " + nomLogServeur2 + " n'existe pas, il est donc le prochain fichier LOG\n";
				this.nomFichierLog = this.nomLogServeur2;
			}

			/*Si les deux fichiers existent, on compare leur date de dernière modification. Pour cela, on fait appel à la méthode lastModified
			qui retourne en millisecondes la dernière fois qu'il a été modifié depuis le 1er Janvier 70
			Le fichier le plus vieux est donc celui qui a été modifié le plus proche de 70, donc celui dont la méthode lastModified retournera le plus petit nombre*/
			else if(fichierLog1.lastModified() <= fichierLog2.lastModified()){
				tracesAAjouter+= "Les deux fichiers " + nomLogServeur1 + " et " + nomLogServeur2 + " existent mais " + fichierLog1 + " est plus vieux donc il devient le prochain Log\n";
				fichierLog1.delete();
				this.nomFichierLog = this.nomLogServeur1;
			}

			else{
				tracesAAjouter+= "Les deux fichiers " + nomLogServeur1 + " et " + nomLogServeur2 + " existent mais " + fichierLog2 + " est plus vieux donc il devient le prochain Log\n";
				fichierLog2.delete();
				this.nomFichierLog = this.nomLogServeur2;
			}

		fichierLog1 = null;
		fichierLog2 = null;

			ajouterTrace(tracesAAjouter, DEBUG);
		}
		 catch (Exception e) { }

		String niveauDebug = InstanceServeur.prop.getPropriete("niveau_debug");
		System.out.println("debug: " + niveauDebug + " = " + this.niveauTraces);


		for (int i=0; i< niveauxDebug.length; i++){
			if(niveauxDebug[i].equals(niveauDebug)){
				this.niveauTraces = i;
				break;
			}
		}


		System.out.println("debug: " + niveauDebug + " = " + this.niveauTraces);


	}


  //*****************************************************************************************
  //                                    Méthodes
  //*****************************************************************************************	

  /**
   * Ajoute une trace au fichier de traces
	* @param	trace	String contenant le message, la trace à ajouter
	* @param niveauTrace	Int représentant le niveau de la trace, c'est à dire son importance. Définit si la trace doit être ajoutée ou non, en fonction de this.niveauTraces
   */
  public static void ajouterTrace(String trace, int niveauTrace){

	if(niveauTrace <= niveauTraces){
		try {
		FileWriter fw = new FileWriter(nomFichierLog,true);	
		fw.write(new Date() + " " + niveauxDebug[niveauTrace] + " " + trace + "\n");

		fw.close();
		}
    catch (Exception e) { }
	}
  }

}
