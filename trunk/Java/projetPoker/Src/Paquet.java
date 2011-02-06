import 	java.util.ArrayList;

/**
  *	Classe : Paquet
  *	Super classe: aucune
  *	Classes filles: River
  *	Interface: aucune
  *	Description: classe gerant un paquet de cartes
  * 	Auteur:	Yann Danielou
  */
class Paquet {

	protected ArrayList <Carte>	Cartes;


  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************
 /**
   * Constructeur par défaut (construit un paquet vide, donc sans carte)
   */
	public Paquet (){
		this.Cartes = new ArrayList<Carte>();
	}

 /**
   * Constructeur: construit un paquet de nbCartesACreer cartes
	* @param	nbCartesACreer nombre de cartes que doit contenir le paquet
   */
	public Paquet (int nbCartesACreer){		
		Cartes = new ArrayList<Carte>();
			for(int i=0;i<nbCartesACreer;i++){
				this.Cartes.add(new Carte(i+1));
			}
	}



  //*****************************************************************************************
  //                                    Accesseurs
  //*****************************************************************************************
	
  /**
   * renvoie la carte à la position i de l'arraylist
	* @param		i			position de la carte dans le paquet (dans l'arraylist)
	* @return 	Carte		La carte qui dont la position est i
   */
	public Carte getCarte(int i){
		return this.Cartes.get(i);
	}



  //*****************************************************************************************
  //                                    Méthodes
  //*****************************************************************************************

  /**
   * Ajoute la carte passée en arguement dans le paquet (ArrayList)
	* @param toAdd	carte à ajouter au paquet
   */
	public void ajouterCarte(Carte toAdd){
		Cartes.add(toAdd);
	}

  /**
   * Met une carte morte (enlève la première carte du paquet et met à jour le nombre de cartes
   */
	public void carteMorte(){
		this.Cartes.remove(0);
	}

  /**
   * Donne une carte: enlève la première carte du paquet et la renvoie. Elle est donc retirée du paquet
	* @return carte toBeGiven: celle qui vient d'être retirée du paquet
   */
	public Carte donner_carte(){
		Carte toBeGiven = new Carte();
		toBeGiven = (this.Cartes.get(0));
		this.Cartes.remove(0);
		return toBeGiven;
	}

  /**
   *  Vide le paquet (retire toutes les cartes contenues dans ce paquet)
   */
      public void viderPaquet(){
	while(this.Cartes.size() > 0)
	  this.Cartes.remove(0);
      }


  /**
   * Mélange un paquet qui était trié lors de sa création
   */
	public void melanger(){
		int compteur_melange=52;
		int pos_carte1= (int)(Math.random()*52);
		int pos_carte2= (int)(Math.random()*52);

		while(compteur_melange-- > 1){

			pos_carte1= (int)(Math.random()*52);
			pos_carte2= (int)(Math.random()*52);
				while(pos_carte1==pos_carte2){
					pos_carte2= (int)(Math.random()*52);
				}
			Carte tempo_carte1 = new Carte();
			Carte tempo_carte2 = new Carte();
	
			tempo_carte1 = this.Cartes.get(pos_carte1);
			tempo_carte2 = this.Cartes.get(pos_carte2);	
	
			this.Cartes.set(pos_carte1,tempo_carte2);
			this.Cartes.set(pos_carte2,tempo_carte1);
		}
	}


  /**
   * Affiche dans la console chaque carte contenue dans le paquet 
   */
	public void afficher(int niveauDebug){
		Log.ajouterTrace(this.toString(), niveauDebug);
	}
  /**
   * Renvoie une chaine décrivant chaque carte contenue dans le paquet
	* @return 	chaine de caractere contenant les parametres de chaque carte
   */
	public String toString(){
		String description = "";
		for(int i=0;i<this.Cartes.size();i++){
			description += (this.Cartes.get(i)).toString() + "\n";
		}
	return description;
	}

}
