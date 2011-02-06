 //A partir de java.io
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
 //A partir de java.io
import java.net.Socket;

/** Le processus client se connecte au site fourni dans la commande
 *   d'appel en premier argument et utilise le port distant 8080.
 */
public class Client {

  private Socket socket ;
  private BufferedReader plec;
  private PrintWriter pred;

  //*****************************************************************************************
  //                                    Constructeurs
  //*****************************************************************************************
 /**
   * Constructeur du client
	* @param args	tableau de string: contient l'adresse IP du serveur ainsi que le numéro du port
   */
	public Client (String[] args){
		try{
			//int port = Integer.parseInt(args[0]);
        socket = new Socket(InstanceJoueur.prop.getPropriete("adresse_ip"), Integer.parseInt(InstanceJoueur.prop.getPropriete("port_socket")));
		
        System.out.println("SOCKET = " + socket);

        plec = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pred = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		}catch (Exception e){}  
	}
	

  //*****************************************************************************************
  //                                 	   Méthodes 
  //*****************************************************************************************	
   /**
   * Ferme la connexion avec le serveur
   */
   public void fermerClient(){
		try{
			plec.close();
			pred.close();
			socket.close();
		}
		catch (Exception e){}   
   }

  /**
   * Envoie une string au serveur
	* @param message	chaine (string) à envoyer au serveur
   */
   	public void envoyerDonnees(String message){	
	//	System.out.println("envoyé au serveur" + " : " + message);
		try{pred.println(message);}  catch (Exception e){}
	}
	
  /**
   * Attend de recevoir une string du serveur
	* @return 	message: chaine recue du serveur
   */
	public String recevoirDonnees(){
		String message = "";
			try{
				message = plec.readLine();        // lecture du message
			} 
			catch (Exception e){}  

	//	System.out.println("recu du serveur" + " : " + message);
		return message;
	}

}
