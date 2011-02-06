import java.util.Properties;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import  java.lang.NullPointerException;

/**
  *	Classe : Langues
  *	Super classe: Properties (java.util.Properties)
  *	Classes filles: aucune
  *	Interfaces: VariablesGlobales, VariablesTraduction
  *	Description: classe servant à traduire les messages à afficher sur l'instance joueur afin de pouvoir afficher le message dans la bonne langue, qui est choisie dans le fichier de parametrage
  * 	Auteur:	Yann Danielou
  */
class Langues extends Properties implements VariablesGlobales, VariablesTraduction{

  private String fichierLangues;
  private int langueChoisie;
 
  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************
 /**
   * Constructeur de la classe de traduction
   * DOIT ETRE APPELE APRES LE CONSTRUCTEUR DU FICHIER DE PARAMETRAGE
  */
  public Langues(){
    super();
    String langueFichierProp;
    File fichier;
    this.fichierLangues = "traductions.lang";

  //On teste l'existence du fichier: s'il n'existe pas, on le cree avec les bonnes valeurs
  fichier = new File(this.fichierLangues);
  System.out.println("le fichier langues existe: " + fichier.exists());
    if (!fichier.exists()){
	this.initFichierLangues();
    }

  //On reccupere la langue choisie grace au fichier de proprietes
  this.langueChoisie=0;

  langueFichierProp = InstanceJoueur.prop.getPropriete("langue");
  for(int i=0; i< languesPossibles.length; i++){
    if(languesPossibles[i].equals(langueFichierProp)){
      this.langueChoisie = i;
      break;
    }
  }

  }

 
  //*****************************************************************************************
  //                                    Methodes
  //*****************************************************************************************	
 /**
   * Reccupere une traduction
	* @param	key: le nom de la propriete que l'on souhaite connaitre
	* @return	value: la valeur de la propriete, null si elle n'existe pas
  */
  public String getTraduction(int key){
    String value = null;
      try{
	String [] traductions;
	FileInputStream fileReader = new FileInputStream(this.fichierLangues);
	this.load(fileReader);
	value = super.getProperty(""+key);
	traductions =  value.split(separationTraductions);
	value = traductions[this.langueChoisie];
	fileReader.close();
      }
      catch (FileNotFoundException e){}
      catch (IOException e){}
     // catch (NullPointerException e){}
    return value;
  }


