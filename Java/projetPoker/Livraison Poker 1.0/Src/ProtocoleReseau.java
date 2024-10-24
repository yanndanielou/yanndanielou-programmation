/** Cette classe regrouppe toutes les donnes de configuration, sorte de variables globales accessibles dans toutes les classes*/

public interface ProtocoleReseau{

  String noms[]={"Deux","Trois", "Quatre","Cinq", "Six", "Sept", "Huit", "Neuf", "Dix", "Valet", "Dame", "Roi","As"};
  String couleurs[]={"trefle","carreau","coeur", "pique"};

	int CARTE1_JOUEUR = 1;
	int CARTE2_JOUEUR = CARTE1_JOUEUR + 1;
	
	int CARTE1_COMMUN = CARTE2_JOUEUR +1;

	int CARTE1_JOUEUR_ADVERSE= CARTE1_COMMUN +5;
	int CARTE2_JOUEUR_ADVERSE= CARTE1_JOUEUR_ADVERSE +1;
	
	int ARGENT_JOUEUR = 0;
	int ARGENT_JOUEUR_ADVERSE = 1;
	int ARGENT_POT= 2;
	int MISE_JOUEUR = 3;
	int MISE_JOUEUR_ADVERSE = 4;

	int INITIALISER_CARTES = 0;

	int DONNER_CARTE = 1;
	int DEMANDER_ACTION = DONNER_CARTE +1;
	int ENVOYER_ACTION_CHOISIE = DEMANDER_ACTION +1;

	int AFFICHER_SOMME_ARGENT = ENVOYER_ACTION_CHOISIE +1;
	int ENVOYER_MISE_TOUR = AFFICHER_SOMME_ARGENT +1;
	int ENVOYER_SOMME_POT = ENVOYER_MISE_TOUR +1;
	int ENVOYER_DEFAITE = ENVOYER_SOMME_POT +1;
	int ENVOYER_VICTOIRE = ENVOYER_DEFAITE +1;

	int DEMANDER_NOM_JOUEUR = ENVOYER_VICTOIRE +1;
	int DEMANDER_SOCKET_JOUEUR = DEMANDER_NOM_JOUEUR +1;
	int AJOUTER_TEXTE_SUIVI_PARTIE = DEMANDER_SOCKET_JOUEUR +1;


	int PAS_ASSEZ_ARGENT = AJOUTER_TEXTE_SUIVI_PARTIE +1;
	int RECAVER = PAS_ASSEZ_ARGENT +1;
	int FIN_PARTIE = RECAVER +1;

	String SEPARATEUR_MESSAGE = "_";

	int RELANCE_INTERDITE = 0;
	int RELANCE_PERMISE = 1;

	String NE_RIEN_ENVOYER = "";

	String ACQUITER_MESSAGE = "OK";



}
