//A partir de java.awt;
import java.awt.Image;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.BorderLayout;
//A partir de java.awt.image
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.CropImageFilter;

//A partir de javax.swing;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Rectangle;

import 	java.util.ArrayList;

import javax.swing.border.Border;

/**
  *	Classe : Panneau
  *	Super classe: JPanel (javax.swing.JPanel)
  *	Classes filles: aucune
  *	Interfaces: ProtocoleReseau
  *	Description: classe gerant la table de jeu qui est placée dans la Fenetre
  * 	Auteur:	Yann Danielou
  */
class Panneau extends JPanel implements ProtocoleReseau, VariablesTraduction{

	private	Image imageCartes;
	private JTextArea suiviPartie;
	private JScrollPane zoneScrollSuiviPartie;
	
	private	Dimension dimensionImage;
	private	Dimension tailleCarteDansFichier;

	private Dimension positionCarteRetournee;
  
	private ArrayList <Image> cartes;

	private String nomJoueur;
	private String nomJoueurAdverse;

	private int argent[] =  {0,0,0,0,0};
	

	public Panneau(){
		super();

		this.setLayout(new BorderLayout());
		
		cartes = new ArrayList <Image> ();

		//On construit et place le label et son contour muni d'une barre de défilement verticale
		suiviPartie = new JTextArea(InstanceJoueur.lang.getTraduction(trad_suiviPartie));
		suiviPartie.setEditable(false);
		zoneScrollSuiviPartie = new JScrollPane(suiviPartie);
		zoneScrollSuiviPartie.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(zoneScrollSuiviPartie, BorderLayout.SOUTH);
		
		//On charge en mémoire le fichier cartes.jpg qui contient toutes les cartes
		ImageIcon imageCartesIcon = new ImageIcon("../images/cartes.jpg");
		//on réccupere la taille du fichier
		dimensionImage =  new Dimension(imageCartesIcon.getIconWidth(), imageCartesIcon.getIconHeight());
		System.out.println(InstanceJoueur.lang.getTraduction(trad_dimensionFichierCartes) + " : " + dimensionImage.width + " par " + dimensionImage.height);
		
		//On réccupere la taille des cartes contenues dans ce fichier
		tailleCarteDansFichier = new Dimension(dimensionImage.width/13, dimensionImage.height/5);
		System.out.println("chaque carte fait: " + tailleCarteDansFichier.width + " par " + tailleCarteDansFichier.height);
		
		//Par défaut, toutes les cartes sont retournées (invisibles). On les affiche donc retournees
		positionCarteRetournee = new Dimension(2* tailleCarteDansFichier.width, 4* tailleCarteDansFichier.height);
		System.out.println("position carte retournee: " + positionCarteRetournee.width + " / " + positionCarteRetournee.height);
		
		imageCartes = imageCartesIcon.getImage();
		
		for(int i=0; i< 9; i++)
			cartes.add(createImage(new FilteredImageSource(imageCartes.getSource(),new CropImageFilter(positionCarteRetournee.width, positionCarteRetournee.height, tailleCarteDansFichier.width, tailleCarteDansFichier.height))));
		
		
	}


  //*****************************************************************************************
  //                                    Mutateurs
  //*****************************************************************************************	
  /**
   * Définit l'argent d'un type d'argent
	* @param		 argent nouvelle somme d'argent
   */
  public void setArgent(int typeArgent, int argent){
    this.argent[typeArgent] = argent;
	this.repaint();
  }

  /**
   * Définit la somme d'argent du joueur
	* @param		 argent nouvelle somme d'argent
   */
  public void setArgentJoueur(int argent){
    this.argent[ARGENT_JOUEUR] = argent;
  }

  /**
   * Définit la somme d'argent du joueur
	* @param		 argent nouvelle somme d'argent
   */
  public void setArgentJoueurAdverse(int argent){
    this.argent[ARGENT_JOUEUR_ADVERSE] = argent;
  }

  /**
   * Définit la mise actuelle du joueur
	* @param		 mise mise actuelle
   */
  public void setMiseJoueur(int mise){
    this.argent[MISE_JOUEUR] = mise;
  }

  /**
   * Définit la mise actuelle du joueur adverse
	* @param		 mise mise actuelle
   */
  public void setMiseJoueurAdverse(int mise){
    this.argent[MISE_JOUEUR_ADVERSE] = mise;
  }

  /**
   * Définit la somme d'argent du pot
	* @param		 pot somme d'argent au pot
   */
  public void setArgentPot(int pot){
    this.argent[ARGENT_POT] = pot;
  }


