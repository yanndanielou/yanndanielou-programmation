import java.lang.String;
import javax.swing.JOptionPane;

class Controleur implements ProtocoleReseau, VariablesGlobales, VariablesTraduction{

	static String traiterReceptionDonnees(Fenetre fen, String message){
	//On découpe le message de sorte de former une chaine par texte entre espaces
	/*Le message est toujours écrit de la sorte: A B C D.. avec
	A = le type de message (distribution de cartes, demande d'action , ...) : un caractere
	B = nb d'arguments
	C, D, .. = arguments */
	
	//On réccupe le type de message
	String[] messageDecoupe = message.split(SEPARATEUR_MESSAGE);
	
	
	//Teste si l'on initialise l'affichage des cartes
	if(messageDecoupe[0].equals(""+INITIALISER_CARTES)){
	  fen.getTable().initCartes();

	  return ACQUITER_MESSAGE;
	}

	//Teste si l'on vient de recevoir une carte
	if(messageDecoupe[0].equals(""+DONNER_CARTE)){
		System.out.print("carte recue");
		//On regarde le type de carte (commun, joueur, ...)
		int typeCarte = Integer.parseInt(messageDecoupe[1]);
		System.out.print(" de type " + typeCarte);
		//On décode la carte
		int numeroCarte = Integer.parseInt(messageDecoupe[2]);
		fen.getTable().assignerCarte(numeroCarte, typeCarte);

	  return ACQUITER_MESSAGE;
	}

	//Teste si l'on recoit une demande d'action
	else if(messageDecoupe[0].equals(""+DEMANDER_ACTION)){
	//On décode alors la trame recue : DEMANDER_ACTION_PeutRelancer_MiseMinimale_MiseMaximale
	  int peutRelancerInt;
	  int miseMinimale;
	  int miseMaximale;
	  boolean peutRelancerBool;

	  peutRelancerInt = Integer.parseInt(messageDecoupe[1]);

	  if(peutRelancerInt == 0)
	    peutRelancerBool = false;
	  else 
	    peutRelancerBool = true;

	  miseMinimale = Integer.parseInt(messageDecoupe[2]);
	  miseMaximale = Integer.parseInt(messageDecoupe[3]);

	
		fen.activerBouttons(peutRelancerBool, miseMinimale, miseMaximale);

	    return "" + NE_RIEN_ENVOYER;
	}

	//Teste si l'on recoit une somme d'argent a afficher
	else if(messageDecoupe[0].equals(""+AFFICHER_SOMME_ARGENT)){
	//On décode alors la trame recue : le deuxième argument correspond au type d'argent (argent du joueur, du pot, de la mise, ...)
	//												le troisieme argument correspond à la somme d'argent (int)
	  int typeArgent;
	  int sommeArgent;

	  typeArgent = Integer.parseInt(messageDecoupe[1]);
	  sommeArgent = Integer.parseInt(messageDecoupe[2]);

		fen.getTable().setArgent(typeArgent, sommeArgent);

	    return ACQUITER_MESSAGE;
	}

	//Envoie le nom du joueur au serveur
	else if(messageDecoupe[0].equals(""+DEMANDER_NOM_JOUEUR)){
	  return fen.getNomJoueur();
	}

	else if(messageDecoupe[0].equals(""+AJOUTER_TEXTE_SUIVI_PARTIE)){
		String texte;// = 	message.substring(2, message.length());

		if(messageDecoupe[1].equals(""+trad_vousAvez)){
			texte = InstanceJoueur.lang.getTraduction(trad_vousAvez) + " : " + InstanceJoueur.lang.getTraduction(Integer.parseInt(messageDecoupe[2]));
		}
		else if(messageDecoupe[1].equals(""+trad_debutTour)){
			texte = InstanceJoueur.lang.getTraduction(trad_debutTour) + "  " + messageDecoupe[2];
		}
		else if(messageDecoupe[2].equals(""+trad_gagne)){
			texte =  messageDecoupe[1] + " " + InstanceJoueur.lang.getTraduction(trad_gagne) + "  " + messageDecoupe[3];
		}

		else{
			texte = 	message.substring(2, message.length());
		}

		fen.getTable().ajouterTexteSuiviPartie(texte);
	 return ACQUITER_MESSAGE;
	}
	
	//Envoie le nom du joueur au serveur
	else if(messageDecoupe[0].equals(""+DEMANDER_NOM_JOUEUR)){
	  return fen.getNomJoueur();
	}
	
	//Envoie le num de socket au serveur
	else if(messageDecoupe[0].equals(""+DEMANDER_SOCKET_JOUEUR)){
	  return InstanceJoueur.prop.getPropriete("port_socket");
	}

	else if(messageDecoupe[0].equals(""+PAS_ASSEZ_ARGENT)){
		String texte;

		if(fen.demanderRecave())
		     return ""+RECAVER;

		else
		  return ""+FIN_PARTIE;

		
	}
	return NE_RIEN_ENVOYER;
	//System.out.println("voici l'ordre : "+ typeMessage);
	}


}