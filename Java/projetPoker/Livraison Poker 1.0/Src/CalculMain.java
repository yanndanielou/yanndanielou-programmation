import 	java.util.ArrayList;

/**
  *	Classe : CalculMain
  *	Super classe: Joueur
  *	Classes filles: aucune
  *	Interface: aucune
  *	Description: classe servant a calculer la main d'un joueur
  * 	Auteur:	Yann Danielou
  */
class CalculMain extends Joueur implements ProtocoleReseau, VariablesTraduction{

	private static final byte  PUISSANCE_PAIRE 			= 5;
	private static final byte  PUISSANCE_DOUBLE_PAIRE	= PUISSANCE_PAIRE +1;
	private static final byte  PUISSANCE_BRELAN 		= PUISSANCE_DOUBLE_PAIRE +1;
	private static final byte  PUISSANCE_SUITE 			= PUISSANCE_BRELAN +1;
	private static final byte  PUISSANCE_COULEUR 		= PUISSANCE_SUITE +1;
	private static final byte  PUISSANCE_FULL 			= PUISSANCE_COULEUR +1;
	private static final byte  PUISSANCE_CARRE 			= PUISSANCE_FULL +1;
	private static final byte  PUISSANCE_QUINT_FLUSH	= PUISSANCE_CARRE +1;
	private static final byte  PUISSANCE_KICKER_1 		= 0;
	private static final byte  PUISSANCE_KICKER_2 		= PUISSANCE_KICKER_1 +1;
	
	private static final byte  VALEUR_AS 					= 13;
	
	private ArrayList<Carte> sauvegardeCartes;
	
	private Joueur joueur;


  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************
 /**
   * Constructeur
	* @param	joueur: le joueur dont on veut calculer la main
	* @param	river: les cinq Cartes de la river
  */
	public CalculMain(Joueur joueur, River river){
	super(joueur.getName());

	//On sauvegarde le joueur, sert à envoyer des messages
	this.joueur = joueur;

	sauvegardeCartes = new ArrayList<Carte>();

		for(int i=0; i< 2; i++)
			Cartes.add(joueur.getCarte(i));
		for(int i=0; i< 5; i++)
			Cartes.add(river.getCarte(i));
	}
	

	
//*****************************************************************************************
//                                    METHODES DE CLASSE
//*****************************************************************************************

