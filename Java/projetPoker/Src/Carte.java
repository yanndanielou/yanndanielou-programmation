
/**
  *	Classe : Carte
  *	Super classe: aucune
  *	Classes filles: aucune
  *	Interface: ProtocoleReseau
  *	Description: classe gerant une Carte de jeu
  * 	Auteur:	Yann Danielou
  */

class Carte implements ProtocoleReseau{
	

  private	String	couleur;
  private	int		valeurCouleur;
  private	String	nom;
  private	int		valeur;
  private	boolean	visible;
  private	int		numeroCarte;


  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************
 /**
   * Constructeur par défaut
   */
  public Carte(){
  }

 /**
   * Constructeur créant la carte n° n
	* @param	n numéro de la carte
   */
  public Carte(int n){
	this.numeroCarte = n;
    n--;
    this.valeurCouleur = n/13;
    this.couleur=couleurs[valeurCouleur];
    this.nom=noms[n%13];
    this.valeur=n%13+1;
    this.visible = true;
  }

  //*****************************************************************************************
  //                                    Accesseurs
  //*****************************************************************************************	
  /**
   * Retourne la valeur de la couleur de la carte (couleur stockée dans un int)
	* @return 	valeurCouleur	couleur de la carte en int
   */
  public int getValeurCouleur(){
    return valeurCouleur;		
  }

  /**
   * Retourne la valeur de la carte
	* @return 	valeur	valeur symbolique de la carte (int)
   */
  public int getValeur(){
    return valeur;
  }

  /**
   * Retourne la visibilité de la carte
	* @return 	visible	true si la carte est visible
   */
  public boolean getVisible(){
    return visible;
  }

  /**
   * Retourne le numéro de la carte (entre 1 et 53)
	* @return 	numeroCarte le rang de la carte dans un paquet trié
   */
  public int getNumeroCarte(){
    return numeroCarte;
  }

  //*****************************************************************************************
  //                                    Mutateurs
  //*****************************************************************************************	
  /**
   * Définit la visibilité de la carte
	* @param		 visible nouvelle visibilité de la carte (boolean)
   */
  public void setVisible(boolean visible){
    this.visible = visible;
  }

  /**
   * Définit la valeur de la carte
	* @param		 visible nouvelle visibilité de la carte (boolean)
   */
  public void setValeur(int valeur){
    this.valeur = valeur;
  }
  //*****************************************************************************************
  //                                    Méthodes de classe
  //*****************************************************************************************	
  /**
   * Affiche la carte
   */
  public void afficher(){
    System.out.println(this.toString());
  }
  /**
   * Envoie une string décrivant la carte
	* @return 	chaine de caractere décrivant la carte
   */
  public String toString(){
    return nom + " de " + couleur + " est de valeur symbolique " + valeur+ " est visible: " + visible;
  }

}