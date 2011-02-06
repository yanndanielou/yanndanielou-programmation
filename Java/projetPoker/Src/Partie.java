
/**
  *	Classe : Partie
  *	Super classe: aucune
  *	Classes filles: aucune
  *	Interface: VariablesGlobales, ProtocoleReseau
  *	Description: classe gerant le déroulement d'une partie
  * 	Auteur:	Yann Danielou
  */
class Partie implements VariablesGlobales, VariablesTraduction, ProtocoleReseau{

  private static final int FIN_DONNE = -1;

  protected Joueur	joueur1 ;
  protected Joueur	joueur2 ;
  protected int		pot;
  protected River	river;

  protected static final int cartesFlop = 3;
  protected static final int carteTournant = 4;
  protected static final int carteRiver = 5;


  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************
 /**
   * Constructeur par défaut
   */
  public Partie(){
    this.joueur1 = new Joueur("joueur 1");
    this.joueur2 = new Joueur("joueur 2");
    this.pot	 = 0;
    this.river	 = new River();
  }


 //*****************************************************************************************
  //                                    Accesseurs
  //*****************************************************************************************	
  /**
   * Retourne le joueur 1
  * @return Joueur joueur1
   */
  public Joueur getJoueur1(){
    return joueur1;		
  }

  /**
   * Retourne le joueur 2
    * @return Joueur joueur2
   */
  public Joueur getJoueur2(){
    return joueur2;		
  }


//*****************************************************************************************
//                                    Méthodes
//*****************************************************************************************
 /**
   * Début d'une nouvelle partie
   */
public void demmarrer(Joueur ancienJeton, Joueur nouveauJeton){

  long mainAncienJeton;
  long mainNouveauJeton;

  int sommeGrosseBlind = Integer.parseInt(InstanceServeur.prop.getPropriete("prix_grosse_blind"));


  this.initTourJeu();


  /**On définit quel joueur a le jeton*/
  ancienJeton.setJeton(true);	//Donc grosse blind
  nouveauJeton.setJeton(false);	//Donc petite blind

  /**Les joueurs payent les blinds*/
  ancienJeton.payerBlind();
  nouveauJeton.payerBlind();


  /**Distribution des cartes aux joueurs et à la river*/
  this.distribuer();

 Log.ajouterTrace("voici les paquets:", INFO);


  /**Debuggage: affichage des paquets*/
 Log.ajouterTrace("joueur 1", INFO);
  ancienJeton.afficher(INFO);
 Log.ajouterTrace("joueur 2", INFO);
  nouveauJeton.afficher(INFO);

  /**Debuggage: affichage de la river*/
 Log.ajouterTrace("River", INFO);
  river.afficher(INFO);



/**--------------------------------------------------Premier tour (aucune carte retournée)------------------------------------------*/
  /**Demande du choix de l'action au joueur de la petite blind: peut suivre, relancer ou se coucher*/


	//On envoie aux joueurs toutes les sommes d'argent
	this.afficherArgentPartie();	

	if(this.jouerTour(ancienJeton, nouveauJeton, TOUR_NUM_0,sommeGrosseBlind) == FIN_DONNE)
			return;
	

/**--------------------------------------------------Deuxième tour (3 cartes retournée)---------------------------------------------*/
/**Demande du choix de l'action au joueur suivant la grosse blind (ici, la petite blind): peut parole, miser ou se coucher*/
	
	this.devoilerCartes(cartesFlop);
		if(this.jouerTour(ancienJeton, nouveauJeton, TOUR_NUM_1,PAROLE) == FIN_DONNE)
			return;


/**--------------------------------------------------Troisième tour (4 cartes retournées)---------------------------------------------*/
/**Demande du choix de l'action au joueur suivant la grosse blind (ici, la petite blind): peut parole, miser ou se coucher*/

	this.devoilerCartes(carteTournant);
		if(this.jouerTour(ancienJeton, nouveauJeton, TOUR_NUM_2,PAROLE) == FIN_DONNE)
			return;


/**--------------------------------------------------Troisième tour (5 cartes retournées)---------------------------------------------*/
/**Demande du choix de l'action au joueur suivant la grosse blind (ici, la petite blind): peut parole, miser ou se coucher*/

	this.devoilerCartes(carteRiver);
		if(this.jouerTour(ancienJeton, nouveauJeton, TOUR_NUM_3,PAROLE) == FIN_DONNE)
			return;


  //On affiche la carte qu'a l'autre joueur
    for(int i=0; i<2; i++){
     ancienJeton.envoyerCarte(nouveauJeton.getCarte(i), CARTE1_JOUEUR_ADVERSE + i);
     nouveauJeton.envoyerCarte(ancienJeton.getCarte(i), CARTE1_JOUEUR_ADVERSE + i);
    }

try {	Thread.sleep(10000);} catch (InterruptedException e) {	}

    mainAncienJeton = ancienJeton.calculerMain(river);
    mainNouveauJeton = nouveauJeton.calculerMain(river);


    if(mainAncienJeton > mainNouveauJeton){
      this.fin_partie(ancienJeton, nouveauJeton, false);
    }
    else if (mainNouveauJeton > mainAncienJeton){
      this.fin_partie(nouveauJeton, ancienJeton, false);
    }
    else{
      this.fin_partie(nouveauJeton, ancienJeton, true);
    }

}