 /**
   * Calcule la valeur de la main du joueur
	* @param	river: les cinq Cartes de la river
	* @return 	un nombre correspondant à la force de la main
  */
public long calculerMain(){
	
//  this.verifSuite();
	this.trierCartes();
	
	
	
 Log.ajouterTrace("affichage des Cartes après tri: \n" + this.toString(), FULL_DEBUG);
	
	long valeurMain;
	// Teste la quint flush
	if((valeurMain = this.calculerQuintFlush()) != COMBINAISON_ABSENTE){
	}
	// Teste le full
	else if((valeurMain = this.calculerFull()) != COMBINAISON_ABSENTE){
	}
	// Teste la couleur
	else if((valeurMain = this.calculerCouleur()) != COMBINAISON_ABSENTE){
	}
	// Teste la suite
	else if((valeurMain = this.calculerSuite()) != COMBINAISON_ABSENTE){
	}
	// Teste le brelan
	else if((valeurMain = this.calculerBrelan()) != COMBINAISON_ABSENTE){
	}
	// Teste la paire et la double paire
	else if((valeurMain = this.calculerPaires()) != COMBINAISON_ABSENTE){
	}
	//Aucune combinaison, la valeur de la main est juste la valeur des 5 cartes les plus fortes du joueur
	else{
		valeurMain = this.calculerPasCombinaison();
	}
		
  return valeurMain;
}

/**
   * Teste si le joueur a une quint flush (1 suite + 1 couleur avec les mêmes cartes : cinq cartes se suivant de la même couleur)
	* @return 	valeurQuintFlush: nombre représentant la force de la quint flush (dépend uniquement de la carte la plus forte de la quint)
  */
private long calculerQuintFlush(){
 Log.ajouterTrace(name + " a une quint flush? ", DEBUG);

//Premièrement, on sauvegarde les cartes du joueur. En effet, la recherche de la couleur va supprimer les cartes dans la main du joueur
//Et si le joueur n'a pas de suite, il faudra lui rendre
this.sauvegarderCartes();

long valeurCouleur = this.calculerCouleur(PUISSANCE_QUINT_FLUSH, false);

if(valeurCouleur  == COMBINAISON_ABSENTE){
	 Log.ajouterTrace("non, car pas de couleur", DEBUG);
	return valeurCouleur;
}

long valeurSuite = this.calculerSuite();
if(valeurSuite == COMBINAISON_ABSENTE){
	Log.ajouterTrace("non, car couleur ok mais pas de suite", DEBUG);
	this.restaurerCartes();
	return valeurSuite;
}

long valeurQuintFlush = valeurCouleur + valeurSuite;
Log.ajouterTrace("oui, il a une quint flush de valeur " + valeurQuintFlush, DEBUG);
joueur.envoyerTexteSuiviPartie(trad_vousAvez + SEPARATEUR_MESSAGE + trad_quintFlush);

return valeurQuintFlush;
}


/**
   * Teste si le joueur a un carré (4 cartes identiques)
	* @return 	valeurCarre: nombre reprèsentant la force du carré (dépend de la carte du carré et du kicker)
  */
private long calculerCarre(){
//Premièrement, on sauvegarde les cartes du joueur. En effet, la recherche du brelan va supprimer les cartes dans la main du joueur
//Et si le joueur n'a pas de full, il faudra lui rendre
Log.ajouterTrace(name + " a un carre? ", DEBUG);


//On cherche ensuite le brelan
long valeurCarre = this.trouverCartesIdentiques(4) * ((long)Math.pow(BASE_CALCULE_MAINS, PUISSANCE_CARRE));

if(valeurCarre == COMBINAISON_ABSENTE){
	Log.ajouterTrace("non, pas de carre", DEBUG);
	return (long) COMBINAISON_ABSENTE;
}
else{
		valeurCarre += this.calculKickers();
		Log.ajouterTrace("oui, un carré de valeur " + valeurCarre, DEBUG);
		joueur.envoyerTexteSuiviPartie(trad_vousAvez + SEPARATEUR_MESSAGE + trad_carre);
	}
return valeurCarre;
}


/**
   * Teste si le joueur a un full (1 brelan + 1 paire)
	* @return 	valeurFull: nombre reprèsentant la force du full (le brelan a plus d'importance que la paire)
  */
private long calculerFull(){
//Premièrement, on sauvegarde les cartes du joueur. En effet, la recherche du brelan va supprimer les cartes dans la main du joueur
//Et si le joueur n'a pas de full, il faudra lui rendre
long valeurFull;
this.sauvegarderCartes();
Log.ajouterTrace(name + " a un full? ", DEBUG);


//On cherche ensuite le brelan
long valeurBrelan = this.trouverCartesIdentiques(3) * ((long)Math.pow(BASE_CALCULE_MAINS, PUISSANCE_FULL));

if(valeurBrelan == COMBINAISON_ABSENTE){
	Log.ajouterTrace("non, car pas de brelan", DEBUG);
	return (long) COMBINAISON_ABSENTE;
}

else{
	//On cherche la paire
	long valeurPaire = this.trouverCartesIdentiques(2) * ((long)Math.pow(BASE_CALCULE_MAINS, PUISSANCE_KICKER_1));
	if(valeurPaire == COMBINAISON_ABSENTE){
		Log.ajouterTrace("non, car brelan ok mais pas de paire", DEBUG);
		this.restaurerCartes();
		return (long) COMBINAISON_ABSENTE;
	}
	valeurFull = valeurBrelan + valeurPaire;
	Log.ajouterTrace("oui, un full de valeur " + valeurFull, DEBUG);
	joueur.envoyerTexteSuiviPartie(trad_vousAvez + SEPARATEUR_MESSAGE + trad_full);
}
return valeurFull;	
}

/**
   * Teste si le joueur a une couleur (5 cartes de la mème couleur)
	* @return 	valeurCouleur: nombre reprèsentant la fovrce de la suite (dèpend uniquement de la carte la plus forte de la suite)
  */
private long calculerCouleur(){
	return this.calculerCouleur(PUISSANCE_COULEUR, true);
}

/**
   * Teste si le joueur a une couleur (5 cartes de la mème couleur). Surcharge la méthode long calculerCouleur()
	* @param	puissanceCouleur puissance à laquelle on doit élever la force de la couleur (dépend si on calcule de la couleur ou la quint flush)
	* @param	calculerKicker définit si l'on doit calculer les kickers ou juste la carte la plus forte(dépend si on calcule de la couleur ou la quint flush)
	* @return 	valeurCouleur: nombre reprèsentant la force de la couleur (dépend des  cartes les plus fortes de la même couleur)
  */
private long calculerCouleur(byte puissanceCouleur, boolean calculerKicker){
//On cherche 5 cartes à la suite dans le jeu du joueur
Log.ajouterTrace(name + " a une couleur? ", DEBUG);
int nbCartesParCouleurs [] = {0,0,0,0};
int couleurPresente = 0;

	for(int i=0; i< Cartes.size(); i++){
		nbCartesParCouleurs[Cartes.get(i).getValeurCouleur()]++;
	}	
	for(int i=0; i<nbCartesParCouleurs.length; i++){
		if(nbCartesParCouleurs[i] >=5){
			couleurPresente = i+1;
			break;
		}
	}	
	if(couleurPresente == 0){
		Log.ajouterTrace("non, pas de couleur", DEBUG);
		return (long)COMBINAISON_ABSENTE;
	}
		
	//On supprime les cartes qui ne sont pas de la couleur
	boolean carteSupprimmee = true;	
	while(carteSupprimmee){
		carteSupprimmee = false;
		for(int i=0; i< Cartes.size(); i++){
			if(Cartes.get(i).getValeurCouleur() != couleurPresente -1){
				carteSupprimmee = true;
				Cartes.remove(i);
			}
		}
	}
	//this.afficher();
	
	//Enfin, on calcule la valeur de la couleur, on prend ses cartes les plus fortes
	long valeurCouleur;
	if(calculerKicker){
	valeurCouleur = Cartes.remove(0).getValeur() *  ( (long) Math.pow(BASE_CALCULE_MAINS, PUISSANCE_COULEUR));
	valeurCouleur += this.calculKickers(4);
	}
	else{
	valeurCouleur = Cartes.get(0).getValeur() *  ( (long) Math.pow(BASE_CALCULE_MAINS, puissanceCouleur));
	}
	
	Log.ajouterTrace(" oui, couleur de valeur " + valeurCouleur, DEBUG);
	joueur.envoyerTexteSuiviPartie(trad_vousAvez + SEPARATEUR_MESSAGE + trad_couleur);
	return valeurCouleur;
}

/**
   * Teste si le joueur a une suite (5 cartes qui se suivent)
	* @return 	valeurSuite: nombre reprèsentant la force de la suite (dèpend uniquement de la carte la plus forte de la suite), sinon  main, sinon COMBINAISON_ABSENTE
  */
private long calculerSuite(){
//On cherche 5 cartes è la suite dans le jeu du joueur

Log.ajouterTrace(name + " a une suite? ", DEBUG);
	for(int i=0; i< Cartes.size(); i++){
	int j=i+1;
	int cartesSeSuivant = 1;
	int derniereValeur=Cartes.get(i).getValeur();
		//On compte le nombre de cartes se suivant dans l'ordre dècroissant è partir du rang i de l'ArrayList
		while((j<Cartes.size()) && (cartesSeSuivant<5)){
			if(Cartes.get(j).getValeur() == derniereValeur-1){
				cartesSeSuivant++;
				derniereValeur = Cartes.get(j).getValeur();
			}
			else if(Cartes.get(j).getValeur() != derniereValeur)
				break;
				
			j++;
			//Log.ajouterTrace(cartesSeSuivant + " cartes se suivent");
		}
		if(cartesSeSuivant == 5){
			//La valeur de la suite est la valeur de la plus grosse carte de la suite
			long valeurSuite = Cartes.get(i).getValeur() * ((long)Math.pow(BASE_CALCULE_MAINS, PUISSANCE_SUITE));
			Log.ajouterTrace("oui, suite de valeur " + valeurSuite, DEBUG);
			joueur.envoyerTexteSuiviPartie(trad_vousAvez + SEPARATEUR_MESSAGE + trad_suite);
			return valeurSuite;
		}
		//Puisque l'AS peut composer une suite haute ou basse, on teste ici si le joueur a l'as et que l'on a une suite basse(2,3,4,5)
		else if((cartesSeSuivant == 4) && (this.aCarteDeValeur(VALEUR_AS)) && (Cartes.get(i).getValeur() == 4)){
			long valeurSuite = Cartes.get(i).getValeur() * ((long)Math.pow(BASE_CALCULE_MAINS, PUISSANCE_SUITE));	
			Log.ajouterTrace("oui, suite de valeur " + valeurSuite, DEBUG);
			joueur.envoyerTexteSuiviPartie(trad_vousAvez + SEPARATEUR_MESSAGE + trad_suite);
			return valeurSuite;
		}
	}
Log.ajouterTrace("non, pas de suite", DEBUG);
return (long)COMBINAISON_ABSENTE;
}


/**
   * Teste si le joueur a un brelan (trois cartes identiques). Si oui, renvoie valeur de la main (= valeur brelan + 2 kickers), sinon COMBINAISON_ABSENTE
	* @return 	valeurBrelan image des cartes composant le brelan et des deux kickers
  */
private long calculerBrelan(){
//On sait que si le joueur a une paire, c'est la seule qu'il a (on a dèja testè la double paire).
//les cartes ètant classèes par valeur dècroissante, on sait que la première paire trouvèe sera la plus forte

Log.ajouterTrace(name + " a un brelan? ", DEBUG);

long valeurBrelan= this.trouverCartesIdentiques(3) * ((long)Math.pow(BASE_CALCULE_MAINS, PUISSANCE_BRELAN));
	//Le joueur n'a pas de brelan
	if(valeurBrelan == COMBINAISON_ABSENTE){
		Log.ajouterTrace("non, pas de brelan", DEBUG);
		return (long)COMBINAISON_ABSENTE;
	}
	//Le joueur a une paire, on regarde alors s'il en a une deuxième
	else{
		valeurBrelan += this.calculKickers();
		Log.ajouterTrace("oui, brelan de valeur " + valeurBrelan, DEBUG);
		joueur.envoyerTexteSuiviPartie(trad_vousAvez + SEPARATEUR_MESSAGE + trad_brelan);
	}
return  valeurBrelan;
}

/**
   * Teste si le joueur a une double paire(= deux paires diffèrentes) ou une paire. si oui renvoie la valeur de la main, sinon COMBINAISON_ABSENTE
	* @param	valeurPaires: nombre reprèsentant la force de la paire ou de la double paire (valeur des paires + 1 ou 3 kickers)
	* @return 	
  */
private long calculerPaires(){
//On sait que si le joueur a une paire, c'est la seule qu'il a (on a dèja testè la double paire).
//les cartes ètant classèes par valeur dècroissante, on sait que la première paire trouvèe sera la plus forte
long valeurPaires;
int cartesPremierePaire;

Log.ajouterTrace(name + " a une paire/ double paire? ", DEBUG);

cartesPremierePaire = this.trouverCartesIdentiques(2);
long valeurPremierePaire= cartesPremierePaire * ((long)Math.pow(BASE_CALCULE_MAINS, PUISSANCE_PAIRE));
	//Le joueur n'a aucune une paire
	if(valeurPremierePaire == COMBINAISON_ABSENTE){
		Log.ajouterTrace("non: aucune paire", DEBUG);
		return (long)COMBINAISON_ABSENTE;
	}
	//Le joueur a une paire, on regarde alors s'il en a une deuxième
	else{
		long valeurDeuxiemePaire = this.trouverCartesIdentiques(2) * ((long)Math.pow(BASE_CALCULE_MAINS, PUISSANCE_KICKER_1));
			//Le joueur n'a qu'une paire
			if(valeurDeuxiemePaire == COMBINAISON_ABSENTE){
				//Calcul des 3 kickers
				valeurPremierePaire += this.calculKickers();
				Log.ajouterTrace("oui; une paire de valeur " + valeurPremierePaire, DEBUG);
				joueur.envoyerTexteSuiviPartie(trad_vousAvez + SEPARATEUR_MESSAGE + trad_paire);
				return valeurPremierePaire;
			}
			//Le joueur a bien une double paire
			else{
				valeurPremierePaire= cartesPremierePaire * ((long)Math.pow(BASE_CALCULE_MAINS, PUISSANCE_DOUBLE_PAIRE));
				valeurPaires = valeurPremierePaire + valeurDeuxiemePaire;
				//On calcule alors son kicker (sa carte la plus forte)	
				valeurPaires += this.calculKickers();
				Log.ajouterTrace("oui, une double paire de valeur " + valeurPaires, DEBUG);
				joueur.envoyerTexteSuiviPartie(trad_vousAvez + SEPARATEUR_MESSAGE + trad_doublePaire);
			}
		}
return  valeurPaires;
}

/**
   * Renvoie la valeur des cartes du joueur, lorsqu'il n'a aucune combinaison (èquivaut è calculer 5 kickers)
	* @return 	valeurCartes : valeur de ses 5 cartes les plus fortes
  */
private long calculerPasCombinaison(){
	//Calcul des cartes: le joueur n'a pas de combinaison, son jeu vaut è ses 5 cartes les plus fortes
	long valeurCartes = this.calculKickers();
	Log.ajouterTrace(name + " n'a aucune combinaison. Valeur de ses cartes: " + valeurCartes, DEBUG);
	joueur.envoyerTexteSuiviPartie(trad_vousAvez + SEPARATEUR_MESSAGE + trad_pasCombinaison);
	
return  valeurCartes;
}

