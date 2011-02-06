 //A partir de java.io
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

//A partir de java.net
import java.net.Socket;
import java.net.ServerSocket;

/**
  *	Classe : Serveur
  *	Super classe: Aucune
  *	Classes filles: aucune
  *	Interface: VariablesGlobales
  *	Description: classe servant au serveur lors de la connexion en sockets. Elle gere l'envoie et la reception de données entre le serveur et les clients
  * 	Auteur:		Yann Danielou
  *	Date:		7 Avril 2009
  */
public class Serveur implements VariablesGlobales{
   	 private static final int port = 8080;
		 private  ServerSocket s;
       private ServerSocket s2;
       private  Socket soc;
       private   Socket soc2;

        // Un BufferedReader permet de lire par ligne.
       private  BufferedReader plec ;
       private  BufferedReader plec2 ;

        // Un PrintWriter possède toutes les opérations print classiques.
        // En mode auto-flush, le tampon est vidé (flush) à l'appel de println.
       private  PrintWriter pred;
       private  PrintWriter pred2 ;


  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************
 /**
   * Constructeur du serveur
   */
		public Serveur(){
		try{
			s = new ServerSocket(Integer.parseInt(InstanceServeur.prop.getPropriete("port_socket_1")));
			s2 = new ServerSocket(Integer.parseInt(InstanceServeur.prop.getPropriete("port_socket_2")));
			soc = s.accept();
			soc2 = s2.accept();
	
			// Un BufferedReader permet de lire par ligne.
			plec = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			plec2 = new BufferedReader(new InputStreamReader(soc2.getInputStream()));
	
			// Un PrintWriter possède toutes les opérations print classiques.
			// En mode auto-flush, le tampon est vidé (flush) à l'appel de println.
			pred = new PrintWriter(	new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())),true);
			pred2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc2.getOutputStream())), true);
			}catch (Exception e){}  
		}



  //*****************************************************************************************
  //                                 	   Méthodes 
  //*****************************************************************************************	
  /**
   * Envoie une string à un client
	* @param port	définit le numéro du port à qui on envoie le message (permet d'envoyer à la bonne personne)
	* @param message	chaine (string) à envoyer au client
   */
	public  void envoyerDonnees(int port, String message){
	//	try {	Thread.sleep(900);} catch (InterruptedException e) {	}
		Log.ajouterTrace("envoyé a " + port + " :" + message, FULL_DEBUG);
		try{
			if(port == 8080)
				pred.println(message);
			else
				pred2.println(message);
		}catch (Exception e){}  

	}
		
  /**
   * Attend de recevoir une string du client
	* @param port	définit le numéro du port que l'on écoute (permet de recevoir le message de la bonne personne)
	* @return 	message: chaine recue du client
   */
	public String recevoirDonnees(int port){
		String messageRecu = "";
		try{
			if(port == 8080)
				messageRecu = plec.readLine();
			
			else
				messageRecu = plec2.readLine();          // lecture du message
		}catch (Exception e){}  

		Log.ajouterTrace("recu de " + port + " :" + messageRecu, FULL_DEBUG);
		return messageRecu;
	
	}

}
