import java.util.Properties;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

/**
  *	Classe : Proprietes
  *	Super classe: Properties (java.util.Properties)
  *	Classes filles: aucune
  *	Interface: VariablesGlobales
  *	Description: classe servant a stocker pour le serveur et les joueurs des parametres de préférence dans un fichier .properties
  * 	Auteur:	Yann Danielou
  */
class Proprietes extends Properties implements VariablesGlobales{

  String fichierProperties;
  
 
  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************
 /**
   * Constructeur de la classe de paramétrage
	* @param	fichierProperties: le nom du fichier de proprietes a créer
	* @param	type: type du fichier propriété, 1 pour le serveur, 2 pour une instance joueur
  */
  public Proprietes(String fichierProperties, int type){
    super();
    this.fichierProperties = fichierProperties + ".properties";

  //On teste l'existence du fichier: s'il n'existe pas, on le crée avec les bonnes valeurs
  File fichier = new File(this.fichierProperties);
  System.out.println("le fichier properties existe: " + fichier.exists());
    if (!fichier.exists()){
      if(type == 1)
	this.initPropertyServer();
      else
	this.initPropertyJoueur();
    }
  }

 
  //*****************************************************************************************
  //                                    Méthodes
  //*****************************************************************************************	
 /**
   * Inscrit ou met à jour une propriete
	* @param	key: le nom du parametre
	* @param	value: la valeur associée au parametre
  */
  public void setPropriete(String key, String value){
	super.setProperty(key, value);
  try{
      OutputStream out = new FileOutputStream(this.fichierProperties);
      this.store(out, "bonjour");
      out.flush();
      out.close();
  }
    catch(IOException e){}
  }
 
 
 /**
   * Inscrit ou met à jour une propriete
	* @param	key: le nom de la propriete que l'on souhaite connaitre
	* @return	value: la valeur de la propriété, null si elle n'existe pas
  */
  public String getPropriete(String key){
    String value = null;
      try{
	FileInputStream fileReader = new FileInputStream(this.fichierProperties);
	this.load(fileReader);
	value = super.getProperty(key);
	fileReader.close();
      }
      catch (FileNotFoundException e){}
      catch (IOException e){}
    return value;
  }


 /**
   * Crée un fichier properties du serveur avec les parametres par defaut
  */
  public void initPropertyServer(){
   //   Log.ajouterTrace("création du fichier de init d'un serveur", INFO);

      this.setProperty("port_socket_1", "8080");
      this.setProperty("port_socket_2", "8081");
      this.setProperty("niveau_debug", "FULL DEBUG");
      this.setProperty("prix_petite_blind", "1");
      this.setProperty("prix_grosse_blind", "2");
      this.setProperty("argent_nouveau_joueur", "200");
      try{
      OutputStream out = new FileOutputStream(this.fichierProperties);
      this.store(out, "fichier de parametrage de l'instance serveur");
      out.flush();
      out.close();
  }
    catch(IOException e){}
  }


 /**
   * Crée un fichier properties de joueur avec les parametres par defaut
  */
  public void initPropertyJoueur(){
      //Log.ajouterTrace("création du fichier de init d'un joueur", INFO);

      this.setProperty("adresse_ip", "192.168.0.1");
      this.setProperty("port_socket", "8080");
      this.setProperty("fenetre_x", "600");
      this.setProperty("fenetre_y", "600");
      this.setProperty("nom_joueur", "joueur ");
      this.setProperty("langue", "fr");
      try{
      OutputStream out = new FileOutputStream(this.fichierProperties);
      this.store(out, "fichier de parametrage d'une instance de joueur");
      out.flush();
      out.close();
  }
    catch(IOException e){}
  }

}