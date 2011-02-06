import 	java.util.ArrayList;
import java.util.Scanner;

class Joueur extends Paquet implements VariablesGlobales, ProtocoleReseau{
	
	protected static final byte COMBINAISON_ABSENTE = 0;
	protected static final byte  BASE_CALCULE_MAINS = 14;
  /**Attributs*/
  protected boolean	aJeton;
  protected int		blind;
  protected int		argent;
  protected int		mise;
  protected String	name;

  protected int nbRelancesRestantes;

	private int port;

	private static int nbJoueursInstancies =0;

  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************

  /**
   * Construit un Joueur qui s'appelle name
	* @param	name nom du joueur créé
   */
    public Joueur(String name){
  //    String argentFichierScores;

      super();
      this.aJeton	= false;

      this.mise	= 0;

      this.port = Integer.parseInt(InstanceServeur.prop.getPropriete("port_socket_1")) + nbJoueursInstancies;

      //On demande au joueur de nous envoyer son port
   /*   this.envoyerDonneesJoueur(DEMANDER_SOCKET_JOUEUR, false);	
      this.port = Integer.parseInt(this.recevoirDonneesJoueur());*/

      //On demande au joueur de nous envoyer son nom
      this.envoyerDonneesJoueur(DEMANDER_NOM_JOUEUR, false);	
      this.name = this.recevoirDonneesJoueur();


      this.initArgentDebutPartie();


      this.nbJoueursInstancies++;
    }

  //*****************************************************************************************
  //                                    ACCESSEURS
  //*****************************************************************************************  

  /**
   * indique si le joueur a le jeton
	* @return 	aJeton: true si le joueur a le jeton
   */
  public boolean hasJeton(){
    return this.aJeton;
  }

 /**
  * La somme d'argent que possède le joueur
	* @return 	argent: somme d'argent du joueur (int)
  */
  public int getArgent(){
    return argent;
  }

 /**
  * La somme d'argent misée dans ce tour par le joueur
	* @return 	mise: mise actuelle du joueur(int)
  */
  public int getMise(){
    return mise;
  }

 /**
  * Le nom du joueur
	* @return 	name: nom du joueur(String)
  */
  public String getName(){
    return name;
  }



 //*****************************************************************************************
 //                                    MUTATEURS
 //*****************************************************************************************
 
 /**
  * Définit si le joueur a le jeton
	* @param		 etatJeton vrai si le joueur prend le jeton
  */
  public void setJeton(boolean etatJeton){
	Log.ajouterTrace(name + ": a le jeton: " + etatJeton, INFO);
    this.aJeton = etatJeton;
  }

 /**
  * La somme d'argent que possède le joueur
	* @param		 argent: nouveau montant de l'argent du joueur
  */
  public void setArgent(int argent){
    this.argent = argent;
    InstanceServeur.scores.setArgent(name, "" + argent);
  }

 /**
  * Le nombre de relances que le joueur peut faire dans ce tour de mises
	* @param nbRelancesRestantes
  */
  public void setNbRelancesRestantes(int nbRelancesRestantes){
    this.nbRelancesRestantes = nbRelancesRestantes;
  }

 /*
  * La somme d'argent misée dans ce tour par le joueur
	* @param		 mise: mise du joueur
  
  public void setMise(int mise){
	Log.ajouterTrace(name + ": mise de valeur = " + mise);
    this.mise = mise;
  }*/

//*****************************************************************************************
//                                    METHODES DE CLASSE
//*****************************************************************************************
 
 /**
   * Méthode servant à payer la blind. Le montant dépend si le joueur est petite blind ou grosse blind
	* @return 	sommeAPayer: montant payé par le joueur(int)
	*/
public int payerBlind(){
	int sommePetiteBlind = Integer.parseInt(InstanceServeur.prop.getPropriete("prix_petite_blind"));
	int sommeGrosseBlind = Integer.parseInt(InstanceServeur.prop.getPropriete("prix_grosse_blind"));

  int sommeAPayer = sommePetiteBlind;

  if(this.aJeton)
    sommeAPayer = sommeGrosseBlind;


	Log.ajouterTrace(name + ": paiement de la blind de valeur = " + sommeAPayer, INFO);
  this.miser(sommeAPayer);
  return sommeAPayer;
}


 /**
   * Méthode demandant au joueur l'action qu'il souhaite effectuer.
	* @return 	actionChoisie: PAROLE, SE_COUCHER ou le montant de la mise
   */
public int demanderAction(int miseMinimale, int miseMaximale){
//ATTENTION: il faudra prévoir le cas où le joueur est au tapis!!
  int actionChoisie=PAROLE;
  String messageRecu;

  int peutRelancer = (nbRelancesRestantes > 0) ? 1 : 0;

	this.envoyerDonneesJoueur(DEMANDER_ACTION +  SEPARATEUR_MESSAGE + peutRelancer + SEPARATEUR_MESSAGE + miseMinimale+ SEPARATEUR_MESSAGE + miseMaximale, false);
	
	messageRecu = this.recevoirDonneesJoueur();
	Log.ajouterTrace("joueur veut faire " +messageRecu, DEBUG);

	String[] messageRecuDecoupe = messageRecu.split(SEPARATEUR_MESSAGE);

	if(messageRecuDecoupe[0].equals(""+ENVOYER_ACTION_CHOISIE)){
	    actionChoisie = Integer.parseInt(messageRecuDecoupe[1]);
	}


  return actionChoisie;
}