  //*****************************************************************************************
  //                                    Méthodes
  //*****************************************************************************************	
  /**
   * Redessine la table de jeu (appellée automatiquement ou suite à l'instruction this.repaint()
	* @param		 crayon le Graphics utilisé pour le dessin
   */
	public void paintComponent(Graphics crayon){
		super.paintComponent(crayon);
		//On redessine la table au milieu de l'écran
	
		int tailleString = 0;
		Point positionArgentJoueur;
		Point positionArgentJoueurAdverse;
		Point positionArgentPot;
		Point positionMiseJoueur;
		Point	positionMiseJoueurAdverse;

		Point positionSuiviPartie;
		
		String texteAAfficher;

		Point			milieuPanneau	= new Point((int)(this.getWidth() /2), (int)this.getHeight()/2);
		Dimension	dimensionOval	= new Dimension((int)this.getWidth() - this.getWidth()/10, (int) this.getHeight()/2);
		Point			origineOval		= new Point((int) (milieuPanneau.x -  dimensionOval.getWidth()/2), (int) (milieuPanneau.y -  dimensionOval.getHeight()/2));
		Dimension	tailleCarte		= new Dimension( (int)(dimensionOval.width /10), (int)(dimensionOval.height/5) );

		crayon.setColor(Color.green);
		crayon.fillOval(origineOval.x,origineOval.y, (int) dimensionOval.getWidth(), (int) dimensionOval.getHeight());

		Point posPremiereCarteCommun = new Point((int) ((milieuPanneau.x - origineOval.x) /5) ,(int) (milieuPanneau.y - tailleCarte.height/2) );

		Point posPremiereCarteJoueur = new Point((int) (milieuPanneau.x - tailleCarte.width) ,(int) (origineOval.y +dimensionOval.height));
		Point posDeuxiemeCarteJoueur = new Point((int) (milieuPanneau.x) ,(int) (origineOval.y +dimensionOval.height));
		

		Point posPremiereCarteJoueurAdverse = new Point((int) (milieuPanneau.x - tailleCarte.width), origineOval.y - tailleCarte.height);
		Point posDeuxiemeCarteJoueurAdverse = new Point((int) (milieuPanneau.x) ,origineOval.y - tailleCarte.height);
		
		
		//On déssine les cartes du joueur
		crayon.drawImage(cartes.get(0),  posPremiereCarteJoueur.x ,posPremiereCarteJoueur.y, tailleCarte.width,tailleCarte.height, this);
		crayon.drawImage(cartes.get(1),  posDeuxiemeCarteJoueur.x ,posPremiereCarteJoueur.y, tailleCarte.width,tailleCarte.height, this);
	

		//On déssine les cartes communes
		crayon.drawImage(cartes.get(2), posPremiereCarteCommun.x ,posPremiereCarteCommun.y, tailleCarte.width,tailleCarte.height, this);
		crayon.drawImage(cartes.get(3), 2* posPremiereCarteCommun.x ,posPremiereCarteCommun.y, tailleCarte.width,tailleCarte.height, this);
		crayon.drawImage(cartes.get(4), 3* posPremiereCarteCommun.x ,posPremiereCarteCommun.y, tailleCarte.width,tailleCarte.height, this);

		crayon.drawImage(cartes.get(5), 7 * posPremiereCarteCommun.x ,posPremiereCarteCommun.y, tailleCarte.width,tailleCarte.height, this);
		crayon.drawImage(cartes.get(6), 8 * posPremiereCarteCommun.x ,posPremiereCarteCommun.y, tailleCarte.width,tailleCarte.height, this);


		//On déssine les cartes du joueur adverse
		crayon.drawImage(cartes.get(7),  posPremiereCarteJoueurAdverse.x ,posPremiereCarteJoueurAdverse.y, tailleCarte.width,tailleCarte.height, this);
		crayon.drawImage(cartes.get(8),  posDeuxiemeCarteJoueurAdverse.x ,posDeuxiemeCarteJoueurAdverse.y, tailleCarte.width,tailleCarte.height, this);

		//On place le label de suivi de la partie
		
		//On écrit la somme d'argent du joueur
		texteAAfficher = InstanceJoueur.lang.getTraduction(trad_argentJoueur) + " : " + argent[ARGENT_JOUEUR];
		tailleString = texteAAfficher.length();
		positionArgentJoueur = new Point(posDeuxiemeCarteJoueur.x - 2 * tailleString, posDeuxiemeCarteJoueur.y + tailleCarte.height + crayon.getFont().getSize());
		crayon.setColor(Color.red);
		crayon.drawString(texteAAfficher, positionArgentJoueur.x, positionArgentJoueur.y);

		//On écrit la mise actuelle du joueur
		texteAAfficher = InstanceJoueur.lang.getTraduction(trad_miseJoueur) + " : " + argent[MISE_JOUEUR];
		tailleString = texteAAfficher.length();
		positionMiseJoueur = new Point(posDeuxiemeCarteJoueur.x - 2 * tailleString, posDeuxiemeCarteJoueur.y - crayon.getFont().getSize());
		crayon.setColor(Color.red);
		crayon.drawString(texteAAfficher, positionMiseJoueur.x, positionMiseJoueur.y);


		//On écrit la somme d'argent du joueur adverse
		texteAAfficher = InstanceJoueur.lang.getTraduction(trad_argentJoueurAdverse) + " : " + argent[ARGENT_JOUEUR_ADVERSE];
		tailleString = texteAAfficher.length();
		positionArgentJoueurAdverse = new Point(posDeuxiemeCarteJoueurAdverse.x - 2 * tailleString, posDeuxiemeCarteJoueurAdverse.y - crayon.getFont().getSize());
		crayon.setColor(Color.red);
		crayon.drawString(texteAAfficher, positionArgentJoueurAdverse.x, positionArgentJoueurAdverse.y);

		//On écrit la mise actuelle du joueur adverse
		texteAAfficher = InstanceJoueur.lang.getTraduction(trad_miseJoueurAdverse) + " : " + argent[MISE_JOUEUR_ADVERSE];
		tailleString = texteAAfficher.length();
		positionMiseJoueurAdverse = new Point(posDeuxiemeCarteJoueurAdverse.x - 2 * tailleString, posDeuxiemeCarteJoueurAdverse.y + tailleCarte.height + crayon.getFont().getSize());
		crayon.setColor(Color.red);
		crayon.drawString(texteAAfficher, positionMiseJoueurAdverse.x, positionMiseJoueurAdverse.y);


		//On écrit la somme d'argent du pot
		texteAAfficher = InstanceJoueur.lang.getTraduction(trad_argentPot) + " : " + argent[ARGENT_POT];
		tailleString = texteAAfficher.length();
		positionArgentPot = new Point(milieuPanneau.x - 2 * tailleString, milieuPanneau.y);
		crayon.setColor(Color.red);
		crayon.drawString(texteAAfficher, positionArgentPot.x, positionArgentPot.y);

		//On place la zone de texte du suivi de la partie tout en bas du Panneau
		zoneScrollSuiviPartie.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() - positionArgentJoueur.y - crayon.getFont().getSize()));
		//Permet d'afficher le texte situé tout en bas du JTextArea et donc d'afficher le texte le plus récent. Maintient le curseur tout en bas
		suiviPartie.setCaretPosition(suiviPartie.getDocument().getLength());


	//	System.out.println("Table de jeu redessinée");

	}
	

  /**
   * Assigne la carte à la bonne valeur (retourne la carte face dévoilée et montre la bonne carte)
	* @param		 numeroCarte numéro de la carte dans le paquet
	* @param		 typeCarte type de la carte (carte joueur, carte commun, ..)
   */
	public void assignerCarte (int numeroCarte, int typeCarte){
	
	      System.out.println("assignation de la carte : " + numeroCarte + " de type: " + typeCarte);
		int valeur = numeroCarte%13;
		int valeurCouleur = numeroCarte/13;

		if(valeur == 0)
			valeurCouleur--;

		typeCarte--;
		cartes.remove(typeCarte);
		cartes.add(typeCarte, createImage(new FilteredImageSource(imageCartes.getSource(),new CropImageFilter(valeur * tailleCarteDansFichier.width, valeurCouleur * tailleCarteDansFichier.height, tailleCarteDansFichier.width, tailleCarteDansFichier.height))));

		this.repaint();
	}


  /**
   * Retourne toutes les cartes du plateau
   */
	public void initCartes (){
	    //On vide d'abord les cartes
	    while(cartes.size() > 0){
	      cartes.remove(0);
	    }
	
	    //On crée ensuite les cartes retournees
	    for(int i=0; i< 9; i++)
	    cartes.add(createImage(new FilteredImageSource(imageCartes.getSource(),new CropImageFilter(positionCarteRetournee.width, positionCarteRetournee.height, tailleCarteDansFichier.width, tailleCarteDansFichier.height))));

	  this.repaint();
	}

  /**
   * Ajoute du texte dans la zone de suivi de la partie
   */
	public void ajouterTexteSuiviPartie (String texte){
	    //On reccupère le texte existant et on ajoute le nouveau texte
		suiviPartie.setText(suiviPartie.getText() + "\n" + texte);
	}
}