 /**
	* Tri è bulle dans l'ordre dècroissant des Cartes de l'ArrayList en fonction de leur valeur
	* @param Cartes
	*/
private void trierCartes(){
boolean echangeEffectue = true;

	while(echangeEffectue){
		echangeEffectue = false;
			for(int i=0; i<Cartes.size()-1; i++){
				if(Cartes.get(i).getValeur() < Cartes.get(i+1).getValeur()){
					Carte superieure = Cartes.remove(i+1);
					Carte inferieure = Cartes.remove(i);
					Cartes.add(i, superieure);
					Cartes.add(i+1, inferieure);
					echangeEffectue = true;
				}
			}
	}
}

 /**
   * Calcule la valeur des kickers. Définit seul le nombre de kickers à calculer en fonction du nombre de cartes réstantes dans la main
	* @return 	la valeur des kickers
  */
private long calculKickers(){
	// On regarde combien de cartes ont ètè supprimèes, car utilisèes dans la combinaison
	int nbCartesUtiliseesDansCombinaison = 7 - Cartes.size();
	// On calcule le nombre de kickers è calculer. Il y en a 5 - nbCartesUtiliseesDansCombinaison
	int nbKickersACalculer = 5 - nbCartesUtiliseesDansCombinaison;
	
	return this.calculKickers(nbKickersACalculer);
}