 /**
   * Méthode réccupérant la somme misée par le joueur et met à jour son argent
	* @return 	mise_joueur: montant des mises de ce tour
  */
public int prendreMise(){

  //on stocke la mise du joueur
  int mise_joueur = mise;
	//On fait payer le joueur
  this.setArgent(argent - mise);

  if(argent < 0)
    argent = 0;

  //on vide la mise que l'on renvoie
   mise = 0;

  Log.ajouterTrace(name + " réccupération mise (somme = " + mise_joueur +  ")", DEBUG);
return mise_joueur;
}

 /**
   * Lorsque le joueur souhaite miser ou relancer
	* @param	mise: mise souhaitée par le joueur
	* @return 	la somme misée
  */
public int miser(int mise){
	Log.ajouterTrace(name + " vous souhaitez miser " + mise, INFO);
  int sommeMisee = mise;

  this.mise = sommeMisee;
  return sommeMisee;
}

 /**
  * Calcule la force de la main du joueur
  * @param	mise: mise souhaitée par le joueur
  * @return 	la somme misée
  */
public long calculerMain(River river){
  CalculMain cartes = new CalculMain(this, river);
return cartes.calculerMain();
}


 /**
  * Décrémente le nombre de relances que le joueur peut faire. Est appellée à chaque fois que le joueur mise ou relance
  */
public void decrementerNbRelances(){
  this.nbRelancesRestantes --;

  if(this.nbRelancesRestantes < 0)
    this.nbRelancesRestantes = 0;  
}

/**
* Regarde dans le fichier des scores si le joueur existe, et s'il n'existe pas le crée avec la bonne valeur d'argent initiale
*/
public void initArgentDebutPartie(){
	int argentFichier;
//On charge l'argent du joueur par le fichier de scores, s'il n'existe pas, on crée un nouveau joueur avec argent = ARGENT_DEBUT_PARTIE
	if(InstanceServeur.scores.getArgent(name) == null)
		InstanceServeur.scores.setArgent(name, "" + ARGENT_DEBUT_PARTIE);


	//Si le joueur n'a pas assez d'argent, on lui demande s'il souhaite recaver
	if(Integer.parseInt(InstanceServeur.scores.getArgent(name)) <= 1){
	  String messageRecu;

	  this.envoyerDonneesJoueur(PAS_ASSEZ_ARGENT, false);
	
	  messageRecu = this.recevoirDonneesJoueur();
	  if(messageRecu.equals(""+RECAVER))
	    InstanceServeur.scores.setArgent(name, "" + ARGENT_DEBUT_PARTIE);
	}

	argentFichier= Integer.parseInt(InstanceServeur.scores.getArgent(name));
		
	this.argent = argentFichier;

	this.envoyerSommeArgent(ARGENT_JOUEUR, argent);

}

/**
* Affiche dans le fichier log
*/
public void afficher(int niveauDebug){
	Log.ajouterTrace(this.toString(), niveauDebug);
}
/**
* Renvoie une chaine décrivant le joueur
* @return 	chaine de caractere contenant les parametres du joueur
*/
public String toString(){
return name + " est le port " + port + "\n"+  super.toString();
}
	

/**
* envoie la somme d'argent du joueur pour l'afficher
* @param	carteAEnvoyer: Carte a envoyer
* @param	typeCarte: type de la carte (joueur, flop, ...)
*/	
public void envoyerSommeArgent(int typeArgent, int sommeArgent){
	this.envoyerDonneesJoueur(AFFICHER_SOMME_ARGENT + SEPARATEUR_MESSAGE + typeArgent + SEPARATEUR_MESSAGE +  sommeArgent, true);
}
/**
* envoie une carte au joueur pour l'afficher
* @param	carteAEnvoyer: Carte a envoyer
* @param	typeCarte: type de la carte (joueur, flop, ...)
*/	
public void envoyerCarte(Carte carteAEnvoyer, int typeCarte){
	this.envoyerDonneesJoueur(DONNER_CARTE + SEPARATEUR_MESSAGE + typeCarte + SEPARATEUR_MESSAGE + carteAEnvoyer.getNumeroCarte(), true);
}
/**
* envoie une chaine String au serveur
* @param	message: message a envoyer
*/
public void envoyerDonneesJoueur(String message, boolean attendreAcquitement){
  InstanceServeur.serveur.envoyerDonnees(port, message);

    if(attendreAcquitement)
      this.recevoirDonneesJoueur();
}


/**
* envoie un message à afficher dans la zone de suivie de la partie. On demande et attend l'acquitement
* @param	texte: texte à envoyer et à afficher dans le JTextArea
*/	
public void envoyerTexteSuiviPartie(String texte){
	this.envoyerDonneesJoueur(AJOUTER_TEXTE_SUIVI_PARTIE + SEPARATEUR_MESSAGE + texte, true);
}

/**
* envoie un entier Int au serveur
* @param	message: nombre a envoyer
*/
public void envoyerDonneesJoueur(int message, boolean attendreAcquitement){
	this.envoyerDonneesJoueur("" + message, attendreAcquitement);
}

/**
* attend de recevoir un message du serveur
* @return 	le message envoyé par le serveur
*/
public String recevoirDonneesJoueur(){
	return InstanceServeur.serveur.recevoirDonnees(port);
}




}