 /**
   * En cas de fin de la donne, on donne l'argent du pot au gagnant
	* @param	joueurGagnant Joueur qui gagne la partie (la donne)
   */
private void fin_partie(Joueur joueurGagnant,Joueur joueurPerdant, boolean egalite){
  this.reccupererMises();

Log.ajouterTrace("Fin de la partie", INFO);

/*	 joueurGagnant.envoyerTexteSuiviPartie("Fin du tour de mises");
	 joueurPerdant.envoyerTexteSuiviPartie("Fin du tour de mises");
*/
  if(egalite){
	 joueurGagnant.envoyerTexteSuiviPartie(""+trad_egalite);
	 joueurPerdant.envoyerTexteSuiviPartie(""+trad_egalite);

    Log.ajouterTrace("il y a égalité, partage du pot", INFO);
    joueurGagnant.setArgent(joueurGagnant.getArgent() + pot/2);	
    joueurPerdant.setArgent(joueurPerdant.getArgent() + pot/2);	
  }
  else{
	 joueurGagnant.envoyerTexteSuiviPartie(joueurGagnant.getName() + SEPARATEUR_MESSAGE +trad_gagne + SEPARATEUR_MESSAGE + pot + " euros");
	 joueurPerdant.envoyerTexteSuiviPartie(joueurGagnant.getName() + SEPARATEUR_MESSAGE +trad_gagne +SEPARATEUR_MESSAGE+ pot + " euros");
    Log.ajouterTrace("il n'y a pas égalité: joueur gagnant: " + joueurGagnant.getName(), INFO);
    joueurGagnant.setArgent(joueurGagnant.getArgent() + pot);	
  }

  if((!egalite) && (joueurPerdant.getArgent() <= 1)){
    /*  System.out.println("effacement joueur");
      InstanceServeur.scores.effacerJoueur(joueurPerdant.getName());

      System.out.println("result " + InstanceServeur.scores.getArgent(joueurPerdant.getName()));*/
  }

  pot = 0;
}

 /**
   * Réccupere les mises des joueurs et les ajoute au pot en enlevant la somme des mises à l'argent des joueurs
   */
private void reccupererMises(){
 Log.ajouterTrace("Réccupération des mises des joueurs", FULL_DEBUG);
  pot += joueur1.prendreMise();
  pot += joueur2.prendreMise();
}