 /**
   * Cree un fichier properties du serveur avec les parametres par defaut
  */
  public void initFichierLangues(){
   //   Log.ajouterTrace("creation du fichier de langues", INFO);

      //Grammaire et mots de liaison
      this.setProperty("" + trad_ou, "ou" + separationTraductions + "or" + separationTraductions + "oder");

      //Traduction des evenements reseaux
      this.setProperty("" + trad_envoyerServeur, "envoye au serveur" + separationTraductions + "sent to server"+ separationTraductions + "entsendet zu der Server");
      this.setProperty("" + trad_recuServeur, "recu du serveur" + separationTraductions + "received from server" + separationTraductions + "bekommt von den Server");
      this.setProperty("" + trad_carteRecue, "carte recue" + separationTraductions + "card received" + separationTraductions + "Karte entsendet");

      //Traduction des actions possibles
      this.setProperty("" + trad_seCoucher, "Se coucher" + separationTraductions + "Fold" + separationTraductions + "Aussteigen");
      this.setProperty("" + trad_parole, "Parole" + separationTraductions + "Check" + separationTraductions + "Schieben");
      this.setProperty("" + trad_suivre, "Suivre" + separationTraductions + "Call" + separationTraductions + "Mitgehen");
      this.setProperty("" + trad_miser, "Miser" + separationTraductions + "Bet" + separationTraductions + "Setzen");
      this.setProperty("" + trad_relancer, "Relancer" + separationTraductions + "Raise" + separationTraductions +"Erhohen");
      this.setProperty("" + trad_tapis, "Tapis" + separationTraductions + "All In" + separationTraductions + "All in");

      this.setProperty("" + trad_toucheAppuyee, "touche tapee" + separationTraductions + "Key pressed" + separationTraductions +"Taste gedruckt");

      this.setProperty("" + trad_suiviPartie, "Suivi de la partie" + separationTraductions + "the follow-up to the game" + separationTraductions + "der Ablauf der Partie");

      //Traduction des dimensions des cartes
      this.setProperty("" + trad_dimensionFichierCartes, "Dimensions du fichier de cartes" + separationTraductions + "Dimension of the card's file" + separationTraductions + "Dimension der Karte's Datei");
      this.setProperty("" + trad_tailleCarte, "Taille d'une carte" + separationTraductions + "Size of one card" + separationTraductions + "Dimension von eine Karte");

      //Traduction des sommes d'argent à afficher
      this.setProperty("" + trad_argentJoueur, "Mon argent" + separationTraductions + "My money" + separationTraductions + "Mein Geld");
      this.setProperty("" + trad_miseJoueur, "Ma mise" + separationTraductions + "My stake" + separationTraductions + "Mein Einsatz");
      this.setProperty("" + trad_argentJoueurAdverse, "Argent Joueur adverse" + separationTraductions + "Opponent Player money" + separationTraductions + "Der Gegner's Geld");
      this.setProperty("" + trad_miseJoueurAdverse, "Mise Joueur adverse" + separationTraductions + "Opponent Player stake" + separationTraductions + "Der Gegner's Einsatz");
      this.setProperty("" + trad_argentPot, "Argent pot" + separationTraductions + "Pot's amount"+ separationTraductions + "Betrag des Pot");

      //Traduction des combinaisons
      this.setProperty("" + trad_vousAvez, "Vous avez" + separationTraductions + "You've got" + separationTraductions + "Sie haben");
      this.setProperty("" + trad_pasCombinaison, "Pas de Combinaison" + separationTraductions + "High cards" + separationTraductions + "die Hochste Karte");
      this.setProperty("" + trad_paire, "une Paire" + separationTraductions + "One Pair" + separationTraductions + "ein Paare");
      this.setProperty("" + trad_doublePaire, "une Double Paire" + separationTraductions + "Two Pair" + separationTraductions + "Zwei Paare");
      this.setProperty("" + trad_brelan, "un Brelan" + separationTraductions + "Three of a Kind" + separationTraductions + "ein Drilling");
      this.setProperty("" + trad_suite, "une Suite" + separationTraductions + "a Straight" + separationTraductions + "ein Straße");
      this.setProperty("" + trad_couleur, "une Couleur" + separationTraductions + "a Flush" + separationTraductions + "ein Flush");
      this.setProperty("" + trad_full, "un Full" + separationTraductions + "a Full House" + separationTraductions + "ein Full House");
      this.setProperty("" + trad_carre, "un Carre" + separationTraductions + "For of a kind" + separationTraductions + "ein Vierling");
      this.setProperty("" + trad_quintFlush, "une Quint Flush" + separationTraductions + "a Straight Flush" + separationTraductions + "ein Straight Flush");

      this.setProperty("" + trad_joueur, "Joueur" + separationTraductions + "Player"+ separationTraductions + "Spieler");
      this.setProperty("" + trad_entrezNom, "Veuillez entrer votre nom svp" + separationTraductions + "Please type your name"+ separationTraductions + "Schreiben ihnen Namen bitte");
      this.setProperty("" + trad_identification, "Identification" + separationTraductions + "Identification"+ separationTraductions + " Identifizierungs");

      this.setProperty("" + trad_demandeRecave, "Vous n'avez plus assez d'argent pour jouer. Souhaitez vous recaver?" + separationTraductions + "You don't have enough money to play. Do you wish to rebuy? "+ separationTraductions + "Sie haben nicht geug Geld, um noch zu spielen. Wollen Sie mehr Geld kaufen?");

    this.setProperty("" + trad_configuration, "Preferences" + separationTraductions + "Preferences "+ separationTraductions + "Vorliebe");

    this.setProperty("" + trad_debutTour, "Debut du tour" + separationTraductions + "Beginning of the tour"+ separationTraductions + "Anfang dem Trick");


    this.setProperty("" + trad_egalite, "Egalité: partage du pot" + separationTraductions + "It's a draw. Division of the pot"+ separationTraductions + "Gleichheit. Das Pot ist geTeilt");
    this.setProperty("" + trad_gagne, "gagne la donne et empoche"+ separationTraductions + "winns the pot's amount"+ separationTraductions + "verdienst");

    try{
      OutputStream out = new FileOutputStream(this.fichierLangues);
      this.store(out, "fichier de traductions des messages pour l'instance joueur");
      out.flush();
      out.close();
  }
    catch(IOException e){}
  }
}