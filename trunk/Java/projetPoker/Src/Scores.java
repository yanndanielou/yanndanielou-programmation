import java.util.Properties;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
  *	Classe : Scores
  *	Super classe: Properties (java.util.Properties)
  *	Classes filles: aucune
  *	Interface: aucune
  *	Description: classe servant a stocker coté serveur le score de chaque joueur dans un fichier .src
  * 	Auteur:	Yann Danielou
  */
class Scores extends Properties{

  private static String fichierScores = "scores.scr";
  
 
  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************
 /**
   * Constructeur par defaut
  */
  public Scores(){
    super();
  }

 
  //*****************************************************************************************
  //                                    Méthodes
  //*****************************************************************************************	
 /**
   * Inscrit ou met à jour la somme d'argent pour un joueur
	* @param	nomJoueur: le nom du joueur dont on souhaite affecter l'argent
	* @param	argentJoueur: la somme d'argent que l'on affecte au joueur
  */
  public void setArgent(String nomJoueur, String argentJoueur){
	super.setProperty(nomJoueur, argentJoueur);
  try{
      OutputStream out = new FileOutputStream(this.fichierScores);
      this.store(out, "Fichier de scores stockant la somme d'argent de chaque joueur");
      out.flush();
      out.close();
  }
    catch(IOException e){}
  }
 
 
 /**
   * Retourne la somme d'argent du joueur dans le fichier
	* @param	nomJoueur: le nom du joueur dont on souhaite connaitre la somme d'argent
	* @return 	la somme du joueur, null si le joueur n'est pas enregistré
  */
  public String getArgent(String nomJoueur){
    String argentJoueur = null;
      try{
	FileInputStream fileReader = new FileInputStream(this.fichierScores);
	this.load(fileReader);
	argentJoueur = super.getProperty(nomJoueur);
	fileReader.close();
      }
      catch (FileNotFoundException e){}
      catch (IOException e){}
    return argentJoueur;
  }
 
 /**
   * efface la somme d'argent du joueur dans le fichier
	* @param	nomJoueur: le nom du joueur dont on souhaite connaitre la somme d'argent
	* @return 	la somme du joueur, null si le joueur n'est pas enregistré
  */
  public void effacerJoueur(String nomJoueur){

      try{
	   OutputStream out = new FileOutputStream(this.fichierScores);
		this.remove(nomJoueur);
      this.store(out, "Fichier de scores stockant la somme d'argent de chaque joueur");
      out.flush();
      out.close();
      }
      catch (FileNotFoundException e){}
      catch (IOException e){}
  }

}