 /**
   * Distribue les cartes aux joueurs et les cartes communes
   */
private void distribuer(){
  Log.ajouterTrace("Distribution du paquets", INFO);

  //On crée un paquet de 52 cartes trié qui sera distribué
  Paquet pioche = new Paquet(52);
  //On mélange ce paquet
  pioche.melanger();

//On distribue les deux cartes aux joueurs (une par une)	
  for(int i=0; i<2; i++){
    this.joueur1.ajouterCarte(pioche.donner_carte());
	joueur1.envoyerCarte(joueur1.getCarte(i), CARTE1_JOUEUR +i);
    this.joueur2.ajouterCarte(pioche.donner_carte());	
	joueur2.envoyerCarte(joueur2.getCarte(i), CARTE1_JOUEUR +i);
  }
	

	
 /* Pour les cartes communes, voici la séquence de distribution:
    * on brule une carte
    * on donne 3 cartes au flop
    * on rebrule une carte
    * on donne la carte du tournant (4eme carte commune)
    * on rebrule une carte
    * on donne la carte de la river (5eme carte commune)
*/
  //carte brulée
 pioche.carteMorte();

  for(int i=0; i<5; i++){
    if(i >= 3)
      pioche.carteMorte();
    this.river.ajouterCarte(pioche.donner_carte());
  }
  river.cacherCartes();
  
}

 /**
   * Joue un tour de mise (il peut y en avoir plusieurs par tour de jeu (autant que de nombre de relance des joueurs)
	* @param	j1 Joueur qui a la parole
	* @param	j2 Joueur qui n'a pas la parole
	* @param	sommeDepart  argent misé par le Joueur j2
	* @return FIN_DONNE s'il ne reste qu'un joueur en jeu, RAS sinon
   */
private int tourMise(Joueur j1, Joueur j2, int sommeDepart){
 Log.ajouterTrace("Entrée fonction tourMise avec somme de départ = " + sommeDepart, DEBUG);
	int choixJ1;
	int choixJ2= sommeDepart;

	//On envoie aux joueurs toutes les sommes d'argent
	this.afficherArgentPartie();

    choixJ1 = j1.demanderAction(sommeDepart, this.calculerMiseMaximale());
		//La grosse blind se couche
		if(choixJ1 == SE_COUCHER){
			Log.ajouterTrace("le joueur " +j1.getName() + " se couche", INFO);
			this.fin_partie(j2, j1, false);
			return FIN_DONNE;
		}
		//La grosse blind relance
		else if(choixJ1 > choixJ2){
			Log.ajouterTrace("le joueur " +j1.getName() + " relance", INFO);
			j1.miser(choixJ1);
			j1.decrementerNbRelances();
			if(this.tourMise(j2,j1,choixJ1) == FIN_DONNE)
				return FIN_DONNE;
		}
		//lLa grosse blind suit
		else{ //on a donc choixJ1 == choixJ2
			Log.ajouterTrace("le joueur " +j1.getName() + " suit", INFO);
			j1.miser(choixJ1);
		}
	return RAS;
}

 /**
   * Joue un tour de jeu
	* @param	grosseBlind Joueur qui est la grosse blind
	* @param	petiteBlind Joueur qui est la petite blind
	* @param	numeroTour  numéro du tour actuel (1er tour, 2eme, ..?)
	* @param	sommeDepart somme misée avant le tour part un des joueurs (correspond aux blinds)
	* @return FIN_DONNE s'il ne reste qu'un joueur en jeu, RAS sinon
   */
private int jouerTour(Joueur grosseBlind, Joueur petiteBlind, int numeroTour, int sommeDepart){
	Log.ajouterTrace("\nDébut du tour num " + numeroTour, INFO);
  
  //On remet à la valeur initiale le compteur du nombre de relances
	grosseBlind.setNbRelancesRestantes(3);
    petiteBlind.setNbRelancesRestantes(3);

	grosseBlind.envoyerTexteSuiviPartie(trad_debutTour + SEPARATEUR_MESSAGE + numeroTour);
	petiteBlind.envoyerTexteSuiviPartie(trad_debutTour + SEPARATEUR_MESSAGE + numeroTour);

  int choixPetiteBlind = petiteBlind.demanderAction(sommeDepart,this.calculerMiseMaximale());

	//On envoie aux joueurs toutes les sommes d'argent
	this.afficherArgentPartie();

  //la petite blind peut soit se coucher, la partie est alors finie
  if(choixPetiteBlind == SE_COUCHER){
    this.fin_partie(grosseBlind, petiteBlind, false);
    return FIN_DONNE;
  }
  //Dans ce cas,la petite blind fait parole
  else if(choixPetiteBlind == sommeDepart){
		petiteBlind.miser(choixPetiteBlind);
		Log.ajouterTrace("la petite blind " + "(" + petiteBlind.getName() + ") checke ou suit", INFO);
		if(this.tourMise(grosseBlind, petiteBlind, choixPetiteBlind) == FIN_DONNE)
			return FIN_DONNE;
	}
	else{ // choixPetiteBlind > 0
		Log.ajouterTrace("la petite blind " + "(" + petiteBlind.getName() + ") relance avec " + choixPetiteBlind, INFO);
		petiteBlind.miser(choixPetiteBlind);
		petiteBlind.decrementerNbRelances();
		if(this.tourMise(grosseBlind, petiteBlind, choixPetiteBlind) == FIN_DONNE)
			return FIN_DONNE;
	}

  Log.ajouterTrace("Fin du tour num " + numeroTour, INFO);
  this.reccupererMises();


	//On envoie aux joueurs toutes les sommes d'argent
	this.afficherArgentPartie();

	return RAS;
}