 /**
   * Calcule la valeur des kickers. Surchage la méthode calculKickers() en donnant en argument le nombre de kickers à calculer
	* @param	nbKickersACalculer: nombre de kickers à calculer
	* @return 	valeurKickers: valeur en fonction des nbKickersACalculer kickers
  */
private long calculKickers(int nbKickersACalculer){
	//On suppose que les cartes de l'ArrayList Cartes sont déja triées par ordre décroissant de leur valeur
	int rangCarte = 0;
	Log.ajouterTrace("calcul des kickers. Il faut en calculer " + nbKickersACalculer, FULL_DEBUG);
		
	long valeurKickers = 0;
	
	for(int puissanceCarte= nbKickersACalculer-1; puissanceCarte>=0; puissanceCarte--){
	
	Log.ajouterTrace(""+ Cartes.get(rangCarte).getValeur() + " " + BASE_CALCULE_MAINS+ " " +puissanceCarte, FULL_DEBUG);
		long ajout= Cartes.get(rangCarte).getValeur()*  ( (long) Math.pow(BASE_CALCULE_MAINS, puissanceCarte));
		
	Log.ajouterTrace("ajout = " + ajout, FULL_DEBUG);
		valeurKickers += ajout;
		rangCarte++;
	}
	
	return valeurKickers;
}

