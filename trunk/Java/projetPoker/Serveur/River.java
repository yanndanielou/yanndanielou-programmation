/**
  *	Classe : River
  *	Super classe: Paquet
  *	Classes filles: aucune
  *	Interface: aucune
  *	Description: classe dérivant la classe Paquet. Concretement, elle représente les cinq cartes de la riviere en commun à tous les joueurs. Ces cartes ne sont pas visibles au départ. C'est par la suite qu'elles le deviennent
  * 	Auteur:	Yann Danielou
  */

class River extends Paquet implements VariablesGlobales{
  private static final int NOMBRE_CARTES_RIVER = 5;


  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************
 /**
   * Constructeur par défaut
   */
  public River(){
    super();
  }


//*****************************************************************************************
//                                    Méthodes de classe
//*****************************************************************************************
 

 /**
   * Méthode retournant face cachée les cinq cartes pour qu'elles ne soient pas visibles par les joueurs
   */
  public void cacherCartes(){
    for(int i=0; i< NOMBRE_CARTES_RIVER; i++)
      this.Cartes.get(i).setVisible(false);
  }

 /**
   * Méthode retournant face visible les cartes afin de les mettre visibles par les joueurs. Recoit en paramètre le nombre de cartes à dévoiler
   */
  public void devoilerCartes(int nbCartes){

    Log.ajouterTrace("Dévoilement des " + nbCartes + " premieres cartes de la river", INFO);
    for(int i = 0; i< nbCartes; i++){
      (this.Cartes.get(i)).setVisible(true);
		//this.Cartes.get(i).afficher();
    }
  
  }

}