//A partir de javax.swing
import javax.swing.JButton;

//A partir de java.awt
import java.awt.Graphics;

/**
  *	Classe : Bouton
  *	Super classe: JButton (API javax.swing.JButton)
  *	Classes filles: BoutonSuivre
  *	Interface: aucune
  *	Description: classe ajoutant au JButton un int (valeur) qui sera utilisé pour les 3 boutons d'action (se coucher, parole, miser)
  * 	Auteur:	Yann Danielou
  */
class Bouton extends JButton{

   protected int valeur;


  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************
 /**
   * Constructeur
    * @param	nom    nom du bouton
    * @param	valeur valeur du bouton
   */
      public Bouton(String nom, int valeur){
		super(nom);
		this.setEnabled(false);
		this.valeur = valeur;
	}


  //*****************************************************************************************
  //                                    Accesseurs
  //*****************************************************************************************	
  /**
   * retourne la valeur du bonton
	* @return	 valeur la valeur actuelle
   */	
	public int getValeur(){
	  return valeur;
	}

  //*****************************************************************************************
  //                                    Mutateurs
  //*****************************************************************************************	
  /**
   * Définit la valeur du bonton
	* @param		 valeur nouvelle valeur
   */
	public void setValeur(int valeur){
	  this.valeur = valeur;
	}

  //*****************************************************************************************
  //                                    Méthodes
  //*****************************************************************************************	
  
	public void paintComponent(Graphics crayon){
		super.paintComponent(crayon);
	} 

}



//***********************************************************************************************************************************
//************************		BoutonSuivre	****************************************************************************************
//***********************************************************************************************************************************
/**
  *	Classe : BoutonSuivre
  *	Super classe: Bouton
  *	Classes filles: BoutonMiser
  *	Interface: aucune
  *	Description: classe ajoutant au Bouton une chaine String qui contient le texte du bouton (son label). Sert aux boutons parole et miser 
  * 	Auteur:		Yann Danielou
  *	Date:		7 Avril 2009
  */
class BoutonSuivre extends Bouton{

	protected String string;
  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************
  /**
    * Constructeur
    * @param	nom    nom du bouton
    * @param	valeur valeur du bouton
    */
	public BoutonSuivre(String nom, int valeur){
		super(nom, valeur);
		this.string = "";
	}



  //*****************************************************************************************
  //                                    Mutateurs
  //*****************************************************************************************	
  /**
   * Définit le texte qui du bouton (son label)
	* @param		 texte nouvelle chaine
   */
	public void setString(String texte){
	  this.string = texte;
	  this.setText(texte + " " + valeur);
	}

  //*****************************************************************************************
  //                                    Méthodes
  //*****************************************************************************************	
	public void actualiserTexte(){
		this.setText(string + " " + valeur); 
	}
}





//***********************************************************************************************************************************
//************************		BoutonMiser	****************************************************************************************
//***********************************************************************************************************************************
/**
  *	Classe : BoutonMiser
  *	Super classe: BoutonSuivre
  *	Classes filles: aucune
  *	Interface: aucune
  *	Description: classe ajoutant au BoutonSuivre deux valeurs: la valeur maximale que peut prendre le bouton, et sa valeur minimale		*     Auteur: Yann Danielou
  *	Date:		7 Avril 2009
  */
class BoutonMiser extends BoutonSuivre{

	private int		valeurMax;
	private int		valeurMin;

  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************
  /**
    * Constructeur
    * @param	nom    nom du bouton
    * @param	valeurMin valeur minimum que peut prendre le bouton (= mise minimum pour rester en jeu)
    * @param	valeurMax valeur maximum que peut prendre le bouton (= mise maximum autorisée)
    */
    public BoutonMiser(String nom, int valeurMin, int valeurMax){
		super(nom, valeurMin);
		this.valeurMin = valeurMin;
		this.valeurMin = valeurMin;
	}


  //*****************************************************************************************
  //                                    Mutateurs
  //*****************************************************************************************	
  /**
   * Définit la valeur minimum du bouton
	* @param valeurMin nouvelle valeur minimum
   */
	public void setValeurMin(int valeurMin){
		this.valeurMin = valeurMin;
	}

  /**
   * Définit la valeur maximum du bouton
	* @param		 valeurMin nouvelle valeur maximum
   */
	public void setValeurMax(int valeurMax){
		this.valeurMax = valeurMax;
	}

  //*****************************************************************************************
  //                                    Méthodes
  //*****************************************************************************************	
 /**
   * Actualise le texte du bouton (son label)
   */
	public void actualiserTexte(){
		this.setText(string + " " + valeur); 
	}

  /**
   * Diminue la valeur du bouton suite à l'appui sur le bouton "-"
   * @param	diminuer JButton qui permet de diminuer la valeur courante
   * @param	augmenter JButton qui permet d'augmenter la valeur courante
   */
	public void diminuerValeur(JButton diminuer, JButton augmenter){
		if((((int)valeur - valeur/10) > valeurMin) && (valeur > 10)){
			valeur = valeur - valeur/10;
		}
		else if((valeur - 1) > valeurMin)
			valeur--;
		
		else{
			valeur = valeurMin;
			diminuer.setEnabled(false);
		}

		this.actualiserTexte();
		augmenter.setEnabled(true);
	}

  /**
   * Augmente la valeur du bouton suite à l'appui sur le bouton "+"
   * @param	diminuer JButton qui permet de diminuer la valeur courante
   * @param	augmenter JButton qui permet d'augmenter la valeur courante
   */
	public void augmenterValeur(JButton diminuer, JButton augmenter){
		if((((int)valeur + valeur/10) < valeurMax) && (valeur > 10)){
			valeur = valeur + valeur/10;
		}
		else if((valeur +1) < valeurMax)
			valeur ++;
		
		else{
			valeur = valeurMax;
			augmenter.setEnabled(false);
		}


		this.actualiserTexte();
		diminuer.setEnabled(true);
	}


}