 /**
   * Cherche si le joueur possede nbCartesIdentiquesDesire cartes identiques
	* @param	nbCartesIdentiquesDesire: nombre de cartes identiques cherchèes dans le jeu du joueur
	* @return 	valeurCartesIdentiques : COMBINAISON_ABSENTE si le joueur ne possède pas nbCartesIdentiquesDesire cartes identiques, ou la valeur des cartes identiques sinon
  */
private int trouverCartesIdentiques(int nbCartesIdentiquesDesire){
	int valeurCartesIdentiques = COMBINAISON_ABSENTE;
//	int nbCartesIdentiquesTrouve;
	Log.ajouterTrace("teste " + nbCartesIdentiquesDesire +" cartes identiques ", FULL_DEBUG);
	
//L'arraylist est dèja trièe par valeur dècroissante, on est donc sur de trouver la combinaison la plus forte
	for(int i=0; i< Cartes.size(); i++){
		int nbCartesIdentiquesTrouve = 0;
		int j = i;
		while((nbCartesIdentiquesTrouve < nbCartesIdentiquesDesire) && (j < Cartes.size()) && (Cartes.get(i).getValeur() == Cartes.get(j).getValeur())) {
			j++;
			nbCartesIdentiquesTrouve++;
		}
		if(nbCartesIdentiquesDesire == nbCartesIdentiquesTrouve){
			valeurCartesIdentiques = Cartes.get(i).getValeur();
			Log.ajouterTrace("on a trouve " + nbCartesIdentiquesDesire +" cartes identiques de valeur " + valeurCartesIdentiques, FULL_DEBUG);
			ArrayList<Carte> CartesARetirer = new ArrayList<Carte>();
				for(int k=i; k<j; k++){
					CartesARetirer.add(Cartes.get(k));
				}
				for(int k=0; k<CartesARetirer.size(); k++){
					Cartes.remove(CartesARetirer.get(k));
				}
			break;
		}		
	}
	return valeurCartesIdentiques;
}


