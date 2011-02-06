/** Cette classe regrouppe toutes les données de configuration, sorte de variables globales accessibles dans toutes les classes*/

public interface VariablesGlobales{

   int ARGENT_DEBUT_PARTIE = 200;


	//Retour d'une fonction qui n'a rien à dire
	 int RAS = 0;


  //Choix possibles de l'utilisateur 
   int SE_COUCHER	= -1;
   int PAROLE	= 0;

  //Numéro du tour
   int TOUR_NUM_0	= 0;
   int TOUR_NUM_1	= 1;
   int TOUR_NUM_2	= 2;
   int TOUR_NUM_3	= 3;

   int ATTENTE_ACTION = -2;


/** Niveaux de debug*/
   int MUET = 0; 
   int INFO  = MUET + 1;
   int DEBUG = INFO + 1;
   int FULL_DEBUG = DEBUG + 1;

   String [] niveauxDebug = {"MUET", "INFO", "DEBUG", "FULL DEBUG"};

}