 /**
   * Dévoile (retourne face visibile) les cartes en commun (cartes du tableau)
	* @param	nbCartes nombre de cartes à afficher (on affiche les cartes de 0 à nbCartes)
   */
private void devoilerCartes(int nbCarte){
int premiereCarte = nbCarte-1;
river.devoilerCartes(nbCarte);

   if(nbCarte == cartesFlop)
      premiereCarte = 0;

  for(int i=premiereCarte; i< nbCarte; i++){
    joueur1.envoyerCarte(river.getCarte(i), CARTE1_COMMUN +i);
    joueur2.envoyerCarte(river.getCarte(i), CARTE1_COMMUN +i);	
  }

}

 /**
   * Envoie aux joueurs toutes les sommes d'argent de la partie, pour qu'ils les affichent
   */
private void afficherArgentPartie(){
	//Affiche pour chaque joueur sa somme du pot
	joueur1.envoyerSommeArgent(ARGENT_POT, this.pot);
	joueur2.envoyerSommeArgent(ARGENT_POT, this.pot);

	//Affiche pour chaque joueur sa somme d'argent
	joueur1.envoyerSommeArgent(ARGENT_JOUEUR, joueur1.getArgent());
	joueur2.envoyerSommeArgent(ARGENT_JOUEUR, joueur2.getArgent());

	//Affiche pour chaque joueur la somme d'argent de l'autre joueur
	joueur1.envoyerSommeArgent(ARGENT_JOUEUR_ADVERSE, joueur2.getArgent());
	joueur2.envoyerSommeArgent(ARGENT_JOUEUR_ADVERSE, joueur1.getArgent());

	//Affiche pour chaque joueur la mise actuelle
	joueur1.envoyerSommeArgent(MISE_JOUEUR, joueur1.getMise());
	joueur2.envoyerSommeArgent(MISE_JOUEUR, joueur2.getMise());

	//Affiche pour chaque joueur la mise actuelle de l'autre joueur
	joueur1.envoyerSommeArgent(MISE_JOUEUR_ADVERSE, joueur2.getMise());
	joueur2.envoyerSommeArgent(MISE_JOUEUR_ADVERSE, joueur1.getMise());
}

 /**
   * Calcule la mise maximale que peuvent miser les joueurs (= maximum d'argent que possède le plus pauvre des joueurs)
   */
private int calculerMiseMaximale(){
  return (joueur1.getArgent() < joueur2.getArgent()) ? joueur1.getArgent() : joueur2.getArgent();
}
 /**
   * Calcule la mise maximale que peuvent miser les joueurs (= maximum d'argent que possède le plus pauvre des joueurs)
   */
private void initTourJeu(){

  joueur1.initArgentDebutPartie();
  joueur2.initArgentDebutPartie();


  joueur1.viderPaquet();
  joueur2.viderPaquet();
  river.viderPaquet();

  joueur1.envoyerDonneesJoueur(INITIALISER_CARTES, true);
  joueur2.envoyerDonneesJoueur(INITIALISER_CARTES, true);

    

}


}