 /**
   * Cherche si le joueur possede une carte de valeur valeurCarteCherchee dans son paquet
	* @param	valeurCarteCherchee: valeur de la carte recherchée
	* @return boolean vrai si le joueur possède une carte de cette valeur, false sinon
  */
private boolean aCarteDeValeur(byte valeurCarteCherchee){
	for(int i=0; i<Cartes.size(); i++){
		if(Cartes.get(i).getValeur() == valeurCarteCherchee){
			return true;
		}
	}
return false;
}


 /**
   * Sauvegarde les cartes du joueur dans une arraylist dédiée (sauvegardeCartes). Sert lorsqu'une méthode va supprimer des cartes au joueur et que l'on risque d'avoir besoin de les lui rendre
  */
private void sauvegarderCartes(){
	//premièrement, on vide l'arraylist dans laquelle les cartes seront sauvegardées (sauvegardeCartes)
	while(sauvegardeCartes.size() >0)
		sauvegardeCartes.remove(0);
		
	//Ensuite, on copie les cartes de l'arraylist Cartes dans l'arraylist sauvegardeCartes
	for(int i=0; i<Cartes.size(); i++){
		sauvegardeCartes.add(Cartes.get(i));
	}
}


 /**
   * Restauration les cartes du joueur à partir de l'a arraylist dédiée (sauvegardeCartes). Sert lorsqu'une méthode a supprimé des cartes au joueur et qu'on a besoin de les lui rendre
  */
private void restaurerCartes(){
	//premièrement, on vide l'arraylist représenant les cartes du joueur
	while(Cartes.size() >0)
		Cartes.remove(0);

	//Ensuite, on retire les cartes de l'arraylist sauvegardeCartes et on les copie dans l'arraylist (préalablement vidée) Cartes
	while(sauvegardeCartes.size() >0)
		Cartes.add(sauvegardeCartes.remove(0));
}

}